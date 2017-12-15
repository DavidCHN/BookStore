package com.bkvt.bookStore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.bkvt.bookStore.dao.AccountDAO;
import com.bkvt.bookStore.dao.BookDAO;
import com.bkvt.bookStore.dao.TradeDAO;
import com.bkvt.bookStore.dao.TradeItemDAO;
import com.bkvt.bookStore.dao.UserDAO;
import com.bkvt.bookStore.dao.impl.AccountDAOIml;
import com.bkvt.bookStore.dao.impl.BookDAOImpl;
import com.bkvt.bookStore.dao.impl.TradeDAOImpl;
import com.bkvt.bookStore.dao.impl.TradeItemDAOImpl;
import com.bkvt.bookStore.dao.impl.UserDAOImpl;
import com.bkvt.bookStore.domain.Book;
import com.bkvt.bookStore.domain.ShoppingCart;
import com.bkvt.bookStore.domain.ShoppingCartItem;
import com.bkvt.bookStore.domain.Trade;
import com.bkvt.bookStore.domain.TradeItem;
import com.bkvt.bookStore.web.CriteriaBook;
import com.bkvt.bookStore.web.Page;

public class BookService {
	
	private BookDAO bookDAO=new BookDAOImpl();
	
	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}
	
	public Page<Book> getPage(CriteriaBook criteriaBook){
		return bookDAO.getPage(criteriaBook);
	}
	
	public Boolean addToCart(int id,ShoppingCart sc) {
		
		Book book=bookDAO.getBook(id);
		if(book!=null) {
			sc.addBook(book);
			return true;
		}
		
		return false;
	}
	
	public void updateItemQuantity(ShoppingCart sc,int id,int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	public void removeItemFromShoppingCart(ShoppingCart sCart,int id) {
		sCart.removeItem(id);
	}
	
	public void clearShoppingCart(ShoppingCart sCart) {
		sCart.clear();
	}
	
	private AccountDAO accountDAO = new AccountDAOIml();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	public void cash(ShoppingCart shoppingCart, String username,
			String accountId) {
		
		//1. 更新 mybooks 数据表相关记录的 salesamount 和 storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		//int i = 10 / 0;
		
		//2. 更新 account 数据表的 balance
		accountDAO.updateBalance(Integer.parseInt(accountId), shoppingCart.getTotalMoney());
		
		//3. 向 trade 数据表插入一条记录
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDAO.getUser(username).getUserId());
		tradeDAO.insert(trade);
		
		//4. 向 tradeitem 数据表插入 n 条记录
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		
		//5. 清空购物车
		shoppingCart.clear();
	}

}
