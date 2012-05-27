package org.lsqt.content.web.console.demo;

import org.apache.wicket.markup.html.basic.Label;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage9 extends AbstractPage{
	public MyDemoPage9(){
		Label name=new Label("name","yuanmingmin");
		Label street=new Label("street","ChangAnStreet");
		Label province=new Label("province","HuNan");
		Label county=new Label("county","China");
		
		add(name);
		add(street);
		add(province);
		add(county);
	}
}
