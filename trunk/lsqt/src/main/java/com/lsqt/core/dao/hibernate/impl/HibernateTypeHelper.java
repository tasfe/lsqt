package com.lsqt.core.dao.hibernate.impl;


import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;
import org.hibernate.type.Type;



public final class HibernateTypeHelper {
	
	/**
	 * 
	 * 方法说明：处理时间、日期类型
	 *
	 * @param param
	 * @return
	 */
	private static NullableType processTime(Object param){
		//日期、时间
		if(param instanceof java.sql.Date ){
			return Hibernate.DATE;
		}
		if(param instanceof java.sql.Time){
			return Hibernate.TIME;
		}
		if(param instanceof java.sql.Timestamp){
			return Hibernate.TIMESTAMP;
		}
		if(param instanceof java.util.Date){
			return Hibernate.DATE;
		}
		return null;
	}
	
	/**
	 * 
	 * 方法说明：处理字符类型
	 *
	 * @param param
	 * @return
	 */
	private static NullableType processString(Object param){
		if(param instanceof java.lang.Character){
			return Hibernate.CHARACTER;
		}
		if(param instanceof java.lang.String){
			return Hibernate.STRING;
		}
		return null;
	}
	
	/**
	 * 
	 * 方法说明：处理数值型
	 *
	 * @param param
	 * @return
	 */
	private static NullableType processNumeric(Object param){
		
		if(param instanceof java.lang.Byte){
			return Hibernate.BYTE;
		}
		if(param instanceof java.lang.Integer){
			return Hibernate.INTEGER;
		}
		if(param instanceof java.lang.Short){
			return Hibernate.SHORT;
		}
		if(param instanceof java.lang.Float){
			return Hibernate.FLOAT;
		}
		if(param instanceof java.lang.Long){
			return Hibernate.LONG;
		}
		if(param instanceof java.lang.Double){
			return Hibernate.DOUBLE;
		}
		if(param instanceof java.math.BigDecimal){
			return Hibernate.BIG_DECIMAL;
		}
		if(param instanceof java.math.BigInteger){
			return Hibernate.BIG_INTEGER;
		}	
		return null;
	}
	
	
	public static Type filter(Object param) {
		NullableType type = null;

		//参数的字符类型
		type = processString(param);
		if (type != null) {
			return type;
		}

		//参数的日期时间类型
		type = processTime(param);
		if (type != null) {
			return type;
		}
		
		//参数据数值类型
		type = processNumeric(param);
		if (type != null) {
			return type;
		}

		//参数的布尔类型
		if (param instanceof java.lang.Boolean) {
			return Hibernate.BOOLEAN;
		}
		
		if(type == null){
			try {
				throw new TypeException("过滤参数值异常,没有找到JAVA基本数据类型!");
			} catch (TypeException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		
		return type;
	}
}
