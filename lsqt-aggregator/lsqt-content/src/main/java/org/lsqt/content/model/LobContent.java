package org.lsqt.content.model;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 数据表，存放新闻内容，贴子，模板的HTMl内容等.
 * @author 袁明敏
 *
 */
@Entity
@Table(name="tb_lobcontent")
public class LobContent implements java.io.Serializable //extends Content
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "pkGenerator", strategy = "foreign", parameters = { @Parameter(name = "property", value = "news") })
	@GeneratedValue(generator = "pkGenerator")
	private String id;
	
	@Column
	@Lob
	@Basic(fetch =FetchType.LAZY)
	private String value;
	
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private News news;
	
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public News getNews()
	{
		return news;
	}

	public void setNews(News news)
	{
		this.news = news;
	}
}
