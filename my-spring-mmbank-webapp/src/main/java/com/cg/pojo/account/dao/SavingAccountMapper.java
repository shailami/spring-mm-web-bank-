package com.cg.pojo.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cg.pojo.account.SavingsAccount;

public class SavingAccountMapper implements RowMapper<SavingsAccount> {

	@Override
	public SavingsAccount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
	//	SavingsAccount savingAccount=new SavingsAccount();
		int accountNumber = resultSet.getInt(1);
		  String accountHolderName = resultSet.getString(2); double accountBalance =
		  resultSet.getDouble(3); boolean salary = resultSet.getBoolean(4);
		  SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
		  return savingsAccount;
	}




}
