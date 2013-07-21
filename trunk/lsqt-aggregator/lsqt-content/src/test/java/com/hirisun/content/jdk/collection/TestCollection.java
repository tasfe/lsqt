package com.hirisun.content.jdk.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TestCollection
{
	
	private String test;
	 class Test{
		
	}
	
	ArrayList<Student> list=new ArrayList<Student>();
	{
		long start=System.currentTimeMillis();
		for(int i=0;i<1;i++) //初使化5百万个对象,初使化时间: 30675ms, 执行时间: 6573ms
			
		{
			Student st=new Student(i+"",1,1F,1D);
			list.add(st);
		}
		long end=System.currentTimeMillis();
		System.out.println("初使化时间: "+(end-start)+"ms");
	}
	
	public void test()
	{
		long start=System.currentTimeMillis();
		for(Student s: list){
			s.toString();
		}
		long end=System.currentTimeMillis();
		System.out.println("执行时间: "+(end-start)+"ms");
	}
	
	public void testAddNull()
	{
		ArrayList list=new ArrayList();
		list.add(null);
		System.out.println(list);
		
		LinkedList list2=new LinkedList();
		list2.add(null);
		
		HashSet set=new HashSet();
		set.add(null);
		
		HashMap<Object,Student> map=new HashMap();
		map.put(null, Student.oneStudent);
		System.out.println(map.get(null).getId());
		
		Hashtable t=new Hashtable();
		t.put(null, Student.oneStudent);
		System.out.println(map.get(null).getId());
	}
	
	
	
	public static void main(String args[]) throws ClassNotFoundException
	{
		TestCollection test=new TestCollection();
		char tt='我';
		System.out.println(tt);
		//test.test();
		test.testAddNull();
		//System.out.println(Student.oneStudent.testFinallyReturn());
		
		/*try
		{
			Student a=(Student)Student.class.newInstance();
			Student b=(Student)Student.class.newInstance();
			String id="张三";
			a.setId(id);
			b.setId(id);
			System.out.println(a.equals(b));
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	//	SessionFactoryUtils.getSession(sessionFactory, allowCreate)
		//TransactionSynchronizationManager
	}
}

class Student 
{
	public static Student oneStudent=new Student("我是张三",1,1F,1D);
	public Student(){}
	public Student(String id,Integer age,Float salary,Double hairs)
	{
		this.id=id;
		this.age=age;
		this.salary=salary;
		this.hairs=hairs;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public Integer getAge()
	{
		return age;
	}
	public void setAge(Integer age)
	{
		this.age = age;
	}
	public Float getSalary()
	{
		return salary;
	}
	public void setSalary(Float salary)
	{
		this.salary = salary;
	}
	public Double getHairs()
	{
		return hairs;
	}
	public void setHairs(Double hairs)
	{
		this.hairs = hairs;
	}
	String id;
	Integer age;
	Float salary;
	Double hairs;

	public int testFinallyReturn()
	{
		int i=99;
		try{
			if(i==99)
			{
				throw new RuntimeException("equal 99 Excetion!");
			}
			return 1000;
		}catch(Exception e){
			e.printStackTrace();
			return 2000;
		}finally{
			return 3000;
		}
		
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
