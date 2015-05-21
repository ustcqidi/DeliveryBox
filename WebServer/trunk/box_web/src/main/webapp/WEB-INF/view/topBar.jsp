<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>首页</title>
		
		<jsp:include page="common/base.jsp"/>
	</head>

	<body>
	<div style="float: right; margin-top: 1px;margin-right: 20px;'">
      <img id="up" src="./resources/images/up.gif" />
      <img id="down" style="display: none;" src="./resources/images/down.gif"  />
     </div>
	</body>
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#main").click(function(){
				window.parent.document.getElementById("contentFrame").contentWindow.location.href = "<c:url value='/dashboard.do'/>";
			});
			
			
			$("#up").click(function(){
				var bar = parent.document.getElementById('topframeset');
			 	$(bar).attr("rows","0,10,*,30");
			 	$("#up").hide();
			 	$("#down").show();
			   /*  $("#down").animate({opacity:0.2});  */
			})
			$("#down").click(function(){
				var bar = parent.document.getElementById('topframeset');
			 	$(bar).attr("rows","40,10,*,30");
			 	$("#down").hide();
			 	$("#up").show();
			 	/* $("#up").animate({opacity:0.2});  */
			})
			
		});
	</script>
</html>
