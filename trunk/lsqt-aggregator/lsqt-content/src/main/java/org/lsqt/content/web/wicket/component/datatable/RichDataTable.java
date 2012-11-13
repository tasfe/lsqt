package org.lsqt.content.web.wicket.component.datatable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * 
 * @author 袁明敏
 *
 */
public class RichDataTable extends Panel {

	/**  */
	private static final long serialVersionUID = 1L;
	
	private Collection<?> beansData;
	private Object [] [] arrayData;
	private String [] displayProperties;
	private Integer[] displayColumnIndexs;
	private String [] headLabels;
	
	public RichDataTable(String id) {
		super(id);
	}

	public RichDataTable bindData(Collection<?> beansData){
		this.beansData=beansData;
		return this;
	}
	
	public RichDataTable bindData(Object[][] arraysData){
		this.arrayData=arraysData;
		return this;
	}
	
	public RichDataTable displayOn(String [] properties){
		this.displayProperties=properties;
		return this;
	}
	
	public RichDataTable displayOn(Integer [] columnIndexs){
		this.displayColumnIndexs=columnIndexs;
		return this;
	}

	public void addHeadLabel(String [] labels){
		this.headLabels=labels;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  void build(){
		WebMarkupContainer tbContain=new WebMarkupContainer("tbContain");
		WebMarkupContainer richDataTable=new WebMarkupContainer("richDataTable");
		
		List<String> list=Arrays.asList(headLabels);
		ListView<String> tbHead=new ListView<String>("tbHead",list) {
			/**  */
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem<String> item) {
				Label lblText=new Label("lblText",item.getModelObject());
				item.add(lblText);
			}
		};
		
		
		
		Model<LinkedList> model=null;
		if(this.beansData!=null && this.arrayData==null){
			LinkedList beanList=new LinkedList(this.beansData);
			model=new Model(beanList);
		}else if(this.arrayData!=null && this.beansData==null){
			LinkedList arrayList=new LinkedList();
			for(Object [] i: this.arrayData){
				arrayList.add(i);
			}
			model=new Model(arrayList);
		}else{
			throw new RuntimeException("the data of RichDataTable is null,Don't forget initial data!");
		}
		
		ListView tbBody=new ListView("tbBody",model.getObject()) {
			/**  */
			private static final long serialVersionUID = 1L;
			@Override
			protected void populateItem(ListItem  item) {
				if(RichDataTable.this.displayColumnIndexs!=null){
					
				}else if(RichDataTable.this.displayProperties!=null){
					
				}else{
					throw new RuntimeException("the displayColumnIndexs or displayProperties of RichDataTable is null !");
				}
				
				ListView tbRow=new ListView("tbRow"){
					@Override
					protected void populateItem(ListItem item) {
						Label lblCell=new Label("tbLabel");
					}
				};
				
			}
		};
		
		
		
		add(tbContain);
		tbContain.add(richDataTable);
		richDataTable.add(tbHead);
		richDataTable.add(tbBody);
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		build();
	}
}
