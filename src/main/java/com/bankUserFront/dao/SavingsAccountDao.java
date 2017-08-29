package com.bankUserFront.dao;

import org.springframework.data.repository.CrudRepository;

import com.bankUserFront.domain.SavingsAccount;

public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long>{
		SavingsAccount findByAccountNumber(int accountNumber);
}
