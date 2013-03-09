package org.lsqt.content.web.wicket.content;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.model.Template;
import org.lsqt.content.model.TmplContent;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.service.NewsService;
import org.lsqt.content.service.TemplateService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.form.SimpleForm;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;
import org.lsqt.content.web.wicket.util.WebUtil;

/**
 * 站点内容的模板管理,模版主要是Velocity文件.
 * @author 袁明敏
 *
 */
@SuppressWarnings("serial")
public class TemplateListPage extends ConsoleIndex
{
	@SpringBean(name="newsServiceImpl") NewsService newsServ;
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	@SpringBean(name="templateServiceImpl") TemplateService templateService;
	
	private static final String ROOT_TEXT="网站列表";
	public static final String NODE_TYPE_APPLICATION="_application";
	public static final String NODE_TYPE_CATEGORY="_category";
	public static final String NODE_TYPE_OTHER="_other";
	
	/**
	 * 获取当前栏目下的模板(一层).
	 * 该方法用于用户点击上传、点击栏目节点后，刷新表格数据的通用方法.
	 * @param target -
	 * @param node -
	 */
	private void load(AjaxRequestTarget target,Node node)
	{
		Page<Template> page=templateService.getPageByCategoryId(node.getId(), table.getCurrPage(), table.getPerPageRecord());
		table.refresh(page);
		target.add(table);
	}
	
	final SimpleTree tree;
	final SimpleDataView table;
	public TemplateListPage()
	{
		freshTree();
		tree=new SimpleTree("tree")
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				load(target,node);
			}
		};
		
		table =(SimpleDataView)new SimpleDataView("table")
		{
			protected void onClickDelete(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
			{
				Template templ=(Template)rowModel.getObject();
				templateService.deleteById(templ.getId());
				load(target,tree.getSelectedNode());
			};
		}.addHeadLabel(new String[]{"文件名","文件别名","所属栏目","类型"})
		.addHeadProp(new String []{"name","alias","cateName","type"})
		.setOutputMarkupId(true);
		table.setVisibleForCreateButton(false);
		
		
		final FileUploadField fileUploadFiled=new FileUploadField("file");
		
		Form  <Void> form=new Form<Void>("form");
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(2000)); // Set maximum size to 2000K for demo purposes
		
		AjaxSubmitLink btnUpload=new AjaxSubmitLink("btnUpload",form)
		{
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				
					FileUpload fileUpload = fileUploadFiled.getFileUpload();
				
					if(fileUpload!=null)
					{
						try
						{
							String path=WebUtil.getWebRoot();
							Node node=tree.getSelectedNode();
							if (NODE_TYPE_APPLICATION.equals(node.getType()))
							{
								Application app=appsService.findById(node.getId());
								path=path.concat(app.getEngName());
							}else if(NODE_TYPE_CATEGORY.equals(node.getType()))
							{
								Application app=categoryServ.findById(node.getId()).getApp();
								path=path.concat(app.getEngName());
							}
							//以上构建服务器布署跟路径: .../lsqt-conent/aaa1/
							
							List<String> fullPath=new ArrayList<String>();
							if(NODE_TYPE_CATEGORY.equals(node.getType()))
							{
								buildPathForCateNode(node.getId(),fullPath);
							}
							Collections.reverse(fullPath);
							StringBuffer tmp=new StringBuffer();
							for(String i: fullPath){
								tmp.append(File.separator.concat(i));
							}
							 
							String wholePath=path.concat(tmp.toString()
									.concat(File.separator)
									.concat(fileUpload.getClientFileName())); //全路径,包含文件名
							path=path.concat(tmp.toString());
							
							Template template=new Template();
							template.setName(fileUpload.getClientFileName());
							
							template.setCateName(tree.getSelectedNode().getName());
							template.setDiskPath(wholePath );
							template.setAlias( fileUpload.getClientFileName());
							template.setOrderNum(0);
							template.setType(fileUpload.getContentType());
							template.setCateId(tree.getSelectedNode().getId());
							template.setCategory(categoryServ.findById(tree.getSelectedNode().getId()));
							
							
							TmplContent tmplContent=new TmplContent();
							tmplContent.setOrderNum(templateService.getMaxOrderNum(tree.getSelectedNode().getId()));
							tmplContent.setBytes(fileUpload.getBytes());
							
							templateService.save(template, tmplContent);
							
							
							File file = new File(path);
							if (!file.exists())
							{
								file.mkdirs();
							}
							fileUpload.writeTo(new File(wholePath));

							
							TemplateListPage.this.info("saved file: "+ fileUpload.getClientFileName());
							
							load(target, tree.getSelectedNode());
							
						} catch (IOException e)
						{
							e.printStackTrace();
						}
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
		};
		
		
		
		UploadProgressBar	progress=new UploadProgressBar("progress", form,fileUploadFiled);
		
		
		
		add(form);
		{
			form.add(btnUpload);
			form.add(fileUploadFiled);
			form.add(progress);
			form.add(tree);
			form.add(table);
		}
		
		
		
	}
	
	public static void main(String args[])
	{
		File file=new File("/home/mm/soft/apache-tomcat-6.0.20/webapps/lsqt-content/33");
		if(!file.exists())
		{
			file.mkdirs();
		}
	}
	
	final List<Node> treeNodes = new ArrayList<Node>();
	/**
	 * 刷新树结构,保持数据与数据库同步.
	 */
	private void freshTree()
	{
		treeNodes.clear();
		
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName(ROOT_TEXT);
		root.setType(NODE_TYPE_OTHER);

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName());
			n.setType(NODE_TYPE_APPLICATION);

			List<Category> list = categoryServ.getCategoryByApp(a.getId());
			for (Category c : list)
			{
				nestedCategory(n, c, c.getSubCategories());
			}
		}
		treeNodes.add(root);
	}
	
	/**
	 * freshTee()的辅助方法,递归设置结点上下级结构关系.
	 * @param n 当前结点,每一个category别作为一个node放置
	 * @param c 类别 当前结点承载的类别
	 * @param subs 当前结点的子类别
	 */
	private void nestedCategory(Node n, Category c, Set<Category> subs)
	{
		Node node = new Node(n, c.getId(), c.getName());
		node.setType(NODE_TYPE_CATEGORY);
		for (Category t : subs)
		{
			nestedCategory(node, t, t.getSubCategories());
		}
	}
	
	
}
