package com.lsqt.components.dto;

import java.util.Collection;
import java.util.Collections;


/**
 * 通用的分页对象
 */
public class Page {
	/**
	 * 默认每页显示20条记录*
	 */
	private Integer perPageRecord=20;
	
	/**
	 * 一共多少条记录
	 */
	private Integer totalRecord=0;
	
	/**
	 * 一共有多少页
	 */
	private Integer totalPage=0;
	
	/**
	 * 当前页码,默认索引从1开始*
	 */
	private Integer currPageNum=1;
	
	/**
	 * 分页对象承载的数据对象
	 */
	private DataTable dataTable=new DataTable();
	
	
	/**
	 * 是否有下页
	 */
	private Boolean hasNextPage=false;
	
	/**
	 * 是否有上页
	 */
	private Boolean hasPreviouPage=false;
	
	
	
	/**
	 * 私有构造方法
	 */
	@SuppressWarnings("unused")
	private Page(){}
	
	/**
	 * 初使化页对象必须的属性
	 * @param perPageRecord 每页显示的记录条数
	 * @param currPageNum 当前页码,索引从1开始
	 * */
	public Page (Integer perPageRecord,Integer currPageNum){
		if (perPageRecord <= 0) {
			this.perPageRecord = 20;
		} else {
			this.perPageRecord = perPageRecord;
		}
		if (currPageNum <= 0) {
			this.currPageNum = 1;
		} else {
			this.currPageNum = currPageNum;
		}
	}



	public Boolean getHasNextPage() {
		return this.hasNextPage;
	}

	
	public Boolean getHasPreviouPage() {
		return this.hasPreviouPage;
	}

	/**
	 * 得到当前页码(注:索引从1开始)
	 * @return 返回当前页码
	 * */
	public Integer getCurrPageNum() {
		return currPageNum;
	}
	

	
	/**
	 * 页对象承载的数据集
	 * @return 返回分页对象承载的数据集
	 * */
	public DataTable getDataTable() {
		return dataTable;
	}


	/**
	 * 方法说明： 得到每页显示的记录数
	 * @return 每页显示的记录数
	 */
	public Integer getPerPageRecord() {
		return perPageRecord;
	}
	
	/**
	 * 
	 * 方法说明：得到总记录数
	 *
	 * @return 总记录数
	 */
	public Integer getTotalRecord() {
		return totalRecord;
	}


	public Integer getTotalPage() {
		return totalPage;
	}

	public void setPerPageRecord(Integer perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public void setCurrPageNum(Integer currPageNum) {
		this.currPageNum = currPageNum;
	}
	
	
	@Override
	public String toString() {
		return "totalPage:"+this.totalPage
				+"  currPageNum:"+this.currPageNum
				+"  perPageRecord:"+this.perPageRecord
				+"  hasNextPage:"+this.hasNextPage
				+"  hasPreviouPage:"+this.hasPreviouPage
				+"  data:"+dataTable;
	}
}
