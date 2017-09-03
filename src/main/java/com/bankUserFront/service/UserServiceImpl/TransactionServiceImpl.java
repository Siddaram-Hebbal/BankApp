package com.bankUserFront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.PrimaryTransaction;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.domain.SavingsTransaction;
import com.bankUserFront.domain.User;
import com.bankUserFront.service.AccountService;
import com.bankUserFront.service.TransactionService;
import com.bankUserFront.service.UserService;
import com.bankUserFront.dao.PrimaryAccountDao;
import com.bankUserFront.dao.PrimaryTransactionDao;
import com.bankUserFront.dao.SavingsAccountDao;
import com.bankUserFront.dao.SavingsTransactionDao;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SavingsAccountDao savingsAccountDao;
	
	@Autowired
	private PrimaryAccountDao primaryAccountDao;
	
	@Autowired
	private PrimaryTransactionDao primaryTransactionDao;
	
	@Autowired
	private SavingsTransactionDao savingsTransactionDao;
	
	public List<PrimaryTransaction> findPrimaryTransactionList(String username){
        
		User user = userService.findByUsername(username);
        List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();

        return primaryTransactionList;
    }

    public List<SavingsTransaction> findSavingsTransactionList(String username) {
        User user = userService.findByUsername(username);
        List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();

        return savingsTransactionList;
    }

    public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionDao.save(primaryTransaction);
    }

    public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDao.save(savingsTransaction);
    }
    
    public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
        primaryTransactionDao.save(primaryTransaction);
    }

    public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
        savingsTransactionDao.save(savingsTransaction);
    }

	
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
			PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
		if(transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Primary")){
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			primaryAccountDao.save(primaryAccount);
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to Primary", "Transfer between accounts", "Finished", Double.parseDouble(amount) , savingsAccount.getAccountBalance(), savingsAccount);
			PrimaryTransaction primaryTransaction =  new PrimaryTransaction(date, "Transfer from Savings", "Transfer between accounts", "Finished", Double.parseDouble(amount) , primaryAccount.getAccountBalance(), primaryAccount);
			savingsTransactionDao.save(savingsTransaction);
			primaryTransactionDao.save(primaryTransaction);
			}
		else if(transferFrom.equalsIgnoreCase("Primary") && transferTo.equalsIgnoreCase("Savings")){
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccountDao.save(savingsAccount);
			primaryAccountDao.save(primaryAccount);
			Date date = new Date();
			
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer from Primary", "Transfer between accounts", "Finished", Double.parseDouble(amount) , savingsAccount.getAccountBalance(), savingsAccount);
			PrimaryTransaction primaryTransaction =  new PrimaryTransaction(date, "Transfer to Savings", "Transfer between accounts", "Finished", Double.parseDouble(amount) , primaryAccount.getAccountBalance(), primaryAccount);
			savingsTransactionDao.save(savingsTransaction);
			primaryTransactionDao.save(primaryTransaction);
		}
		else{
			throw new Exception("Invalid Transfer");
		}
		
	}

	
	
	
}
