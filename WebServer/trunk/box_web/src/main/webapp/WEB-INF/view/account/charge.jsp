<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<jsp:include page="../common/base.jsp" />
<link type="text/css" href="${ctx}/resources/css/common.css"
	rel="stylesheet" />
<script type="text/javascript">
$(function(){
	$("#btnSave").click(function(){
		$("#form").submit();
	})
})

</script>

<title>财务管理</title>
</head>
<body>
	<div class="right" >
		<form:form id="form" action="${ctx}/account/saveCharge.json"   method="post">
				<div class="row-fluid ">
				<ul class="breadcrumb" style="margin-bottom: 5px;">
					<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
					<li class="active">财务管理 <span class="divider">&gt;</span></li>
					<li class="active">
							快递员充值
					</li>
	            </ul>
		              	<ul class="nav nav-tabs">
		                	<li class="active"><a href="#tab1" data-toggle="tab">快递员充值</a></li>
		              	</ul>
		              	
		              	  <div class="control-group">
			                <c:if test="${not empty msg}">
					      		<div class="ui-widget result">
				                  	<div class="ui-state-error ui-corner-all" style="width: 300px;margin-left: 30px;">
				                    	<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
				                    	<strong>错误:</strong>${msg}</p>
			                  		</div>
				                </div>
				      		</c:if>
			            </div>
			            
		              	<div  class="tab-content" style="padding-bottom: 1px; border-left: 1px solid #ddd;">
		                	<div class="tab-pane active" id="tab1" style="margin-left: 35px;">
		                  	 	<div class="control-group">
				              		<label class="control-label" >
				              			充值账号：
				              		</label>
				              		<div class="controls">
				              			<input type="text"  id="tel" name="tel" value='${tel}' >
				              		</div>
				              		 <span class="label label-important"></span>
				            	</div>
							   
							   <div class="control-group">
				              		<label class="control-label" for="phone">确认账号：</label>
				              		<div class="controls">
				                		<input type="text" id="ptel" name="ptel" value='${ptel}'>
				              		</div>
				              		 <span class="label label-important"></span>
							   </div>
				            	
	          					<div class="control-group">
				              		<label class="control-label" for="phone">充值金额：</label>
				              		<div class="controls">
				                		<input type="text" id="money" name="money" >
				              		单位：元
				              		</div>
				              		 <span class="label label-important"></span>
							   </div>
				            	
				            	
				            	
		                	</div></div>
		            
				<div class="btns">
						<input id="btnSave" type="button" value="保存"
							class="btn-primary btn" style="margin-left: 45%; height: 30px;"
							name="btnSave" />
					<input id="btnCancel" class="btn btn-primary" type="button"
						value="取消" style="height: 30px;" />
				</div>
				</form:form>
			</div>
</body>
</html>