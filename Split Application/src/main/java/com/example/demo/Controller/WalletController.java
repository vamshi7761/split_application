package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Wallet;
import com.example.demo.Service.WalletService;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * Create a new wallet for a user.
     *
     * @param userId ID of the user for whom the wallet is created
     * @return The created wallet
     */
    @PostMapping("/create/{userId}")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId) {
        Wallet wallet = walletService.createWallet(userId);
        return ResponseEntity.ok(wallet);
    }

    /**
     * Add money to a user's wallet.
     *
     * @param userId ID of the user whose wallet is being updated
     * @param amount The amount to be added to the wallet
     * @return The updated wallet
     */
    @PostMapping("/addMoney/{userId}")
    public ResponseEntity<Wallet> addMoney(@PathVariable Long userId, @RequestParam Long amount) {
        Wallet wallet = walletService.addMoney(userId, amount);
        return ResponseEntity.ok(wallet);
    }
}
