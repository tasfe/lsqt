package org.lsqt.content.web.console.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable{
	private String name;
	private List parameters=new ArrayList();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List getParameters() {
		return parameters;
	}
	public void setParameters(List parameters) {
		this.parameters = parameters;
	}
}
