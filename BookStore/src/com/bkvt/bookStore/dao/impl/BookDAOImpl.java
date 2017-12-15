package com.bkvt.bookStore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bkvt.bookStore.dao.BookDAO;
import com.bkvt.bookStore.domain.Book;
import com.bkvt.bookStore.domain.ShoppingCartItem;
import com.bkvt.bookStore.web.CriteriaBook;
import com.bkvt.bookStore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingDate, "
				+ "salesAmount, storeNumber, remark FROM mybooks WHERE id = ?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page<Book> page = new Page<>(cb.getPageNo());

		page.setTotalItemNumber(getTotalBookNumber(cb));
		// 校验 pageNo 的合法性
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 5));

		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());

	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id, author, title, price, publishingDate, "
				+ "salesAmount, storeNumber, remark FROM mybooks " + "WHERE price >= ? AND price <= ? " + "LIMIT ?, ?";
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, " + "storeNumber = storeNumber - ? "
				+ "WHERE id = ?";

		Object[][] params = null;
		params = new Object[items.size()][3];
		List<ShoppingCartItem> shoppingCartItems = new ArrayList<>(items);
		for (int i = 0; i < items.size(); i++) {
			params[i][0] = shoppingCartItems.get(i).getQuantity();
			params[i][1] = shoppingCartItems.get(i).getQuantity();
			params[i][2] = shoppingCartItems.get(i).getBook().getId();

		}
		batch(sql, params);

	}

}
