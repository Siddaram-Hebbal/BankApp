package com.bankUserFront.service;

import java.security.Principal;

import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.SavingsAccount;

public interface AccountService {

	 PrimaryAccount createPrimaryAccount();
	 SavingsAccount createSavingsAccount();
	 void deposit(String accountType, Double amount, Principal prinicpal);
	 void withdraw(String accountType, Double amount, Principal prinicpal);
	
	
}
