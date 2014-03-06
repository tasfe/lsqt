package weixin.popular.util;

import java.io.Reader;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import weixin.popular.bean.EventMessage;

/**
 * XML 数据接收对象转换工具类
 *
 */
public class EventMessageXMLConverUtil {
	
	private static Unmarshaller unmarshaller;
	
	static{
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(EventMessage.class);
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static EventMessage convert(String xml){
		try {
			return (EventMessage) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static EventMessage convert(Reader reader){
		try {
			return (EventMessage) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
