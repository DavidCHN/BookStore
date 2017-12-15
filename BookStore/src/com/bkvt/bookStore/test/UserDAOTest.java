package com.bkvt.bookStore.test;

import org.junit.jupiter.api.Test;

import com.bkvt.bookStore.dao.UserDAO;
import com.bkvt.bookStore.dao.impl.UserDAOImpl;
import com.bkvt.bookStore.domain.User;

class UserDAOTest {
	
private UserDAO userDAO = new UserDAOImpl();
	
	@Test
	public void testGetUser() {
		User user = userDAO.getUser("AAA");
		System.out.println(user); 
	}

	

}
