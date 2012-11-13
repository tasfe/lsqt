package com.hirisun.content.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.lsqt.content.model.Resource;
import org.lsqt.content.service.ResourceService;
import org.lsqt.content.service.impl.ResourceServiceImpl;

import com.hirisun.AbstractTest;

public class ResourceServiceTest extends AbstractTest{
	final  ResourceService service=	getBean( ResourceServiceImpl.class);
	
	
	@Test
	public void testCRUDCategory(){
		
		Resource resourceRoot=service.getRoot();
		if(resourceRoot!=null){
			service.deleteById(resourceRoot.getId());
		}
		
		/*＊
		 * 添加类别,如下：
		 * ----资源根结点
		 * -------|-------资源二级结点A
		 * -------|-------资源二级结点B
		 * -------|-------资源结点C
		**/
		Resource c=new Resource();
		c.setResourceName("资源根结点");
		c.setParentResource(c);
		c.setHasChildNode(false);
		
		
		Resource c2_A=new Resource();
		c2_A.setResourceName("资源二级结点A");
		c2_A.setParentResource(c);
		service.save(c2_A);
		
		Resource c2_B=new Resource();
		 c2_B.setResourceName("资源二级结点B");
		 c2_B.setParentResource(c);
		service.save( c2_B);
		
		Resource c2_C = new Resource();
		c2_C.setResourceName("资源结点C");
		c2_C.setParentResource(c);
		service.save(c2_C);
		
		
		/*
		 * 移动结点,如下：
		 * ----资源根结点
		 * -------|-------资源二级结点A
		 * -------|-------资源二级结点B
		 *                       |-------------资源结点C
		 * */
		
		c2_C.setParentResource(c2_B);
		c2_C=service.update(c2_C);
		
		c.setHasChildNode(true);
		c=service.update(c);
	}
}
