package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tree.table.IRenderable;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.LinkTree;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.validation.validator.StringValidator;
import org.lsqt.content.model.News;
import org.lsqt.content.web.wicket.AbstractPage;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.tab.SimpleTab;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;

public class NewsContentPage extends AbstractPage {
	public NewsContentPage(){
		final FeedbackPanel panel=new FeedbackPanel("feedback");
		final News news=new News();
		final Form<News> form=new Form<News>("form"){
			@Override
			protected void onSubmit() {
				System.out.println(news);
			
				//super.onSubmit();
			}
		};
		add(form);
		
		form.add(panel);
		
		
		final ModalWindow modalCategories=new ModalWindow("modalCategories");
		modalCategories.setTitle("新闻类别");
		modalCategories.setCookieName("modalCategories");
		modalCategories.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new CategoryPage();
			}
		});
		modalCategories.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			@Override
			public void onClose(AjaxRequestTarget target) {
				
			}
		});
		modalCategories.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
			@Override
			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				
				return true;
			}
		});
		form.add(modalCategories);
		
		
		
		
		final TextField txtCategories=new TextField("txtCategories",new PropertyModel(news, "categories"));
		txtCategories.add(new AjaxFormComponentUpdatingBehavior("onClick") {
			protected void onUpdate(AjaxRequestTarget target) {
				modalCategories.show(target);
			}
		});
		form.add(txtCategories);
		
		//在线时间
		DateTextField dtfOnlineTime = new DateTextField("onlineTime", "yyyy-MM-dd"){
			@Override
			protected Date convertValue(String[] value)
					throws ConversionException {
				System.out.println(value);
				return super.convertValue(value);
			}
		};
		dtfOnlineTime.add(new DatePicker());
		dtfOnlineTime.setRequired(true);
		dtfOnlineTime.setModel(new PropertyModel(news,"onlineTime"));
		form.add(dtfOnlineTime);
		
		//新闻标题
		TextField<String> txtTitle=new RequiredTextField<String>("title2",new PropertyModel<String>(news, "title"));
		form.add(txtTitle);
		
		//是否启用
		CheckBox cbxIsEnable=new CheckBox("isEnable",new PropertyModel<Boolean>(news,"isEnable"));
		cbxIsEnable.setRequired(true);
		form.add(cbxIsEnable);
		
		//是否发布
		 IChoiceRenderer<Boolean> render=new IChoiceRenderer<Boolean>(){
			private static final long serialVersionUID = 1L;
			public Object getDisplayValue(Boolean object) {
				return object ? "是":"否" ;
			}
			public String getIdValue(Boolean object, int index) {
				return object+"" ;
			}
		 };
		RadioChoice<Boolean> radIsPublished = new RadioChoice<Boolean>("isPublished", Arrays.asList(true,false),render).setSuffix("");
		radIsPublished.setModel(new PropertyModel(news, "isPublished") );
		radIsPublished.setRequired(true);
		form.add(radIsPublished);
		
		//后台发布日期
		DateTextField dtfPubTime = new DateTextField("pubTime", "yyyy-MM-dd");
		dtfPubTime.setModel(new PropertyModel(news,"pubTime"));
		dtfPubTime.add(new DatePicker());
		dtfPubTime.setRequired(true);
		form.add(dtfPubTime);
		
		//内容
		TextArea<String> txtContent = new TextArea<String>("content");
		txtContent.setModel(new PropertyModel<String>(news,"content"));
		txtContent.add(new TinyMceBehavior(new TinyMCESettings(Theme.advanced)));
		txtContent.add(StringValidator.maximumLength(4000));
		form.add(txtContent);
		
		//描述信息
		TextArea<String> txtDescription = new TextArea<String>("description");
		txtDescription.setModel(new PropertyModel<String>(news,"description"));
		txtDescription.add(StringValidator.maximumLength(500));
		form.add(txtDescription);
		
		
	}
}
