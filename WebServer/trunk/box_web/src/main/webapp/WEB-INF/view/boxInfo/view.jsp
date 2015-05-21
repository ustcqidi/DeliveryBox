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
	src="${ctx}/resources/js/boxInfo/view.js?<%=System.currentTimeMillis() %>"></script>
<script type="text/javascript">
    operation = '${operation}';
	id = '${id}';
	
	$(function() {
		
	})
	

</script>
<style>
#actionMenu ul li {
padding: 8px 20px;
cursor: pointer;
list-style: none outside none;
font-size: 12px;
font-family: Verdana, Arial, Helvetica, AppleGothic, sans-serif;
color: #555;
border-radius: 0;
}

.list-group-item {
position: relative;
display: block;
padding: 10px 15px;
margin-bottom: -1px;
background-color: #fff;
border: 1px solid #ddd;
}
#actionMenu ul li:hover {
color: #222;
background-color: #0088cc
}
</style>
<title>业务</title>
</head>
<body>
<div id="actionMenu" style="z-index: 4;position: absolute;
				visibility: hidden;visibility: hidden; width: 140px">
		<ul class="list-group">
			<li data-action="create" onclick="editBox()"class="list-group-item" style="display: block;">
				<i class="icon-file"></i>
				&nbsp;修改
			</li>
			<li data-action="view" onclick="insert(1)" class="list-group-item" style="display: block;">
			<i class="icon-chevron-up"></i>
				&nbsp;插入
			</li>
			<li data-action="edit" onclick="insert(2)" class="list-group-item" style="display: block;">
				<i class=" icon-chevron-down"></i>
				&nbsp;插入
			</li>
			<li data-action="delete" onclick="deleteBox()" class="list-group-item" style="display: block;">
				<i class="icon-trash"></i>
				&nbsp;删除
			</li>
		</ul>
	</div>

	<div class="right">
		<form:form id="form" action="${ctx}/boxInfo/updateBoxInfo.json"
			method="post">
			<div class="row-fluid ">
				<ul class="breadcrumb" style="margin-bottom: 5px;">
					<li><a href="<c:url value='/dashboard.do'/>">首页</a> <span
						class="divider">&gt;</span></li>
					<li class="active">宝柜管理 <span class="divider">&gt;</span></li>
					<li class="active"><c:if test="${operation == 'update'}">
							宝柜修改
						</c:if> <c:if test="${operation == 'view'}">
							宝柜查看
						</c:if></li>
				</ul>
				<div class="tabbable" style="margin-bottom: 18px;">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#tab1" data-toggle="tab">柜子信息</a></li>
						<li><a id="panel2" href="#tab2" data-toggle="tab">柜子使用情况</a></li>
					</ul>
					<div class="tab-content"
						style="padding-bottom: 1px; border-left: 1px solid #ddd;">
						<div class="tab-pane active" id="tab1" style="margin-left: 35px;">
							<div class="control-group">
								<label class="control-label"> 柜子id： </label>
								<div class="controls">
									<input type="text" id="cabinetId" name="cabinetId"
										value='${boxInfo.cabinetId}' disabled="disabled">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="trueName"> 柜子名称： </label>
								<div class="controls">
									<input type="text" id="cabinetName" name="cabinetName"
										value='${boxInfo.cabinetName}'> <span
										class="label label-important"></span>
								</div>

							</div>

							<div class="control-group">
								<label class="control-label" for="phone">省/市/区：</label>
								<div class="controls">
									<input type="text" id="province" name="province"
										value='${boxInfo.province}'> <input type="text"
										id="city" name="city" value='${boxInfo.city}'> <input
										type="text" id="region" name="region"
										value='${boxInfo.region}'>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="phone">社区名称：</label>
								<div class="controls">
									<input type="text" id="communityName" name="communityName"
										value='${boxInfo.communityName}'>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="phone">社区联系方式：</label>
								<div class="controls">
									<input type="text" id="communityContact"
										name="communityContact" value='${boxInfo.communityContact}'>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label" for="position">负责人：</label>
								<div class="controls">
									<input type="text" name=manager id="manager"
										value='${boxInfo.manager}'>
								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="position">副柜个数：</label>
								<div class="controls">
									<input type="text" name=smallCount id="smallCount"
										disabled="disabled" value='${boxInfo.smallCount}'> <span
										class="label label-important"></span> <span> <i
										id="lock" title="修改柜子个数" style="cursor: pointer;"
										class="icon-lock"></i></span>

								</div>
							</div>


							<div class="control-group" style="margin-top: 20px;">
								<label class="control-label" id="labelContent"><font>详细地址&nbsp;&nbsp;</font>
								</label>
								<div class="controls">
									<textarea rows="4" cols="" style="width: 78%"
										name="communityAddress" id="communityAddress"
										placeholder="1024个字以内" maxlength="4096">${boxInfo.communityAddress}</textarea>

								</div>
							</div>

							<div class="control-group">
								<label class="control-label" for="position">二维码：</label>
								<div class="controls">
									<img style="height: 300px; width: 400px"
										src='${boxInfo.img_url}'>
								</div>
							</div>


						</div>

						<div class="tab-pane" id="tab2" style="margin-left: 35px;">
							<div id="tab2">
								<div style="width: 49%; float: left;" id="main_div">
									<label class="control-label"> 主柜： </label>
									<table id="main"
										class="table table-hover table-bordered table-condensed table-striped">
										<thead>
											<tr>
												<th style="width: 9%;">格号</th>
												<th style="width: 9%;">格号</th>
												<th style="width: 9%;">是否占用</th>
												<th style="width: 9%;">占用时间</th>
												<th style="width: 9%;">占用人</th>

											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>

								<div id="fugui"></div>
							</div>
						</div>
					</div>
				</div>

				<input type="hidden" id="cabinetId" name="cabinetId" value="${id}" />
				<div class="btns">
					<c:if test="${operation == 'update'}">
						<input id="btnSave" type="button" value="保存"
							class="btn-primary btn" style="margin-left: 45%; height: 30px;"
							name="btnSave" />
						<input id="btnCancel" class="btn btn-primary" type="button"
							value="取消" style="height: 30px;" />
					</c:if>


					<c:if test="${operation == 'view'}">
						<input id="btnCancel" class="btn btn-primary" type="button"
							value="取消" style="height: 30px; margin-left: 45%;" />
					</c:if>
				</div>
		</form:form>
	</div>

	<div id="panel" class="panel form-horizontal" title="新增/修改">
		<div class="control-group">
			<label class="control-label" for="name">格子编号</label>
			<div class="controls">
				<input type="text" id="name" placeholder="必填" value=""><span
					id="tipOpCode" class="help-inline">*</span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="name">柜子类型</label>
			<div class="controls">
				<select>
				<option value="1">大</option>
				<option value="2">中</option>
				<option value="3">小</option>
				</select>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label" for="desc">用戶描述:</label>
			<div class="controls">
				<textarea type="text" id="desc" value=""></textarea>
			</div>
		</div>
	</div>
</body>
</html>