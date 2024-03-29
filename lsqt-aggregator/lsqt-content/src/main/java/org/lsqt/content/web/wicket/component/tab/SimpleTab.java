package org.lsqt.content.web.wicket.component.tab;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
//import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;


public class SimpleTab extends Panel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ITab> tabs = new ArrayList<ITab>();
	
	// default style sheet resource
	private static final CssResourceReference CSS = new CssResourceReference(
			SimpleTab.class, "res/style.css");

	/**
	 * Returns the stylesheet reference
	 * 
	 * @return style sheet reference
	 */
	protected CssResourceReference getCSS() {
		return CSS;
	}
 
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		// TODO Auto-generated method stub
		super.renderHead(container);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		ResourceReference css = getCSS();
		if (css != null) {
			//response.renderCSSReference(css);
			 response.render(CssHeaderItem.forReference(CSS));
		}
	}
		
	public SimpleTab(String id) {
		super(id);
	}
	
	/**
	 * 
	 * @param id id
	 * @param clazz panel class
	 * @param title the title of the tab
	 * @return SimpleTab
	 */
	@Deprecated
	public SimpleTab addPanel(final Class<? extends Panel> clazz ,final String title){
		
		tabs.add(new AbstractTab(new Model<String>(title))
		{
			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public Panel getPanel(String panelId)
			{
				try {
					return (Panel)clazz.getDeclaredConstructors()[0].newInstance(new Object[]{panelId});
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		return this;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new TabbedPanel("tabs", tabs));
	}
}
class Student {
	String name;
	int age;
	Student(){
	}
	
	Student(int age){
		this.age=age;
	}
	Student (String name){
		this.name=name;
	}
	public void run(){
		System.out.println(name+"  "+ age);
	}
	
	public static void main(String args[]){
		Constructor [] cs=Student.class.getDeclaredConstructors();
		for(Constructor i :cs){
			System.out.println(i.toGenericString()+" "+i.getName());
		}
	}
}
