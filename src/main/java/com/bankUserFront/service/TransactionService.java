package com.bankUserFront.service;

import java.util.List;

import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.PrimaryTransaction;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.domain.SavingsTransaction;

public interface TransactionService {

	List<PrimaryTransaction> findPrimaryTransactionList(String username);

    List<SavingsTransaction> findSavingsTransactionList(String username);

    void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction);

    void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);
    
    void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction);
    void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction);
    
    void betweenAccountsTransfer(String transferFrom,String transferTo, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception;
    
    
}
