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
@Table(name="tb_resource")
public class Resource extends Content implements Serializable{
	public Resource(){
	}
	/****/
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	
	/**父类资源**/
	@Column(name="pid",length=32,insertable=false,updatable=false)
	private String pid;
	
	/**资源类型**/
	@Column(name="type",length=4)
	private String type;
	
	/**是否启用**/
	@Column(name="isEnable")
	private Boolean isEnable;
	
	
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
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
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
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

}
