package org.lsqt.content.web.wicket.content.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.model.IModel;

public class Node implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	
	private Node parent;

	private List<Node> subNodes = new ArrayList<Node>();

	public Node(){}
	
	public Node(String id)
	{
		this.id = id;
	}

	public Node(Node parent, String name)
	{
		this(name);

		this.parent = parent;
		this.parent.subNodes.add(this);
	}

	public Node getParent()
	{
		return parent;
	}

	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getSubNodes() {
		return Collections.unmodifiableList(subNodes);
	}

	public void setSubNodes(List<Node> subNodes) {
		this.subNodes = subNodes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

}
