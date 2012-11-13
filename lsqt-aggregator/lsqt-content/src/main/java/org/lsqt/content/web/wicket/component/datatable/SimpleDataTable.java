package org.lsqt.content.web.wicket.component.datatable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

/**
 * 
 * @author mm
 *
 */
public class SimpleDataTable extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<?> beansData;
	private Object [] [] arrayData;
	private String [] displayProperties;
	protected Integer[] displayColumnIndexs;

	private List<IColumn> columns;
	private int rowsPerPage=20;
	
	public SimpleDataTable(String id) {
		super(id);
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
				DataTable dataTable=new DataTable("dataTable", columns, new ListDataProvider(new ArrayList(beansData)),rowsPerPage);
				
				
				add(dataTable);
				
				AjaxPagingNavigator navigator=new AjaxPagingNavigator("pagingNavigator", dataTable){
					@Override
					public boolean isVisible() {
						if(beansData==null || beansData.isEmpty()){
							return false;
						}else{
							return true;
						}
					}
				};
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
