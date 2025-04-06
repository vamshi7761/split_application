package com.example.demo.Service;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayService {

//    @Value("${stripe.apiKey}")
//    private String apiKey;
//
//    public boolean processPayment(Long amount, Long amount2) {
//        Stripe.apiKey = apiKey;
//
//        // Convert amount to the smallest currency unit (e.g., paise for INR)
//        long amountInPaise = amount * 100;
//
//        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//                .setAmount(amountInPaise) // Amount in smallest unit (e.g., paise for INR)
//                .setCurrency("inr")       // Currency code for Indian Rupee
//                .addPaymentMethodType("card") // Specifies card as the payment method
//                .setConfirm(true)         // Automatically confirm the payment
//                .build();
//
//        try {
//            PaymentIntent intent = PaymentIntent.create(params);
//            return intent.getStatus().equals("succeeded");
//        } catch (StripeException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
