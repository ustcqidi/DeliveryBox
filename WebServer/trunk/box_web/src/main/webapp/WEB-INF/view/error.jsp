<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.iflytek.com/ossptag" prefix="ossp" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理</title>
		
		<jsp:include page="common/base.jsp"/>
		<style>
		.form-horizontal .control-label {
			width: 100px;
		}
		.form-horizontal .controls {
			margin-left: 120px;
		}
		legend + .control-group {
		  margin-top: 1px;
		  -webkit-margin-top-collapse: separate;
		}
		.nav {
		  margin-bottom: 10px;
		}
		legend {
		  margin-bottom:1px;
		}
		.nav-tabs > .active > a,
		.nav-tabs > .active > a:hover,
		.nav-tabs > .active > a:focus {
		  color: #0088cc;
		}
		</style>
	</head>
<!-- 	<body> -->
	<body onload = "secondsReduce()">
		<div class="container-fluid">
			<div class="row-fluid">
				<ul class="breadcrumb" style="margin-bottom: 5px;">
					<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">/</span></li>
					<li class="active">错误页面</li>
	            </ul>	            
<%-- 	      		<ossp:result/> --%>
	      		<div style = "position:absolute;top:30%;left:40%;margin-top:-50px;margin-left:-100px;">
		            <div style = "float:left;">
		            	<img style="height:100px;padding-right:70px" src="<c:url value='resources/img/error.jpg'/>" />
		            </div>
		            <div style = "float:left;">
		            	<p style = "color:red; margin-bottom:10%; font-size:150%; font-weight:bold;">出错了！</p>
		            	<p>错误原因：${errorMsg}</p>
		            	<p><span id = "seconds"></span>秒后返回首页……</p>
		            	<button style = "padding:15px; margin-top:20px; text-decoration:underline; color:blue; font-weight:bold;">立即返回</button>
		            </div>
	            </div>
			</div>
		</div>
	</body>
		
	<script type="text/javascript">
		sr = 5;
		secondSpan = document.getElementById("seconds");
		function secondsReduce(){
			if(sr>0){
				secondSpan.innerHTML = sr+"";
				sr--;
				window.setTimeout("secondsReduce();", 1000);
			}else{
				//跳转
				window.location = "/ossp/dashboard.do";
			}
		}
		
		$(document).ready(function(){
			$("button").click(function(){
				window.location = "/ossp/dashboard.do";
			});
		});
	</script>
</html>
