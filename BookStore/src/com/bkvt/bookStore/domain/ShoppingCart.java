package com.bkvt.bookStore.domain;



import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ShoppingCart {
	
	private Map<Integer, ShoppingCartItem> books=new HashMap<>();
	
	/**
	 * �޸�ָ�������������
	 */
	
	public void updateItemQuantity(Integer id,int quantity) {
		ShoppingCartItem scis=books.get(id);
		if(scis!=null) {
			scis.setQuantity(quantity);
		}
	}
	
	/**
	 * �Ƴ�ָ���Ĺ�����
	 * @param id
	 */
	public void removeItem(Integer id) {
		books.remove(id);
	}
	
	/**
	 * ��չ��ﳵ
	 */
	public void clear() {
		books.clear();
	}
	
	/**
	 * �жϹ��ﳵ�Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty() {
		return books.isEmpty();
	}
	
	/**
	 * ��ȡ���ﳵ�е����е� ShoppingCartItem ��ɵļ���
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	public float getTotalMoney() {
		float total=0;
		for(ShoppingCartItem sci:getItems()) {
			total+=sci.getItemMoney();
		}
		return total;
	}
	
	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	
	public int getBookNumber() {
		int total=0;
		for(ShoppingCartItem sci: books.values()){
			total += sci.getQuantity();
		}
		
		return total;
		
	}
	
	/**
	 * ���ﳵ�����һ����Ʒ		
	 * @param book
	 */
	public void addBook(Book book){
		//1. ��鹺�ﳵ����û�и���Ʒ, ����, ��ʹ������ +1, ��û��, 
		//�´������Ӧ�� ShoppingCartItem, ��������뵽 books ��
		ShoppingCartItem sci = books.get(book.getId());
		
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getId(), sci);
		}else{
			sci.increment();
		}
	}

}
