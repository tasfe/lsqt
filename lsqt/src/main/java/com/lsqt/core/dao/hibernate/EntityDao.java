package com.lsqt.core.dao.hibernate;

import java.io.Serializable;

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
public interface EntityDao<T> {

	/***
	 * 根据id删除对象
	 * 
	 * @param id
	 *            对象标识id
	 * @return boolean
	 */
	boolean deleteById(Serializable id);

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 *            对象标识id
	 * @return 对象模型
	 */
	T findById(Serializable id);

	/**
	 * 保存对象
	 * 
	 * @param e
	 *            保存实体对象
	 * @return boolean
	 */
	boolean save(T e);

	/**
	 * 更新实体对象
	 * 
	 * @param e
	 *            需更新的实体模型
	 * @return 返回更新后的实体模型
	 */
	T update(T e);

}
