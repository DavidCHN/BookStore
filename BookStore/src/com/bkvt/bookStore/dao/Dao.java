package com.bkvt.bookStore.dao;

import java.util.List;

public interface Dao<T> {
	
	/**
	 * 执行Insert操作，返回插入记录的ID
	 * @param sql 待执行的SQL语句
	 * @param args 填充占位符的可变参数
	 * @return 插入新纪录的ID
	 */
	long insert(String sql,Object ...args);
	
	/**
	 * 执行更新操作
	 * @param sql
	 * @param args
	 */
	void update(String sql,Object ...args);
	
	/**
	 * 执行单条记录的查询操作, 返回与记录对应的类的一个对象
	 * @param sql
	 * @param args
	 * @return
	 */
	T query(String sql,Object ...args);
	
	/**
	 * 执行多条记录的查询操作, 返回与记录对应的类的一个 List
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 与记录对应的类的一个 List
	 */
	List<T> queryForList(String sql, Object ... args);
	
	/**
	 * 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
	 * @param sql: 待执行的 SQL 语句
	 * @param args: 填充占位符的可变参数
	 * @return: 执行一个属性或值的查询操作, 例如查询某一条记录的一个字段, 或查询某个统计信息, 返回要查询的值
	 */
	<V> V getSingleVal(String sql, Object ... args);
	
	
	/**
	 * 执行批量更新操作
	 * @param sql
	 * @param params
	 */
	void batch(String sql,Object[] ...params);
	

}
