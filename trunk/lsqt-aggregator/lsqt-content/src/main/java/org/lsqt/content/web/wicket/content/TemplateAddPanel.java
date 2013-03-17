package org.lsqt.content.web.wicket.content;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ComponentModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.Template;
import org.lsqt.content.model.TmplContent;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.service.TemplateService;
import org.lsqt.content.web.wicket.component.form.SimpleForm;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.content.bean.TinySettingBean;
import org.lsqt.content.web.wicket.util.WebUtil;

@SuppressWarnings("serial")
public class TemplateAddPanel extends WebPage//Panel
{
	@SpringBean(name="newsServiceImpl") NewsService newsServ;
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	@SpringBean(name="templateServiceImpl") TemplateService templateService;
	
	public static final String NODE_TYPE_APPLICATION="_application";
	public static final String NODE_TYPE_CATEGORY="_category";
	public static final String NODE_TYPE_OTHER="_other";
	
	private final Template template=new Template() ;
	private TmplContent tmplContent=new TmplContent();
	private Node node;
	
	private String savedDir=StringUtils.EMPTY;
	private String rootDir = WebUtil.getWebRoot(); 
	

	public TemplateAddPanel(Node node)
	{
		this.node=node;
		template.setCateName(node.getName());
		init();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void init()
	{
		final FeedbackPanel feedbackPanel=new FeedbackPanel("feedbackPanel");
		
		final Form form=new Form("form")
		{
			@Override
			protected void onSubmit()
			{
				System.out.println("aaaa");
			}
		
		};
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(20000));
		
		
		final WebMarkupContainer tmplInfo=new WebMarkupContainer("tmplInfo");
		
		final TextField<String> name=new TextField<String>("name",new PropertyModel<String>(template, "name"));
		final TextField<String> alias=new TextField<String>("alias",new PropertyModel<String>(template, "alias"));
		final Label cateName=new Label("cateName",new PropertyModel<String>(template, "cateName"));
		final Label type=new Label("type", new PropertyModel<String>(template,"type"));
		final Label diskPath=new Label("diskPath",new PropertyModel<String>(template, "diskPath"));
		
		final TextArea<String> content=new TextArea<String>("content", new PropertyModel<String>(tmplContent,"content"));
		TinySettingBean.initSetting(content, form);
		
		final FileUploadField file=new FileUploadField("file");
		final UploadProgressBar progress=new UploadProgressBar("progress", form,file);
		
		AjaxSubmitLink btnAdd=new AjaxSubmitLink("btnAdd",form)
		{
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				target.add(feedbackPanel);
			}
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				if(StringUtils.isEmpty(tmplContent.getContent()))
				{
					error("没有模板内容!");
					target.add(feedbackPanel);
					return ;
				}
				
				try
				{
					templateService.save(template, tmplContent);

					File fileDir = new File(WebUtil.getWebRoot().concat(savedDir));
					if (!fileDir.exists())
					{
						fileDir.mkdirs();
					}
					String fullPaht = rootDir.concat(File.separator)
							.concat(savedDir).concat(File.separator)
							.concat(file.getFileUpload().getClientFileName());
					file.getFileUpload().writeTo(new File(fullPaht));

				} catch (IOException e)
				{
					e.printStackTrace();
				} finally
				{
					target.add(tmplInfo);
					onClickSave(target);
				}
			}
			
		};
		
		AjaxSubmitLink btnUpload=new AjaxSubmitLink("btnUpload")
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				FileUpload fileUpload = file.getFileUpload();
				if(fileUpload==null)
				{
					error("没有文件上传");
					target.add(feedbackPanel);
					return ;
				}
				
				String tmpName=fileUpload.getClientFileName().toLowerCase();
				
				if(tmpName.endsWith(".vm")== false)
				{
					error("不是有效的模板文件(模板文件以*.vm结尾)");
					target.add(feedbackPanel);
					return ;
				}
				
				String content=StringUtils.EMPTY;
				try{
					content=new String(fileUpload.getBytes(),"UTF-8");
				}catch(UnsupportedEncodingException ex){
					error("不是有效的模板文件: "+ex.getMessage());
					target.add(feedbackPanel);
					return ;
				}
				

				String generDir=StringUtils.EMPTY;
				if (NODE_TYPE_APPLICATION.equals(node.getType()))
				{
					Application app=appsService.findById(node.getId());
					generDir=generDir.concat(app.getEngName());
				}else if(NODE_TYPE_CATEGORY.equals(node.getType()))
				{
					Application app=categoryServ.findById(node.getId()).getApp();
					generDir=generDir.concat(app.getEngName());
				}
				
				
				List<String> fullPath=new ArrayList<String>();
				if(NODE_TYPE_CATEGORY.equals(node.getType()))
				{
					buildPathForCateNode(node.getId(),fullPath);
				}
				Collections.reverse(fullPath);
				StringBuffer tmp=new StringBuffer();
				for(String i: fullPath){
					tmp.append(i);
				}
				 
				savedDir=generDir.concat(tmp.toString()); //全路径,未包含文件名
				
				
				template.setName(fileUpload.getClientFileName());
				template.setCateName(node.getName());
				template.setDiskPath(rootDir.concat(savedDir).concat(File.separator).concat(fileUpload.getClientFileName()));
				template.setAlias(fileUpload.getClientFileName());
				template.setOrderNum(0);
				template.setType(fileUpload.getContentType());
				template.setCateId(node.getId());
				template.setCategory(categoryServ.findById(node.getId()));
				
				tmplContent.setName(template.getName());
				tmplContent.setOrderNum(templateService.getMaxOrderNum(node.getId()));
				tmplContent.setContent(content);
				
			
				getFeedbackMessages().clear();
				target.add(feedbackPanel);
				
				target.add(tmplInfo);
			}
		};
		
		
		AjaxSubmitLink btnDelete=new AjaxSubmitLink("btnDelete",form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				/*file.setModelObject(new ArrayList());
				file.setDefaultModelObject(new ArrayList());*/
			
				tmplContent.setContent(StringUtils.EMPTY);
				template.setDiskPath(StringUtils.EMPTY);
				template.setType(StringUtils.EMPTY);
				
				getFeedbackMessages().clear();
				target.add(feedbackPanel);
				
				target.add(tmplInfo);
			}
		};
		
		
		AjaxLink<Void> btnCancel=new AjaxLink("btnCancel")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				onClickCancel(target);
			}
		};
		
		
		add(feedbackPanel.setOutputMarkupId(true));
		add(form);
		{
			form.add(tmplInfo.setOutputMarkupId(true));
			{
				tmplInfo.add(name);
				tmplInfo.add(alias);
				tmplInfo.add(cateName);
				tmplInfo.add(type);
				tmplInfo.add(diskPath);
				tmplInfo.add(content);
			}
			form.add(file);
			form.add(progress);
			form.add(btnUpload);
			form.add(btnDelete);
			form.add(btnAdd);
			form.add(btnCancel);
		}
	}
	
	/**
	 * 跟据栏目树结构，递归构建模板文件结构.
	 * @param id 栏目id
	 * @param fullPath -
	 * @return
	 */
	private String buildPathForCateNode(String id,List<String> fullPath) 
	{
		Category temp=categoryServ.findById(id);
		fullPath.add(File.separator.concat(temp.getEngName()));
		
		if(temp.getParentCategory()==null)
		{
			return fullPath.toString();
		}else
		{
			return buildPathForCateNode(temp.getParentCategory().getId(),fullPath);
		}
	}
	
	public TmplContent getTmplContent()
	{
		return tmplContent;
	}

	public void setTmplContent(TmplContent tmplContent)
	{
		this.tmplContent = tmplContent;
	}
	
	protected void onClickCancel(AjaxRequestTarget target)
	{
		
	}
	
	protected void onClickSave(AjaxRequestTarget target)
	{
		
	}
	
}
