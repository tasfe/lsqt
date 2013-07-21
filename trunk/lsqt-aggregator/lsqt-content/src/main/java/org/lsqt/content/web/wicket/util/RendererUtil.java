package org.lsqt.content.web.wicket.util;

import org.apache.wicket.markup.html.form.IChoiceRenderer; 
import org.apache.wicket.model.StringResourceModel;
import org.lsqt.content.model.Category;

import com.mchange.v2.c3p0.ComboPooledDataSource;
public final class RendererUtil {
	private RendererUtil(){
		
	}
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 资源管理页,用于呈现全局资源和模板资源选项.
	 * @return
	 */
	/*
	public static  IChoiceRenderer<String> getResourceRenderer(){
		IChoiceRenderer<String> label=new IChoiceRenderer<String>() {

			*//**  *//*
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(String object) {
				return object;
			}

			@Override
			public String getIdValue(String object, int index) {
				return object.getId();
			}
		};
		return label;
	}
	*/
}
