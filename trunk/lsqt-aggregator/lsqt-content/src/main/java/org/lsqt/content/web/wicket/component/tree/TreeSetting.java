package org.lsqt.content.web.wicket.component.tree;

import org.apache.wicket.markup.html.form.Form;

public class TreeSetting {
	public TreeSetting(){
	}
	
	/**zTree 的唯一标识，初始化后，等于 用户定义的 zTree 容器的 id 属性值**/
	private String treeId;
	/**zTree 容器的 jQuery 对象，主要功能：便于操作**/
	private String treeObj;
	
}
