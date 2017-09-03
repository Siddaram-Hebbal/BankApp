package com.bankUserFront.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bankUserFront.dao.PrimaryTransactionDao;
import com.bankUserFront.dao.UserDao;
import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.PrimaryTransaction;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.domain.SavingsTransaction;
import com.bankUserFront.domain.User;
import com.bankUserFront.service.AccountService;
import com.bankUserFront.service.TransactionService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Principal principal, Model model){
		User user = userDao.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		
		
		List<PrimaryTransaction> primaryTransactionList = transactionService.findPrimaryTransactionList(principal.getName());
		
		model.addAttribute("primaryTransactionList", primaryTransactionList);
		model.addAttribute("primaryAccount", primaryAccount);
		
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Principal principal, Model model){
		User user = userDao.findByUsername(principal.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
		
		model.addAttribute("savingsAccount", savingsAccount);
		model.addAttribute("savingsTransactionList", savingsTransactionList);
		
		return "savingsAccount";
	}
	
	@RequestMapping(value="/deposit", method=RequestMethod.GET)
	public String deposit(Model model){
		model.addAttribute("accountType", "");
		model.addAttribute("amount", "");
		
		return "deposit";
	}
	
	@RequestMapping(value="/deposit", method=RequestMethod.POST)
	public String depositPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal){
		accountService.deposit(accountType, Double.parseDouble(amount), principal);
		
		
		
		return "redirect:/userFront";
	}
	
	@RequestMapping(value = "/withdraw", method  = RequestMethod.GET)
	public String withdraw(Model model){
		model.addAttribute("accountType", "");
		model.addAttribute("amount", "");
		
		return "withdraw";
	}
	
	@RequestMapping(value = "/withdraw", method  = RequestMethod.POST)
	public String withdrawPOST(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal){
		accountService.withdraw(accountType, Double.parseDouble(amount), principal);
		
		return "redirect:/userFront";
	}
	
}
