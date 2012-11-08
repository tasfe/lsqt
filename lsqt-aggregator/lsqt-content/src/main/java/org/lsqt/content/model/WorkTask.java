package org.lsqt.content.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * <pre>
 * 功能说明: 工作内任务类定义,一个任务分解成多个Issues
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
@Table(name="tb_task")
public class WorkTask extends Content {
	/**任务关闭时间**/
	@Column(name="closeTime")
	private Long closeTime;

	/**任务进度信息是否邮件发送**/
	@Column(name="isEmailTo")
	private Boolean isEmailTo;
	
	/** 任务优先级*/
	@Column(name="priority",length=4)
	private String priority;
	
	@OneToMany(mappedBy = "workTask", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<WorkIssue> Issues=new HashSet<WorkIssue>();
	
	public Long getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Long closeTime) {
		this.closeTime = closeTime;
	}

	public Boolean getIsEmailTo() {
		return isEmailTo;
	}

	public void setIsEmailTo(Boolean isEmailTo) {
		this.isEmailTo = isEmailTo;
	}

	public Set<WorkIssue> getIssues() {
		return Issues;
	}

	public void setIssues(Set<WorkIssue> issues) {
		Issues = issues;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
}
