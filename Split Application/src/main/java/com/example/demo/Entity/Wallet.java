package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//@GeneratedValue(strategy = GenerationType.IDENTITY)
@Entity
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long walletid;

	private long Amount;

	
    private Long userid;

	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Wallet()
	{
		this.Amount=0;
	}
	public long getWalletid() {
		return walletid;
	}

	public void setWalletid(long walletid) {
		this.walletid = walletid;
	}

	public long getAmount() {
		return Amount;
	}

	public void setAmount(long amount) {
		Amount = amount;
	}


	

	

}
