package org.lsqt.components.dto;


/**
 * 通用的分页对象
 */
public class Page {
	private Integer pageSize=20; //默认每页显示20条记录
	
	private Integer totalRecord=0; //一共多少条记录
	
	private Integer totalPage=0;  //一共有多少页
	
	private Integer currentPage=1; //当前页码,默认索引从1开始
	
	private DataTable dataTable; //分页对象承载的数据对象 
	
	private Boolean hasNext=false; //是否有下页
	
	private Boolean hasPreviou=false; //是否有上页
	
	
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
	public Page (Integer perPageRecord,Integer currentPage){
		if (perPageRecord <= 0) {
			this.pageSize = 20;
		} else {
			this.pageSize = perPageRecord;
		}
		if (currentPage <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
	}



	@Override
	public String toString() {
		return "totalPage:"+this.totalPage
				+"  currentPage:"+this.currentPage
				+"  pageSize:"+this.pageSize
				+"  hasNext:"+this.hasNext
				+"  hasPreviou:"+this.hasPreviou
				+"  totalRecord:"+this.totalRecord
				+"  data:"+dataTable.toString();
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public Boolean getHasNext() {
		return hasNext;
	}

	public Boolean getHasPreviou() {
		return hasPreviou;
	}

}
