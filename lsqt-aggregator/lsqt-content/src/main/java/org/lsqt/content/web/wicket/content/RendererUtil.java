package org.lsqt.content.web.wicket.content;

import org.apache.wicket.markup.html.form.IChoiceRenderer; 
import org.apache.wicket.model.StringResourceModel;
import org.lsqt.content.model.Category;
public final class RendererUtil {
	private RendererUtil(){}
	
	public static  IChoiceRenderer<Boolean> getYesNoRenderer(){
		IChoiceRenderer<Boolean> yesOrNo=new IChoiceRenderer<Boolean>() {

			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Boolean object) {
				if(object.booleanValue()){
					//StringResourceModel str=new 
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
	
	public static  IChoiceRenderer<Category> getLabelRenderer(){
		IChoiceRenderer<Category> label=new IChoiceRenderer<Category>() {

			/**  */
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Category object) {
				return object.getName();
			}

			@Override
			public String getIdValue(Category object, int index) {
				return object.getId();
			}
		};
		return label;
	}
}
