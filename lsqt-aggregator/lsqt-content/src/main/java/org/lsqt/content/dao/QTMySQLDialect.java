package org.lsqt.content.dao;

import org.hibernate.dialect.MySQLDialect;
import java.sql.Types;   
import org.hibernate.Hibernate;   
public class QTMySQLDialect extends MySQLDialect
{
	public QTMySQLDialect(){
		super();
		registerHibernateType(Types.DECIMAL, Hibernate.BIG_DECIMAL.getName());   
	    registerHibernateType(-1, Hibernate.STRING.getName());
	}
}
