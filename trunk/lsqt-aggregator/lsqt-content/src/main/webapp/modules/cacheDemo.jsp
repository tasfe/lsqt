<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="cache" uri="http://www.opensymphony.com/oscache" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'studentAdd.jsp' starting page</title>
    
	<link rel="stylesheet" href="">
  </head>
  
  <body>
  	<cache:cache key="name" scope="session">
  		<%=new Date() %> ${param.name }
  	</cache:cache>
    
    <font>用户模块</font>
    <input type="button" value="测试按钮" class="actionBtnSty"/>
  </body>
</html>
