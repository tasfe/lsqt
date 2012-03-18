package com.lsqt.modules.resource.model;

import java.io.Serializable;

public class Resource implements ResourceType,Serializable{
	private String id;
	/**父类资源**/
	private String pid;
	/**资源名称**/
	private String resourceName;
	/**资源小图标,如指定用户人物图标**/
	private String resourceIcon;
	/**资源类型**/
	private String resourceType;
	/**是否启用**/
	private Boolean isEnable;
	/**资源描述**/
	private String description;
	/**是否有下级结点**/
	private Boolean hasChildNode;
	/**资源层级数**/
	private Integer levelNum;
	/**资源管理地址**/
	private String manageUrl;
	/**资源管理地址打开的目标**/
	private String openTarget;
}
