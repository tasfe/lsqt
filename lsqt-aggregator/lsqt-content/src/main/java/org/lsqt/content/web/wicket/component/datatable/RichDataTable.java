package org.lsqt.content.web.wicket.component.datatable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * 
 * @author 袁明敏
 *
 */
public class RichDataTable extends Panel {

	/**  */
	private static final long serialVersionUID = 1L;
	/**表格头部标签**/
	private String [] headLabels ;
	
	/**表格显示的bean**/
	private Collection<?> beansData;
	
	/**表格显示的bean的属性名**/
	private String [] displayProperties;
	
	
	
	/**标格显示的二维数组**/
	private Object [] [] arrayData;
	
	/**表格显示的二维数组列索引**/
	private Integer[] displayColumnIndexs;
	
	
	
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

	public RichDataTable addHeadLabel(String [] labels){
		this.headLabels=labels;
		return this;
	}
	
	
	
	public  void build(){
		checkDataInitialized();
		checkDisplayInitialized();
		
		WebMarkupContainer tbContain=new WebMarkupContainer("tbContain");
		WebMarkupContainer richDataTable=new WebMarkupContainer("richDataTable");
		
		//表格头部标签
		if(headLabels==null){
			headLabels=new String[0];
		}
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
		
		
	 
		IModel<LinkedList<Object[]>> model;
		try {
			model = new Model<LinkedList<Object[]>>(convertArray());
		} catch (Exception e) {
			throw new RuntimeException("convert bean exception, please check the bean of property name! ("+e.getMessage()+")");
		}
		
		//表格体
		ListView<Object[]> tbBody=new ListView<Object[]>("tbRow",model) {
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Object[]> item) {
				
				ListView<Object> cells=new ListView<Object>("tbCell",Arrays.asList(item.getModelObject())){
					/** 	 */
					private static final long serialVersionUID = 1L;

					@Override
					protected void populateItem(ListItem<Object> item) {
						Label lblValue=new Label("tbLabel", item.getModelObject()==null ? StringUtils.EMPTY : item.getModelObject().toString());
						item.add(lblValue);
					}
				};
				item.add(cells);
			}
		};
		
		
		
		add(tbContain);
		tbContain.add(richDataTable);
		richDataTable.add(tbHead);
		richDataTable.add(tbBody);
	}

	private LinkedList<Object[]> convertArray() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		LinkedList<Object[]> arrayList=new LinkedList<Object[]>();
		
		
		if(this.beansData!=null && this.arrayData==null){
			if(beansData.size()>0){
				Class clazz=this.beansData.iterator().next().getClass();
				
				
				
				for(Object e: this.beansData){
						List<Object> row=new LinkedList<Object>();
						
						LinkedHashMap<String, Object> map=new LinkedHashMap<String,Object>(displayProperties.length);
						for(Class superClacc=clazz;superClacc != Object.class;superClacc=superClacc.getSuperclass()){
							Field[] fs=superClacc.getDeclaredFields();
							for(Field f:fs){
								for(String p: this.displayProperties){
									if(f.getName().equalsIgnoreCase(p)){
										boolean oldAccess=f.isAccessible();
										f.setAccessible(true);
										map.put(f.getName(), f.get(e));
										f.setAccessible(oldAccess);
									}
								}
							}
						}
						System.out.println(map);
						
						for(String p: this.displayProperties){
							row.add(map.get(p));
						}
						Object [] temp=new Object[row.size()];
						row.toArray(temp);
						arrayList.add(temp);
				}
			}
		}else if(this.arrayData!=null && this.beansData==null){
			for(Object [] i: this.arrayData){
				arrayList.add(i);
			}
		}
		
		return arrayList;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		build();
	}
	
	/**
	 * 检查表格需要显示的属性,bean属性或二维数组数据显示的列.
	 */
	private void checkDisplayInitialized() {
		if(this.displayProperties!=null && this.displayColumnIndexs!=null){
			throw new RuntimeException("the displayColumnIndexs or displayProperties illegal exception !");
		}
		
		if(this.displayColumnIndexs==null && this.displayProperties == null){
			throw new NullPointerException("the displayColumnIndexs or displayProperties is null ,either initial displayColumnIndexs or displayProperties !");
		}
	}
	
	/**
	 * 检查数据是否初使化,并进行合法校验.
	 */
	private void checkDataInitialized() {
		if(this.beansData==null && this.arrayData==null){
			throw new NullPointerException("the data of RichDataTable is null,Don't forget initial data, you can RichDataTable#bindData( List<bean> data ) or RichDataTable#bindData(array[][] data) !");
		}
		
		if(this.beansData!=null && this.arrayData!=null){
			throw new RuntimeException("the BeansData and ArrayData  of RichDataTable are both  non-null,please initial  beansData not null  or arrayData not null!");
		}
	}
}
