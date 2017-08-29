package com.bankUserFront.service.UserServiceImpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankUserFront.dao.PrimaryAccountDao;
import com.bankUserFront.dao.SavingsAccountDao;
import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	private static int nextAccountNumber = 1122145;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;

	
	public PrimaryAccount createPrimaryAccount() {
		PrimaryAccount primaryAccount = new PrimaryAccount();
		primaryAccount.setAccountBalance(new BigDecimal(0.0));
		primaryAccount.setAccountNumber(accountGen());
		
		primaryAccountDao.save(primaryAccount);
		
		return primaryAccountDao.findByAccountNumber(primaryAccount.getAccountNumber());
		
	}

	@Override
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(accountGen());
		
		savingsAccountDao.save(savingsAccount);
		
		return savingsAccountDao.findByAccountNumber(savingsAccount.getAccountNumber());
		
	}
	
	int accountGen(){
		return ++nextAccountNumber;
	}

}
