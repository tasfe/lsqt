package com.lsqt.modules.resource.model;

import java.io.Serializable;

public class TableAttribute implements Serializable {
	private String id;
	/**引用的表的主键**/
	private String pk;
	/****/
	private String attrName;
	private String simpleValue;
	private String lobValue ;
}
