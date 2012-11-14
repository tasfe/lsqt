package org.lsqt.content.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author 袁明敏
 *用于新闻、论坛贴、招聘、文学网、电商网的栏目分类
 */
@Entity
@Table(name="tb_category")
public class Category implements Serializable{

	
	/****/
	private static final long serialVersionUID = 1L;
	private static long OBJECT_COUNTER=0L;
	public Category(){
		OBJECT_COUNTER++;
	}

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@Column(name="pid",insertable=false,updatable=false)
	private String pid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="type")
	private CategoryType type;
	
	@Column(name="description")
	private String description;
	
	@Column(name="createTime")
	private Long createTime=System.currentTimeMillis()+OBJECT_COUNTER;
	
	/**类别排序号**/
	@Column(name="orderNum")
	private Integer orderNum;
	
	/**是否有下级结点**/
	@Column(name="hasChildNode")
	private Boolean hasChildNode;
	
	/**访问路径**/
	@Column(name="accessPath")
	private String accessPath;
	
	/**是否显示**/
	@Column(name="isVisible")
	private Boolean isVisible;
	
	/**新窗口打开或其它目标窗口打开**/
	@Column(name="target")
	private String target;
	
	/**是否需要审核**/
	@Column(name="hasAudit")
	private Boolean hasAudit;
	
	/**审核后的操作（如已审核的新闻再进行修改会有何效果）**/
	private Integer auditedOps;
	
	/**是否需要有评论**/
	@Column(name="hasComment")
	private Boolean hasComment;
	
	/**栏目层级数**/
	@Column(name="levelNum")
	private Integer levelNum;
	
	/**子站编码**/
	@Column(name="levelNum",insertable=false,updatable=false)
	private String appId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pid", referencedColumnName = "id")
	private Category parentCategory;
	
	@OneToMany(mappedBy="parentCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Category> subCategories=new HashSet<Category>();
	
	/**一个新闻可以属多个栏目（栏目与新闻的多对多关系）**/
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "categories", targetEntity = News.class)
	private Set<News> news;
	
	/**一个应用下的栏目（栏目与应用的多对一关系）**/
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "app_id", referencedColumnName = "id")
	private Application app;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<Category> subCategories) {
		this.subCategories = subCategories;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Set<News> getNews() {
		return news;
	}

	public void setNews(Set<News> news) {
		this.news = news;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getHasChildNode() {
		return hasChildNode;
	}

	public void setHasChildNode(Boolean hasChildNode) {
		this.hasChildNode = hasChildNode;
	}

	public Integer getLevelNum() {
		return levelNum;
	}

	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getAccessPath() {
		return accessPath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Boolean getHasAudit() {
		return hasAudit;
	}

	public void setHasAudit(Boolean hasAudit) {
		this.hasAudit = hasAudit;
	}

	public Boolean getHasComment() {
		return hasComment;
	}

	public void setHasComment(Boolean hasComment) {
		this.hasComment = hasComment;
	}

	public Integer getAuditedOps() {
		return auditedOps;
	}

	public void setAuditedOps(Integer auditedOps) {
		this.auditedOps = auditedOps;
	}
}
