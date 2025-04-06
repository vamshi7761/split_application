package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

	@jakarta.persistence.Id
	@jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long expenseId;

	@Column(name = "group_id", nullable = false)
	private Long groupId;

	@Column(name = "paid_by", nullable = false)
	private Long paidBy;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private String description;

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(Long paidBy) {
		this.paidBy = paidBy;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
