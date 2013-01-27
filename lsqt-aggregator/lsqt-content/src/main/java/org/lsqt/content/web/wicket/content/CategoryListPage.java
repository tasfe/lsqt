package org.lsqt.content.web.wicket.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
	
	final SimpleDataView ctnCategoryList=(SimpleDataView) new SimpleDataView("categoryList")
	.addHeadLabel(new String[]{"栏目名称","访问路径","序号","创建时间"})
	.addHeadProp(new String[]{"name","description","orderNum","createTime"})
	.setOutputMarkupPlaceholderTag(true);
	
	final WebMarkupContainer ctnSearch=(WebMarkupContainer) new WebMarkupContainer("ctnSearch").setOutputMarkupId(true);
/*	final DropDownChoice<Category> addSub = (DropDownChoice<Category>) new DropDownChoice<Category>("addSub", 
			new PropertyModel(this, "selects"),
			new PropertyModel(this, "displays"),
			RendererUtil.getLabelRenderer())
			.setOutputMarkupId(true)
			.setOutputMarkupPlaceholderTag(true);*/
	
	
	private Node selectedNode;
	
	
	private static final String ROOT_TEXT="网站列表";
	private static final String APPLICATION="_application";
	private static final String CATEGORY="_category";
	private static final String OTHER="_other";
	
	
	private static final String DEF_ITEM_LABEL="---添加子栏目---";
	
	private Category selects = new Category();
	private List<Category> displays = new ArrayList<Category>();
	
	private final static Category defItem = new Category();
	{
		defItem.setId(UUID.randomUUID().toString());
		defItem.setName(DEF_ITEM_LABEL);
		displays.add(defItem);
	}
	
	private void nestedCategory(Node n,Category c, Set<Category> subs){
		Node node=new Node(n,c.getId(),c.getName());
		node.setTag(CATEGORY);
		for(Category t:subs)
		{
			nestedCategory(node,t,t.getSubCategories());
		}
	}
	
	
	final List<Node> nodes = new ArrayList<Node>();
	private void freshTree()
	{
		nodes.clear();
		
		Node root = new Node();
		root.setId(UUID.randomUUID().toString());
		root.setName(ROOT_TEXT);
		root.setTag(OTHER);

		for (Application a : appsService.findAll())
		{
			Node n = new Node(root, a.getId(), a.getName());
			n.setTag(APPLICATION);

			List<Category> list = categoryServ.getCategoryByApp(a.getId());
			for (Category c : list)
			{
				nestedCategory(n, c, c.getSubCategories());
			}
		}
		nodes.add(root);
	}
	
	public CategoryListPage()
	{
		
		
		freshTree();
		
		final SimpleTree tree = (SimpleTree) new SimpleTree("tree", nodes)
		{
			@Override
			protected void onClickNode(AjaxRequestTarget target, Node node)
			{
				Page page=new Page(20,1);
				selectedNode=node;
				if (APPLICATION.equals(node.getTag()))
				{
					categoryServ.getCategoryByApp(node.getId(), page);
					ctnCategoryList.refresh(page);
					
					displays.clear();
					displays.add(defItem);
					displays.addAll(page.getData());
					
					
					
					target.add(ctnSearch);
					target.add(ctnCategoryList);
					
				} else if (CATEGORY.equals( node.getTag()))
				{
					categoryServ.getCategoryByPID(node.getId(), page);
					ctnCategoryList.refresh(page);
					
					displays.clear();
					displays.add(defItem);
					displays.addAll(page.getData());
					
				
					
					target.add(ctnSearch);
					target.add(ctnCategoryList);
				}else if(OTHER.equals(node.getTag() ))
				{
					
					
				}
			}
		}.setOutputMarkupPlaceholderTag(true);
		
		
		
		AutoCompleteTextField<String> key=new AutoCompleteTextField<String>("key",new PropertyModel<String>(this, "keyWord")){
			@Override
			protected Iterator<String> getChoices(String input)
			{
				List<String> list=new ArrayList();
				if(StringUtils.isNotEmpty(input))
				{
					Page page=new Page(20, 1);
					page.addConditions(new Condition().like("name", input, MatchWay.ANYWHERE));
					categoryServ.getCategoryByApp(tree.getSelectedNode().getId(), page);
					list.addAll(page.getData());
				}
				return list.iterator();
			}
		};
		
		AjaxLink<Void> btnSearch=new AjaxLink<Void>("btnSearch")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				Page page=new Page(20, 1);
				page.addConditions(new Condition().like("name", getKeyWord(), MatchWay.ANYWHERE));
				categoryServ.getCategoryByApp(tree.getSelectedNode().getId(), page);
				ctnCategoryList.refresh(page);
				
				target.add(ctnCategoryList);
			}
		};
		 
		
		AjaxLink<Void>  btnAddSub =(AjaxLink) new AjaxLink<Void>("btnAddSub")
		{
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if(tree.getSelectedNode()==null)
				{
					target.appendJavaScript("alert('请选择某个应用或父级栏目!')");
					return ;
				}
				ModalWindow modal=ctnCategoryList.getModalWindow();
				modal.setWindowClosedCallback(new WindowClosedCallback()
				{
					@Override
					public void onClose(AjaxRequestTarget target)
					{
						freshTree();
						tree.refresh(nodes);
						target.add(tree);
					}
				});
				
				if (APPLICATION.equals(tree.getSelectedNode().getTag()))
				{
					modal.setContent(new CategoryAddPanel(modal.getContentId(),tree.getSelectedNode().getId(),null,modal));
					modal.show(target);
				} else if (CATEGORY.equals(tree.getSelectedNode().getTag()))
				{
					modal.setContent(new CategoryAddPanel(modal.getContentId(),null,tree.getSelectedNode().getId(),modal));
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
