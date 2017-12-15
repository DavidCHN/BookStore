package com.bkvt.bookStore.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.bkvt.bookStore.dao.BookDAO;
import com.bkvt.bookStore.dao.impl.BookDAOImpl;
import com.bkvt.bookStore.db.JDBCUtils;
import com.bkvt.bookStore.domain.Book;
import com.bkvt.bookStore.web.ConnectionContext;
import com.bkvt.bookStore.web.CriteriaBook;
import com.bkvt.bookStore.web.Page;

class BookDAOTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
private BookDAO bookDAO = new BookDAOImpl();
	
	@Test
	public void testGetBook() {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		
		Book book = bookDAO.getBook(5);
		System.out.println(book); 
	}

	@Test
	public void testGetPage() {
		CriteriaBook  cb = new CriteriaBook(50, 60, 90);
		Page<Book> page = bookDAO.getPage(cb);
		
		System.out.println("pageNo: " + page.getPageNo());
		System.out.println("totalPageNumber: " + page.getTotalPageNumber());
		System.out.println("list: " + page.getList());
		System.out.println("prevPage: " + page.getPrevPage());
		System.out.println("nextPage: " + page.getNextPage()); 
	}

}
