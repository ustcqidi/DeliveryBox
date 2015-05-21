<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<jsp:include page="../common/base.jsp" />
<script src="${ctx}/resources/js/user/userData.js" type="text/javascript">
</script>

</head>

<body>
	<div class="right">
		<div class="row-fluid">
			<ul class="breadcrumb" >
				<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span
					class="divider">&gt;</span></li>
				<li class="active">权限管理 <span class="divider">&gt;</span></li>
				<li class="active">用户管理</li>
			</ul>

			<div class="form-inline">
				<div class="control-group inline">
					<label class="control-label" for="query-bizId">用户姓名:</label> 
					<input type="text" id="userName" name="userName" value="" />
				</div>


				<div class="control-group inline">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary" id="btnAdd">新增</button>
				</div>

				<div class="control-group inline">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary" id="btnQuery">查询</button>
				</div>
			</div>

				<table class="table table-hover table-bordered table-condensed table-striped">
					<thead>
						<tr>
							<th style="width: 10%;">编号</th>
							<th style="width: 15%;">登陆名</th>
							<th style="width: 15%;">真实名</th>
							<th style="width: 20%;">描述</th>
							<th style="width: 10%;">密码重置</th>
							<th style="width: 10%;">查看</th>
							<th style="width: 10%;">修改</th>
							<th style="width: 10%;">删除</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
	</div>
	<input type="hidden" name="id" id="id" />
	<div id="panelOpCode" class="panel form-horizontal" title="新增/修改用戶">
	
	
		<div class="control-group">
			<label class="control-label" for="name">登陆名</label>
			<div class="controls">
				<input type="text" id="name" placeholder="必填" value=""><span
					id="tipOpCode" class="help-inline">*</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="name">真实名</label>
			<div class="controls">
				<input type="text" id="trueName" placeholder="必填" value=""><span
					id="tipOpCode" class="help-inline">*</span>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label" for="desc">用戶描述:</label>
			<div class="controls">
				<textarea type="text" id="desc" value=""></textarea>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	</script>
</body>

</html>