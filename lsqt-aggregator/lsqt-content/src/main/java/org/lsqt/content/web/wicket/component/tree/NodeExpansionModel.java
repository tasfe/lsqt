package org.lsqt.content.web.wicket.component.tree;

import java.util.Set;

import org.apache.wicket.model.AbstractReadOnlyModel;

public class NodeExpansionModel extends AbstractReadOnlyModel<Set<Node>> {
	
		@Override
		public Set<Node> getObject()
		{
			return NodeExpansion.get();
		}
}
