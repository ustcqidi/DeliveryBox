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
<script src="${ctx}/resources/js/delivery/deliverylist.js" type="text/javascript">
</script>
<style>
.form-horizontal .control-label {
width: 90px;
}
</style>
</head>

<body>
	<div class="right">
		<div class="row-fluid">
			<ul class="breadcrumb" >
				<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span
					class="divider">&gt;</span></li>
				<li class="active">用户管理 <span class="divider">&gt;</span></li>
				<li class="active">用户管理</li>
			</ul>

			<div class="form-inline">
				<div class="control-group inline">
					<label class="control-label" for="query-bizId">快递员姓名:</label> 
					<input type="text" id="username" name="username" value="" />
				</div>

                 <div class="control-group inline">
					<label class="control-label" for="query-bizId">公司:</label> 
					<input type="text" id="company" name="company" value="" />
				</div>

                <div class="control-group inline">
					<label class="control-label" for="query-bizId">手机号:</label> 
					<input type="text" id="tel" name="tel" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary" id="btnQuery">查询</button>
				</div>
			</div>

				<table class="table table-hover table-bordered table-condensed table-striped">
					<thead>
						<tr>
							<th style="width: 10%;">公司</th>
							<th style="width: 10%;">姓名</th>
							<th style="width: 10%;">手机号</th>
							<th style="width: 10%;">余额</th>
							<th style="width: 15%;">寄件价格说明</th>
							<th style="width: 20%;">备注</th>
							<th style="width: 20%;">查看</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
	</div>
	<input type="hidden" name="id" id="id" />
	<div id="panel" class="panel form-horizontal" title="查看/修改用戶">
	
	
		<div class="control-group">
			<label class="control-label" for="name">姓名</label>
			<div class="controls">
				<input type="text" id="pusername" placeholder="必填" value=""><span
					id="tipOpCode" style=" color: red;" class="help-inline">*</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="name">手机号</label>
			<div class="controls">
				<input type="text" id="ptel" placeholder="必填" value=""><span
					id="tipOpCode" style="color: red;" class="help-inline">*</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="name">公司</label>
			<div class="controls">
				<input type="text" id="pcompany" value="">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="name">寄件价格说明</label>
			<div class="controls">
				<input type="text" id="pdelivery_price_desc"  value="">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="name">余额</label>
			<div class="controls">
				<input type="text" id="pbalance" disabled="disabled"  value="">
				&nbsp&nbsp&nbsp<a id="charge" style="color: #005580;text-decoration: none;" href="#">充值</a>
			</div>
			
		</div>
		
		
		<div class="control-group">
			<label class="control-label" for="desc">备注:</label>
			<div class="controls">
				<textarea type="text" id="puser_desc" value=""></textarea>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	</script>
</body>

</html>