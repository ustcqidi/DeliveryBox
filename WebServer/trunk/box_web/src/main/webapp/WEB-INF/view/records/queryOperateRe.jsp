<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset = utf-8" />

<jsp:include page="../common/base.jsp" />
<link href="<c:url value='static/dataTables/jquery.dataTables.css'/>" rel="stylesheet" type="text/css" />
 <link type="text/css"
	href="<c:url value='static/styles/recored/record.css'/>"
	rel="stylesheet" />   
    <script src="<c:url value='static/js/records/dTables.js'/>" type="text/javascript"></script>
	<script src="<c:url value='static/js/records/test_2.js'/>" type="text/javascript"></script>
	<script src="<c:url value='static/My97DatePicker/WdatePicker.js'/>" type="text/javascript"></script>
	<title>查询操作记录</title>
</head>

 <body>

  <div class ="right">
     <!-- 筛选框start -->
     <div style="display:none; position: absolute;background-color:#fff;border: 1px solid #bbb;height:400px; z-index:1;" id="menu1" >
		<!-- 筛选标题 -->
		<div style = "padding:0 0 5px 5px;border-bottom:1px solid gray;background-color:#57B1EF;">
			<span style = "float:left;margin-left:5px;font-weight:bold;">筛选</span>			
			<span class="add-on" style = "float:right;cursor:pointer" onclick="hideDiv(this)"><i class="icon-remove-sign" ></i></span>
			<div style = "clear:both;"></div>
		</div>
		<!-- 筛选标题结束 -->
		
		<!-- 条件筛选start -->
		<div style="height:320px;overflow:auto;overflow-x:hidden;margin:10px;" id="selectDiv">
			<!-- <div id = "rolediv" class = "condition">
				<span class = "span2">角色：</span>
				<span class = "span1 no select">全不选</span>
				<span class = "span1 all select">全选</span>
				<div style = "clear:both;"></div>
				<div class = "check-border" id="role">
					
				</div>
				<input type="hidden" id="roleSec">
			</div> -->
			<!-- 用户昵称start -->
			<div id = "niNamediv" class = "condition">
				<span class = "span2">用户昵称：</span>
				<span id="userNo" class = "span1 no select">全不选</span>
				<span id ="userAll" class = "span1 all select">全选</span>
				<div style = "clear:both;"></div>
				<div class = "check-border" id="user">
					
				</div>
				<input type="hidden" id="userSec" name="users">
			</div>
			<!-- 用户昵称end -->
			<!-- 二级菜单start -->
			<div id = "secMenudiv" class = "condition">
				<span class = "span2">二级菜单选项：</span>
				<span id = "optNo" class = "span1 no select">全不选</span>
				<span id="optAll" class = "span1 all select">全选</span>
				<div style = "clear:both;"></div>
				<div class = "check-border" id="opt">
					<label class="checkbox">
						<input type="checkbox" value="查询BFocus" name = "secMenu"/>
						查询BFocus						
					</label>
					<label class="checkbox">
						<input type="checkbox" value="新增BFocus" name = "secMenu">
						新增BFocus
					</label>
					<label class="checkbox">
						<input type="checkbox" value="查询信源" name = "secMenu">
						查询信源
					</label>
					<label class="checkbox">
						<input type="checkbox" value="新增信源" name = "secMenu">
						新增信源
					</label>
					
					<label class="checkbox">
						<input type="checkbox" value="查询规则" name = "secMenu">
						查询规则
					</label>
					<label class="checkbox">
						<input type="checkbox"  value="新增规则" name = "secMenu">
						新增规则
					</label>
					<label class="checkbox">
						<input type="checkbox" value="扩展规则" name = "secMenu">
						扩展规则
					</label>
					
					<label class="checkbox">
						<input type="checkbox" value="用户管理" name = "secMenu">
						用户管理
					</label>
					<label class="checkbox">
						<input type="checkbox"  value="用户新增" name = "secMenu">
						用户新增
					</label>
					<label class="checkbox">
						<input type="checkbox" value="角色管理" name = "secMenu">
						角色管理
					</label>
					<label class="checkbox">
						<input type="checkbox" value="角色新增" name = "secMenu">
						角色新增
					</label>
					<label class="checkbox">
						<input type="checkbox" value="密码修改" name = "secMenu">
						密码修改
					</label>
					
					<div style = "clear:both;"></div>
				</div>
				<input type="hidden" id="optSec" name="opts">
			</div>
			<!-- 二级菜单end -->
			
			<!-- 操作结果start -->
			<div id = "resultdiv" class = "condition">
				<span class = "span2">操作结果：</span>
				<span id="resNo" class = "span1 no select">全不选</span>
				<span id="resAll" class = "span1 all select">全选</span>
				<div style = "clear:both;"></div>
				<div class = "check-border" id="result">
					<label class="checkbox">
						<input type="checkbox" value="成功" name = "result">
						成功
					</label>
					<label class="checkbox">
						<input type="checkbox" value="失败" name = "result">
						失败
					</label>
					
					<div style = "clear:both;"></div>
					
				</div>
				<input type="hidden" id="resultSec" name="result">
			</div>
			<!-- 操作结果end -->		
		</div>
		<!-- 条件筛选end -->
		<div class = "select-icon">
			<button id="btnSave" class="btn btn-small btn-primary" onclick = "hideDiv(this)">保存</button>
			<button id="btnCancel" class="btn btn-small btn-primary" onclick = "cancel(this)">取消</button>
		</div>
	</div>
	<!-- 筛选框end -->

	<div class = "container-fluid">
		<div class = "row-fluid">
			<ul class="breadcrumb" style="margin-bottom: 5px;">
				<li>
					<a href="<c:url value='/dashboard.do'/>">首页</a>
					<span class="divider">&gt;</span>
				</li>
				<li class="active">系统管理 <span class="divider">&gt;</span></li>
				<li class="active">查询操作记录</li>
			</ul>
			
			<!-- 提示start -->
			<div class="alert alert-info">
				<button class="close" data-dismiss="alert" type="button">×</button>
				<i class = "icon-info-sign"></i> 
				<strong>提示：</strong>
				查询操作记录帮助了解用户操作明细。
			</div>
			<!-- 提示end -->
			
			<!-- 查询条件start -->
			<div class="controls controls-row text-right">
			    <span class="span2" style="margin-left:0px;">开始时间：</span>
				<input id="startDate" type="text" style="cursor:pointer;" placeholder=""  name="startDate" class="span2 Wdate" onchange="changeStart()" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,lang:'zh-cn',maxDate:'#F{$dp.$D(\'endDate\')}'})"> 
				<span class="span2" style="text-align:center;margin-left:-10px;">结束时间：</span>
				<input id="endDate" type="text"  style="cursor:pointer;margin-left:-20px;" placeholder="" name="endDate" class="span2 Wdate" onchange="changeEnd()" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,lang:'zh-cn',minDate:'#F{$dp.$D(\'startDate\')}'})"> 
				<button id = "test1" fordiv = "menu1" class = "btn span2">筛选</button>
			</div>		
			<!-- 查询条件end -->
			
			<!-- table div -->
			<div id="content">
				<table class = "table table-bordered table-hover table-condensed" id = "table1">
					<thead>
						<tr>
							<th>序号</th>
							<th>菜单</th>
							<th>用户昵称</th>
							<th>操作</th>
							<th>操作时间</th>
							<th>操作结果</th>
							<th>操作对象记录</th>
							<th>对象记录名称</th>
							<th>操作前值</th>
							<th>操作后值</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			<!-- table div end -->
		</div>
	</div>
  </div>
  
</body>
</html>