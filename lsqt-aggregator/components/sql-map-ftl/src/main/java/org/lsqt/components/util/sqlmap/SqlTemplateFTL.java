package org.lsqt.components.util.sqlmap;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SqlTemplateFTL {
	
	private static Configuration config = new Configuration();
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	public static String getStatement(Class clazz, Map param) throws IOException, TemplateException {
		config.setClassForTemplateLoading(clazz, "");
		Template template = config.getTemplate("SysDataSourceController.sql.xml");
		StringWriter sw = new StringWriter();
		template.process(param, sw);
		return sw.toString();
	}
	
	public static void main(String args[]) throws IOException, TemplateException, ParserConfigurationException, SAXException{
		User user=new User();
		Map root=new HashMap();
		user.setName("jack");
		user.setId(1L);
		user.setBirthday(new Date());
		user.setAddress(null);
		root.put("user",user);
		
		
		User user2=new User();
		user2.setName("lilei");
		user2.setId(2L);
		user2.setBirthday(new Date());
		user2.setAddress("北京");
		
		User user3=new User();
		user3.setName("xiaoming");
		user3.setId(3L);
		user3.setBirthday(new Date());
		user3.setAddress("上海");
		
		List<User> list=new ArrayList<User>();
		list.add(user);
		list.add(user2);
		list.add(user3);
		root.put("userList",list);
		
		root.put("key", "张三");
		
		long begin=System.currentTimeMillis();
		String sql=getStatement(SqlTemplateFTL.class, root);
		//long end=System.currentTimeMillis();
	//	System.out.println("=============>"+(end-begin));
		
		//System.out.println(sql);
		
		
	//	begin=System.currentTimeMillis();
		
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(sql.trim())));
        Element xmlRoot = doc.getDocumentElement();
        NodeList optionNodeList = xmlRoot.getChildNodes();
        if(optionNodeList!=null){
        	 int totalNode = optionNodeList.getLength();
             for (int i=0;i<totalNode;i++) {
            	 Node optionNode = optionNodeList.item(i);
            	 System.out.println( optionNode.getTextContent().replaceAll("\n","").replaceAll("\r", "").trim());
             }
        }
		long end=System.currentTimeMillis();
		//System.out.println(end-begin);
	}
	
	
	public static void main333(String[] args){

		String xmlStr = "<user><username>zhoub</username><password>123456</password></user>";
		
		        try
		        {
		            DocumentBuilder builder = factory.newDocumentBuilder();
		            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
		            Element root = doc.getDocumentElement();
		            NodeList optionNodeList = root.getChildNodes();
		            if(optionNodeList!=null)
		            {
		                int totalNode = optionNodeList.getLength();
		                for (int i=0;i<totalNode;i++)
		                {
		                    Node optionNode = optionNodeList.item(i);
		                    System.out.println(optionNode.getNodeName()+" - "+optionNode.getNodeType()+" - "+optionNode.getNodeValue()+" - "+optionNode.getTextContent());
		                }
		            }
		        }
		        catch(Exception e)
		        {
		            e.printStackTrace();
		        }

		}
}
