package org.lsqt.content.web.wicket.content.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class NodeProvider implements ITreeProvider<Node>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeProvider( List<Node> nodes){
		this.nodes=nodes;
	}
	
	private  List<Node> nodes = new ArrayList<Node>();
	
	public Node getNode(String id)
	{
		return findNode(nodes, id);
	}

	private  Node findNode(List<Node> foos, String id)
	{
		for (Node foo : foos)
		{
			if (foo.getId().equals(id))
			{
				return foo;
			}

			Node temp = findNode(foo.getSubNodes(), id);
			if (temp != null)
			{
				return temp;
			}
		}

		return null;
	}
	
	
	@Override
	public void detach() {
		
		
	}

	@Override
	public Iterator<? extends Node> getRoots() {
		return nodes.iterator();
	}

	@Override
	public boolean hasChildren(Node node) {
		return node.getParent() == null || !node.getSubNodes().isEmpty();
	}

	@Override
	public Iterator<? extends Node> getChildren(Node node) {
		
		return node.getSubNodes().iterator();
	}

	@Override
	public IModel<Node> model(Node object) {
		
		return new NodeModel(object);
	}

	
	class NodeModel extends LoadableDetachableModel<Node>{
		private static final long serialVersionUID = 1L;

		private final String id;
		public NodeModel(Node node)
		{
			super(node);
			id = node.getId();
		}
		@Override
		protected Node load() {
			return NodeProvider.this.getNode(id);
		}
		
		/**
		 * Important! Models must be identifyable by their contained object.
		 */
		@Override
		public boolean equals(Object obj)
		{
			if (obj instanceof NodeModel)
			{
				return ((NodeModel)obj).id.equals(id);
			}
			return false;
		}

		/**
		 * Important! Models must be identifyable by their contained object.
		 */
		@Override
		public int hashCode()
		{
			return id.hashCode();
		}
	}
}

