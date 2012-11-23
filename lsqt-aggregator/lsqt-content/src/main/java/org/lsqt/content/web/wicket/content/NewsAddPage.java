package org.lsqt.content.web.wicket.content;

import java.util.Arrays;
import java.util.Date;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.StringValidator;
import org.lsqt.content.model.News;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.ConsoleIndex;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;

/**
 *新闻内容添加页面
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 * 
 */
public class NewsAddPage extends ConsoleIndex {
	
	@SpringBean NewsService newsServ;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewsAddPage(){
		final FeedbackPanel panel=new FeedbackPanel("feedback");
		panel.setMaxMessages(1);
		/*panel.onEvent(new IEvent<T>() {
		});*/
		
		final News news=new News();
		final Form<News> form=new Form<News>("form"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				newsServ.save(news);
				System.out.println(news);
				news.setContent(Strings.escapeMarkup(news.getContent()).toString());
				setResponsePage(NewsListPage.class);
			}
		};
		add(form);
		
		form.add(panel);
		/*panel.setFilter(new IFeedbackMessageFilter() {
			
			*//**
			 * 
			 *//*
			private static final long serialVersionUID = 1L;

			@Override
			public boolean accept(FeedbackMessage message) {
				info(message);
				System.out.println(message.getReporter());
				return false;
			}
		});*/
		panel.setFilter(new ContainerFeedbackMessageFilter(form));
		
		final ModalWindow modalCategories=new ModalWindow("modalCategories");
		modalCategories.setTitle("新闻类别");
		modalCategories.setCookieName("modalCategories");
		modalCategories.setPageCreator(new ModalWindow.PageCreator() {
			public Page createPage() {
				return new CategoryAddPage();
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
		
		Link<Void> lnkBack=new Link<Void>("lnkBack") {
			@Override
			public void onClick() {
				setResponsePage(NewsListPage.class);
			}
		};
		form.add(lnkBack);
	}
}
