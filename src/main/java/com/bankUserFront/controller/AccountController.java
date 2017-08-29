package com.bankUserFront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bankUserFront.dao.UserDao;
import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.domain.User;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/primaryAccount")
	public String primaryAccount(Principal principal, Model model){
		User user = userDao.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		model.addAttribute("primaryAccount", primaryAccount);
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount(Principal principal, Model model){
		User user = userDao.findByUsername(principal.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();
		model.addAttribute("savingsAccount", savingsAccount);
		
		return "savingsAccount";
	}
}
