package com.bankUserFront.controller;

import java.security.Principal;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bankUserFront.domain.PrimaryAccount;
import com.bankUserFront.domain.SavingsAccount;
import com.bankUserFront.domain.User;
import com.bankUserFront.service.TransactionService;
import com.bankUserFront.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value="betweenAccounts", method = RequestMethod.GET)
	public String betweenAccounts(Model model){
		model.addAttribute("transferTo", "");
		model.addAttribute("transferFrom", "");
		model.addAttribute("amount", "");
		
		return "betweenAccounts";
	}
	
	@RequestMapping(value="betweenAccounts", method = RequestMethod.POST)
	public String betweenAccountsPost(@ModelAttribute("transferTo") String transferTo, @ModelAttribute("transferFrom") String transferFrom, 
			@ModelAttribute("amount") String amount, Principal principal)
	throws Exception{
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
		
		return "redirect:/userFront";
	}
	
	
}
