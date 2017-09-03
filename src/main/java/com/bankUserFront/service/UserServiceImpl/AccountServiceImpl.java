package com.bankUserFront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankUserFront.dao.PrimaryAccountDao;
import com.bankUserFront.dao.SavingsAccountDao;
import com.bankUserFront.dao.UserDao;
import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.PrimaryTransaction;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.domain.SavingsTransaction;
import com.bankUserFront.domain.User;
import com.bankUserFront.service.AccountService;
import com.bankUserFront.service.TransactionService;

@Service
public class AccountServiceImpl implements AccountService{
	
	private static int nextAccountNumber = 1122145;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TransactionService transactionService;

	
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

	@Override
	public void deposit(String accountType, Double amount, Principal prinicpal) {
		User user = userDao.findByUsername(prinicpal.getName()) ;
		if(accountType.equalsIgnoreCase("Primary")){
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
			transactionService.savePrimaryDepositTransaction(primaryTransaction);
			
			
		}
		else if(accountType.equalsIgnoreCase("Savings")){
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
			transactionService.saveSavingsDepositTransaction(savingsTransaction);
		}
	}

	@Override
	public void withdraw(String accountType, Double amount, Principal prinicpal) {
		User user = userDao.findByUsername(prinicpal.getName()) ;
		if(accountType.equalsIgnoreCase("Primary")){
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			
			if((primaryAccount.getAccountBalance().subtract(new BigDecimal(amount))).doubleValue()>=0.0){
				primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			}
			else{
				primaryAccount.setAccountBalance(new BigDecimal(0));
			}
			
			primaryAccountDao.save(primaryAccount);
			
			Date date = new Date();
			
			PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
			transactionService.savePrimaryDepositTransaction(primaryTransaction);
			
			
		}
		else if(accountType.equalsIgnoreCase("Savings")){
			SavingsAccount savingsAccount = user.getSavingsAccount();
			
			if((savingsAccount.getAccountBalance().subtract(new BigDecimal(amount))).doubleValue()>=0.0){
				savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			}
			else{
				savingsAccount.setAccountBalance(new BigDecimal(0));
			}
			
			savingsAccountDao.save(savingsAccount);
			
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from Savings account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
			transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
		}
		
	}

}
