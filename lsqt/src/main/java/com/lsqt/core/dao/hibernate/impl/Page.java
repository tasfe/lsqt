package com.lsqt.core.dao.hibernate.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 通用的分页对象
 * <p>	调用范例 ：
 * 		Page<User> page=new Page<User>(1,1);
		page.addConditions(Condition.getInstance().like("name", "张",MatchWay.ANYWHERE).in("name", new Object[]{"aaaaa"}));
		page.addOrderProperties("name", true);
		page = userService.loadPage(page);
 * </p>
 * @author 袁明敏
 * @see #Page(Integer, Integer)
 * @see #addConditions(Condition)
 * @see #addOrderProperties(String, Boolean)
 * @param <T>
 */
@SuppressWarnings({ "unchecked", "serial" })
public class Page<T> implements Serializable{
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
	 * 分页对象承载的数据对象
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
	 * 多个字段的排序
	 */
	private List orderProperties=new ArrayList(0);
	
	/**
	 * 按哪个属性查询对象
	 */
	private Condition conditions;
	
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
		if(perPageRecord<=0){
			this.perPageRecord=20;
		}else{
			this.perPageRecord=perPageRecord;
		}
		if(currPageNum<=0){
			this.currPageNum=1;
		}else{
			this.currPageNum=currPageNum;
		}
	}

	/**
	 * 添加页对象所承载的实体对象 按多条件进行查找
	 * @param condition 条件对象
	 * */
	public void addConditions(Condition condition){
		this.conditions=condition;
	}
	
	/**
	 * 添加 页对象所承载对象的排序字段集合
	 * @param property 对象的属性
	 * @param isAsc 值为true时升序
	 * @return 返回分页对象
	 * */
	public Page addOrderProperties(String property, Boolean isAsc) {
		List map=new ArrayList(2);
		map.add(property);
		map.add(isAsc);
		this.orderProperties.add(map);
		return this;
	}
	
	public Boolean getHasNextPage() {
		return this.hasNextPage;
	}

	
	public Boolean getHasPreviouPage() {
		return this.hasPreviouPage;
	}


	public Condition getConditions(){
		return this.conditions;
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
	public Collection<T> getData() {
		return data;
	}

	/**
	 * 页对象所承载对象的排序字段集合
	 * @return 返回排序的属性名称集
	 * */
	public List<String> getOrderProperties() {
		return orderProperties;
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
	
	

}
