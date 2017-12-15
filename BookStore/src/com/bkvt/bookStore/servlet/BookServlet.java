package com.bkvt.bookStore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bkvt.bookStore.domain.Account;
import com.bkvt.bookStore.domain.Book;
import com.bkvt.bookStore.domain.ShoppingCart;
import com.bkvt.bookStore.domain.ShoppingCartItem;
import com.bkvt.bookStore.domain.User;
import com.bkvt.bookStore.service.AccountService;
import com.bkvt.bookStore.service.BookService;
import com.bkvt.bookStore.service.UserService;
import com.bkvt.bookStore.web.BookStoreWebUtils;
import com.bkvt.bookStore.web.CriteriaBook;
import com.bkvt.bookStore.web.Page;
import com.google.gson.Gson;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookServlet() {
		super();

	}

	private AccountService accountService = new AccountService();
	private BookService bookService = new BookService();
	private UserService userService = new UserService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");

		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");

		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}

		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}

		CriteriaBook criteriaBook = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(criteriaBook);

		request.setAttribute("bookpage", page);

		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}

	protected void getBook(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String bookId = request.getParameter("id");
		int id = -1;
		Book book = null;
		try {
			id = Integer.parseInt(bookId);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (id > 0) {
			book = bookService.getBook(id);
		}

		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);

	}

	protected void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String bookId = request.getParameter("id");
		int id = -1;
		Boolean flag = false;

		id = Integer.parseInt(bookId);

		if (id > 0) {
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			flag = bookService.addToCart(id, sc);
		}

		if (flag) {
			getBooks(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath() + "/error-1.jsp");
	}

	protected void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request, response);
	}

	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");

		ShoppingCart sCart = BookStoreWebUtils.getShoppingCart(request);

		int id = -1;
		int quantity = -1;

		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (id > 0 && quantity > 0) {
			bookService.updateItemQuantity(sCart, id, quantity);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", sCart.getBookNumber());
		result.put("totalMoney", sCart.getTotalMoney());

		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);

	}

	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idString = request.getParameter("id");

		int id = -1;
		try {
			id = Integer.parseInt(idString);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ShoppingCart sCart = BookStoreWebUtils.getShoppingCart(request);
		sCart.removeItem(id);

		if (sCart.isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);

	}

	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ShoppingCart sCart = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sCart);
		request.getRequestDispatcher("/WEB-INF/pages/emptyCart.jsp").forward(request, response);
	}

	protected void cash(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");

		StringBuffer errors = validateFormField(username, accountId);
		
		if(errors.toString().equals("")) {
			errors=validateUser(username,accountId);
			if(errors.toString().equals("")) {
				errors = validateBookStoreNumber(request);
				
				//库存验证通过
				if(errors.toString().equals("")){
					errors = validateBalance(request, accountId);
				}
			}
		}
		
		if(!errors.toString().equals("")){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		
		//验证通过执行具体的逻辑操作
		bookService.cash(BookStoreWebUtils.getShoppingCart(request), username, accountId); 
		response.sendRedirect(request.getContextPath() + "/success.jsp");
	}

	private StringBuffer validateBalance(HttpServletRequest request, String accountId) {
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		Account account = accountService.getAccount(Integer.parseInt(accountId));
		if(cart.getTotalMoney() > account.getBalance()){
			errors.append("余额不足!");
		}
		
		return errors;
	}

	private StringBuffer validateBookStoreNumber(HttpServletRequest request) {
		StringBuffer errors = new StringBuffer("");
		ShoppingCart cart = BookStoreWebUtils.getShoppingCart(request);
		
		for(ShoppingCartItem sci: cart.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
		}
		
		return errors;
	}

	private StringBuffer validateUser(String username, String accountId) {
		boolean flag = false;
		User user = userService.getUserByUserName(username);
		if(user != null){
			int accountId2 = user.getAccountId();
			if(accountId.trim().equals("" + accountId2)){
				flag = true;
			}
		}
		
		StringBuffer errors2 = new StringBuffer("");
		if(!flag){
			errors2.append("用户名和账号不匹配");
		}
		
		return errors2;
	}

	private StringBuffer validateFormField(String username, String accountId) {
		StringBuffer errors = new StringBuffer("");

		if (username == null || username.trim().equals("")) {
			errors.append("用户名不能为空<br>");
		}

		if (accountId == null || accountId.trim().equals("")) {
			errors.append("账号不能为空");
		}

		return errors;
	}

}
