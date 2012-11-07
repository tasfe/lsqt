package org.lsqt.content.service.impl;


import javax.annotation.Resource;

import org.lsqt.content.dao.WorkTaskDao;
import org.lsqt.content.model.WorkTask;
import org.lsqt.content.service.WorkTaskService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 功能说明: 工作任务业务类，如：某个任务下的多个Issues
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 	修改日期：2011-5-13
 *    	修改人：袁明敏
 *   	修改内容：
 * </pre>
 */
@Service
public class WorkTaskServiceImpl implements WorkTaskService {
	
	private WorkTaskDao workTaskDao;
	@Resource
	public void setWorkTaskDao(WorkTaskDao workTaskDao) {
		this.workTaskDao = workTaskDao;
	}
	public boolean save(WorkTask workTask){
		return workTaskDao.save(workTask);
	}

	public WorkTask update(WorkTask workTask){
		return workTaskDao.update(workTask);
	}

	public boolean deleteById(String id){
		return workTaskDao.deleteById(id);
	}

	public WorkTask findById(String id) {
		return workTaskDao.findById(id);
	}
	
	public void deleteAll() {
		workTaskDao.deleteAll();
	}

}
