<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<jsp:include page="../common/base.jsp"/>
<script type="text/javascript">

$(document).ready(function(){
	$("#button1").click(function(){
		
		window.parent.location = "<c:url value='/login.do'/>";
	});
});
</script>
<title></title>
</head>
<body >
       <div class="container-fluid">
			<div class="row-fluid">
				
	            <div style = "position:absolute;top:30%;left:40%;margin-top:-50px;margin-left:-100px;">
		            <div style = "float:left;">
		            	<img style="height:150px;padding-right:70px" src="<c:url value='/resources/images/success.png'/>" />
		            </div>
		            <div style = "float:left;">
		            	<p style = "margin-bottom:10%; font-size:150%; font-weight:bold;">恭喜你，保存<span style="color:green;">成功！</span></p>
		            	<button id="button1" style = "padding:15px; margin-top:20px; text-decoration:underline; color:blue; font-weight:bold;">请返回重新登陆</button>
		            </div>
	            </div>
			</div>
		</div>
</body>
</html>