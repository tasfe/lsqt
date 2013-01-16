package org.lsqt.content.web.wicket.content.bean;

import java.util.Collection;

import org.apache.wicket.util.io.IClusterable;
import org.lsqt.components.dao.suport.Page;

public class PagenationBean implements IClusterable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 默认每页显示20条记录*
	 */
	private Integer perPageRecord=20;
	
	/**
	 * 一共多少条记录
	 */
	private Integer totalRecord;
	
	/**
	 * 一共有多少页
	 */
	private Integer totalPage;
	
	/**
	 * 当前页码,默认索引从1开始*
	 */
	private Integer currPageNum=1;
	
	/**
	 * 分页对象承载的数据对象(每一页的数据)
	 */
	private Collection data;
	
	
	/**
	 * 是否有下页
	 */
	private Boolean hasNextPage;
	
	/**
	 * 是否有上页
	 */
	private Boolean hasPreviouPage;
	
	/**
	 * 跳转到第几页
	 */
	private String jumpPage;
	

	public String getJumpPage() {
		return jumpPage;
	}
	public void setJumpPage(String jumpPage) {
		this.jumpPage = jumpPage;
	}
	public Integer getPerPageRecord() {
		return perPageRecord;
	}
	public void setPerPageRecord(Integer perPageRecord) {
		this.perPageRecord = perPageRecord;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurrPageNum() {
		return currPageNum;
	}
	public void setCurrPageNum(Integer currPageNum) {
		this.currPageNum = currPageNum;
	}
	public Boolean getHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public Boolean getHasPreviouPage() {
		return hasPreviouPage;
	}
	public void setHasPreviouPage(Boolean hasPreviouPage) {
		this.hasPreviouPage = hasPreviouPage;
	}
}
