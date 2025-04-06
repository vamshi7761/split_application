package com.example.demo.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Payment;
import com.example.demo.Entity.Transaction;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Wallet;
import com.example.demo.Exception.WalletNotFoundException;
import com.example.demo.Repository.PaymentRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.WalletRepository;

@Service
public class TransactionService {

	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private UserRepository u;
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private WalletRepository walletRepository;
	
	//@Autowired
	//private EmailServic e;

	public List<Transaction> getUserTransactions(Long userId) {
		// Fetch transactions where the user is either the sender or the receiver
		return transactionRepository.findByFromUserIdOrToUserId(userId, userId);
	}

	public List<Transaction> getUserTransactionsbasedontheGroup(Long userId, Long groupId) {
		// Fetch transactions where the user is either the sender or the receiver
		return transactionRepository.findByFromUserIdOrToUserIdAndGroupId(userId, userId, groupId);
	}

	public Payment makePayment(Long transactionId, Double amount) {
		
		
		Transaction transactio = transactionRepository.getReferenceById(transactionId);
		Wallet fromUserWallet = walletRepository.findByUserid(transactio.getFromUserId());
		Wallet toUserWallet = walletRepository.findByUserid(transactio.getToUserId());
		Optional<User> us = u.findById(transactio.getFromUserId());
		User uo = us.get();
		String name =uo.getUserId()+" " +uo.getName();

		if (fromUserWallet == null) {
			throw new WalletNotFoundException("wallet not found for user: " + transactio.getFromUserId());
		}
		if (toUserWallet == null) {
			throw new WalletNotFoundException("wallet not found for user: " + transactio.getToUserId());
		}

		// Check if the fromUser has sufficient balance
		if (fromUserWallet.getAmount() < amount) {
			throw new WalletNotFoundException(
					"Insufficient balance in the wallet of user: " + transactio.getFromUserId());
		}
		// Create a new payment record
		Payment payment = new Payment();

		payment.setFromUserId(transactio.getFromUserId());
		payment.setToUserId(transactio.getToUserId());
		payment.setAmount(amount);
		payment.setDate(LocalDate.now());
		payment.setStatus("Completed");
		Payment savedPayment = paymentRepository.save(payment);

		// Update the transaction status to "Completed"
		Transaction transaction = transactionRepository.findById(transactionId)
				.orElseThrow(() -> new WalletNotFoundException("Transaction not found"));

		transaction.setStatus("Completed");
		transactionRepository.save(transaction);
		
		
		//e.sendConfirmationEmail(uo.getEmail(), name,amount,String.valueOf(transactionId));
		// Delete the transaction if status is "Completed"
		

		return savedPayment;
	}

	public Map<String, List<Transaction>> getUserSendingAndReceivingTransactions(Long userId) {
		// Fetch all transactions involving the user
		List<Transaction> allTransactions = transactionRepository.findByFromUserIdOrToUserId(userId, userId);

		// Separate transactions into sending and receiving
		List<Transaction> sendingTransactions = allTransactions.stream()
				.filter(transaction -> transaction.getFromUserId().equals(userId)).toList();

		List<Transaction> receivingTransactions = allTransactions.stream()
				.filter(transaction -> transaction.getToUserId().equals(userId)).toList();

		Map<String, List<Transaction>> transactionsMap = new HashMap<>();
		transactionsMap.put("sending", sendingTransactions);
		transactionsMap.put("receiving", receivingTransactions);

		return transactionsMap;
	}

	public Map<Long, Double> calculateUserBalances(Long targetuserId) {
		List<Transaction> relevantTrasactions = transactionRepository.findByFromUserIdOrToUserId(targetuserId,
				targetuserId);
		Map<Long, Double> userBalances = new HashMap<>();

		for (Transaction transaction : relevantTrasactions) {
			Long fromUserId = transaction.getFromUserId();
			Long toUserId = transaction.getToUserId();
			Double amount = transaction.getAmount();
			// Calculate balance adjustment based on whether the targetUserId is sending or
			// receiving
			if (fromUserId.equals(targetuserId)) { // Target user is the sender
				userBalances.put(toUserId, userBalances.getOrDefault(toUserId, (double) 0) - amount);
			} else if (toUserId.equals(targetuserId)) {
				// Target user is the receiver
				userBalances.put(fromUserId, userBalances.getOrDefault(fromUserId, 0.0) + amount);
			}
		}

		// Filter out zero balances
		userBalances.entrySet().removeIf(entry -> entry.getValue() == 0.0);
		return userBalances;
	}

	public void optimizeSettlements(Long groupId) {
		// Step 1: Get all transactions for the group
		List<Transaction> transactions = transactionRepository.findByGroupId(groupId);

		// Step 2: Calculate net balances for each user
		Map<Long, Double> netBalances = new HashMap<>();
		for (Transaction transaction : transactions) {
			if(transaction.getStatus() == "Pending")
			{
			Long fromUserId = transaction.getFromUserId();
			Long toUserId = transaction.getToUserId();
			Double amount = transaction.getAmount();

			netBalances.put(fromUserId, netBalances.getOrDefault(fromUserId, 0.0) - amount);
			netBalances.put(toUserId, netBalances.getOrDefault(toUserId, 0.0) + amount);
			}
		}

		// Step 3: Categorize users into senders and receivers
		List<Long> senders = new ArrayList<>();
		List<Long> receivers = new ArrayList<>();

		for (Map.Entry<Long, Double> entry : netBalances.entrySet()) {
			
			Long userId = entry.getKey();
			Double balance = entry.getValue();
			if (balance < 0) {
				senders.add(userId);
			} else if (balance > 0) {
				receivers.add(userId);
			}
		}

		// Step 4: Create optimized transactions
		List<Transaction> newTransactions = new ArrayList<>();

		while (!senders.isEmpty() && !receivers.isEmpty()) {
			Long sender = senders.remove(0);
			Long receiver = receivers.remove(0);

			Double senderAmount = -netBalances.get(sender); // Amount sender needs to pay
			Double receiverAmount = netBalances.get(receiver); // Amount receiver needs to receive

			Double settlementAmount = Math.min(senderAmount, receiverAmount);

			// Create a new transaction
			Transaction newTransaction = new Transaction();
			newTransaction.setFromUserId(sender);
			newTransaction.setToUserId(receiver);
			newTransaction.setAmount(settlementAmount);
			newTransaction.setDate(LocalDate.now());
			newTransaction.setStatus("Pending");
			newTransaction.setGroupId(groupId);
			newTransaction.setExpenseId((long) -1); // Set to a relevant value if necessary

			newTransactions.add(newTransaction);

			// Update net balances
			netBalances.put(sender, senderAmount - settlementAmount);
			netBalances.put(receiver, receiverAmount - settlementAmount);

			// Handle remaining balances
			if (netBalances.get(sender) == 0) {
				netBalances.remove(sender);
			}
			if (netBalances.get(receiver) == 0) {
				netBalances.remove(receiver);
			}
		}

		// Step 5: Save new transactions and delete old ones
		transactionRepository.saveAll(newTransactions);
		transactionRepository.deleteAll(transactions);
	}
	public void deleteTransactionById(Long transactionId) {
	    // Find the transaction by ID and delete it if it exists
	    Transaction transaction = transactionRepository.findById(transactionId)
	            .orElseThrow(() -> new WalletNotFoundException("Transaction not found with ID: " + transactionId));
	    transactionRepository.delete(transaction);
	}

	

}
