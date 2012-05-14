package org.lsqt.content.web.console.demo;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.border.BoxBorder;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.panel.Panel;
import org.lsqt.content.web.console.AbstractPage;

public class MyDemoPage extends AbstractPage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1653252664376978260L;

	public MyDemoPage(){
		Label l=new Label("birthday","<span>生日<br/>信息</span>");
		Label l2=new Label("birthday2","<span>我是<br>中国人</span>");
		add(l);
		add(l2);
		l2.setEscapeModelStrings(false);
		
		MultiLineLabel mLabel=new MultiLineLabel("message","这是描述信息");
		add(mLabel);
		
		WebMarkupContainer container=new WebMarkupContainer("container");
		container.add(mLabel);
		add(container);
		
		
		Border b=new BoxBorder("myBorder");
		Label lName=new Label("stuName","名字");
		b.add(lName);
		add(b);
		
		add(new Include("footer","foot.html"));
	}
}
