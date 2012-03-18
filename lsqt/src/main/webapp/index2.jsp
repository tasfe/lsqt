<%@ page language="java"  pageEncoding="UTF-8"%>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.blockUI.js"></script>
  </head>
  
  <body>
    <script type="text/javascript"> 
    $(document).ready(function() { 
 
        $('#test').click(function() { 
            $.blockUI({ message: $('#question'), css: { width: '275px' } }); 
        }); 
 
        $('#yes').click(function() { 
            // update the block message 
            $.blockUI({ message: "<h1>Remote call in progress...</h1>" }); 
 
            $.ajax({ 
                url: 'wait.php', 
                cache: false, 
                complete: function() { 
                    // unblock when remote call returns 
                    $.unblockUI(); 
                } 
            }); 
        }); 
 
        $('#no').click(function() { 
            $.unblockUI(); 
            return false; 
        }); 
 
    }); 
</script> 
 
... 
 
<input id="test" type="submit" value="Show Dialog" /> 
 
... 
 
<div id="question" style="display:none; cursor: default"> 
        <h1>Would you like to contine?.</h1> 
        <input type="button" id="yes" value="Yes" /> 
        <input type="button" id="no" value="No" /> 
</div> 
  </body>
</html>
