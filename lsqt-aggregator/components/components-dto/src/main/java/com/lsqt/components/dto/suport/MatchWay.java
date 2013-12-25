package com.lsqt.components.dto.suport;

/**
 * 模糊查询关键字的配方式
 * ANYWHERE 相当于SQL： "like '%keyword%'"
 * END 相当于SQL: "like '%keyword'";
 * EXACT 相当于SQL: "like 'keyword'"
 * 
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
