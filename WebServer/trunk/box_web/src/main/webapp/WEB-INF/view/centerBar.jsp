
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>

<jsp:include page="common/base.jsp" />


</head>

<body>

	<div  style=" background-color: #F5F5F5">
   <table width="100%" height="100%" id="table">
            <tr>
               <td align="center" valign="bottom" id="HideLeft" style="">
                <img onClick="switchHideBar()" src="./resources/images/left.gif"/>
               </td>
               <td align="center" valign="middle" style="display:none;" id="HideRight"  style="" >
                <img onClick="switchShowBar()" src="./resources/images/right.gif"/>
               </td>
           </tr>
       </table> 
	</div>

</body>

<script type="text/javascript">
		$(document).ready(function() {
			/* alert($(document).height()) */
		  $("table").css("margin-top",$(document).height()*2/5)
		/* $("#HideLeft").mouseover(function() {
		    $("#HideLeft").animate({opacity:1}); 
		  }).mouseout(function() {
		    $("#HideLeft").animate({opacity:0.2}); 
		  }) */
		})	
			
		function switchHideBar(){
			var bar = parent.document.getElementById('attachucp');
			$(bar).attr("cols","0,10,*");
			$("#HideLeft").hide("slow");
			$("#HideRight").show();
			/* $("#HideRight").animate({opacity:0.2});  */
		}
		function switchShowBar(){
			var bar = parent.document.getElementById('attachucp');
			$(bar).attr("cols","200,10,*");
			$("#HideLeft").show();
			$("#HideRight").hide("slow");
			/* $("#HideLeft").animate({opacity:0.2});  */
		}
		
		 
		
	</script>
</html>

