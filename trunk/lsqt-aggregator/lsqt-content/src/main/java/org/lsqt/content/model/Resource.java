package org.lsqt.content.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * <pre>
 * 功能说明: 
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
@Entity
@Table(name="lsqt_resource")
public class Resource implements Serializable{
	private static long OBJECT_COUNTER=0L;
	public Resource(){
		OBJECT_COUNTER++;
	}
	/****/
	private static final long serialVersionUID = 2746748130837926699L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	
	/**父类资源**/
	@Column(name="pid",length=32,insertable=false,updatable=false)
	private String pid;
	
	/**资源名称**/
	@Column(name="resourceName",length=100)
	private String resourceName;
	
	/**资源小图标,如指定用户人物图标**/
	@Column(name="resourceIcon",length=1000)
	private String resourceIcon;
	
	/**资源类型**/
	@Column(name="resourceType",length=4)
	private String resourceType;
	
	/**是否启用**/
	@Column(name="isEnable")
	private Boolean isEnable;
	
	/**资源描述**/
	@Column(name="description",length=2000)
	private String description;
	
	/**是否有下级结点**/
	@Column(name="hasChildNode")
	private Boolean hasChildNode;
	
	
	/**资源层级数**/
	@Column(name="levelNum")
	private Integer levelNum;
	
	/**资源管理地址**/
	@Column(name="manageUrl",length=2000)
	private String manageUrl;
	
	/**资源管理地址打开的目标**/
	@Column(name="openTarget",length=50)
	private String openTarget;
	
	/**资源创建时间**/
	@Column(name="createTime")
	private Long createTime=System.currentTimeMillis()+OBJECT_COUNTER;
	
	/**上级结点**/
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pid", referencedColumnName = "id")
	private Resource parentResource;
	
	/**下级结点**/
	@OneToMany(mappedBy="parentResource",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Resource> subResources=new HashSet<Resource>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getResourceIcon() {
		return resourceIcon;
	}
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getManageUrl() {
		return manageUrl;
	}
	public void setManageUrl(String manageUrl) {
		this.manageUrl = manageUrl;
	}
	public String getOpenTarget() {
		return openTarget;
	}
	public void setOpenTarget(String openTarget) {
		this.openTarget = openTarget;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Resource getParentResource() {
		return parentResource;
	}
	public void setParentResource(Resource parentResource) {
		this.parentResource = parentResource;
	}
	public Set<Resource> getSubResources() {
		return subResources;
	}
	public void setSubResources(Set<Resource> subResources) {
		this.subResources = subResources;
	}

}