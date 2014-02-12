package com.hirisun.content.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.lsqt.components.util.db.DataBaseUtil;

public class OtherTest {
	
	@Test
	@Ignore("数据库环境切换到本机mysql进纳入测试")
	public void otherTest(){
		boolean isOk2=DataBaseUtil.executeConnection("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8", "mm", "mm");
		Assert.assertTrue(isOk2);
	}
}
