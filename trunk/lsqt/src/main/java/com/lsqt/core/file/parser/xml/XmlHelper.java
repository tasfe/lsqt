package com.lsqt.core.file.parser.xml;

import java.io.StringReader;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlHelper {

	/***
	 * 
	 * 方法说明：格式化xml字符串,xmlString不在同一行显示
	 * 
	 * @param xmlString
	 * @return
	 * @throws XmlException
	 */
	public String toFormat(String xmlString) throws XmlException {
		StringWriter out=null;
		try{
			SAXReader reader = new SAXReader();
			StringReader in = new StringReader(xmlString);
			Document doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			writer.close();
		}catch(Exception xe){
			throw new XmlException("xml格试化错误!");
		}
		return out.toString();
	}
}
