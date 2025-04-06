package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Expense;
import com.example.demo.Service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    /**
     * Create a new expense and distribute it among group members.
     *
     * @param expense The expense to create
     * @param groupId ID of the group where the expense is created
     * @return The created expense
     */
    @PostMapping("/create")
    public Expense createExpense(@RequestBody Expense expense, @RequestParam Long groupId) {
//        return expenseService.createExpense(expense, groupId);
    	return expenseService.createExpense(expense, groupId);
    }

    /**
     * Retrieve all expenses for a specific group.
     *
     * @param groupId ID of the group
     * @return List of expenses for the specified group
     */
    @GetMapping("/group/{groupId}")
    public List<Expense> getExpensesByGroupId(@PathVariable Long groupId) {
        return expenseService.getExpensesByGroupId(groupId);
    }
}
