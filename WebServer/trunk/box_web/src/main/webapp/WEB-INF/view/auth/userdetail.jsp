<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理</title>
		
		<jsp:include page="../common/base.jsp"/>
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
	<body>
		<div class="right">
			<div class="row-fluid">
          		<form:form id="form" method="post" modelAttribute="userDetailForm" cssClass="form-horizontal">
					<ul class="breadcrumb" style="margin-bottom: 5px;">
						<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
						<li class="active">权限管理 <span class="divider">&gt;</span></li>
						<c:if test="${userDetailForm.operate eq 'add' }">
							<li class="active">用户新增</li>
						</c:if>
						<c:if test="${userDetailForm.operate eq 'update' }">
							<li class="active">用户修改</li>
						</c:if>
		            </ul>
		            <ossp:result />
		            <div class="tabbable" style="margin-bottom: 18px;">
		              	<ul class="nav nav-tabs">
	                		<li class="active"><a href="#tab1">用户基本资料</a></li>
		                	<li class="disabled"><a href="#">权限设置</a></li>
		              	</ul>
		              <div class="tab-content" style="padding-bottom: 1px; border-bottom: 1px solid #ddd;">
		                <div class="tab-pane active" id="tab1">
	          				<div class="control-group">
				              <div class="controls">
				              	<div class="span3">
					                <label class="checkbox">
						              <form:checkbox path="isforbid" value="true" />
						              	将管理员置为无效
						            </label>
				              	</div>
				              </div>
				            </div>
		                  	<div class="control-group">
				              <label class="control-label" for="username"><span style="color: red;">*</span>登录名：</label>
				              <div class="controls">
				                <input type="text" id="username" name="username" placeholder="登录名" value="${userDetailForm.username }">
				                <form:errors path="username" cssClass="label label-important" />
				              </div>
				            </div>
	          				<div class="control-group">
				              <label class="control-label" for="truename"><span style="color: red;">*</span>真实姓名：</label>
				              <div class="controls">
				                <input type="text" id="truename" name="truename" placeholder="真实姓名" value="${userDetailForm.truename }">
				                <form:errors path="truename" cssClass="label label-important" />
				              </div>
				            </div>
	          				<div class="control-group">
				              <label class="control-label">性别：</label>
				              <div class="controls">
				              	<div class="span1">
						            <label class="radio">
						              <form:radiobutton path="sex" value="男"/>
						              	男
						            </label>
				              	</div>
				              	<div class="span1">
						            <label class="radio">
						              <form:radiobutton path="sex" value="女"/>
						              	女
						            </label>
				              	</div>
				              </div>
				            </div>
	          				<div class="control-group">
				              <label class="control-label" for="phone">手机号：</label>
				              <div class="controls">
				                <input type="text" id="phone" name="phone" placeholder="密码将短信下发到此手机号,仅支持中国移动手机" style="width:300px;" value="${userDetailForm.phone }">
				                <form:errors path="phone" cssClass="label label-important" />
				              </div>
				            </div>
	          				<div class="control-group">
				              <label class="control-label" for="email">电子邮箱：</label>
				              <div class="controls">
				                <input type="text" id="email" name="email" placeholder="电子邮箱" value="${userDetailForm.email }">
				                <form:errors path="email" cssClass="label label-important" />
				              </div>
				            </div>
	          				<div class="control-group">
				              <label class="control-label" for="position">职务：</label>
				              <div class="controls">
				                <input type="text" id="position" name="position" placeholder="职务" value="${userDetailForm.position }">
				                <form:errors path="position" cssClass="label label-important" />
				              </div>
				            </div>
		                </div>
		               
		                <div class="tab-pane" id="tab2">
		                	<fieldset>
		                		<legend>数据权限</legend>
		          				<div class="control-group">
					              <div class="controls">
					              	<div class="span3">
						                <label class="checkbox">
							              <form:checkbox path="isonlyopcmccuser" value="true" />
							              	仅允许操作中国移动用户
							            </label>
					              	</div>
					              </div>
					            </div>
		          				<div class="control-group">
					              <label class="control-label" for="province">省份：</label>
					              <div class="controls">
					                <input type="text" id="province" name="province" placeholder="点击选择省份" value="${userDetailForm.province }" style="width:500px;">
					                <!-- <input type="hidden" id="hiddenProvince" name="hiddenProvince"/> -->
					                <form:hidden path="hiddenProvince"/>
					                <form:errors path="province" cssClass="label label-important" />
					              </div>
					            </div>
		          				<div class="control-group">
					              <label class="control-label" for="city">地市：</label>
					              <div class="controls">
					                <input type="text" id="city" name="city" placeholder="点击选择地市" value="${userDetailForm.city }" style="width:500px;">
					                <!-- <input type="hidden" id="hiddenCity" name="hiddenCity"/> -->
					                <form:hidden path="hiddenCity"/>
					                <form:errors path="city" cssClass="label label-important" />
					              </div>
					            </div>
		                	</fieldset>
		                	<fieldset>
		                		<legend>操作权限</legend>
		          				<div class="control-group">
					              <label class="control-label">操作权限：</label>
					              <div class="controls">
					              	<div class="span2" id="div_role">
					              	</div>
					              </div>
					            </div>
		                	</fieldset>
		                </div>

		              </div>
		            </div>

          			<div class="form-actions">
          				<div class="row pull-right">
				              <button id="btnSave" type="button" class="btn btn-primary">下一步</button>
				              <button id="btnCancel" type="button" class="btn btn-primary">取消</button>
          				</div>
		            </div>
					
		            <input type="hidden" id="userid" name="userId" value="${userDetailForm.userId }"/>
		            <input type="hidden" id="back" name="back" value="${back }"/>
		            <ossp:query formbean="userDetailForm"/>
				</form:form>
			</div>
		</div>
			<script type="text/javascript">
		var hiddenProvince = '${userDetailForm.hiddenProvince}';
		var hiddenCity = '${userDetailForm.hiddenCity}';
		var role = '${userDetailForm.role}';
		$(document).ready(function() {
			$("#btnSave").click(function(){
				if(checkForm()){
					$("#form").attr("action","<c:url value='/userdetailsave.auth'/>");
					$("#form").submit();
				}
			});
			$("#btnCancel").click(function(){
				$("#form").attr("action","<c:url value='/userlist.auth'/>");
				$("#form").submit();
			});
			
			// get roles
			ajaxPost({
				type:"GET",
				url :"<c:url value='/role.json'/>",
				success:function(roles){
					var sel_r = $("#div_role");
					var html_r = "";
					$.each(roles,function(index, item){
						html_r += "<label class='radio' style='padding-top:5px;'><input type='radio' style='margin:1px 0 0;' name='role' id='role" + index + "' value='" + item.roleId + "'>";
						html_r += item.roleName + "</label>";
					});
					sel_r.append(html_r);
					
					if(role != ''){
						$("input[name='role'][value='" + role + "']").attr("checked", true);
					}
				}
			});
		});
		
		function checkForm(){
			if(cacuLength($("#username").val()) > 64){
				alert("登录名长度不能超过64个字符");
				return false;
			}
			if(isNull($("#username").val())){
				alert("登录名不能为空");
				return false;
			}
			if(cacuLength($("#truename").val()) > 64){
				alert("真实姓名长度不能超过64个字符");
				return false;
			}
			if(isNull($("#truename").val())){
				alert("真实姓名不能为空");
				return false;
			}
			if(cacuLength($("#phone").val()) > 20){
				alert("手机号长度不能超过20个字符");
				return false;
			}
			if(cacuLength($("#email").val()) > 64){
				alert("电子邮箱长度不能超过64个字符");
				return false;
			}
			if(!isNull($("#email").val()) && !email($("#email").val())){
				alert("电子邮箱格式不正确");
				return false;
			}
			if(cacuLength($("#position").val()) > 64){
				alert("职务长度不能超过64个字符");
				return false;
			}
			return true;
		}
		
	</script>
	</body>
		

</html>
