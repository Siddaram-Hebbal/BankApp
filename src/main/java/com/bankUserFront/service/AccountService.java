package com.bankUserFront.service;

import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.SavingsAccount;

public interface AccountService {

	 PrimaryAccount createPrimaryAccount();
	 SavingsAccount createSavingsAccount();
	
	
}
