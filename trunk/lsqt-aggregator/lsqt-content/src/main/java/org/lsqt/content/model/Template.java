package org.lsqt.content.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 内容模板定义,主要为velocity模板文件.
 * 一个模板可以有多个内容,比如一个栏目的内容显示，可以用多个模板文件去展现.
 * @author 袁明敏
 *
 */
@Entity
@Table(name="tb_template")
public class Template extends Content{
	/**标识ID**/
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	/**模板别名,因模板文件都是英文取名,加了一个别名专门用于中文显示意思**/
	@Column(name="alias",length=20)
	private String alias;
	
	/**栏目名称,用于显示该模板是用于哪个栏目**/
	@Column(name="cateName",length=20)
	private String cateName;
	
	/**模板类型,用于签定是velocity模板还是jsp模板，或者其它**/
	@Column(name="type",length=20)
	private String type;
	
	/**模板的访问路径:模板文件存于数据库或磁盘都可,path为磁盘路径,默认行为是即上传到数据库又上传到磁盘**/
	private String path;

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

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}
}
