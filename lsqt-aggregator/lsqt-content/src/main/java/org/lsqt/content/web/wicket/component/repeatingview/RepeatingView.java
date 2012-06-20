package org.lsqt.content.web.wicket.component.repeatingview;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;

public class RepeatingView extends Panel{
	public RepeatingView(String id){
		super(id);
		
		List<Book> books=new ArrayList<Book>();
		for (int i = 1; i < 10; i++) {
			Book book = new Book();
			book.setId(String.valueOf(i));
			book.setAuthor("author" + i);
			book.setTitle("title" + i);
			books.add(book);
		}

		
		
	}
}
