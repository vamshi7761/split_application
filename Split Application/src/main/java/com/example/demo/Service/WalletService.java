package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Wallet;
import com.example.demo.Exception.WalletNotFoundException;
import com.example.demo.Repository.WalletRepository;

@Service
public class WalletService {
    
    @Autowired
    private WalletRepository walletrepository;
    
  
    public Wallet createWallet(Long userid) {
        Wallet wallet = walletrepository.findByUserid(userid);
        if (wallet != null) {
            throw new WalletNotFoundException("Wallet already exists for user: " + userid);
        }
        
        Wallet newWallet = new Wallet();
        newWallet.setUserid(userid);
        
        return walletrepository.save(newWallet);
    }
    
    public Wallet addMoney(Long userid, Long amount) {
        Wallet wallet = walletrepository.findByUserid(userid);
        if (wallet == null) {
            throw new WalletNotFoundException("Wallet not found for user: " + userid);
        }
        wallet.setAmount(wallet.getAmount() + amount);
        return walletrepository.save(wallet);
//        // Call the payment gateway service to process the payment
//        boolean paymentSuccess = paymentGatewayService.processPayment(userid, amount);
//        if (paymentSuccess) {
//            wallet.setAmount(wallet.getAmount() + amount);
//            return walletrepository.save(wallet);
//        } else {
//            throw new RuntimeException("Payment processing failed");
//        }
    }
}
