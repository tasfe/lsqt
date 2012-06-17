package com.hirisun.content.service;


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
		workTask.setContent("任务内容");
		workTask.setContentKeys("abc,def,efg");
		workTask.setCreateTime(System.currentTimeMillis());
		workTask.setDescription("描述");
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
		workTask.setContent("河南电力公司项目任务内容");
		workTask.setContentKeys("abc,def,efg");
		workTask.setCreateTime(System.currentTimeMillis());
		workTask.setDescription("描述");
		workTask.setIsEmailTo(true);
		workTask.setName("河南四期项目");
		workTask.setPriority("高");
	
		
		WorkIssue IssueOne=new WorkIssue();
		IssueOne.setBeginTime(System.currentTimeMillis());
		IssueOne.setContent("需求分析");
		IssueOne.setContentKeys("河南需求分析");
		IssueOne.setCreateTime(System.currentTimeMillis());
		IssueOne.setDescription("河南需求分析描述");
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
