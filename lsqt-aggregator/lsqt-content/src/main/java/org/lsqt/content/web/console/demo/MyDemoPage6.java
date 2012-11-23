package org.lsqt.content.web.console.demo;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.RenderedDynamicImageResource;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.util.lang.Bytes;
import org.lsqt.content.web.wicket.AbstractPage;

public class MyDemoPage6 extends AbstractPage{
	public MyDemoPage6(){
		final Map<String,String> map=new HashMap<String,String>();
		map.put("key1",  "The Server Side1............");
		map.put("key2",  "The Server Side2.......................");
		map.put("key3",  "The Server Side3...................................");
		
		IChoiceRenderer<String> render=new ChoiceRenderer<String>(){
			public Object getDisplayValue(String object) {
				return map.get(object);
			}
		};
		
		List SITE=new ArrayList<String>(map.keySet());
		DropDownChoice down=new DropDownChoice("dropDwonList",SITE );  //下拉列表
		/*
		down.setRequired(true);
		down.setNullValid(true);
		*/
		down.setNullValid(true);
		down.setChoiceRenderer(render);
		down.setEnabled(true);
		add(down);
		
		ListChoice temp1=new ListChoice("temp1",SITE);
		/*IModel model=new Model();
		model.setObject(SITE);*/
		temp1.setRequired(false);
		temp1.setChoiceRenderer(render);
		temp1.setEscapeModelStrings(true);
		temp1.setNullValid(true);
		add(temp1);
		
		ListMultipleChoice temp2=new ListMultipleChoice("temp2",SITE);
		temp2.setChoiceRenderer(render);
		add(temp2);
		
		RadioChoice radios=new RadioChoice("radios",SITE,render);
		add(radios);
		
		RadioGroup group=new RadioGroup("group");
		add(group);
		
		ListView listView=new ListView("items",SITE){
			protected void populateItem(ListItem item) {
				Object key=item.getModelObject();
				
				Radio radio=new Radio("itemCheck",new Model((Serializable)map.get(key)));
				item.add(radio);
				
				Label lbl=new Label("itemValue",new Model(key.toString()));
				item.add(lbl);
			}
		};
		
		group.add(listView);
		
		Image img=new Image("staticImg","MyDemoPage6.png");
		add(img);
		
		//动态图片
		Image img2 = new Image("dynamicImg", new RenderedDynamicImageResource(100, 100) {
			@Override
			protected boolean render(Graphics2D graphics, Attributes attributes) {
//				graphics.drawString("测试", 10, 10);
				try {
					graphics.drawBytes("测试".getBytes("UTF-8"), 0, "测试".getBytes("UTF-8").length, 10, 10);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		add(img2);
		
		//实现ResourceReference接口，可从数据库内获取图片等，不知道怎么用还
		Image img3= new Image("dynamicImg2",new ResourceReference(""){
			public IResource getResource() {
				return new IResource() {
					public void respond(Attributes attributes) {
						try {
							attributes.getResponse().write("test".getBytes("UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				};
			}
		});
		add(img3);
		
		//文件上传
		final FileUploadField file=new FileUploadField("file");
		Form form=new Form("form"){
			protected void onSubmit() {
				FileUpload upload= file.getFileUpload();
				if(upload!=null){
					try {
						String fileName=upload.getClientFileName();
						upload.writeTo(new File("/home/mm/"+fileName));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		form.add(file);
		form.setMultiPart(true);
		form.setMaxSize(Bytes.kilobytes(1000));
		add(form);
		
		
	}

}
