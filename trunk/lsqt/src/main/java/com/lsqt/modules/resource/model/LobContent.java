package com.lsqt.modules.resource.model;

import java.io.UnsupportedEncodingException;

public class LobContent {
	private String id;
	private byte[] value;
	
	
	
	public String toString(){
		 String content="";
		try {
			content = new String(value,"utf-8");
		} catch (UnsupportedEncodingException e) {
			
			
		}
		 return content;
	}
}
