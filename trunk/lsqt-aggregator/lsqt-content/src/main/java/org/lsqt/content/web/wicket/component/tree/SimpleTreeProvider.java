package org.lsqt.content.web.wicket.component.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class SimpleTreeProvider  implements ITreeProvider<String[]>{
	/**  */
	private static final long serialVersionUID = 1L;
	
	private List<String[]> dataTable;
	
	/**
	 * build tree data.
	 * <pre>
	 * ID         PID         LABEL
	 * 1          1            根结点
	 * 2          1            子结点
	 * 3          ...
	 * </pre>
	 * @param dataTable
	 */
	public SimpleTreeProvider(List<String[]>dataTable){
		this.dataTable=dataTable;
	}
	/**
	 * Nothing to do.
	 */
	@Override
	public void detach() {
	}
	
	@Override
	public Iterator<? extends String[]> getRoots() {
		List<String[]> list = new ArrayList<String[]>();
		for (String[] currItem : dataTable) {
			if (currItem[0] == currItem[1]) { // root item condition
				list.add(currItem);
				break;
			}

			if ("".equals(currItem[1])) { // or root item condition
				list.add(currItem);
				break;
			}
		}
		return list.listIterator();
	}
	
	@Override
	public boolean hasChildren(String[] node) {

		boolean hasChild = false;
		for (String[] currItem : dataTable) {
			if (currItem[1].equals(node[0])) {
				return true;
			}
		}
		return hasChild;
	}
	@Override
	public Iterator<? extends String[]> getChildren(String[] node) {
		List<String[]> list = new ArrayList<String[]>();
		for (String[] currItem : dataTable) {
			if (currItem[1].equals(node[0])) {
				list.add(currItem);
			}
		}
		return list.listIterator();
	}
	@Override
	public IModel<String[]> model(String[] object) {
		
		return new SimpleTreeModel(object);
	}

	public String[] getNode(String id){
		for (String[] currItem : dataTable) {
			if(id.equals(currItem[0])){
				return currItem;
			}
		}
		return null;
	}
	
	private class  SimpleTreeModel extends LoadableDetachableModel<String[]>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String id;
		public SimpleTreeModel(String[] row)
		{
			super(row);
			id = row[0];
		}
		@Override
		protected String[] load() {
			
			return SimpleTreeProvider.this.getNode(id);
		}
		
	}
	 
}
