package org.lsqt.components.dao.suport;


/**
 * <pre>
 * 业务名:数据库结果集接口定义
 * 功能说明:
 * 编写日期:2011-4-21
 * 作者:袁明敏
 * 
 * 历史记录
 * 修改日期：2011-4-21
 * 修改人：袁明敏
 * 修改内容：
 * </pre>
 *  @param <T>
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
