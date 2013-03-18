package org.lsqt.content.web.wicket.component.menu;

import org.lsqt.content.web.wicket.component.tree.Node;

@SuppressWarnings("serial")
public class UrlNode extends Node
{
	/**结点链接**/
	private String url;

	public UrlNode(){}
	
	public UrlNode(String id)
	{
		super(id);
	}
	
	public UrlNode(String id,String name)
	{
		super(id,name);
	}
	
	public UrlNode(UrlNode parant,String id,String name)
	{
		super(parant,id,name);
	}
	
	public UrlNode(UrlNode parant,String id,String name,String url)
	{
		super(parant,id,name);
		this.url=url;
	}
	
	public UrlNode(String id,String name,String url)
	{
		super(id,name);
		this.url=url;
	}
	
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}
}
