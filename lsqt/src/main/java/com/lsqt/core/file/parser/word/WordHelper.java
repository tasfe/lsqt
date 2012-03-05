
package com.lsqt.core.file.parser.word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.BadElementException;
import com.lowagie.text.DocWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.Table;



import java.awt.Color;


/**
 * word文件的操规操作,如:添加标题\表格\图片等
 * @author 袁明敏
 *
 */
public final class WordHelper {
	private String fullPath=null;
	private OutputStream out = null;
	private Document document = new Document(PageSize.A4);
	private List<Table> tables=new ArrayList<Table>();
	
	@SuppressWarnings("unused")
	private WordHelper(){}
	
	public WordHelper(String fullPath){
		this.fullPath=fullPath;
	}
	
	/**
	 * 关闭文件,释放资源
	 */
	public void write(){
		document.close();
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	
	/**
	 * 创建空的文档
	 * @param fullPathName
	 * @return
	 * @throws WordException
	 */
	public WordHelper createWord() throws WordException{
		try{
			out = new FileOutputStream(fullPath);
			
			RtfWriter2.getInstance(document, out);
			document.open();
			Paragraph context = new Paragraph();
			StyleSheet ss = new StyleSheet();
			List htmlList = HTMLWorker.parseToList(new StringReader(""), ss);
			for (int i = 0; i < htmlList.size(); i++) {
				com.lowagie.text.Element e = (com.lowagie.text.Element) htmlList.get(i);
				context.add(e);
			}
			document.add(context);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new WordException(ex.getMessage());
		}
		return this;
	}
	
	public WordHelper appendImage(String fullImagePath) throws WordException {
		try {
			Image img = Image.getInstance(fullImagePath);
			img.setAbsolutePosition(50, 100);// 图片添加位置
			document.add(img);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WordException("word文档添加图片失败!");
		}
		return this;
	}
	
	/**
	 * 
	 * @param properties 描述word内文字的属性,例: "size:14;color:red;BOLB:true"
	 * @return
	 */
	private static Font getFont(String properties){
		
		String [] prop=properties.split(";");
		for(String e: prop){
			System.out.println(e);
		}
		
		return FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 14);
	}
	
	

	
	public WordHelper appendText(String text,String properties)throws WordException{
		try {
			
			Paragraph context = new Paragraph();
			StyleSheet ss = new StyleSheet();
			List htmlList = HTMLWorker.parseToList(new StringReader(text), ss);
			for (int i = 0; i < htmlList.size(); i++) {
				com.lowagie.text.Element e = (com.lowagie.text.Element) htmlList.get(i);
				context.add(e);
			}
			document.add(context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WordException("添加文本失败!");
		}
		return this;
	}
	
	
	public WordHelper appendText(String text)throws WordException{
		try {
			
			Paragraph context = new Paragraph(text);
			StyleSheet ss = new StyleSheet();
			
			List htmlList = HTMLWorker.parseToList(new StringReader(text), ss);
			for (int i = 0; i < htmlList.size(); i++) {
				com.lowagie.text.Element e = (com.lowagie.text.Element) htmlList.get(i);
				context.add(e);
			}
			document.add(context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WordException("添加文本失败!");
		}
		return this;
	}
	
	
	/**
	 * 
	 * @param cols 表格有多少列
	 * @param width为数组 每列占得比例，如三列的话{40，40，20}
	 * @throws DocumentException
	 */
	public WordHelper appendTable(int cols) throws WordException{
		Table table;
		try {
			table = new Table(cols);
			//table.setWidths(proportion);
		
	    
		    table.setWidth(95); // 占页面宽度 90%
		    table.setAlignment(Element.ALIGN_CENTER);//居中显示 
		    table.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示 
		    table.setAutoFillEmptyCells(true); //自动填满 
		    table.setBorderWidth(1); //边框宽度 
		    table.setBorderColor(new Color(0, 125, 255)); //边框颜色 
		    table.setPadding(10);//衬距，看效果就知道什么意思了 
		    table.setSpacing(0);//即单元格之间的间距 
		    table.setBorder(2);//边框 	
		    
		   
		    
		    document.add(table);
		    
		} catch (Exception e) {
			e.printStackTrace();
			throw new WordException("创建word的表格失败!");
		}
		return this;
	}
	
	/**
	 * 添加word文件标题
	 * @return
	 */
	public WordHelper addTitle(String content) throws WordException{
		try {
			Font font=FontFactory.getFont(FontFactory.HELVETICA_BOLD, 48,Font.BOLD, new Color(255,0,0));
			Paragraph title=new Paragraph(content.trim(),font);
			title.setAlignment(Element.ALIGN_CENTER);//设置标题居中
		
			document.add(title);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WordException("word文档添加标题错误!");
		}
		return this;
	}
	
	/**
	 * 换行
	 * @return
	 * @throws WordException
	 */
	public WordHelper br() throws WordException{
		Paragraph content=new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
		content.setAlignment(Element.ALIGN_LEFT);	
		try {
			document.add(content);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WordException("word文件添加换行失败!");
		}
		return this;
	}
	
	public static void main(String args[]) throws WordException{
		WordHelper h=new WordHelper("/home/mm/test.doc");
		h.createWord().appendText("我是中国人")
				.appendText("你是美国人")
				.appendImage("/home/mm/1.png")
				.br()
				.addTitle("这是一个title")
				.appendText("<p>这是段落</p>")
				.br()
				.appendTable(3)
				.write();
		
		//getFont("size:14;color:red;bolb:true");
	}

	
	
	/**
	public static void main(String[] args) throws Exception {
		
		OutputStream out = new FileOutputStream("/home/mm/a.doc");
		Document document = new Document(PageSize.A4);
		RtfWriter2.getInstance(document, out);
		document.open();
		Paragraph context = new Paragraph();
		String s = "<p>我是中国人</p>  <img width=\"800\" height=\"600\" alt=\"\" src=\"http://www.iteye.com/upload/logo/user/329199/d399b53f-2ad9-36ea-b6c5-b2221c7f31f5.jpg\">";
		System.out.println(s);
		
		Image img = Image.getInstance("/home/mm/1.jpg");
		img.setAbsolutePosition(50, 100);//
		
		document.add(img);
		
		Image img2 = Image.getInstance("/home/mm/2.jpg");
		img2.setAbsolutePosition(50, 100);//
		document.add(img2);
		
		StyleSheet ss = new StyleSheet();
		List htmlList = HTMLWorker.parseToList(new StringReader(s), ss);
		for (int i = 0; i < htmlList.size(); i++) {
			com.lowagie.text.Element e = (com.lowagie.text.Element) htmlList.get(i);
			context.add(e);
		}
		document.add(context);
		document.close();
		System.out.println("ok");
	}
	**/
	
}