package org.lsqt.content.web.wicket.component.tree;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Node implements Serializable
{

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	
	private Node parent;

	private List<Node> subNodes = new ArrayList<Node>();

	private Object tag;
	private String type;
	public Node(){}


	public Node(Node parent,String id, String name)
	{
		this.parent = parent;
		this.parent.subNodes.add(this);
		
		this.id=id;
		this.name=name;
	}

	public Node getParent()
	{
		return parent;
	}

	public String getId()
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

	public Object getTag(){
		return tag;
	}


	public void setTag(Object tag){
		this.tag = tag;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
