package com.cg.pojo.account.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.cg.pojo.account.SavingsAccount;
import com.cg.pojo.exception.AccountNotFoundException;

@Repository
@Primary
public class SpringJDBCDao implements SavingsAccountDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public SavingsAccount createNewAccount(SavingsAccount account) throws ClassNotFoundException, SQLException {
		jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)",
				new Object[] { account.getBankAccount().getAccountNumber(),
						account.getBankAccount().getAccountHolderName(), account.getBankAccount().getAccountBalance(),
						account.isSalary(), null, "SA" });

		return account;
	}

	@Override
	public List<SavingsAccount> searchAccountBy(String string)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		// SavingAccountMapper savingAccountMapper=new SavingAccountMapper();
		List<SavingsAccount> list = (List<SavingsAccount>) jdbcTemplate.query(
				"Select * from account where accountHolderName=(?)", new Object[] { string },
				new SavingAccountMapper());
		return list;
	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount savingsAccount)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		jdbcTemplate.update("Update account set accountHolderName=? , isSalary=? where accountNumber=?",
				new Object[] { savingsAccount.getBankAccount().getAccountHolderName(), savingsAccount.isSalary(),
						savingsAccount.getBankAccount().getAccountNumber() });
		return savingsAccount;
	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		SavingsAccount account = jdbcTemplate.queryForObject("Select * from account where accountNumber=?",
				new Object[] { accountNumber }, new SavingAccountMapper());
		return account;
	}

	@Override
	public boolean deleteAccount(int accountNumber)
			throws SQLException, ClassNotFoundException, AccountNotFoundException {
		jdbcTemplate.update("delete from account where accountNumber=?", new Object[] { accountNumber });
		return true;
	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list = jdbcTemplate.query("SELECT * FROM ACCOUNT", new SavingAccountMapper());
		return list;
	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance) throws ClassNotFoundException, SQLException, AccountNotFoundException {
		//SavingsAccount saccount=getAccountById(accountNumber);
		jdbcTemplate.update("Update account set accountBalance=? where accountNumber=?",new Object[] {currentBalance,accountNumber} );
	}

	@Override
	public void commit() throws SQLException {

	}

	@Override
	public List<SavingsAccount> searchAccountBy(int min, int max) throws ClassNotFoundException, SQLException {
		List<SavingsAccount> list = jdbcTemplate.query("SELECT * FROM ACCOUNT where accountBalance BETWEEN ? AND ?",
				new Object[] { min, max }, new SavingAccountMapper());
		return list;
	}

}
