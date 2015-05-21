<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>登录</title>
		<style type="text/css">
		
		.syf_top{
	width: 165px;
	height: 61px;
	line-height:61px;
	padding-left:140px;
	font-family: 微软雅黑;
	font-size: 16px;
	color: #656565;
	background-image: url(resources/images/input-box_header.png);
}
.syf_name{
	height: 30px;
	width: 286px;
	padding-top:12px;
	padding-bottom:14px;
	border-left: solid 1px #ededed;
	border-right: solid 1px #ededed;
	margin-left: 1px;
}
.syf_pwd{
	height: 30px;
	width: 286px;
	padding-top:12px;
	padding-bottom:14px;
	border: solid 1px #ededed;
	margin-left: 1px;
}
.syf_checkcode{
	width: 290px;
	height: 61px;
	background-image: url(resources/images/input-box_below.png);
}
.syf_checkcode img{
	width: 30%;
	height: 50%;
	margin-bottom: 5px;
}
.syf_input{
	padding-left: 15px;
	font-family: 微软雅黑;
	font-size: 16px;
	color: #656565;
}
#username,#password,#checkcode{
	height: 26px;
	width: 190px;
	letter-spacing: 2px;
	border-color: #ededed;
}
#checkcode{
	border:0;
	height:21px;
	padding-top:18px;
	padding-bottom:16px;
	background-image: url(resources/images/password.png);
	background-position: center;
	background-repeat: no-repeat;
}
.syf_commit{
	width: 303px;
	height: 54px;
	line-height:54px;
	text-align:center;
	margin-top: 10px;
	margin-left: 1px;
	border:0;
	background:none;
	background-image: url(resources/images/button_register.png);
	background-position: center;
	background-repeat: no-repeat;
	font-size: 24px;
	color: #ffffff;
	letter-spacing: 5px;
	cursor: pointer;
}
.syf_tip{
	position: relative;
	top: -44px;
	left: 295px;
}
		
		</style>
		<link rel="shortcut icon" href="http://lingxi.voicecloud.cn/favicon.ico" />
		<jsp:include page="common/base.jsp"/>
	</head>

	<body style=" margin: 0; padding:0; font-family: 微软雅黑;">
	<div style=" background-color: #57b1ef; height:95px; width:100%;">
		<div style=" width: 960px; padding-left: 64px; height:95px; padding-top:15px; margin:0 auto;">
			<img src="<c:url value='resources/images/logo_xy.jpg'/>" />
		</div>
	</div>
	<div style=" width:1024px; margin:0 auto; padding-top: 80px; padding-left: 30px;">
		<img style=" width: 544px; height:384px; float:left;" src="<c:url value='resources/images/advertising-.png'/>"  />
		<div style=" padding-left: 30px; width:305px; padding-top:10px; float: left;">
			<form:form id="form" action="${ctx}/login.do" method="post" modelAttribute="loginForm">
	            <div class="control-group">
	                <c:if test="${not empty message}">
			      		<div class="ui-widget result">
		                  <div class="ui-state-error ui-corner-all">
		                    <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
		                    <strong>错误:</strong>${message }</p>
		                  </div>
		                </div>
		      		</c:if>
	            </div>
	            <div class="syf_top" style="background-color: #f3f3f3;background-image:none;">请登录后进行操作</div>
	            
	            <div class="syf_name syf_input" for="username">
	            	账　号：<input type="text" id="username" name="username" value="${loginForm.username }">
			  		<form:errors path="username" cssClass="label label-important syf_tip" />
	            </div>
	            <div class="syf_pwd syf_input">
	            	密　码：<input type="password" id="password" name="password">
			  		<form:errors path="password" cssClass="label label-important syf_tip" />
	            </div>
	            <div class="syf_checkcode syf_input">
		           	 验证码：<input type="text" id="checkcode" name="checkcode" maxlength="4" style="width:73px;">
			        <img id="checkimg" src="<c:url value='/captcha-image.do'/>"><i class="icon-refresh" style="margin-left: 5px; cursor: pointer;"></i>
			  		<form:errors path="checkcode" cssClass="label label-important syf_tip" />
	            </div>
<!-- 	            <button type="submit" class="syf_commit">登录</button> -->
				<div class="syf_commit" onmousedown="down();" onmouseup="up();">登录</div>
          </form:form>
		</div>
	</div>
	<div style=" position:fixed; bottom:0px; width:100%; padding-top:10px; height: 30px; clear:both; color:#9e9e9e; background-color: #F2F1F1; text-align: center;">
	v1.0beta &nbsp;@版权所有&nbsp; 合肥金邻信息科技有限公司
	</div>
	</body>
		
	<script type="text/javascript">
		$(document).ready(function() {
			
			//$("#username")[0].focus();
			
			$("#loginform").submit(function(){
				doLogin();
			});
			$(".icon-refresh").click(function(){
				$("#checkimg").attr('src',$("#checkimg").attr('src')+"?t="+Math.random());
			});
			$("input").keypress(function(event){
				if(event.which == 13){
					//doLogin();
					/* $("#form1").attr("action",Global.webRoot+"/logon.do"); */
					$("#form").submit();
				}
			});
		});
		
		function doLogin(){

			$.post($(this).attr("action"),$("#loginform").serialize(),function(html){
				$("#content").replaceWith(html);
			});
		}
		
	
		function down(){
			$(".syf_commit").css("background-image","url(<c:url value='resources/images/button_register_press.png'/>)");
		}
		function up(){
			$(".syf_commit").css("background-image","url(<c:url value='resources/images/button_register.png'/>)");
			$("#form").submit();
		}
	</script>
</html>
