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
<script type="text/javascript"
	src="${ctx}/resources/js/bizManage/view.js?<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript">
	operate = '${operate}';
	id = '${id}';
</script>

<title>业务</title>
</head>
<body>
${boxInfo.cabinetName}
	<div class="right" >
		<form:form id="form" action="${ctx}/biz/saveBizInfo.json" method="post">
				<div class="row-fluid ">
				<ul class="breadcrumb" style="margin-bottom: 5px;">
					<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
					<li class="active">宝柜管理 <span class="divider">&gt;</span></li>
					<li class="active">
						<c:if test="${operate == 'update'}">
							宝柜修改
						</c:if>
						<c:if test="${operate == 'view'}">
							宝柜查看
						</c:if>
					</li>
	            </ul>
		            <div class="tabbable" style="margin-bottom: 18px;">
		              	<ul class="nav nav-tabs">
		                	<li class="active"><a href="#tab1" data-toggle="tab">基本资料</a></li>
		              	</ul>
		              	<div style="padding-bottom: 1px; border-left: 1px solid #ddd;">
		                	<div class="tab-pane active" style="margin-left: 35px;">
		                  		<div class="control-group">
				              		<label class="control-label" for="username">
				              			微信名：
				              		</label>
				              		<div class="controls">
				              			<input type="text"  id="weixin_name" name="weixin_name" >
				              		</div>
				            	</div>
	          					<div class="control-group">
				              		<label class="control-label" for="trueName">
				              			用户姓名：
				              		</label>
				              		<div class="controls">
				                		<input type="text" id="user_name" name="user_name">
				              		</div>
				            	</div>
	          					<div class="controls">
									<label class="radio inline">
										<input type="radio" id="sex_male" name="sex" value="男" checked="checked"> 男
									</label>
									<label class="radio inline">
										<input type="radio" id="sex_female" name="sex" value="女" > 女
									</label>
								</div>
	          					<div class="control-group">
				              		<label class="control-label" for="phone">手机号：</label>
				              		<div class="controls">
				                		<input type="text" name="phone"  id="phone">
				                		<span> <i title="选择号码" style="cursor: pointer;" class="icon-th-list"></i></span>
				              		</div>
								
							</div>
	          					<div class="control-group">
				              		<label class="control-label" for="position">职务：</label>
				              		<div class="controls">
				                		<input type="text" name=profession  id="profession">
				              		</div>
				            	</div>
				            	
				            		<div class="control-group">
				              		<label class="control-label" for="position">创建人：</label>
				              		<div class="controls">
				                		<input type="text" name=creator_name  readonly="readonly" id="creator_name">
				              		</div>
				            	</div>
				            	
				            	<div class="control-group" style="margin-top: 20px;">
								<label class="control-label" id="labelContent"><font
									>描述&nbsp;&nbsp;</font> </label>
								<div class="controls">
									<textarea rows="4" cols="" style="width: 88%"
										 name="desc" id="desc"
										placeholder="4096个字以内" maxlength="4096"></textarea>

								</div>
							</div>
			                	
		                	</div>
		              	</div>
		            </div>
		            
		        <input type="hidden" id="id" name="id" value="${id}" />
				<div class="btns">
					<c:if test="${operate == 'update'}">
						<input id="btnSave" type="button" value="保存"
							class="btn-primary btn" style="margin-left: 45%; height: 30px;"
							name="btnSave" />
					<input id="btnCancel" class="btn btn-primary" type="button"
						value="取消" style="height: 30px;" />
					</c:if>
					
					<c:if test="${operate == 'add'}">
						<input id="btnSave" type="button" value="保存"
							class="btn-primary btn" style="margin-left: 45%; height: 30px;"
							name="btnSave" />
					<input id="btnCancel" class="btn btn-primary" type="button"
						value="取消" style="height: 30px;" />
					</c:if>
					
					<c:if test="${operate == 'view'}">
					<input id="btnCancel" class="btn btn-primary" type="button"
						value="取消" style="height: 30px;margin-left: 45%;" />
					</c:if>
				</div></form:form>
			</div>
</body>
</html>