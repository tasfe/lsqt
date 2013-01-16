package org.lsqt.content.web.wicket.content.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IClusterable;

public  class TableModel<T>implements IClusterable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> head=new ArrayList<String>();
	private List<T> data=new ArrayList<T>();
	
	public TableModel(List<String> head,List<T> data){
		this.head=head;
		this.data=data;
	}

	public List<String> getHead() {
		return head;
	}

	public void setHead(List<String> head) {
		this.head = head;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	
 
}
