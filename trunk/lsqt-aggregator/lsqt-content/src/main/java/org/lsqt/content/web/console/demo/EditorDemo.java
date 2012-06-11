package org.lsqt.content.web.console.demo;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.validation.validator.StringValidator;
import org.lsqt.content.web.wicket.AbstractPage;

import wicket.contrib.tinymce.TinyMceBehavior;
import wicket.contrib.tinymce.settings.TinyMCESettings;
import wicket.contrib.tinymce.settings.TinyMCESettings.Theme;

public class EditorDemo extends AbstractPage{
	public EditorDemo(){
		
		Form form=new Form("form");
		TextArea contentTextArea = new TextArea("content");
		// tinymce
		contentTextArea.add(new TinyMceBehavior(new TinyMCESettings(Theme.simple)));
		        
		contentTextArea.add(StringValidator.maximumLength(4000));
		
		form.add(contentTextArea); 
		add(form);
	}
}
