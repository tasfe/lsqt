package org.lsqt.content.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <pre>
 * 功能说明: 一个IT项目的定义
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
@Table(name="tb_project")
public class Project extends Content{
	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
}
