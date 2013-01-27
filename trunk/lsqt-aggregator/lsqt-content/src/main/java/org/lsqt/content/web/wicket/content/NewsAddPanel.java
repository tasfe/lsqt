package org.lsqt.content.web.wicket.content;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.StringValidator;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.News;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.content.bean.TimeInMillisConverter;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;

/**
 *新闻内容添加页面
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 */
public class NewsAddPanel extends Panel {
	
	@SpringBean NewsService newsServ;
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsServ;
	
	private String parentCategoryID;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void onSaveAfter(AjaxRequestTarget target)
	{
		
	}
	
	protected void onCacelAfter(AjaxRequestTarget target)
	{
		
	}
	
	public NewsAddPanel(String id){
		super(id);
		
		final FeedbackPanel panel=new FeedbackPanel("feedback");
		panel.setMaxMessages(1);
		
		
		final News news=new News();
		final Form<News> form = new Form<News>("form",new Model(news));
		
		final AjaxSubmitLink btnAdd=new AjaxSubmitLink("btnAdd",form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				super.onSubmit(target, form);
				if (parentCategoryID != null)
				{
					Category category = categoryServ.findById(parentCategoryID);
					News news = (News) form.getModelObject();
					news.setContent(Strings.escapeMarkup(
							news.getContent() == null
									? StringUtils.EMPTY
									: news.getContent()).toString());
					news.setApp(category.getApp());
					news.getCategories().add(category);
					newsServ.save(news);
				}
				
				NewsAddPanel.this.onSaveAfter(target);
			}
		};

		Calendar c=Calendar.getInstance(TimeZone.getDefault(),getSession().getLocale());
		int h=c.get(Calendar.HOUR_OF_DAY);
		int m=c.get(Calendar.MINUTE);
		int s=c.get(Calendar.SECOND);
		String dateFmtStr="yyyy-MM-dd "+(h<10 ? ("0"+h): h)+":"+(m<10 ? ("0"+m): h)+":"+(s<10 ? ("0"+s): s);
		//后台发布日期
		DateTextField dtfPubTime = new DateTextField("pubTime",new PropertyModel(news, "pubTime"),dateFmtStr)
		{
			@Override
			public <C> IConverter<C> getConverter(Class<C> type)
			{
				if(Date.class.isAssignableFrom(type))
				{
					return (IConverter<C>) TimeInMillisConverter.INSTANCE;
				}
				return super.getConverter(type);
			}
		};
		dtfPubTime.add(new DatePicker());
		dtfPubTime.setRequired(true);
		
		//在线时间
		DateTextField dtfOnlineTime = new DateTextField("onlineTime",new PropertyModel(news,"onlineTime"),dateFmtStr)
		{
			@Override
			public <C> IConverter<C> getConverter(Class<C> type)
			{
				if(Date.class.isAssignableFrom(type))
				{
					return (IConverter<C>) TimeInMillisConverter.INSTANCE;
				}
				return super.getConverter(type);
			}
		};
		dtfOnlineTime.add(new DatePicker());
		dtfOnlineTime.setRequired(true);
		
		
		
		//新闻标题
		TextField<String> txtTitle=new RequiredTextField<String>("title2",new PropertyModel<String>(news, "title"));
		
		
		//是否启用
		CheckBox cbxIsEnable=new CheckBox("isEnable",new PropertyModel<Boolean>(news,"isEnable"));
		cbxIsEnable.setRequired(true);
		
		
		
		//是否发布
		IChoiceRenderer<Boolean> render = new IChoiceRenderer<Boolean>()
		{
			private static final long serialVersionUID = 1L;
			public Object getDisplayValue(Boolean object)
			{
				return object ? "是" : "否";
			}
			public String getIdValue(Boolean object, int index)
			{
				return object + "";
			}
		};
		RadioChoice<Boolean> radIsPublished = new RadioChoice<Boolean>("isPublished",new PropertyModel<Boolean>(news,"isPublished"), Arrays.asList(true,false),render).setSuffix("");
		radIsPublished.setRequired(true);
		
		
		
		
		
		
		
		//内容
		TextArea<String> txtContent = new TextArea<String>("content",new PropertyModel<String>(news,"content"));
		txtContent.add(new TinyMceBehavior(new TinyMCESettings(Theme.advanced)));
		txtContent.add(StringValidator.maximumLength(4000));
		form.add(txtContent);
		
		
		//描述信息
		TextArea<String> txtDescription = new TextArea<String>("description",new PropertyModel<String>(news,"description"));
		txtDescription.add(StringValidator.maximumLength(500));
		
		
		AjaxLink btnBack = new AjaxLink<Void>("btnBack")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				
			}
		};
		
		
		
		add(form);
		{
			form.add(panel);
			form.add(dtfOnlineTime);
			form.add(txtTitle);
			form.add(cbxIsEnable);
			form.add(radIsPublished);
			form.add(dtfPubTime);
			form.add(txtDescription);
			form.add(btnAdd);
			form.add(btnBack);
		}
	}

	public String getParentCategoryID()
	{
		return parentCategoryID;
	}

	public void setParentCategoryID(String parentCategoryID)
	{
		this.parentCategoryID = parentCategoryID;
	}
}
