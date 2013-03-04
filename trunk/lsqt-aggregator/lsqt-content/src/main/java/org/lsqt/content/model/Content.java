package org.lsqt.content.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;
import org.lsqt.content.web.wicket.util.VarUtil;

/**
 *所有内容抽象，如：新闻、贴子、招聘、文学等信息内容抽象
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public abstract class Content implements java.io.Serializable {
	
	/** 编号 **/
	@Column(name="code",length=10)
	private String code;
	
	/**内容名称**/
	@Column(name="name",length=500)
	private String name;
	
	/**创建时间戳**/
	@Column(name="createTime")
	private String createTime=new DateTime().toString(VarUtil.DEFAULT_DATA_PATTERN);
	
	/**记录修改日期**/
	@Column(name="modifyDate")
	private String modifyDate=new DateTime().toString(VarUtil.DEFAULT_DATA_PATTERN);
	
	/**排序号**/
	@Column(name="orderNum")
	protected Integer orderNum;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getModifyDate()
	{
		return modifyDate;
	}
	public void setModifyDate(String modifyDate)
	{
		this.modifyDate = modifyDate;
	}
	public Integer getOrderNum()
	{
		return orderNum;
	}
	public void setOrderNum(Integer orderNum)
	{
		this.orderNum = orderNum;
	}
	
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
