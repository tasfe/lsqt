package org.lsqt.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * 功能说明: 任务下的Issue定义
 * 编写日期:	2012-6-17
 * 作者:	袁明敏
 * 
 * 历史记录
 * 	修改日期：
 *    	修改人：袁明敏
 *    	修改内容：
 * </pre>
 */
@Entity
@Table(name="tb_issue")
public class WorkIssue extends Content{
	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name="beginTime")
	private Long beginTime;
	
	@Column(name="endTime")
	private Long endTime;
	
	/** 优先级*/
	@Column(name="priority",length=4)
	private String priority;
	
	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	@JoinColumn(name = "task_id", referencedColumnName = "id")
	private WorkTask workTask;

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public WorkTask getWorkTask() {
		return workTask;
	}

	public void setWorkTask(WorkTask workTask) {
		this.workTask = workTask;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
