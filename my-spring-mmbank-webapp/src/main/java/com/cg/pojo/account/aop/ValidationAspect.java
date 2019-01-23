package com.cg.pojo.account.aop;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.cg.pojo.account.SavingsAccount;

@Component
@Aspect
public class ValidationAspect {
	Logger logger = Logger.getLogger(ValidationAspect.class.getName());

	/*
	 * @Before("execution(* com.cg.pojo.account.service.*.*(..))") public
	 * void log1() { System.out.println("inside logger before");
	 * logger.info("Before-Entering the selected method"); }
	 * 
	 * 
	 * @After("execution(* com.cg.pojo.account.service.*.*(..))") public void
	 * log2() { logger.info("After-Entering the selected method"); }
	 */

	@Around("execution(* com.cg.pojo.account.service.SavingsAccountServiceImpl.withdraw(..))")
	public void log3(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("Before-Entering the selected method");
		Object[] params = pjp.getArgs();
		SavingsAccount savingAccount = (SavingsAccount) params[0];
		double amount = (Double) params[1];
		if (savingAccount.getBankAccount().getAccountBalance()>amount){
			if (amount > 0) {
				pjp.proceed();
			} else {
				logger.info("please enter a valid amount");
			}		
		} else {
			logger.info("INSUFFICIENT FUNDS EXCEPTION");
		}
		logger.info("After-Entering the selected method");
		
	}

	@Around("execution(* com.cg.pojo.account.service.SavingsAccountServiceImpl.deposit(..))")
	public void log4(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("Before-Entering the selected method");
		Object[] params = pjp.getArgs();

		double amount = (Double) params[1];
		if (amount > 0) {
			pjp.proceed();
		} else {
			logger.info("please enter a valid amount");
		}
		logger.info("After-Entering the selected method");
	}

	/*
	 * @Around("execution(* com.cg.pojo.account.service.SavingsAccountServiceImpl.getAllSavingsAccount(..))"
	 * ) public void log4(ProceedingJoinPoint pjp) throws Throwable {
	 * 
	 * pjp.proceed(); System.out.println("successfull"); }
	 */
}
