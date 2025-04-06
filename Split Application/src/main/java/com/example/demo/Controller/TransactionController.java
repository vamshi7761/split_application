package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Payment;
import com.example.demo.Entity.Transaction;
import com.example.demo.Service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    // Changed from @PathVariable to @RequestParam
    @GetMapping("/delete")
    public ResponseEntity<String> deleteTransaction(@RequestParam Long transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.ok("Transaction deleted successfully with ID: " + transactionId);
    }


    /**
     * Get all transactions for a user where they are either the sender or receiver.
     *
     * @param userId ID of the user
     * @return List of transactions involving the user
     */
    @GetMapping("/getUserTransactions/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Long userId) {
       // return transactionService.getUserTransactions(userId);
    	return transactionService.getUserTransactions(userId);
    }
    @GetMapping("/getUserTransactions/{userId}/{groupId}")
    public List<Transaction> getUserTransactionsbasedongroup(@PathVariable Long userId,@PathVariable Long groupId) {
       // return transactionService.getUserTransactions(userId);
    	return transactionService.getUserTransactionsbasedontheGroup(userId,groupId);
    }

    /**
     * Get separate lists of sending and receiving transactions for a user.
     *
     * @param userId ID of the user
     * @return Map with sending and receiving transactions
     */
    @GetMapping("/getUserSendingAndReceivingTransactions/{userId}/details")
    public Map<String, List<Transaction>> getUserSendingAndReceivingTransactions(@PathVariable Long userId) {
        return transactionService.getUserSendingAndReceivingTransactions(userId);
    }

    /**
     * Make a payment for a specific transaction.
     *
     * @param transactionId ID of the transaction to settle
     * @param fromUserId    ID of the sender
     * @param toUserId      ID of the receiver
     * @param amount        Amount to be paid
     * @return Payment record
     */
    @PostMapping("/pay")
    public Payment makePayment(
            @RequestParam Long transactionId,
            
            @RequestParam Double amount) {

        return transactionService.makePayment(transactionId, amount);
    }
    
    @GetMapping("/calculateUserBalances/{targetUserId}")
    public ResponseEntity<Map<Long, Double>> getUserBalances(@PathVariable Long targetUserId) {
        Map<Long, Double> balances = transactionService.calculateUserBalances(targetUserId);
        return ResponseEntity.ok(balances);
    }
    
    @PostMapping("/optimize/{groupId}")
    public ResponseEntity<String> optimizeSettlements(@PathVariable Long groupId) {
        transactionService.optimizeSettlements(groupId);
        return ResponseEntity.ok("Settlements optimized for group ID: " + groupId);
    }
}
