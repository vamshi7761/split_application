package com.example.demo.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Service.EmailServic;

public class EmailController {

	private EmailServic emailService;
	
	
	 
    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email) {
        try {
            emailService.verifyEmailAddress(email);
            return "Verification email sent to: " + email;
        } catch (Exception e) {
            return "Failed to send verification email: " + e.getMessage();
        }
    }

}
