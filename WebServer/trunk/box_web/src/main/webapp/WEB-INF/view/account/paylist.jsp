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
<title>收入记录</title>
<jsp:include page="../common/base.jsp" />
<script src="${ctx}/resources/js/charge/paylist.js"
	type="text/javascript">
	
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
			<ul class="breadcrumb">
				<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span
					class="divider">&gt;</span></li>
				<li class="active">财务管理 <span class="divider">&gt;</span></li>
				<li class="active">收入记录</li>
			</ul>

			<div class="form-inline">

				<!--  <div class="control-group inline">
					<label class="control-label" >社区:</label> 
					<input type="text" id="communityAddress" name="communityAddress" value="" />
				</div>  -->


				<div class="control-group inline ">
					<label class="control-label right">柜子id:</label> <input type="text"
						id="cabinetId" name="cabinetId" value="" />
				</div>

				<div class="control-group inline">
					<label class="control-label">时间范围:</label> <input id="startTime"
						type="text" style="cursor: pointer;width: 150px" name="startTime"
						class="span4"
						onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\')}'})">
					<span> ~</span> <input id="endTime" type="text"
						style="cursor: pointer;width: 150px" placeholder="" name="endTime"
						class="span4"
						onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\')}'})">
				</div>


				<div class="control-group inline">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary" id="btnQuery">查询</button>
				</div>
			</div>

			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab1" data-toggle="tab">明细</a></li>
				<li><a id="panel2" href="#tab2" data-toggle="tab">汇总</a></li>
			</ul>
			<div class="tab-content"
				>

				<div class="tab-pane active" id="tab1">
					<table id="table2"
						class="table table-hover table-bordered table-condensed table-striped">
						<thead>
							<tr>
								<th style="width: 25%;">柜子ID</th>
								<th style="width: 20%;">时间</th>
								<th style="width: 20%;">账户</th>
								<th style="width: 20%;">金额</th>
								<th style="width: 15%;">格子编号</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>

				<div class="tab-pane" id="tab2">
					<table id="table1"
						class="table table-hover table-bordered table-condensed table-striped">
						<thead>
							<tr>
								<th style="width: 30%;">柜子ID</th>
								<th style="width: 25%;">副柜个数</th>
								<th style="width: 20%;">使用次数</th>
								<th style="width: 25%;">收入合计</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			
		</script>
</body>

</html>