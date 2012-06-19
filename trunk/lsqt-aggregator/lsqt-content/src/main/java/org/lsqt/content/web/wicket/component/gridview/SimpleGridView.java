package org.lsqt.content.web.wicket.component.gridview;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.GridView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

/**
 * <pre>
 * 功能说明: 表格框组件显示，如单点图标表格
 * 编写日期:	2011-6-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 	修改日期：2011-6-18
 *    	修改人：袁明敏
 *   	修改内容：
 * </pre>
 */
public class SimpleGridView extends Panel {
	public SimpleGridView(String id){
		super(id);
		List<Integer> list=new ArrayList<Integer>();
		for(int i=0;i<50;i++){
			list.add(i);
		}
		
		
		// 功能选择区域
		IDataProvider dataProvider = new ListDataProvider(list);
		GridView gridView = new GridView("rows", dataProvider) {
			protected void populateItem(Item item) {
				final Integer integer = (Integer) item.getModelObject();
				item.add(new Image("static", "images/" + integer + ".gif"));
				item.add(new Label("label", "功能" + integer));
			}

			protected void populateEmptyItem(Item item) {
				Image image = new Image("static", "images/null.gif");
				image.setVisible(false);
				item.add(image);
				item.add(new Label("label", ""));
				// 因为没有图片,所以将图片隐藏起来
			}
		};
		gridView.setRows(10);
		gridView.setColumns(5);
		add(gridView);
	}
}
