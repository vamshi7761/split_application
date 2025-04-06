package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Payment save(com.example.demo.Entity.Payment payment);
}