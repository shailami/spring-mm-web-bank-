package com.cg.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.pojo.account.SavingsAccount;
import com.cg.pojo.account.service.SavingsAccountService;
import com.cg.pojo.exception.AccountNotFoundException;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private SavingsAccountService savingsAccountService;
	private boolean flag = false;

	@RequestMapping(value = "/getAll")
	public String getAllAccount(Model model) throws ClassNotFoundException, SQLException {
		System.out.println("hello fdhgyjty world");
		List<SavingsAccount> list = savingsAccountService.getAllSavingsAccount();
		System.out.println(list);
		model.addAttribute("accounts", list);
		return "/AccountDetails";
	}

	@RequestMapping(value = "/addNew")
	public String addNewAccount(Map map) {
		map.put("savings", new SavingsAccount());
		return "/AddNewAccount";
	}

	@RequestMapping(value = "/createNewAccount", method = RequestMethod.POST)
	public String createNewAccount(@RequestParam("name") String name, @RequestParam("bal") int bal,
			@RequestParam("sal") String salary/*
												 * @ModelAttribute("savings") SavingsAccount savings, BindingResult
												 * result
												 */ ) throws ClassNotFoundException, SQLException {
		System.out.println(name);
		System.out.println(bal);
		System.out.println(salary);
		// System.out.println(savings.getBankAccount().getAccountHolderName());
		savingsAccountService.createNewAccount(name, bal, Boolean.parseBoolean(salary));
		return "redirect:getAll";
	}

	@RequestMapping(value = "/closeAccount")
	public String closeAccount() {
		return "remove";
	}

	@RequestMapping(value = "/deleteAccount")
	public String delete(@RequestParam("accountNumber") int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		savingsAccountService.deleteAccount(accountNumber);
		return "getAll";
	}


	@RequestMapping("/withdraw")
	public String withdrawForm() {
		return "withdraw";
	}

	@RequestMapping("/amountWithdrawn")
	public String withdraw(Model model, @RequestParam("accountNumber") int accountNumber,
			@RequestParam("amount") double amount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount savingAccount = savingsAccountService.getAccountById(accountNumber);
		savingsAccountService.withdraw(savingAccount, amount);
		SavingsAccount savingAccount1 = savingsAccountService.getAccountById(accountNumber);
		model.addAttribute("account", savingAccount1);
		return "AccountDetails";
	}

	@RequestMapping("/deposit")
	public String depositForm() {
		return "deposit";
	}

	@RequestMapping("/amountDeposited")
	public String deposit(Model model, @RequestParam("accountNumber") int accountNumber,
			@RequestParam("amount") double amount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount savingAccount = savingsAccountService.getAccountById(accountNumber);
		savingsAccountService.deposit(savingAccount, amount);
		SavingsAccount savingAccount1 = savingsAccountService.getAccountById(accountNumber);
		model.addAttribute("account", savingAccount1);
		return "AccountDetails";
	}

	@RequestMapping("/fundTransfer")
	public String fundTransferForm() {
		return "fundtransfer";
	}

	@RequestMapping("/amountTransferred")
	public String fundTransfer(Model model, @RequestParam("senderAccountNumber") int accountNumber1,
			@RequestParam("receiverAccountNumber") int accountNumber2, @RequestParam("amount") double amount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount sender = savingsAccountService.getAccountById(accountNumber1);
		SavingsAccount receiver = savingsAccountService.getAccountById(accountNumber2);
		savingsAccountService.fundTransfer(sender, receiver, amount);
		return "successfull";
	}

	@RequestMapping("/update")
	public String updateForm() {
		return "update";
	}

	@RequestMapping("/updateAccount")
	public String update(Model model, @RequestParam("accountNumber") int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount savingAccount = savingsAccountService.getAccountById(accountNumber);
		model.addAttribute("accountSingle", savingAccount);
		return "AccountDetails";
	}

	@RequestMapping("/updateWithValues")
	public String updateWithValues(Model model, @RequestParam("accountNumber") int accountNumber,
			@RequestParam("isSalary") String isSalary, @RequestParam("accountHolderName") String accountHolderName)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		// model.addAttribute("account",account);
		return "AccountDetails";
	}

	@RequestMapping("/sorting")
	public String sorting() {
		return "redirect:getAll";
	}

	@RequestMapping("/sortByName")
	public String sortByName(Model model) throws ClassNotFoundException, SQLException {
		flag = !flag;
		List<SavingsAccount> inputList = new ArrayList<SavingsAccount>();

		inputList = savingsAccountService.getAllSavingsAccount();
		Collections.sort(inputList, new Comparator<SavingsAccount>() {

			@Override
			public int compare(SavingsAccount arg0, SavingsAccount arg1) {
				int result = arg0.getBankAccount().getAccountHolderName()
						.compareTo(arg1.getBankAccount().getAccountHolderName());
				if (flag == true)
					return result;
				else
					return -result;
			}
		});
		model.addAttribute("accounts", inputList);
		return "AccountDetails";
	}

	@RequestMapping("/sortByBalance")
	public String sortByBalance(Model model) throws ClassNotFoundException, SQLException {
		flag = !flag;
		Collection<SavingsAccount> accounts = savingsAccountService.getAllSavingsAccount();

		Set<SavingsAccount> accountSet = new TreeSet<>(new Comparator<SavingsAccount>() {

			@Override
			public int compare(SavingsAccount arg0, SavingsAccount arg1) {
				int result = arg0.getBankAccount().getAccountBalance() > arg1.getBankAccount().getAccountBalance() ? 1
						: -1;
				// if (count % 2 != 0)
				if (flag == true)
					return result;
				else
					return -result;
			}

		});
		accountSet.addAll(accounts);
		model.addAttribute("accounts", accountSet);
		return "AccountDetails";
	}

	@RequestMapping("/sortBySalary")
	public String sortBySalary(Model model) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> inputList1 = new ArrayList<SavingsAccount>();
		flag = !flag;
		inputList1 = savingsAccountService.getAllSavingsAccount();
		Collections.sort(inputList1, new Comparator<SavingsAccount>() {
			@Override
			public int compare(SavingsAccount arg0, SavingsAccount arg1) {
				int result = String.valueOf(arg0.isSalary()).equals("true") ? 1 : -1;
				if (flag == true)
					return result;
				else
					return -result;
			}
		});
		model.addAttribute("accounts", inputList1);
		return "AccountDetails";
	}

	@RequestMapping("/getCurrentBalance")
	public String getCurrentBalanceForm() {
		return "getCurrentBalance";
	}

	@RequestMapping("/currentBalance")
	public String getCurrentBalance(Model model, @RequestParam("accountNumber") int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount savingAccount = savingsAccountService.getAccountById(accountNumber);
		model.addAttribute("account", savingAccount);
		return "AccountDetails";
	}
	
	@RequestMapping("/searchAccount")
	public String getSearchAccountForm() {
		return "SearchForm";	
	}
	
	@RequestMapping("/searchByAccountNumber")
	public String searchAccountByNumberForm() {
		return "search";
	}

	@RequestMapping("/searchValidate")
	public String validateSearchByAccountNumber(Model model, @RequestParam("accountNumber") String accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount savingAccount = savingsAccountService.getAccountById(Integer.parseInt(accountNumber));
		model.addAttribute("account", savingAccount);
		return "AccountDetails";
	}
	
	@RequestMapping("/searchByAccountHolderName")
	public String getSearchAccountByNameForm() {
		return "searchByAccountHolderNameForm";
	}
	
	@RequestMapping("/searchNameValidate")
	public String SearchAccountByName(Model model,@RequestParam("accountHolderName")String accountHolderName) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		List list= savingsAccountService.searchAccountBy(accountHolderName);
		model.addAttribute("accounts", list);
		return "AccountDetails";
	}
	
	@RequestMapping("/searchBySalaryRange")
	public String getSearchAccountBySalaryRange() {
		return "searchBySalaryRangeForm";
	}
	
	@RequestMapping("/searchSalaryRangeValidate")
	public String SearchAccountBySalaryRange(Model model,@RequestParam("minimum")String minimum,@RequestParam("maximum")String maximum) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		List list= savingsAccountService.searchAccountBy(Integer.parseInt(minimum),Integer.parseInt(maximum));
		model.addAttribute("accounts", list);
		return "AccountDetails";
	}
	
	
	
	
}
