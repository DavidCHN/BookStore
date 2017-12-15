package com.bkvt.bookStore.service;

import java.util.Iterator;
import java.util.Set;

import com.bkvt.bookStore.dao.BookDAO;
import com.bkvt.bookStore.dao.TradeDAO;
import com.bkvt.bookStore.dao.TradeItemDAO;
import com.bkvt.bookStore.dao.UserDAO;
import com.bkvt.bookStore.dao.impl.BookDAOImpl;
import com.bkvt.bookStore.dao.impl.TradeDAOImpl;
import com.bkvt.bookStore.dao.impl.TradeItemDAOImpl;
import com.bkvt.bookStore.dao.impl.UserDAOImpl;
import com.bkvt.bookStore.domain.Trade;
import com.bkvt.bookStore.domain.TradeItem;
import com.bkvt.bookStore.domain.User;

public class UserService {
	
	private UserDAO userDAO=new UserDAOImpl();
	
	private TradeDAO tradeDAO=new TradeDAOImpl();
	
	private TradeItemDAO tradeItemDAO=new TradeItemDAOImpl();
	
	private BookDAO bookDAO=new BookDAOImpl();
	
	public User getUserByUserName(String userName) {
		return userDAO.getUser(userName);
	}
	
	public User getUserWithTrades(String username) {
		
		User user=userDAO.getUser(username);
		
		if(user==null) {
			return null;
		}
		
		int userId=user.getUserId();
		
		Set<Trade> trades=tradeDAO.getTradesWithUserId(userId);
		
		if(trades!=null) {
			Iterator<Trade> tradeIt=trades.iterator();
			while (tradeIt.hasNext()) {
				Trade trade = (Trade) tradeIt.next();
				int tradeId=trade.getTradeId();
				Set<TradeItem> items=tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				
				if(items!=null) {
					for(TradeItem item:items) {
						item.setBook(bookDAO.getBook(item.getBookId()));
					}
					
					if(items!=null&&items.size()!=0) {
						trade.setItems(items);
					}
				}
				
				if(items == null || items.size() == 0){
					tradeIt.remove();	
				}
				
			}
		}
		
		if(trades != null && trades.size() != 0){
			user.setTrades(trades);			
		}
		
		return user;
		
	}
	
	

}
