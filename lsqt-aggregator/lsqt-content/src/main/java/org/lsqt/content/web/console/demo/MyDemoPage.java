package org.lsqt.content.web.console.demo;

import java.io.File;
import java.io.IOException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.border.BoxBorder;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.panel.Panel;
import org.lsqt.content.web.wicket.AbstractPage;
import org.lsqt.content.web.wicket.UserManage;

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
		
		//页面include
		add(new Include("footer","foot.html"));  //只能包含静态文件，用WebMarkupContainer 和 Panel,Border 等
		
		//页面跳转
		BookmarkablePageLink<UserManage> booklink=new BookmarkablePageLink<UserManage>("booklink", UserManage.class);
		this.add(booklink);
		
		//文件下载
		try {  
			File file = File.createTempFile("new",",txt");
			DownloadLink downLink=new DownloadLink("download", file);
			this.add(downLink);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//弹出窗口
		BookmarkablePageLink<UserManage> booklink2=new BookmarkablePageLink<UserManage>("popWindow", UserManage.class);
		PopupSettings settings=new PopupSettings();
		settings.setWidth(500);
		settings.setHeight(500);
		settings.setTop(200);
		settings.setLeft(200);
		settings.setWindowName("用户列表管理");
		booklink2.setPopupSettings(settings);
		this.add(booklink2);
		
		//弹出模式窗口
		ModalWindow modelWin=new ModalWindow("modelWin");
		modelWin.setTitle("新增操作");
		modelWin.setInitialHeight(341);
		modelWin.setInitialWidth(732);
		modelWin.setHeightUnit("em");//设置单位
		modelWin.setWidthUnit("em");
		modelWin.setVisible(true);
		modelWin.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				
			}
		});
		this.add(modelWin);
		
		//
	}
}
