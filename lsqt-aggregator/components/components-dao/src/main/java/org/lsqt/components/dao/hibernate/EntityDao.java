package org.lsqt.components.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.lsqt.components.dao.IDataAccess;
import org.lsqt.components.dao.suport.DataSet;
import org.lsqt.components.dao.suport.Page;


/**
 * 
 * <pre>
 * 业务名: 针对单个Entity的操作定义,不依赖于具体ORM实现方案
 * 所有实体层数据对象持久化访问接口
 * 功能说明: 
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 *            模型对象
 */
public interface EntityDao<T> extends IDataAccess
{

	/**
	 * 保存一个对象
	 * 
	 * @param e 要保存的实体对象
	 * @return boolean 保存成功返回true
	 */
	public boolean save(T e) ;
	/**
	 * 根据ID执行删除操作
	 */
	/**
	 * @param id
	 *            id
	 * @return delete
	 */

	public boolean deleteById(Serializable id);

	/**
	 * 跟据id查询对象
	 * 
	 * @param id
	 *            对象标识
	 * @return 返回查询的对象值
	 */

	public T findById(Serializable id);

	/**
	 * 更新一个实体对象
	 * 
	 * @param obj
	 *            要更新的实体对象
	 * @return 返回更新后的实体对象
	 */

	public T update(T obj);
	
}
