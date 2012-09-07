package org.lsqt.content.web.wicket.component.datatable;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.lsqt.content.model.User;
import org.lsqt.content.web.wicket.AbstractPage;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
public class SimpleDataTable extends Panel {
	protected List<?> beansData;
	protected Object [] [] arrayData;
	protected String [] displayProperties;
	protected Integer[] displayColumnIndexs;

	private List<IColumn> columns;
	private int rowsPerPage=20;
	
	public SimpleDataTable(String id) {
		super(id);
	}
	
	public SimpleDataTable bindData(Object[][] data,int rowsPerPage){
		this.arrayData=data;
		this.rowsPerPage=rowsPerPage;
		return this;
	}
	
	public SimpleDataTable bindData(List<?> beans,int rowsPerPage){
		this.beansData=beans;
		this.rowsPerPage=rowsPerPage;
		return this;
	}
	
	public SimpleDataTable displayOn(String [] properties){
		this.displayProperties=properties;
		return this;
	}
	
	public SimpleDataTable displayOn(Integer [] columnIndexs){
		this.displayColumnIndexs=columnIndexs;
		return this;
	}
	
	private void build(){
		if(beansData!=null){
			
			if(displayProperties!=null){  //指定显示的属性,否则显示全部属性
				List<IColumn> columns=new ArrayList<IColumn>();
				for(String p: displayProperties){
					columns.add(new PropertyColumn(new Model(p), p));
				}
				DataTable dataTable=new DataTable("dataTable", columns, new ListDataProvider(beansData),rowsPerPage);
				add(dataTable);
				
				PagingNavigator navigator=new PagingNavigator("pagingNavigator", dataTable);
				add(navigator);
			}else{
				
			}
		}
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		build();
	}
}
