package org.lsqt.content.web.wicket.component.other;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.head.OnLoadHeaderItem;
import org.apache.wicket.markup.head.ResourceAggregator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.Response;

/**
 * 禁用鼠标右键.
 * @author mm
 *
 */
public class DisableContextMenuBehavior extends Behavior
{
	private static StringBuilder sb=new StringBuilder();
	static
	{
		sb.append("$(function(){");
		sb.append("		document.oncontextmenu = function(event) {");
		sb.append("			if (window.event) {");
		sb.append("				event = window.event;");
		sb.append("			}");
		sb.append("			try {");
		sb.append("				var the = event.srcElement;");
		sb.append("				if (!((the.tagName == 'INPUT' && the.type.toLowerCase() == 'text') || the.tagName == 'TEXTAREA')) {");
		sb.append("					return false;");
		sb.append("				}");
		sb.append("				return true;");
		sb.append("			} catch (e) {");
		sb.append("				return false;");
		sb.append("			}");
		sb.append("		}");
		sb.append("})");
	}
	
	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		HeaderItem head=OnLoadHeaderItem.forScript(sb);
		response.render(head);
	}
	 
	 
}
