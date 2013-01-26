package org.lsqt.content.dao;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

public class QTNamingStrategy extends ImprovedNamingStrategy implements NamingStrategy
{
	@Override
	public String columnName(String columnName)
	{
		return addUnderscores(columnName).toUpperCase();
		
	}
	
	@Override
	public String tableName(String tableName)
	{
		return addUnderscores(tableName).toUpperCase();
	}
}
