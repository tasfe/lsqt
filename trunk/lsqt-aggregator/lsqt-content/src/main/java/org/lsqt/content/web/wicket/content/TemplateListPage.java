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
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.PageCreator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
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
	
	final SimpleTree tree=new SimpleTree("tree")
	{
		public java.util.List<Node> onLoadTree() 
		{
			return rebuildTreeData();
		};
		@Override
		protected void onClickNode(AjaxRequestTarget target, Node node)
		{
			load(target,node);
		}
	};
	
	final SimpleDataView table =(SimpleDataView)new SimpleDataView("table")
	{
		protected void onClickUpdate(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			final ModalWindow window=getModalWindow();
			final Template tmpl=(Template)rowModel.getObject();
			window.setInitialWidth(800);
			window.setInitialHeight(600);
			window.setPageCreator(new PageCreator()
			{
				@Override
				public org.apache.wicket.Page createPage()
				{
					return null;
				}
			});
			
			window.setWindowClosedCallback(new WindowClosedCallback()
			{
				@Override
				public void onClose(AjaxRequestTarget target)
				{
					
				}
			});
			window.show(target);
		};
		
		protected void onClickDelete(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			Template templ=(Template)rowModel.getObject();
			templateService.deleteById(templ.getId());
			load(target,tree.getSelectedNode());
		};
	}.addHeadLabel(new String[]{"所属栏目","模板名","模板简称","类型"})
	.addHeadProp(new String []{"cateName","name","alias","type"})
	.setVisibleForCreateButton(false)
	.setOutputMarkupId(true);
	
	
	public TemplateListPage()
	{
		Form  <Void> form=new Form<Void>("form");
		
		
		AjaxLink<Void> btnAdd=new AjaxLink<Void>("btnAdd")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (tree.getSelectedNode() == null
						|| (!NODE_TYPE_CATEGORY.equals(tree.getSelectedNode().getType())))
				{
					target.appendJavaScript("alert('请选择一个栏目!')");
					return;
				}
				
				final ModalWindow window=table.getModalWindow();
				window.setTitle("添加模板");
				window.setInitialWidth(800);
				window.setInitialHeight(600);
				window.setPageCreator(new PageCreator()
				{
					
					@Override
					public org.apache.wicket.Page createPage()
					{
						return new TemplateAddPanel(tree.getSelectedNode())
						{
							@Override
							protected void onClickSave(AjaxRequestTarget target)
							{
								window.close(target);
							}
							
							@Override
							protected void onClickCancel( AjaxRequestTarget target)
							{ 
								window.close(target);
							}
						};
					}
				});
				window.show(target);
			}
		};
		
		
		
		
		
		add(form);
		{
			form.add(btnAdd);
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
	
	
	/**
	 * 刷新树结构,保持数据与数据库同步.
	 */
	private List<Node> rebuildTreeData()
	{
		List<Node> treeNodes = new ArrayList<Node>();
		
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
		
		return treeNodes;
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
