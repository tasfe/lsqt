package com.lsqt.modules.resource.model;

import java.io.Serializable;
import java.util.Date;

public class News implements ResourceType ,Serializable{
	private String id;
	/**新闻关键字**/
	private String newsKey;
	
	/**新闻标题**/
	private String title;
	/**内容**/
	private String content;
	/**是否禁用**/
	private Boolean isEnable;
	
	/**是否发布**/
	private Boolean isPublish;
	/**发布用户**/
	private String publishUser;
	/**发布日期**/
	private Date publishDate;
	/**更新日期**/
	private Date lastUpdatDate;
	/**该新闻的评论计数**/
	private Integer commentCnt;
	
	/**动态网页地址**/
	private String dynamicHttpUrl;
	/**静态网页地址**/
	private String staticHttpUrl;
	/**是否启用动态地址访问**/
	private Boolean isDynamic;
}
