package com.cg.pojo.account.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.pojo.account.SavingsAccount;
import com.cg.pojo.account.dao.SavingsAccountDAO;
import com.cg.pojo.account.factory.AccountFactory;
import com.cg.pojo.exception.AccountNotFoundException;
import com.cg.pojo.exception.InsufficientFundsException;
import com.cg.pojo.exception.InvalidInputException;

@Service
@Transactional(rollbackForClassName= {"Throwable"})
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;
	
	public SavingsAccountServiceImpl() {
		factory = AccountFactory.getInstance();
		//savingsAccountDAO = new SavingsAccountDAOImpl();
		//this.savingsAccountDAO=savingsAccountDAO;
	}


	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		
		System.out.println("in service");
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		savingsAccountDAO.createNewAccount(account);
		System.out.println("out service");

		return account;
	}


	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}


	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		if (amount > 0) {
			double currentBalance = account.getBankAccount().getAccountBalance();
			currentBalance += amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		}else {
			throw new InvalidInputException("Invalid Input Amount!");
		}
	}

	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		double currentBalance = account.getBankAccount().getAccountBalance();
		if (amount > 0 && currentBalance >= amount) {
			currentBalance -= amount;
			savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);
			//savingsAccountDAO.commit();
		} else {
			throw new InsufficientFundsException("Invalid Input or Insufficient Funds!");
		}
	}


	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		deposit(receiver, amount);
			withdraw(sender, amount);
	}


	public SavingsAccount updateAccount(SavingsAccount savingsAccount) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		// TODO Auto-generated method stub
		return  savingsAccountDAO.updateAccount(savingsAccount) ;
	}

	
	public SavingsAccount getAccountById(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	public boolean deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.deleteAccount(accountNumber);
	}


	public List<SavingsAccount>  searchAccountBy(String string) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		
		return savingsAccountDAO.searchAccountBy(string) ;
	}


	public List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return savingsAccountDAO.searchAccountBy(min,max) ;
	}
	

	public List<SavingsAccount> sort(int choice, final int order)
			throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list = getAllSavingsAccount();
		switch (choice) {
		case 1:
			Collections.sort(list, new Comparator<SavingsAccount>() {

			
				public int compare(SavingsAccount account1,
						SavingsAccount account2) {
					if (account1.getBankAccount().getAccountNumber() > account2
							.getBankAccount().getAccountNumber())
						if (order == 1)
							return 1;
						else
							return -1;
					else if (account1.getBankAccount().getAccountNumber() == account2
							.getBankAccount().getAccountNumber())
						return 0;
					else if (order == 1)
						return -1;
					else
						return 1;
				}
			});
			break;
		case 2:
			Collections.sort(list, new Comparator<SavingsAccount>() {

			
				public int compare(SavingsAccount account1,
						SavingsAccount account2) {
					int result = (account1.getBankAccount()
							.getAccountHolderName().compareTo(account2
							.getBankAccount().getAccountHolderName()));
					result = order == 1 ? result : -result;
					return result;
				}
			});

			break;
		case 3:
			Collections.sort(list, new Comparator<SavingsAccount>() {

		
				public int compare(SavingsAccount account1,
						SavingsAccount account2) {
					if (account1.getBankAccount().getAccountBalance() > account2
							.getBankAccount().getAccountBalance())
						if (order == 1)
							return 1;
						else
							return -1;
					else if (account1.getBankAccount().getAccountBalance() == account2
							.getBankAccount().getAccountBalance())
						return 0;
					else if (order == 1)
						return -1;
					else
						return 1;
				}
			});
			break;
		}
		System.out.println(list);
		return list;
	}

}
