package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.lsqt.components.dao.suport.Condition;
import org.lsqt.components.dao.suport.MatchWay;
import org.lsqt.components.dao.suport.Page;
import org.lsqt.content.model.Application;
import org.lsqt.content.model.Category;
import org.lsqt.content.service.AppsService;
import org.lsqt.content.service.CategoryService;
import org.lsqt.content.web.wicket.ConsoleIndex;
import org.lsqt.content.web.wicket.component.dataview.SimpleDataView;
import org.lsqt.content.web.wicket.component.tree.Node;
import org.lsqt.content.web.wicket.component.tree.SimpleTree;
public class CategoryListPage extends ConsoleIndex {
	@SpringBean(name="categoryServiceImpl") CategoryService categoryServ;
	@SpringBean(name="appsServiceImpl") AppsService appsService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("serial")
	private SimpleTree tree=(SimpleTree) new SimpleTree("tree")
	{
		public java.util.List<Node> onLoadTree() 
		{
			List<Node> nodes=new ArrayList<Node>();
			
			Node root = new Node();
			root.setId(UUID.randomUUID().toString());
			root.setName(ROOT_TEXT);
			root.setType(NODE_TYPE_OTHER);

			for (Application a : appsService.findAll())
			{
				Node n = new Node(root, a.getId(), a.getName()==null ? "":"[应用]".concat(a.getName()));
				n.setType(NODE_TYPE_APPLICATION);

				List<Category> list = categoryServ.getCategoryByApp(a.getId());
				for (Category c : list)
				{
					c.setName(c.getName());
					nestedCategory(n, c, c.getSubCategories());
				}
			}
			nodes.add(root);
			return nodes;
		};
		
		@Override
		protected void onClickNode(AjaxRequestTarget target, Node node)
		{
			rebuildViewData(target,node);
			target.add(table);
		}
	}.setOutputMarkupId(true);
	
	@SuppressWarnings("serial")
	final SimpleDataView table=(SimpleDataView) new SimpleDataView("table")
	{
		protected void onLoadPage(Page page) 
		{
			//根据关键字搜索栏目
			if (StringUtils.isNotEmpty(keyWord))
			{
				categoryServ.getPageByKey(keyWord, page);
			}else
			{
				categoryServ.getPageByKey(StringUtils.EMPTY, page);
			}
		};
		
		protected void onClickUpdate(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			Category temp=(Category)rowModel.getObject();
			final ModalWindow window=getModalWindow();
			window.setTitle("修改栏目");
			window.setContent(new CategoryUpdatePanel(window.getContentId(),temp.getId())
			{
				protected void onCancelAfter(AjaxRequestTarget target) 
				{
					window.close(target);
				};
				
				protected void onUpdateAfter(AjaxRequestTarget target) 
				{
					
				};
			});
			
			window.show(target);
		};
		
		protected void onClickCreate(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			
			Category temp=(Category)rowModel.getObject();
			final ModalWindow window=getModalWindow();
			window.setTitle("添加下级栏目");
			window.setContent(new CategoryAddPanel(window.getContentId(), null, temp.getId(), window)
			{
				protected void onSaveAfter(AjaxRequestTarget target) 
				{
					/*rebuildTreeData();
					rebuildViewData(target, selectedNode);
					
					target.add(table);
					target.add(tree);*/
					window.close(target);
				};
				
				protected void onCancelAfter(AjaxRequestTarget target) 
				{
					window.close(target);
				};
			});
			window.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
			{
				@Override
				public void onClose(AjaxRequestTarget target)
				{
					
					rebuildViewData(target, tree.getSelectedNode());
					
					target.add(table);
					target.add(tree);
				}
			});
			window.show(target);
		};
		
		protected void onClickDelete(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			Category app=(Category)rowModel.getObject();
			categoryServ.deleteById(app.getId());
		
			
			rebuildViewData(target, tree.getSelectedNode());
			
			target.add(table);
			target.add(tree);
		};
	}
	.addHeadLabel(new String[]{"栏目名称","访问路径","序号","创建时间"})
	.addHeadProp(new String[]{"name","description","orderNum","createTime"})
	.setOutputMarkupId(true);
	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("ctnSearch").setOutputMarkupId(true);

	
	
	
	
	private static final String ROOT_TEXT="网站列表";
	private static final String NODE_TYPE_APPLICATION="_application";
	private static final String NODE_TYPE_CATEGORY="_category";
	private static final String NODE_TYPE_OTHER="_other";
	

	
	/**
	 * 递归构建上下级关系.
	 * @param n
	 * @param c
	 * @param subs
	 */
	private void nestedCategory(Node n,Category c, Set<Category> subs)
	{
		Node node=new Node(n,c.getId(),c.getName());
		node.setType(NODE_TYPE_CATEGORY);
		for(Category t:subs)
		{
			nestedCategory(node,t,t.getSubCategories());
		}
	}
	
	

	
	

	
	
	/**
	 * 加载栏目表格数据.
	 * @param target
	 * @param node
	 */
	private void rebuildViewData(AjaxRequestTarget target,Node node)
	{
		Page page=new Page(table.getPerPageRecord(),table.getCurrPage());
		if (NODE_TYPE_APPLICATION.equals(node.getType()))
		{
			categoryServ.getPageByApp(node.getId(), page);
			table.refresh(page);
			
		} else if (NODE_TYPE_CATEGORY.equals( node.getType()))
		{
			categoryServ.getPageByPID(node.getId(), page);
			table.refresh(page);
			
		}else if(NODE_TYPE_OTHER.equals(node.getType()))
		{
			//target.appendJavaScript("alert('请选择要添加的应用或父栏目！')");
		}
	}
	
	@SuppressWarnings({"rawtypes", "serial"})
	public CategoryListPage()
	{
		Form form=new Form("form");
		
		
		//AutoCompleteTextField
		
		final TextField<String> key=new AutoCompleteTextField<String>("key",new PropertyModel<String>(this, "keyWord")){
			
			@Override
			protected Iterator<String> getChoices(String input)
			{
				List<String> list=new ArrayList<String>();
				if(StringUtils.isNotEmpty(input))
				{
					Page page=new Page(20, 1);
					page.addConditions(new Condition().like("name", input, MatchWay.ANYWHERE));
					categoryServ.getPageByApp(tree.getSelectedNode().getId(), page);
					list.addAll(page.getData());
				}
				return list.iterator();
			}
			
		};
		key.add(new AjaxFormComponentUpdatingBehavior("onchange")
		{
			
			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				keyWord=key.getModelObject();
				if(StringUtils.isEmpty(key.getModelObject()))
				{
					keyWord=StringUtils.EMPTY;
				}
				target.add(key);
			}
		});
		
		
		AjaxLink<Void> btnSearch=new AjaxLink<Void>("btnSearch")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				Page page=new Page(table.getPerPageRecord(), table.getCurrPage());
				categoryServ.getPageByKey(keyWord, page);
				table.refresh(page);
				
				target.add(table);
			}
		};
		 
		
		@SuppressWarnings({ "unchecked"})
		AjaxLink<Void>  btnAddSub =(AjaxLink) new AjaxLink<Void>("btnAddSub")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if(tree.getSelectedNode()==null || NODE_TYPE_OTHER.equals(tree.getSelectedNode().getType()))
				{
					target.appendJavaScript("alert('请选择某个应用或父级栏目!')");
					return ;
				}
				final ModalWindow modal=table.getModalWindow();
				modal.setWindowClosedCallback(new WindowClosedCallback()
				{
					@Override
					public void onClose(AjaxRequestTarget target)
					{
						target.add(tree);
						modal.close(target);
					}
				});
			
				
				if (NODE_TYPE_APPLICATION.equals(tree.getSelectedNode().getType()))
				{
					modal.setContent(new CategoryAddPanel(modal.getContentId(),tree.getSelectedNode().getId(),null,modal)
					{
						@Override
						protected void onSaveAfter(AjaxRequestTarget target)
						{
							modal.close(target);   
						}
						
						protected void onCancelAfter(AjaxRequestTarget target) 
						{
							modal.close(target);
						};
						
					});
					modal.show(target);
				} else if (NODE_TYPE_CATEGORY.equals(tree.getSelectedNode().getType()))
				{
					modal.setContent(new CategoryAddPanel(modal.getContentId(),null,tree.getSelectedNode().getId(),modal)
					{ 
						@Override
						protected void onSaveAfter(AjaxRequestTarget target)
						{
							modal.close(target);
						}
						
						protected void onCancelAfter(AjaxRequestTarget target) 
						{
							modal.close(target);
						};
						
					});
					
					modal.show(target);
				}
			}
		};
	
		
		add(form);
		{
			form.add(tree);
			form.add(ctnSearch);
			{
				ctnSearch.add(key);
				ctnSearch.add(btnSearch);
				ctnSearch.add(btnAddSub);
			}
			form.add(table);
		}
	}
	
	
	
	private String keyWord=StringUtils.EMPTY;

	public String getKeyWord()
	{
		return keyWord;
	}

	public void setKeyWord(String key)
	{
		this.keyWord = key;
	}
}
