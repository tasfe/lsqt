<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.blockUI.js"></script>
	<script type="text/javascript">
	
		/*示例1*/
		$(document).ready(function() {
		 	
		 	
		    $('#demo1').click(function() { 
		        $.blockUI({ message: $('#loginForm') }); 
		 
		        setTimeout($.unblockUI, 2000); 
		    }); 
		}); 
		
		
		
		
		/*示例9*/
		$(document).ready(function() { 
			    $('#demo9').click(function() { 
			    	
			        $.blockUI(); 
			        $('.blockOverlay').attr('title','Click to unblock').click($.unblockUI); 
			    }); 
			}); 
      
      
      
	</script>
</head>
<body>
<%=new java.util.Date() %>
<h2>Hello World!</h2>
<button id="demo1">示例1</button><br>
<button id="demo2">示例2</button><br>
<button id="demo3">示例3</button><br>
<button id="demo4">示例4</button><br>
<button id="demo5">示例5</button><br>
<button id="demo6">示例6</button><br>
<button id="demo7">示例7</button><br>
<button id="demo8">示例8</button><br>
<button id="demo9">示例9</button><br>
<button id="demo10">示例10</button><br>
<form id="loginForm" style="display: none"><input/><input/></form>
</body>
</html>
