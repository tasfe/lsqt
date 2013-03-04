package org.lsqt.content.model;

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
 * 内容模板定义,主要为velocity模板文件.
 * 一个模板可以有多个内容,比如一个栏目的内容显示，可以用多个模板文件去展现.
 * @author 袁明敏
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tb_template")
public class Template extends Content {
	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	/**模板别名,因模板文件都是英文取名,加了一个别名专门用于中文显示意思**/
	@Column(name="alias",length=1000)
	private String alias;
	
	/**栏目名称,用于显示该模板是用于哪个栏目**/
	@Column(name="cateName",length=50)
	private String cateName;
	
	/**当前模板所属的栏目id**/
	@Column(name="cateId",insertable=false,updatable=false)
	private String cateId;
	
	@ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})	
	@JoinColumn(name = "cateId", referencedColumnName = "id")
	private Category category;
	
	/**模板类型,用于签定是velocity模板还是jsp模板，或者其它**/
	@Column(name="type",length=20)
	private String type;
	
	/**模板的访问路径:模板文件存于数据库或磁盘都可,path为磁盘路径,默认行为是即上传到数据库又上传到磁盘**/
	@Column(name="diskPath",length=2000)
	private String diskPath;

	/**模板文件上传时:记录模板基本信息,并把模板类容写入数据库,也同时按栏目类别存储在磁盘上**/
	/**因性能考虑,获到模板时,系统将优先从磁盘获到，如果没有再从数据库获取**/
	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL, fetch = FetchType.LAZY)// mappedBy与books中的studnet映射
	private Set<TmplContent> tmplContentSet=new HashSet<TmplContent>();
	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type = type;
	}

	public Set<TmplContent> getTmplContentSet()
	{
		return tmplContentSet;
	}

	public void setTmplContentSet(Set<TmplContent> tmplContentSet)
	{
		this.tmplContentSet = tmplContentSet;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getCateName()
	{
		return cateName;
	}

	public void setCateName(String cateName)
	{
		this.cateName = cateName;
	}

	public String getDiskPath()
	{
		return diskPath;
	}

	public void setDiskPath(String diskPath)
	{
		this.diskPath = diskPath;
	}

	public String getCateId()
	{
		return cateId;
	}

	public void setCateId(String cateId)
	{
		this.cateId = cateId;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

}
