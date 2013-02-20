package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.PageCreator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.form.select.SelectOption;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SpringBean CategoryService categoryServ;
	@SpringBean AppsService appsService;
	
	private SimpleTree tree=null; 
	
	@SuppressWarnings("serial")
	final SimpleDataView ctnCategoryList=(SimpleDataView) new SimpleDataView("categoryList")
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
					rebuildTreeData();
					tree.refresh(nodes);
					rebuildViewData(target, selectedNode);
					
					target.add(ctnCategoryList);
					target.add(tree);
					
					window.close(target);
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
					
					target.add(ctnCategoryList);
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
					rebuildTreeData();
					tree.refresh(nodes);
					rebuildViewData(target, selectedNode);
					
					target.add(ctnCategoryList);
					target.add(tree);
				}
			});
			window.show(target);
		};
		
		protected void onClickDelete(AjaxRequestTarget target, org.apache.wicket.model.IModel<Object> rowModel) 
		{
			Category app=(Category)rowModel.getObject();
			categoryServ.deleteById(app.getId());
			rebuildTreeData();
			
			rebuildViewData(target,selectedNode);
			
			target.add(ctnCategoryList);
			target.add(tree);
		};
	}
	.addHeadLabel(new String[]{"栏目名称","访问路径","序号","创建时间"})
	.addHeadProp(new String[]{"name","description","orderNum","createTime"})
	.setOutputMarkupId(true);
	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("ctnSearch").setOutputMarkupId(true);

	
	
	private Node selectedNode;
	
	
	private static final String ROOT_TEXT="网站列表";
	private static final String NODE_TYPE_APPLICATION="_application";
	private static final String NODE_TYPE_CATEGORY="_category";
	private static final String NODE_TYPE_OTHER="_other";
	
	
	private static final String DEF_ITEM_LABEL="---添加子栏目---";
	
	private Category selects = new Category();
	private List<Category> displays = new ArrayList<Category>();
	
	private final static Category defItem = new Category();
	{
		defItem.setId(UUID.randomUUID().toString());
		defItem.setName(DEF_ITEM_LABEL);
		displays.add(defItem);
	}
	
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
	
	
	final List<Node> nodes = new ArrayList<Node>();
	/**
	 * 重新加载树所承载的数据.
	 */
	private void rebuildTreeData()
	{
		nodes.clear();
		
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
		
	}
	
	
	

	
	
	/**
	 * 加载栏目表格数据.
	 * @param target
	 * @param node
	 */
	private void rebuildViewData(AjaxRequestTarget target,Node node)
	{
		Page page=new Page(20,1);
		selectedNode=node;
		if (NODE_TYPE_APPLICATION.equals(node.getType()))
		{
			categoryServ.getPageByApp(node.getId(), page);
			ctnCategoryList.refresh(page);
			
			displays.clear();
			displays.add(defItem);
			displays.addAll(page.getData());
			
			
			
			target.add(ctnSearch);
			target.add(ctnCategoryList);
			
		} else if (NODE_TYPE_CATEGORY.equals( node.getType()))
		{
			categoryServ.getPageByPID(node.getId(), page);
			ctnCategoryList.refresh(page);
			
			displays.clear();
			displays.add(defItem);
			displays.addAll(page.getData());
			
		
			
			target.add(ctnSearch);
			target.add(ctnCategoryList);
		}else if(NODE_TYPE_OTHER.equals(node.getType()))
		{
			//target.appendJavaScript("alert('请选择要添加的应用或父栏目！')");
		}
	}
	
	@SuppressWarnings({"rawtypes", "serial"})
	public CategoryListPage()
	{
		
		
		rebuildTreeData();
		
		
		tree = (SimpleTree) new SimpleTree("tree", nodes)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				rebuildViewData(target,node);
			}
		}.setOutputMarkupPlaceholderTag(true);
		
		
		
		//AutoCompleteTextField
		final TextField<String> key=new TextField<String>("key",new PropertyModel<String>(this, "keyWord")){
			/*
			@Override
			protected Iterator<String> getChoices(String input)
			{
				List<String> list=new ArrayList<String>();
				if(StringUtils.isNotEmpty(input))
				{
					Page page=new Page(20, 1);
					page.addConditions(new Condition().like("name", input, MatchWay.ANYWHERE));
					categoryServ.getCategoryByApp(tree.getSelectedNode().getId(), page);
					list.addAll(page.getData());
				}
				return list.iterator();
			}
			*/
		};
		key.add(new AjaxFormComponentUpdatingBehavior("onblur")
		{
			
			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				//keyWord=key.getModelObject();
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
				Page page=new Page(ctnCategoryList.getPerPageRecord(), ctnCategoryList.getCurrPage());
				categoryServ.getPageByKey(keyWord, page);
				ctnCategoryList.refresh(page);
				
				target.add(ctnCategoryList);
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
				final ModalWindow modal=ctnCategoryList.getModalWindow();
				modal.setWindowClosedCallback(new WindowClosedCallback()
				{
					@Override
					public void onClose(AjaxRequestTarget target)
					{
						rebuildTreeData();
						tree.refresh(nodes);
						target.add(tree);
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
					});
					modal.show(target);
				}
				
			}
		}.setOutputMarkupId(true);
	
		
		
		add(tree);
		add(ctnCategoryList);
		add(ctnSearch);
		{
			ctnSearch.add(key);
			ctnSearch.add(btnSearch);
			ctnSearch.add(btnAddSub);
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

	public Category getSelects()
	{
		return selects;
	}

	public void setSelects(Category selects)
	{
		this.selects = selects;
	}

	public List<Category> getDisplays()
	{
		return displays;
	}

	public void setDisplays(List<Category> displays)
	{
		this.displays = displays;
	}
}
