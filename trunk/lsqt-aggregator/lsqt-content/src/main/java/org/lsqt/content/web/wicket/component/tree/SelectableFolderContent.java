/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lsqt.content.web.wicket.component.tree;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree.State;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IModel;

/**
 * @author Sven Meier
 */
public class SelectableFolderContent extends Content
{

	private static final long serialVersionUID = 1L;

	private ITreeProvider<Node> provider;

	private IModel<Node> selected;

	public SelectableFolderContent(ITreeProvider<Node> provider)
	{
		this.provider = provider;
	}

	@Override
	public void detach()
	{
		if (selected != null)
		{
			selected.detach();
		}
	}

	protected boolean isSelected(Node foo)
	{
		IModel<Node> model = provider.model(foo);

		try
		{
			return selected != null && selected.equals(model);
		}
		finally
		{
			model.detach();
		}
	}

	
	protected void select(Node foo, AbstractTree<Node> tree, final AjaxRequestTarget target)
	{
		if (selected != null)
		{
			tree.updateNode(selected.getObject(), target);

			selected.detach();
			selected = null;
		}

		selected = provider.model(foo);
		tree.updateNode(foo, target);
	}

	public IModel<Node> getSeleted(){
		return selected;
	}
	
	@Override
	public Component newContentComponent(String id, final AbstractTree<Node> tree, IModel<Node> model)
	{
		return new Folder<Node>(id, tree, model)
		{
			private static final long serialVersionUID = 1L;

			/**
			 * Always clickable.
			 */
			@Override
			protected boolean isClickable()
			{
				return true;
			}

			@Override
			protected void onClick(AjaxRequestTarget target)
			{
				Node t = getModelObject();
				SelectableFolderContent.this.select(t, tree, target);
				
				
				/*if (tree.getState(t) == State.EXPANDED)
				{
					tree.collapse(t);
				}
				else
				{
					tree.expand(t);
				}*/
				
				SelectableFolderContent.this.onClick(target);
			}

			@Override
			protected boolean isSelected()
			{
				return SelectableFolderContent.this.isSelected(getModelObject());
			}
		};
	}
	
	protected void onClick(AjaxRequestTarget target){
		
	}
	//jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.81)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.0.82)(PORT=1521))(LOAD_BALANCE=YES)(FAILOVER=ON)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=orcl)(FAILOVER_MODE=(TYPE=SELECT)(METHOD=BASIC))))
	 
}