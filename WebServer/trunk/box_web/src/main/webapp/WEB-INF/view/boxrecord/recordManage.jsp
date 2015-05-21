<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>

<head>
<jsp:include page="../common/base.jsp" />
<script type="text/javascript"
	src="${ctx}/resources/js/recordmanage/manage.js"></script>
<title>业务管理</title>
<style>
.form-inline .control-group label{ width: 60px;}
</style>
</head>
<body>
	<div class="right">
	<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
			<li class="active">快件管理 <span class="divider">&gt;</span></li>
			<li class="active">快件信息</li>
		</ul>
	

			<div class="form-inline">
				<div class="control-group inline ">
					<label class="control-label right" >单号:</label> 
					<input type="text" id="expressNumber" name="expressNumber" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label" >收件人:</label> 
					<input type="text" id="receiveTel" name="receiveTel" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label" >派件人:</label> 
					<input type="text" id="deliveryTel" name="deliveryTel" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label" >柜子ID:</label> 
					<input type="text" id="cabinetId" name="cabinetId" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label" >社区:</label> 
					<input type="text" id="communityName" name="communityName" value="" />
				</div>
				
					
				<div class="control-group inline">
					<label class="control-label"  >快件状态:</label> 
					<select type="text" id="pickupType" name="pickupType" value="" >
					<option value=''>请选择快件状态</option>
					<option value='0'>用户未确认</option>
					<option value='1'>正在派件</option>
					<option value='2'>用户已取件</option>
					<option value='3'>快递员取消返回</option>
					<option value='5'>快递员自取</option>
					<option value='6'>帮他代取</option>
					</select>
				</div>


			<div class="control-group inline">
				<label class="control-label">到柜时间:</label> <input id="dstartTime"
					type="text" style="cursor: pointer;" name="dstartTime" class="span2"
					onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'dendTime\')}'})">
				<span> ~</span> <input id="dendTime" type="text"
					style="cursor: pointer;" placeholder="" name="dendTime"
					class="span2"
					onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'dstartTime\')}'})">
			</div>

		 <div class="control-group inline">
				<label class="control-label">取件时间:</label> <input id="rstartTime"
					type="text" style="cursor: pointer;" name="rstartTime" class="span2"
					onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'rendTime\')}'})">
				<span> ~</span> <input id="rendTime" type="text"
					style="cursor: pointer;" placeholder="" name="rendTime"
					class="span2"
					onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'rstartTime\')}'})">
			</div>
				
				
				

				<div class="control-group inline">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary" id="btnQuery">查询</button>
				</div>
			</div>
                <div style="">
				<table class="table table-hover table-bordered table-condensed table-striped" >
					<thead>
						<tr>
							<th style="width: 9%;">派送时间</th>
							<th style="width: 8%;">快递单号</th>
							<th style="width: 8%;">收件人</th>
							<th style="width: 8%;">派件人</th>
							<th style="width: 8%;">社区</th>
							<th style="width: 8%;">柜子ID</th>
							<th style="width: 6%;">格号</th>
							<th style="width: 8%;">柜子名称</th>
							<th style="width: 8%;">快递公司</th>
							<th style="width: 8%;">取件时间</th>
							<th style="width: 8%;">派件状态</th>
							<th style="width: 6%;">验证码</th>
							<th style="width: 7%;">删除</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				</div>
	</div>
</body>
</html>