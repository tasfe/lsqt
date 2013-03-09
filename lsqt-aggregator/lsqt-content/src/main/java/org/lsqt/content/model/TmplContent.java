package org.lsqt.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	private String id;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "tmpl_id", referencedColumnName = "id")
	private Template template;

	@Lob
	@Column(name="value")
	private byte [] bytes;
	
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

	public byte[] getBytes()
	{
		return bytes;
	}

	public void setBytes(byte[] bytes)
	{
		this.bytes = bytes;
	}
}