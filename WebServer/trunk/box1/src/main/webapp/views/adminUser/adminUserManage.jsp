<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>resources/js/commonQueryAndPaged.js"></script>
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>管理人员</title>
<script type="text/javascript">
	function buildTable(data){
	    var tempHtml ="";
	    if(data.totalCount==0){
	        tempHtml ="<tr bgcolor=\"#FFFFFF\" align=\"center\"><td colspan=\"10\" style=\"color:red;\">查询无数据</td></tr>";
	    }
	    var index = (currentPage - 1) * recordPerPage;
	    if(data.listInfo!=null && data.listInfo.length>0){
		    for(var i=0;i<data.listInfo.length;i++){
		    	var adminUserSex="";
		    	var adminUserLevel="";
		    	var cz="";
		    	if(data.listInfo[i].adminUserSex!=null&&data.listInfo[i].adminUserSex==1){
		    		adminUserSex="男";
		    	}else if(data.listInfo[i].adminUserSex!=null&&data.listInfo[i].adminUserSex==2){
		    		adminUserSex="女";
		    	}
		    	if(data.listInfo[i].adminUserLevel!=null&&data.listInfo[i].adminUserLevel==1){
		    		adminUserLevel="超级管理员";
		    	}else if(data.listInfo[i].adminUserLevel!=null&&data.listInfo[i].adminUserLevel==2){
		    		adminUserLevel="充值人员";
		    	}else if(data.listInfo[i].adminUserLevel!=null&&data.listInfo[i].adminUserLevel==3){
		    		adminUserLevel="管理员";
		    		cz="&nbsp;<a href=\"javascript:void(0)\" onclick=\"communityModel("+data.listInfo[i].id+");\">社区分配</a>";
		    	}
		        tempHtml +=
		            "<tr id='tableTr"+data.listInfo[i].id+"' bgcolor=\"#FFFFFF\">"+
		            "<td align=\"center\" style=\"display:none;\">"+"<input type=\"checkbox\" />"+"</td>"+
		            "<td align=\"center\">"+parseInt(++index)+"</td>"+
		            "<td align=\"left\">"+data.listInfo[i].adminTrueName+"</td>"+
		            "<td>"+adminUserSex+"</td>"+
		            "<td align=\"left\">"+data.listInfo[i].adminUserName+"</td>"+
		            "<td>"+data.listInfo[i].adminUserPhone+"</td>"+
		            "<td>"+adminUserLevel+"</td>"+
		            "<td align=\"center\">"+
		            "<a href=\"javascript:void(0)\" onclick=\"addorUpdateModel("+data.listInfo[i].id+");\">修改</a>&nbsp;" +
		            "<a href=\"javascript:void(0)\" onclick=\"delModel("+data.listInfo[i].id+");\">删除</a>" +
		            cz+
		            "</td>"+
		            "</tr>";
		    }
	    }
	    $("#infoTable>tbody").html(tempHtml);
	    $("#totalCount").html(data.totalCount);
	    setTableStyle();
	}
	
	//添加或修改
	function addorUpdateModel(id){
		window.location.href="<%=basePath%>adminUser/adminUserAddorUpdate.shtml?id="+id+"&_t="+new Date();
	}
	//社区分配
	function communityModel(id){
		window.location.href="<%=basePath%>userCommunity/userCommunityManage.shtml?userId="+id+"&_t="+new Date();
	}
	
	//删除
	function delModel(id){
		if(confirm("您确定要删除？")) {
			$.ajax({
				url:"adminUser/adminUserDel.shtml",
               	data:{
                	id:id
               	},
               	type:"POST",
              	contentType:"application/x-www-form-urlencoded;charset=utf-8",
               	//数据返回格式
               	dataType:"json",
               	//是否同步
               	async:false,
               	//ajax请求失败回调函数
               	error:function(e){
                   	alert("操作失败，请重试！");
               	},
               	//成功回调函数
               	success:function(data){
                   	if (data.result==1) {
                	   goPage(toPage);
                   	}
				}
			});
		}
	}
</script>
</head>
<body style="background:#FFF">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">管理员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查询</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="queryForm" action="<%=basePath%>adminUser/adminUserList.shtml" method="post">
	<input type="hidden" id="isDelete" name="isDelete" value="1" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">姓名：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="adminTrueName" name="adminTrueName" />
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">帐号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="adminUserName" name="adminUserName" />
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">手机号：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="adminUserPhone" name="adminUserPhone" />
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">级别：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="adminUserLevel" name="adminUserLevel" class="Aselect">
	      			<option value=""></option>
	      			<option value="1">超级管理员</option>
	      			<option value="2">充值人员</option>
	      			<option value="3">管理员</option>
	      		</select>&nbsp;&nbsp;
	      		<input type="button" class="CBtext" id="querySubmit" value="查询" style="width: 80px;" />
	      	</td>
	    </tr>
	</table>
	</form>
	</div>
	<div class="clear"></div>
	<div class="list_area">
	    <table id="infoTable" width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#DDDDDD" class="showTable" style="font-size:12px">
			<thead>
		    <tr class="ttr" height="25">
		       	<th align="center" style="display:none;"><input type="checkbox" id="allSelect" /></th>
		        <th align="center">序号</th>
		        <th align="center">姓名</th>
		        <th align="center">性别</th>
		        <th align="center">帐号</th>
		        <th align="center">手机号</th>
		        <th align="center">级别</th>
		        <th align="center">操作</th>
		    </tr>
		    </thead>
		    <tbody>
		   	</tbody>
	   	</table>
	   	<div class="admin_Pagination" style="width:100%;">
	   		<table width="100%" align="center" style="font-size:12px;background:#FFF;">
	   			<tr>
	   				<td align="center">
       					<span>
				         	<span style="padding-left:5px;">共有</span>
				         	<span style="color:red;padding-left:5px;"><label id="totalCount">0</label></span>
				         	<span style="padding-left:5px;">条记录</span>
				        </span>
       					<a href="javascript:void(0)" id="first" name="first" onclick="topPage();">首页 </a>
       					<a href="javascript:void(0)" id="prev" name="prev" onclick="prevPage();">上一页</a>
				        <a href="javascript:void(0)" id="next" name="next" onclick="nextPage();">下一页</a>
				        <a href="javascript:void(0)" id="last" name="last" onclick="endPage();">末页</a>
       					<input type="text" class="Ftext" id="pageIndex" name="pageIndex" />
       					<span id="pageSpan" style="color: red;"></span>
       					<input type="button" class="MNbutton" id="go" name="go" value="GO" />
       					<input type="hidden" id="pageCount" name="pageCount" value="15" />
					</td>
				</tr>
			</table>
		</div>
 	</div>
</body>
</html>