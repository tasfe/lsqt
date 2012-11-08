package org.lsqt.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
}
