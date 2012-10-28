package org.lsqt.components.dao;

public interface IDataAccess {
	public int executeUpdate(final String sql, final Object... params);

	public Object executeQuery(final String sql,final Object... params);
}
