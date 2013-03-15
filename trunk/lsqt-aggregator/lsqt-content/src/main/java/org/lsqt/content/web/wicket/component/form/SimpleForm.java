package org.lsqt.content.web.wicket.component.form;

import java.util.UUID;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.UnauthorizedActionException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 阻止表单重复提交的form组件.
 * @author 袁明敏
 *
 * @param <T>
 */
public class SimpleForm<T> extends Form<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final transient Logger logger = LoggerFactory.getLogger(  SimpleForm.class);
	
	private String actionToken;
	
	public SimpleForm(String id) {
		super(id,null);
		//actionToken = UUID.randomUUID().toString();
	}
	
	public SimpleForm(String id, IModel<T> model) {
		super(id, model);
		actionToken = UUID.randomUUID().toString();
	}

	
	@Override
	 public void onComponentTagBody(final MarkupStream markupStream,  final  ComponentTag openTag)
	 {
	 // render the hidden field
	 AppendingStringBuffer buffer = new AppendingStringBuffer("<div  style=\"display:none\"><input type=\"hidden\" name=\"");
	 buffer.append(getActionTokenHiddenFieldId()) 
		 	.append("\" id=\"") 
		 	.append(getActionTokenHiddenFieldId())
		 	.append("\" value=\"")
		 	.append(actionToken)
		 	.append("\" /></div>");
	 getResponse().write(buffer);
	
	  
	 super.onComponentTagBody(markupStream, openTag);
	 }
 
	@Override
	protected void onValidate() {
		super.onValidate();
		// verify that the token was provided
				String token = getRequest().getRequestParameters()
						.getParameterValue(getActionTokenHiddenFieldId()).toString();

				if (!actionToken.equals(token)) {
					logger.warn("Attempted unauthorized form submission.");
					throw new UnauthorizedActionException(this, new Action(
							"SECUREFORM.SUBMIT"));
				}
	}

	private String getActionTokenHiddenFieldId() {
		return "_actiontoken";
	}

}
