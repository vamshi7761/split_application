package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Expense;
import com.example.demo.Entity.Transaction;
import com.example.demo.Repository.ExpenseRepository;
import com.example.demo.Repository.TransactionRepository;
import com.example.demo.Repository.UserRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenserepository;
	
	@Autowired
	private TransactionRepository transactionrepository;
	
	@Autowired
	private UserRepository userrepository;
	

	public List<Expense> getExpensesByGroupId(Long groupId)
	{
		return expenserepository.findByGroupId(groupId);
	}

	

	public Expense createExpense(Expense expense, Long groupId) {
		// TODO Auto-generated method stub
				Expense savedexpense = expenserepository.save(expense);
				Set<Long> groupMemberIds = userrepository.findUserIdsByGroupId(groupId);
				
				double amountOwed = savedexpense.getAmount()/groupMemberIds.size();
				
				for(Long userId : groupMemberIds)
				{
					if(!userId.equals(savedexpense.getPaidBy()))
					{
						Transaction transaction = new Transaction();
						
						  transaction.setExpenseId(savedexpense.getExpenseId());
			                transaction.setGroupId(groupId);
			                transaction.setFromUserId(userId);
			                transaction.setToUserId(savedexpense.getPaidBy());
			                transaction.setAmount(amountOwed);
			                transaction.setDate(LocalDate.now());
			                transaction.setStatus("Pending"); // Default status
			                transactionrepository.save(transaction);
					}
				}
				return savedexpense;
	}
	
}
