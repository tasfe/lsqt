package com.hirisun.content.web.temptest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import org.junit.Test;

public class NIOStudy {
	@Test
	public void demo1() throws IOException{
		final String fin="/home/mm/test.txt";
		final String fout="/home/mm/dest.txt";
		File fi=new File(fin);
		File fo=new File(fout);
		if(fi.exists()==false){
			
		}
		FileInputStream fis=new FileInputStream(fi);
		FileOutputStream fouts=new FileOutputStream(fo);
		FileChannel finChannel= fis.getChannel();
		FileChannel foutChannel=fouts.getChannel();
		
		ByteBuffer buffer = ByteBuffer.allocate(1024*5);  
		while(true){
			buffer.clear();
			if(finChannel.read(buffer)==-1){
				break;
			}
			buffer.flip();
			foutChannel.write(buffer);
		}
	}
	
	@Test
	public void demo2() throws IOException{
		
		Charset charset=Charset.forName("GBK");
		
		InetSocketAddress  socketAddress =new InetSocketAddress("www.baidu.com", 80);
		SocketChannel channel=SocketChannel.open(socketAddress);
		
	}
	
	@Test
	public void testIO() throws IOException{
		/*
		final String fin="/home/mm/test.txt";
		final String fout="/home/mm/dest.txt";
		FileReader reader =new FileReader(new File(fin));
		BufferedReader br=new BufferedReader(reader);
		String line;
		while( (line=br.readLine() )  !=  null ){
			System.out.println(line);
		}
		*/
		Dao<String> dao=new Dao<String>("hello!");
		System.out.println(dao.getGen());
		
		Student stu=new Student();
		stu.setGrad(ClassGrad.A);
		System.out.println(ClassGrad.A+"  "+ClassGrad.B);
		Dao<Student> dao2=new Dao<Student>(stu);
		System.out.println(dao2.getGen());
		
		 System.out.println((int)'A');
		 
		 ClassGrad [] grads= ClassGrad.values();
		 for(ClassGrad i: grads){
			 System.out.println(i);
		 }
	}
}

class Student {
	
	public  strictfp void setGrad(ClassGrad grad){
		
	}
	
}
class Dao<E>{
	private E e;
	public  Dao(E e){
		this.e=e;
	}
	public E getGen(){
		return e;
	}
}

enum ClassGrad{
	A,B,C
}