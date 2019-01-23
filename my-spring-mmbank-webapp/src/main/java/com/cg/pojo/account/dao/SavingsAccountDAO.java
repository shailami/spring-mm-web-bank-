package com.cg.pojo.account.dao;

import java.sql.SQLException;
import java.util.List;

import com.cg.pojo.account.SavingsAccount;
import com.cg.pojo.exception.AccountNotFoundException;

public interface SavingsAccountDAO {
	
	SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException;
	List<SavingsAccount> searchAccountBy(String string) throws ClassNotFoundException, SQLException, AccountNotFoundException;

	SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	boolean deleteAccount(int accountNumber) throws SQLException, ClassNotFoundException, AccountNotFoundException;
	List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException;
	void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException, AccountNotFoundException;
	void commit() throws SQLException;
	List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException;
	SavingsAccount updateAccount(SavingsAccount savingsAccount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException;
	
	
}
