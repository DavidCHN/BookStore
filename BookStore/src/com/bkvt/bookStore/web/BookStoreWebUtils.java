package com.bkvt.bookStore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bkvt.bookStore.domain.ShoppingCart;

public class BookStoreWebUtils {
	
	public static  ShoppingCart getShoppingCart(HttpServletRequest request) {
		
		HttpSession session=request.getSession();
		
		ShoppingCart shoppingCart=(ShoppingCart) session.getAttribute("ShoppingCart");
		
		if(shoppingCart==null) {
			shoppingCart=new ShoppingCart();
			session.setAttribute("ShoppingCart", shoppingCart);
		}
		
		return shoppingCart;
		
	}

}
