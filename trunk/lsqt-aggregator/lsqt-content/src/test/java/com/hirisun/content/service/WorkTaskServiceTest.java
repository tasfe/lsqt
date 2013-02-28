package com.hirisun.content.service;


import org.joda.time.DateTime;
import org.junit.Test;

import org.lsqt.content.model.WorkIssue;
import org.lsqt.content.model.WorkTask;
import org.lsqt.content.service.WorkIssueService;
import org.lsqt.content.service.WorkTaskService;

import com.hirisun.AbstractTest;

public class WorkTaskServiceTest extends AbstractTest{
	private WorkTaskService workTaskService=getBean(WorkTaskService.class);
	private WorkIssueService workIssueService=getBean(WorkIssueService.class);

	
	@Test
	public void testCRUD(){
		workTaskService.deleteAll();
		workIssueService.deleteAll();
		
		WorkTask workTask=new WorkTask();
		workTask.setCloseTime(System.currentTimeMillis());
		
		
		workTask.setCreateTime(new DateTime().toString("yyyy-MM-dd hh:mm:ss S"));
		
		workTask.setIsEmailTo(true);
		workTask.setName("华润N31项目");
		workTask.setPriority("高");
		
		workTaskService.save(workTask);
		workTaskService.findById(workTask.getId());
		
		workTask.setName("华润N31项目二期");
		workTask=workTaskService.update(workTask);
		
		workTaskService.deleteById(workTask.getId());
	}
	
	/**
	 * 方法说明：在某个任务下添加多个Issues
	 */
	private static String taskId=null;
	@Test
	public void testRelationShips(){
		workTaskService.deleteAll();
		workIssueService.deleteAll();
		
		WorkTask workTask=new WorkTask();
		workTask.setCloseTime(System.currentTimeMillis());
		
		
		workTask.setCreateTime(new DateTime().toString("yyyy-MM-dd hh:mm:ss S"));
		
		workTask.setIsEmailTo(true);
		workTask.setName("河南四期项目");
		workTask.setPriority("高");
	
		
		WorkIssue IssueOne=new WorkIssue();
		IssueOne.setBeginTime(System.currentTimeMillis());
		
		IssueOne.setCreateTime(new DateTime().toString("yyyy-MM-dd hh:mm:ss S"));
		
		IssueOne.setEndTime(System.currentTimeMillis());
		IssueOne.setName("河南四期需求分析");
		IssueOne.setPriority("高");
		IssueOne.setWorkTask(workTask);
		
		
		workTask.getIssues().add(IssueOne);
		workTaskService.save(workTask);
		
		taskId=workTask.getId();
		
		workTaskService.deleteById(taskId) ;
	}
}
