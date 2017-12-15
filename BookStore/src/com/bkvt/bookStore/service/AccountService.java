package com.bkvt.bookStore.service;

import com.bkvt.bookStore.dao.AccountDAO;
import com.bkvt.bookStore.dao.impl.AccountDAOIml;
import com.bkvt.bookStore.domain.Account;

public class AccountService {
private AccountDAO accountDAO = new AccountDAOIml();
	
	public Account getAccount(int accountId){
		return accountDAO.get(accountId);
	}

}
