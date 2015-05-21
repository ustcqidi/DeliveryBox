<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户查看</title>
		
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
			.nav-tabs > .active > a, .nav-tabs > .active > a:hover, .nav-tabs > .active > a:focus{
	background-color:#0088CC;
	color: #FFFFFF;
	width:100px;
	text-align:center;
}
.nav-tabs > li > a {
	text-align:center;
	width:100px;
	border-color:#0088CC; 
}
		</style>
	</head>
	<body>
		<div class="right">
			<div class="row-fluid">
				<ul class="breadcrumb" style="margin-bottom: 5px;">
					<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
					<li class="active">权限管理 <span class="divider">&gt;</span></li>
					<li class="active">用户查看</li>
	            </ul>
          		<form:form id="form" method="post" modelAttribute="userDetailForm" cssClass="form-horizontal">
		            <div class="tabbable" style="margin-bottom: 10px;">
		              	<ul class="nav nav-tabs">
		                	<li class="active"><a href="#tab1" data-toggle="tab">用户基本资料</a></li>
		              	</ul>
		              	<div style="padding-bottom: 1px;border: 1px solid #ddd;">
		                	<div class="tab-pane active" id="tab1">
	          					<div class="control-group">
				              		<div class="controls">
				              			<div class="span3">
					                		<label class="checkbox">
						              			<form:checkbox path="forbid" value="true" disabled="true" />将用户置为无效
						            		</label>
				              			</div>
				              		</div>
				            	</div>
		                  		<div class="control-group">
				              		<label class="control-label" for="username">登录名：</label>
				              		<div class="controls">
				                		<input type="text" name="username" readonly="true" value="${userDetailForm.username}">
				                		<form:errors path="username" cssClass="label label-important" />
				              		</div>
				            	</div>
	          					<div class="control-group">
				              		<label class="control-label" for="trueName">用户姓名：</label>
				              		<div class="controls">
				                		<input type="text" name="trueName" readonly="true" value="${userDetailForm.trueName}">
				                		<form:errors path="trueName" cssClass="label label-important" />
				              		</div>
				            	</div>
	          					<div class="control-group">
				              		<label class="control-label">性别：</label>
				              		<div class="controls">
				              			<div class="span1">
						            		<label class="radio">
						              			<form:radiobutton path="sex" disabled="true" value="男"/>男
						            		</label>
				              			</div>
				              			<div class="span1">
						            		<label class="radio">
						              			<form:radiobutton path="sex" disabled="true" value="女"/>女
						           			</label>
				              			</div>
				              		</div>
				            	</div>
	          					<div class="control-group">
				              		<label class="control-label" for="phone">手机号：</label>
				              		<div class="controls">
				                		<input type="text" name="phone" readonly="readonly"  value="${userDetailForm.phone}">
				               	 		<form:errors path="phone" cssClass="label label-important" />
				              		</div>
				            	</div>
	          					<div class="control-group">
				              		<label class="control-label" for="email">电子邮箱：</label>
				              		<div class="controls">
				                		<input type="text" name="email" readonly="readonly" value="${userDetailForm.email}">
				                		<form:errors path="email" cssClass="label label-important" />
				              		</div>
			            		</div>
	          					<div class="control-group">
				              		<label class="control-label" for="position">职务：</label>
				              		<div class="controls">
				                		<input type="text" name="position" readonly="readonly" value="${userDetailForm.position}">
				                		<form:errors path="position" cssClass="label label-important" />
				              		</div>
				            	</div>
			                
		                	</div>
		              	</div>
		            </div>
		            <div  style="margin-top: 20px;">
			              	<div align="center" style="margin-top:10px;"><button id="btnCancel" type="button" class="btn btn-primary">返回</button></div>
			              
			             </div>
		            
				</form:form>
			</div>
		</div>
			<script>
		var queryProvince = '${userForm.queryProvince}';
		var queryCity = '${userForm.queryCity}';
		$(document).ready(function() {
			initPage();
			
			// bind select province onchange method
			$("#queryProvince").change(function(){
				getCity();
			});
			$("#btnCancel").click(function(){
				$("#form").attr("action","<c:url value='/auth/userlist.auth'/>");
				$("#form").submit();
			});
		});
		
		function initPage(){
			// load province
			ajaxPost({
				type: "GET",
				url: "<c:url value='/province.json'/>",
				success:function(provinces){
					var sel_p = $("#queryProvince");
					var html_p = "<option value=''>全国</option>";
					$.each(provinces, function(index, item){
						if('${userDetailForm.provinceId}'&&'${userDetailForm.provinceId}'!='-1'){
							if('${userDetailForm.provinceId}' == item.aiid){
								html_p += "<option value='" + item.aiid + "' selected>" + item.name + "</option>";
								$("#province").val(item.name);
							}else{
							
								html_p += "<option value='" + item.aiid + "'>" + item.name + "</option>";
							}
						}else if('${userDetailForm.provinceId}'=='-1'){
							$("#province").val("全国");
						}else{
							html_p += "<option value='" + item.aiid + "'>" + item.name + "</option>";
						}
					});
					sel_p.append(html_p);
					
					// 表示userForm中有相应的值
					if(queryProvince){
						sel_p.val(queryProvince);
						getCity();
					}else{
					 	$("#queryCity").append("<option value='' selected>全省/市</option>"); 
					}
					
					if('${userDetailForm.provinceId}'){
						getCity(true);
					}
				}
			});
		}
		
		function getCity(){
			if(!$("#queryProvince").val()){
				$("#queryCity").append("<option value='' selected>全省/市</option>");
				return false;
			}
			ajaxPost({
				type: "GET",
				url: "<c:url value='/city.json'/>",
				data: {aiid:$("#queryProvince").val()},
				success: function(cities){
					var sel_c = $("#queryCity");
					sel_c.empty();
					var html_c = "<option value=''>全省/市</option>";
					$.each(cities, function(index, item){
						if('${userDetailForm.cityId}'){
							if('${userDetailForm.cityId}' == item.aiid){
								html_c += "<option value='" + item.aiid + "' selected>" + item.name + "</option>";
								$("#city").val(item.name);
							}else{
								html_c += "<option value='" + item.aiid + "'>" + item.name + "</option>";
							}
						}else{
								html_c += "<option value='" + item.aiid + "'>" + item.name + "</option>";
						}
					});
					sel_c.append(html_c);
					
					// 表示userForm中有相应的值
					if(queryCity){
						sel_c.val(queryCity);
					}
				}
			});
		}
	</script>
	</body>

</html>
