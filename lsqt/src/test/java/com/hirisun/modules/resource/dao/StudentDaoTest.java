package com.hirisun.modules.resource.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;

import com.hirisun.AbstractTest;
import com.hirisun.components.dao.hibernate.AbstractHibernateDaoSupport;
import com.hirisun.components.dao.suport.Condition;
import com.hirisun.components.dao.suport.DataSet;
import com.hirisun.components.dao.suport.MatchWay;
import com.hirisun.components.dao.suport.Page;
import com.hirisun.components.dao.suport.ParamType;
import com.lsqt.modules.resource.dao.UserDaoImpl;
import com.lsqt.modules.resource.model.User;
import com.lsqt.modules.resource.service.UserService;

public class StudentDaoTest extends AbstractTest{
	private static final Logger LOGGER = Logger.getLogger(StudentDaoTest.class);
	//@Test
	public void testSessionFactory(){
		UserService us=(UserService)getBean("userService");
		User user=new User();
		user.setEmail("keke@hirisun.com");
		us.save(user);
		
		SessionFactory facotry=getBean("sessionFactory", SessionFactory.class);
		LOGGER.debug(facotry);
		Assert.assertNotNull(facotry);
	}
	
	@Test
	public void testBaseDao() throws SQLException{
		AbstractHibernateDaoSupport<User> baseDao=getBean(UserDaoImpl.class);
		/**
		
		
		
		User tempUser=new User();
		tempUser.setUserId("临时用户");
		baseDao.save(tempUser);
		
		//数据删除
		baseDao.delete(tempUser);
		baseDao.deleteById("99");
		baseDao.deleteAll();
		baseDao.deleteByIds("99","100","87");
		baseDao.executeHql("delete User u where u.userId=10");  //删除一个用户账号为10的用户
		baseDao.executeHql("delete User u where u.userId=?", new Object[]{10});
		
		baseDao.executeSql("delete from lsqt_user  where userId='张三' "); //用原生SQL删除一个用户
		baseDao.executeSql("delete from lsqt_user  where userId=?",new Object[]{"张三"});//用原生SQL占位符形式删除一个用户
		
		//数据添加
		User user=new User();
		user.setUserId("mm");
		user.setUserName("明敏");
		user.setEmail("yuanmingmin@hirisun.com");
		baseDao.save(user);
		
		
		//数据更新
		user.setUserPwd("abcdefg");
		user=baseDao.update(user);
		
		//数据查询
		baseDao.findAll();
		baseDao.findAll("userId", true); //查询所有用户并按userId排序
		
		
		User exampleUser=new User();
		exampleUser.setBirthday(new Date());
		exampleUser.setUserName("李红");
		List<User> list=baseDao.findByExample(exampleUser);  //查询符合某几个属性的用户,如:查找今天过生日并且姓名为"李红"所有女性.
		
		
		baseDao.uniqueResultByHql("select count(*) from User"); //单一结果集查询:统计所有用户数
		baseDao.uniqueResultByHql("select count(*) from User u where u.userId=?", new Object[]{"22"});
		
		baseDao.uniqueResultBySql("select count(*) from lsqt_user"); //oracle原生ＳＱＬ统计
		//baseDao.uniqueResultBySql("select count(*) from lsqt_user  where bodyWeight > ? ", new Object[]{150}) ;// oracle原生SQL查找体重大于75kg的用户人数
		
		
		//存储过程调用
		
		//DataSet ds=baseDao.excuteProcedure("getSynchronizedUser");  //执行名为"getSynchronizedUser"的存储过程,并返回一些用户
		
		
		//baseDao.excuteProcedure("getUsers",new Object[]{"男","1984"}); //以oracle为例,执行的PL/SQL代码为:　... ;execute getUsers("男","1984")
		
		
		//存储过程的入参和输出参数,输出参数以ParamType.HOLDER定义.
		//执行存储过程: 
		/*
		 CREATE OR REPLACE PROCEDURE getUser2 (
				     p_user_name IN     VARCHAR2,
				     p_out_val   OUT    VARCHAR2,　　--输出参数　
				     p_inout_val IN OUT VARCHAR2	--输入输出参数　
				  ) AS
				  BEGIN
				      
				     p_out_val := 'sucess';
				     p_inout_val := 'B';
				     select * from user where userName in(p_user_name);
				  END getUser2;
			  
		DataSet dataSet=baseDao.excuteProcedure("getUser2", 
				new Object[] { "张三",ParamType.HOLDER,ParamType.HOLDER }, 
				new int[] { ParamType.VARCHAR, ParamType.VARCHAR ,ParamType.VARCHAR});//设置输入输出参数的数据类型
		
		
		Object[] statuInfo=dataSet.getOutputParams(); //返回输出参数: {"sucess","B"}
		
		dataSet.get(0);  //返回结果集:　所有姓名为张三的数据表格
		*/	
		
		
		
		//简单(单表)分页
		Page<User> page=new Page<User>(20,1);
		page=baseDao.loadPage(page);
		System.out.println(page.getData().size());
		
		//复杂分页1
		Page<User> pg=new Page<User>(20,7);
		pg.addConditions(Condition.getInstance().gt("bodyWeight", 150).like("userName", "张", MatchWay.START)); //条件
		pg.addOrderProperties("bodyWeight", false).addOrderProperties("userId", false);  //排序
		pg=baseDao.loadPage(page);
		System.out.println(pg.getHasNextPage());
		System.out.println(pg.getCurrPageNum());
		System.out.println(pg.getHasPreviouPage());
		System.out.println(pg.getTotalPage());
		System.out.println(pg.getTotalRecord());
		
		
		//复杂分页2
		Page<User> pg2=new Page<User>(20,2);
		//pg2=baseDao.loadPageByHql("select c.* from User u , address a  where a.userId=u.userId and u.userId= ? ", new Object[]{"张三"}, pg2);
		
		//复杂分页3
		Page<Object [] > pg3=new Page<Object []>(20,1);
		//pg3=baseDao.loadPageBySql("select u.*,a.addressName from U2_USER u, U2_ADDRESS a where userId=? and  u.userId=a.userId  ", new Object[]{"张三"}, pg3);
				
	}

}
