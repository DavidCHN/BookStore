package com.bkvt.bookStore.dao;

import com.bkvt.bookStore.domain.User;

public interface UserDAO {
	
	public abstract User getUser(String username);

}
