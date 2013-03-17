package org.lsqt.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 模板内容
 * @author 袁明敏
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="tb_tmplcontent")
public class TmplContent extends Content{
	/**标识ID**/
	@Id
	@GenericGenerator(name = "pkGenerator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "template") })
	@GeneratedValue(generator = "pkGenerator")
	private String id;
	
	@OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, mappedBy = "tmplContent") //mappedBy ,定义在被控方
	private Template template;

	@Lob
	@Column(name="content")
	private String content;
	
	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}

	public Template getTemplate(){
		return template;
	}

	public void setTemplate(Template template){
		this.template = template;
	}
}
