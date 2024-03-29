package org.lsqt.content.web.wicket.content;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.datetime.StyleDateConverter;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.file.File;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.MidCateNews;
import org.lsqt.content.model.NewsContent;
import org.lsqt.content.model.News;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.web.wicket.component.form.SimpleForm;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.content.bean.NewsBean;
import org.lsqt.content.web.wicket.content.bean.TimeInMillisConverter;
import org.lsqt.content.web.wicket.content.bean.TinySettingBean;
import org.lsqt.content.web.wicket.util.RendererUtil;
import org.lsqt.content.web.wicket.util.VarUtil;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.image.ImageUploadPanel;
import wicket.contrib.tinymce.settings.AdvListPlugin;
import wicket.contrib.tinymce.settings.AutoSavePlugin;
import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.ContextMenuPlugin;
import wicket.contrib.tinymce.settings.DateTimePlugin;
import wicket.contrib.tinymce.settings.DirectionalityPlugin;
import wicket.contrib.tinymce.settings.EmotionsPlugin;
import wicket.contrib.tinymce.settings.FullScreenPlugin;
import wicket.contrib.tinymce.settings.IESpellPlugin;
import wicket.contrib.tinymce.settings.ImageUploadPlugin;
import wicket.contrib.tinymce.settings.MediaPlugin;
import wicket.contrib.tinymce.settings.PastePlugin;
import wicket.contrib.tinymce.settings.PreviewPlugin;
import wicket.contrib.tinymce.settings.PrintPlugin;
import wicket.contrib.tinymce.settings.SavePlugin;
import wicket.contrib.tinymce.settings.SearchReplacePlugin;
import wicket.contrib.tinymce.settings.TablePlugin;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.WordcountPlugin;
/**
 *新闻内容添加页面
 * @author 袁明敏
 * @version 1.1
 * @since 2012-05-18
 * 
 */
public class NewsAddPage extends WebPage {
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsServ;
	@SpringBean(name="newsServiceImpl") NewsService newsServ;

	private Node selectedNode;
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
	
	private NewsBean newsBean=new NewsBean();
	public NewsAddPage(Node selectedNode){
		//super(id);
		this.selectedNode=selectedNode;
		
		final FeedbackPanel panel=new FeedbackPanel("feedback");
		panel.setMaxMessages(1);
		
		
		
		final SimpleForm<News> form =new SimpleForm<News>("form",new PropertyModel(this,"newsBean"))
		{
			@Override
			protected void onSubmit()
			{
			
				
			}
		};
		form.setMultiPart(true);
		
		final AjaxSubmitLink btnAdd=new AjaxSubmitLink("btnAdd",form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				NewsBean bean = (NewsBean) form.getModelObject();
				if (NewsAddPage.this.selectedNode != null)
				{
					
					if(NewsListPage.NODE_TYPE_CATEGORY.equals(NewsAddPage.this.selectedNode.getType()))
					{
						Category category = categoryServ.findById(NewsAddPage.this.selectedNode.getId());
						if(category!=null)
						{
							StringEscapeUtils.escapeHtml((newsBean.getContent() == null ? StringUtils.EMPTY : newsBean.getContent()).toString()) ;
							bean.getNews().setApp(category.getApp());
						
							newsServ.save(newsBean.getNews(),newsBean.getContent(),category);
						}
					}else 
					{
						target.appendJavaScript("alert('请选择一个栏目!')");
					}
				}
				NewsAddPage.this.onSaveAfter(target);
			}
		};
		
		Calendar c=Calendar.getInstance(TimeZone.getDefault(),getSession().getLocale());
		int h=c.get(Calendar.HOUR_OF_DAY);
		int m=c.get(Calendar.MINUTE);
		int s=c.get(Calendar.SECOND);
		String dateFmtStr="yyyy-MM-dd "+(h<10 ? ("0"+h): h)+":"+(m<10 ? ("0"+m): h)+":"+(s<10 ? ("0"+s): s);
	//	StyleDateConverter converter=new 
		//后台发布日期
		
		DateTextField dtfPubTime = new DateTextField("pubTime",new PropertyModel(newsBean,"news.pubTime"),dateFmtStr)
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
		
		DateTextField dtfOnlineTime = new DateTextField("onlineTime",new PropertyModel(newsBean,"news.onlineTime"),dateFmtStr)
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
		TextField<String> txtTitle=new RequiredTextField<String>("title",new PropertyModel(newsBean,"news.title"));
		
		//关键字
		TextField<String> txtContentKeys=new TextField<String>("contentKeys",new PropertyModel(newsBean,"news.contentKeys"));
		
		//摘要
		TextArea<String> txtShortContent=new TextArea<String>("shortContent",new PropertyModel(newsBean,"news.shortContent"));
		
		//作者
		TextField<String> txtAuthor=new TextField<String>("author",new PropertyModel(newsBean,"news.author"));
		
		//是否启用
		CheckBox cbxIsEnable=new CheckBox("isEnable",new PropertyModel(newsBean,"news.isEnable"));
	
		
		
		//是否发布
		RadioChoice<Boolean> radIsPublished = new RadioChoice<Boolean>("isPublished", new PropertyModel(newsBean,"news.isPublished"),Arrays.asList(true,false),RendererUtil.getYesNoRenderer()).setSuffix("");

		
		//内容
		TextArea<News> txtContent = new TextArea<News>("content",new PropertyModel(newsBean,"content"));
		TinySettingBean.initSetting(txtContent,form);

		//描述信息
		TextArea<String> txtDescription = new TextArea<String>("description",new PropertyModel(newsBean,"news.description"));
		//txtDescription.add(StringValidator.maximumLength(500));
		

		
		add(form);
		{
			form.add(panel);
			form.add(txtTitle);
			form.add(txtContentKeys);
			form.add(txtShortContent);
			form.add(txtAuthor);
			form.add(cbxIsEnable);
			form.add(radIsPublished);
			
			form.add(dtfPubTime);
			form.add(dtfOnlineTime);
			
			form.add(txtContent);
			//form.add(imageUploadPanel);
			form.add(txtDescription);
			form.add(btnAdd);
		}
	}

	
	public Node getSelectedNode()
	{
		return selectedNode;
	}

	public void setSelectedNode(Node selectedNode)
	{
		this.selectedNode = selectedNode;
	}

	public NewsBean getNewsBean()
	{
		return newsBean;
	}

	public void setNewsBean(NewsBean newsBean)
	{
		this.newsBean = newsBean;
	}
}
