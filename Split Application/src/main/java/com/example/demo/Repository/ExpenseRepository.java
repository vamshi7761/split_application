package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Entity.Expense;

import java.util.*;
public interface ExpenseRepository extends CrudRepository<Expense,Long> {

	List<Expense> findByGroupId(Long groupId);
}
