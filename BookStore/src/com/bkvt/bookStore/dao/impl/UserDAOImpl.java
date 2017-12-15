package com.bkvt.bookStore.dao.impl;

import com.bkvt.bookStore.dao.UserDAO;
import com.bkvt.bookStore.domain.User;

public class UserDAOImpl  extends BaseDAO<User> implements UserDAO{

	@Override
	public User getUser(String username) {
		String sql = "SELECT userId, username, accountId " +
				"FROM userinfo WHERE username = ?";
		return query(sql, username); 
	}

}
