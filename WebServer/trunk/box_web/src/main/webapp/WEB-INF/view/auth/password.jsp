<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>密码修改</title>
		
		<jsp:include page="../common/base.jsp"/>
		<style type="text/css">
		 .label-important {
		   background-color: #fff;
		   color: #FF0000;
		   display: inline-block;
		   padding: 2px 4px;
		   font-size: 14px;
		   font-weight: normal;
		   line-height: 20px;
		   white-space: nowrap;
		   text-shadow: none;
		}
		</style>
		<script type="text/javascript">
		   $(function(){
			   $("#submit").click(function(){
				   if(!$("#oldPassword").val()){
					   alert("请输入原密码");
					   return;
				   }
				   if(!$("#newPassword").val()){
					   alert("请输入新密码");
					   return;
				   }
				   if(!$("#checkPassword").val()){
					   alert("请确认密码");
					   return;
				   }
				   $("form").submit();
			   })
			   
			   $("#btnCancel").click(function(){
			        //  window.history.go(-1);
			        window.location.href=Global.webRoot+"/auth/userlist.auth?cancel=cancelMM";
				  });
		   });
		</script>
	</head>
	<body>
	<div class="right " >
		<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
			<li class="active">权限管理 <span class="divider">&gt;</span></li>
			<li class="active">密码修改</li>
		</ul>
		<div class="container" style="margin-top:40px; margin-left:0">
			<div class="row">
				<div class="span3"></div>
				<div class="span8">
					<form:form id="from" action="${ctx}/auth/updatePassword.json" method="post" modelAttribute="passwordForm" cssClass="form-horizontal">
					<div style="font-size:16px; margin:50px 0 20px 0; font-family:微软雅黑; padding-left:25%">登录名：<shiro:principal property="name"/> </div>
			            
			             <div class="control-group">
			                <c:if test="${not empty message}">
					      		<div class="ui-widget result">
				                  	<div class="ui-state-error ui-corner-all">
				                    	<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
				                    	<strong>错误:</strong>${message}</p>
			                  		</div>
				                </div>
				      		</c:if>
			            </div>
			            
			            <div class="control-group">
			              	<label class="control-label" for="oldPassword">原密码：</label>
			              	<div class="controls">
					        	<input type="password" name="oldPassword" autocomplete="off";>					  			
					  			<form:errors path="oldPassword" cssClass="label label-important" />
			              	</div>
			            </div>
			            <div class="control-group">
			              	<label class="control-label" for="newPassword">新密码：</label>
			              	<div class="controls">
					        	<input type="password" name="newPassword">
					  			<form:errors path="newPassword" cssClass="label label-important" />
			              	</div>
			            </div>
			            <div class="control-group">
			              	<label class="control-label" for="checkPassword">确认密码：</label>
			              	<div class="controls">
				        		<input type="password" name="checkPassword">
					  			<form:errors path="checkPassword" cssClass="label label-important" />
			              	</div>
			            </div>
			            
			            <div class="control-group">
			                	<input type="submit" class="btn btn-primary" style="margin-left:25%;height:30px;" value="保存">
			                	<input type="button" class="btn btn-primary" style="height:30px;" value="取消" id="btnCancel">		                	
				            </div>
	          		</form:form>
	          	</div>
				<div class="span3"></div>
			</div>
	    </div>
	   
	    </div>
	</body>
</html>