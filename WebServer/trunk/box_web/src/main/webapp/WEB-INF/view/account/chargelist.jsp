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
<script src="${ctx}/resources/js/charge/chargelist.js" type="text/javascript">
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
				<li class="active">财务管理 <span class="divider">&gt;</span></li>
				<li class="active">充值记录</li>
			</ul>

			<div class="form-inline">

                <div class="control-group inline">
					<label class="control-label" for="query-bizId">账户:</label> 
					<input type="text" id="tel" name="tel" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label" for="query-bizId">充值类型:</label> 
					<select type="text" id="charge_type" name="charge_type" value="" >
					<option value="">充值类型</option>
					<option value="0">系统充值</option>
					<option value="1">支付宝充值</option>
					<option value="-1">快递员取消退款</option>
					</select>
				</div>
				
				<div class="control-group inline">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary" id="btnQuery">查询</button>
				</div>
			</div>
			
				<table class="table table-hover table-bordered table-condensed table-striped">
					<thead>
						<tr>
							<th style="width: 10%;">序号</th>
							<th style="width: 20%;">充值时间</th>
							<th style="width: 10%;">账户</th>
							<th style="width: 10%;">金额</th>
							<th style="width: 20%;">公司</th>
							<th style="width: 15%;">公司</th>
							<th style="width: 15%;">充值类型</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
	</div>
	<script type="text/javascript">
	
	</script>
</body>

</html>