package org.lsqt.content.web.console.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.lsqt.content.web.wicket.AbstractPage;

public class AjaxDemo_AutoCompleteTextField extends AbstractPage {
	public AjaxDemo_AutoCompleteTextField(){
		Form form=new Form("form");
		add(form);
		
		AutoCompleteTextField textField = new AutoCompleteTextField( "country", new Model( "" )){
			protected Iterator getChoices(String input) {
				if(StringUtils.isEmpty(input)){
					return Collections.EMPTY_LIST.iterator();
				}
				
				Set choices=new HashSet(10);
				Locale[] locales = Locale. getAvailableLocales ();
				for (int i = 0; i < locales.length; i++) {
					final Locale locale = locales[i];
					final String country = locale.getDisplayCountry(Locale.CHINESE);
					if (country.toUpperCase().startsWith(input.toUpperCase())) {
						choices.add(country);
						if (choices.size() == 10) {
							break;
						}
					}
				}

				return choices.iterator();
			}
			
		};

		form.add(textField);
	}
}
