<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"
	scope="request" />
<title>登陆</title>
<link type="text/css" href="${ctx}/resources/css/login.css"
	rel="stylesheet">	
<script src="${ctx}/resources/third-party/jquery-ui-bs/js/jquery-1.8.3.min.js" type="text/javascript">
</script>
<style>

.cont-lt1 .side {
	width: 120px;
}

.cont-lt1 .side .box-title {
	padding: 3px;
}

.side .box-content a {
	padding-left: 5px
}

html {
	background-color: #06294e;
}

body {
	background-image: url(resources/images/test.jpg);
	background-position: center top;
	background-repeat: no-repeat;
}

form {
	padding-top: 200px;
	text-align: center
}

table,tr,td,th,input {
	border: none;
	vertical-align: middle;
}

.table-4 {
	text-align: left
}

.table-4 tr,.table-4 th,.table-4 td {
	padding: 2px
}

.rowhead {
	width: 300px;
	font-weight: normal;
	font-size: 14px;
	color: #fff;
	border: none
}

.text-2 {
	width: 150px;
	height: 22px;
	background-color: #a4c5e0;
	border: 1px solid #035793;
	font-size: 16px;
	font-weight: bold
}

.select-2 {
	width: 150px
}

#welcome {
	background: none;
	border: none;
	color: #FFF;
	padding-top: 20px;
	font-size: 14px;
	font-weight: normal
}

#poweredby {
	color: #fff;
	margin-top: 30px;
	height: 50px;
	text-align: center;
	line-height: 1
}

#poweredby a {
	color: #fff
}

#keeplogin {
	color: white;
	font-size: 14px
}

.button-s,.button-c {
	padding: 3px 5px 3px 5px;
	height: 30px;
	width: 80px;
	font-size: 14px;
	font-weight: bold;
	border: none
}

.debugwin,.helplink {
	display: none
}

#updater {
	width: 500px;
	height: 30px;
	border: none;
	overflow: hidden;
}

.hidden {
	display: none
}

#demoUsers {
	margin: 25px 0px -15px 0px;
}

#demoUsers span {
	color: white
}

#demoUsers a {
	color: yellow
}

#checkimg {
  width: 60px;
  height: 29.5px;
}

.icon-refresh{
  display: inline-block;
  width: 25px;
  height: 24px;
  background-image: url(resources/images/refresh1.png);;
}

.msg-error {
  position: absolute;
  left: 45%;
  top: 140px;
  background: #ffebeb;
  color: #e4393c;
  border: 1px solid #e4393c;
  padding: 3px 10px 3px 40px;
  line-height: 18px;
  min-height: 18px;
}

</style>
<link rel='shortcut icon' href='http://120.27.45.163:88/epi.jpg' type='image/x-icon' />
</head>
<body>
	<form method='post' id="form" action="${ctx}/login.do">
		<c:if test="${not empty message}">
			<div class="msg-error">
				<b></b>${message }
			</div>
		</c:if>
		<table align='center' class='table-4'>
			<caption id='welcome'></caption>
			<tr>
				<td class='rowhead'>用户名：</td>
				<td><input class='text-2' type='text' name='username'
					id='username' /></td>
			</tr>
			<tr>
				<td class='rowhead'>密码：</td>
				<td><input class='text-2' type='password' name='password' /></td>
			</tr>
			<tr>
				<td class='rowhead' valign='top' >验证码:</td>
				<td>
				<input class='text-2' style="width: 80px;float: left;" type='text' name='checkcode'
					id='checkcode' />
					 <img id="checkimg" src="<c:url value='/captcha-image.do'/>"><i class="icon-refresh" style="margin-left: 5px; cursor: pointer;"></i>
					</td>
				</td>
			</tr>
			<tr>
				<td></td>
				<td id='keeplogin'><span><input type='checkbox'
						name='keepLogin[]' value='on' /> <label>保持登录</label></span></td>
			</tr>
			<tr>
				<td colspan='2' class='a-center'><input type='submit'
					id='submit' value='登录' class='button-s' />
					
			</tr>
		</table>

		<div id='poweredby'>
		v1.0beta  @版权所有  常州中科若比邻电子科技有限公司 
			<br />
		</div>
	</form>
	</div>
	<script laguage='Javascript'>
$(document).ready(function()
{
    $('#account').focus();
	$(".icon-refresh").click(function(){
		$("#checkimg").attr('src',$("#checkimg").attr('src')+"?t="+Math.random());
	});
	$("#submit").click(function(){
		$("#form").submit();
	});
});
</script>
</body>
</html>
