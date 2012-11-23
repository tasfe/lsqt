package org.lsqt.content.web.wicket.content;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import java.awt.Checkbox;
import java.awt.peer.ButtonPeer;
public class RendererUtil {
	
	
	public static  IChoiceRenderer<Boolean> getYesNoRenderer(){
		IChoiceRenderer<Boolean> yesOrNo=new IChoiceRenderer<Boolean>() {

			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Boolean object) {
				if(object.booleanValue()){
					return "是";
				}else{
					return "否";
				}
			}

			@Override
			public String getIdValue(Boolean object, int index) {
				return object.toString();
			}
		};
		return yesOrNo;
	}
	
}
