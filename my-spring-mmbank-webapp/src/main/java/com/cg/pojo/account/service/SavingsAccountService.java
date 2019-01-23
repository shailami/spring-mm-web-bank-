package com.cg.pojo.account.service;

import java.sql.SQLException;
import java.util.List;

import com.cg.pojo.account.SavingsAccount;
import com.cg.pojo.exception.AccountNotFoundException;

public interface SavingsAccountService {

	SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary) throws ClassNotFoundException, SQLException;

	SavingsAccount updateAccount(SavingsAccount savingsAccount) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	List<SavingsAccount> searchAccountBy(String string) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	boolean deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;

	void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	List<SavingsAccount>sort(int choice, int order) throws ClassNotFoundException, SQLException;

	 	List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException;
}











