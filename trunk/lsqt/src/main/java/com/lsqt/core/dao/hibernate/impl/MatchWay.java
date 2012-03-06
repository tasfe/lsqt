package com.lsqt.core.dao.hibernate.impl;

/**
 * 模糊查询关键字的配方式
 * ANYWHERE 相当于SQL： "like '%keyword%'"
 * END 相当于SQL: "like '%keyword'";
 * EXACT 相当于SQL: "like 'keyword'"
 * 
 * @author 袁明敏
 * @since 1.0
 * @see com.lsqt.core.dao.hibernate.impl.hirisun.hea.common.model.Condition
 * 
 *
 */
public enum MatchWay {
	/**
	 *ANYWHERE 相当于SQL： "like '%keyword%'"
	 */
	 ANYWHERE,
	 
	 /**
	  *End 相当于SQL: "like '%keyword'";
	  */
	 END,
	 
	 /**
	  * EXACT 相当于SQL: "like 'keyword'"
	  */
	 EXACT,
	 
	 /**
      * END 相当于SQL: "like 'keyword%'";
	  */
	 START
}
