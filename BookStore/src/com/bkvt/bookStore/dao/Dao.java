package com.bkvt.bookStore.dao;

import java.util.List;

public interface Dao<T> {
	
	/**
	 * ִ��Insert���������ز����¼��ID
	 * @param sql ��ִ�е�SQL���
	 * @param args ���ռλ���Ŀɱ����
	 * @return �����¼�¼��ID
	 */
	long insert(String sql,Object ...args);
	
	/**
	 * ִ�и��²���
	 * @param sql
	 * @param args
	 */
	void update(String sql,Object ...args);
	
	/**
	 * ִ�е�����¼�Ĳ�ѯ����, �������¼��Ӧ�����һ������
	 * @param sql
	 * @param args
	 * @return
	 */
	T query(String sql,Object ...args);
	
	/**
	 * ִ�ж�����¼�Ĳ�ѯ����, �������¼��Ӧ�����һ�� List
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ŀɱ����
	 * @return: ���¼��Ӧ�����һ�� List
	 */
	List<T> queryForList(String sql, Object ... args);
	
	/**
	 * ִ��һ�����Ի�ֵ�Ĳ�ѯ����, �����ѯĳһ����¼��һ���ֶ�, ���ѯĳ��ͳ����Ϣ, ����Ҫ��ѯ��ֵ
	 * @param sql: ��ִ�е� SQL ���
	 * @param args: ���ռλ���Ŀɱ����
	 * @return: ִ��һ�����Ի�ֵ�Ĳ�ѯ����, �����ѯĳһ����¼��һ���ֶ�, ���ѯĳ��ͳ����Ϣ, ����Ҫ��ѯ��ֵ
	 */
	<V> V getSingleVal(String sql, Object ... args);
	
	
	/**
	 * ִ���������²���
	 * @param sql
	 * @param params
	 */
	void batch(String sql,Object[] ...params);
	

}
