package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Transaction;

public interface TransactionRepository extends JpaRepository<com.example.demo.Entity.Transaction, Long> {

	// List<Transaction> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

	List<Transaction> findByFromUserIdOrToUserId(Long fromUserId, Long toUserId);

	List<Transaction> findByGroupId(Long groupId);
	
	List<Transaction> findByFromUserIdOrToUserIdAndGroupId(Long fromUserId, Long toUserId, Long groupId);
	

}
