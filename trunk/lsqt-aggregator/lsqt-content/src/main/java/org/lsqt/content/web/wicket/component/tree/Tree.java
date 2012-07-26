package org.lsqt.content.web.wicket.component.tree;

import org.apache.wicket.extensions.markup.html.tree.DefaultAbstractTree;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class Tree extends Panel{
	
	private static final ResourceReference CSS = new PackageResourceReference(Tree.class, "css/zTreeStyletree.css");
	public Tree(String id){
		super(id);
	}
}
