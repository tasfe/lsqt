package com.lsqt.core.dao.hibernate.impl;


/**
 * 
 * @author 操作数据结果集接口定义
 *
 * @param <T>
 */
public interface IDataResultSet<T> extends Iterable<T> {
	
	public boolean add(T e);
	
	public void add(int index ,T e);
	
	T get(int index);
	
	boolean isEmpty();
	
	public T remove(int index);
	
	public boolean remove(T e);
	
	public int size();
	
	T[] toArray();
}
