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
	src="${ctx}/resources/js/boxInfo/manage.js"></script>
<title>宝柜管理</title>
<style>
.form-inline .control-group label{ width: 60px;}
.form-inline .control-group input{ width: 160px;}
</style>
</head>
<body>
	<div class="right">
	<ul class="breadcrumb" style="margin-bottom: 5px;">
			<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span class="divider">&gt;</span></li>
			<li class="active">宝柜管理 <span class="divider">&gt;</span></li>
			<li class="active">宝柜管理</li>
		</ul>
	

			<div class="form-inline">
			
			   
				<div class="control-group inline">
					<label class="control-label" >省份:</label> 
					<input type="text" id="province" name="province" value="" />
				</div> 
				
				 <div class="control-group inline">
					<label class="control-label" >城市:</label> 
					<input type="text" id="city" name="city" value="" />
				</div> 
				
				 <div class="control-group inline">
					<label class="control-label" >区域:</label> 
					<input type="text" id="region" name="region" value="" />
				</div> 
				
				
				 <div class="control-group inline">
					<label class="control-label" >社区:</label> 
					<input type="text" id="communityAddress" name="communityAddress" value="" />
				</div> 
				
				
			 	<div class="control-group inline ">
					<label class="control-label right" >柜子id:</label> 
					<input type="text" id="cabinetId" name="cabinetId" value="" />
				</div>
				
				<div class="control-group inline">
					<label class="control-label" >柜子名称:</label> 
					<input type="text" id="communityName" name="communityName" value="" />
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
							<th style="width: 9%;">柜子id</th>
							<th style="width: 10%;">社区名称</th>
							<th style="width: 9%;">柜子名称</th>
							<th style="width: 7%;">省份</th>
							<th style="width: 7%;">地市</th>
							<th style="width: 7%;">区域</th>
							<th style="width: 9%;">地址</th>
							<th style="width: 9%;">社区联系方式</th>
							<th style="width: 9%;">创建时间</th>
							<th style="width: 8%;">副柜个数</th>
							<th style="width: 10%;">查看</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
				</div>
	</div>
</body>
</html>