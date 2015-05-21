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
<title>派件员管理</title>
<script type="text/javascript">
	function buildTable(data){
	    var tempHtml ="";
	    if(data.totalCount==0){
	        tempHtml ="<tr bgcolor=\"#FFFFFF\" align=\"center\"><td colspan=\"10\" style=\"color:red;\">查询无数据</td></tr>";
	    }
	    var index = (currentPage - 1) * recordPerPage;
	    if(data.listInfo!=null && data.listInfo.length>0){
		    for(var i=0;i<data.listInfo.length;i++){
		    	var sex="男";
		    	if(data.listInfo[i].deliverySex==2){
		    		sex="女";
		    	}
		        tempHtml +=
		            "<tr id='tableTr"+data.listInfo[i].id+"' bgcolor=\"#FFFFFF\">"+
		            "<td align=\"center\" style=\"display:none;\">"+"<input type=\"checkbox\" />"+"</td>"+
		            "<td align=\"center\">"+parseInt(++index)+"</td>"+
		            "<td>"+data.listInfo[i].deliveryCompany+"</td>"+
		            "<td>"+data.listInfo[i].deliveryTrueName+"</td>"+
		            "<td>"+sex+"</td>"+
		            "<td>"+data.listInfo[i].deliveryContact+"</td>"+
		            "<td>"+data.listInfo[i].identityNumber+"</td>"+
		            "<td>"+data.listInfo[i].deliveryMoney+"￥</td>"+
		            "<td align=\"center\">"+
		            "<a href=\"javascript:void(0)\" onclick=\"viewModel("+data.listInfo[i].id+");\">查看</a>&nbsp;&nbsp;" +
		            "<a href=\"javascript:void(0)\" onclick=\"addorUpdateModel("+data.listInfo[i].id+");\">修改</a>&nbsp;&nbsp;" +
		            "<a href=\"javascript:void(0)\" onclick=\"delModel("+data.listInfo[i].id+");\">删除</a>" +
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
		window.location.href="<%=basePath%>deliveryUser/deliveryUserAddorUpdate.shtml?id="+id+"&_t="+new Date();
	}
	
	//查看
	function viewModel(id){
       	var url = "<%=basePath%>deliveryUser/deliveryUserView.shtml?id="+id;
       	window.open (url, '查看派件员信息', 'height=400px, width=800px,resizable=yes,scrollbars');
	}
	
	//删除
	function delModel(id){
		if(confirm("您确定要删除？")) {
			$.ajax({
				url:"deliveryUser/deliveryUserDel.shtml",
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">派件员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查询</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="queryForm" action="<%=basePath%>deliveryUser/deliveryUserList.shtml" method="post">
	<input type="hidden" id="isDelete" name="isDelete" value="1" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">姓名：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryTrueName" name="deliveryTrueName" />
	      	</td>
	      	
	      	<td width="15%" align="right" bgcolor="#FFFFFF">手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryContact" name="deliveryContact" />
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">公司：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryCompany" name="deliveryCompany" />
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">身份证号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="identityNumber" name="identityNumber" />&nbsp;&nbsp;
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
		        <th align="center">公司</th>
		        <th align="center">姓名</th>
		        <th align="center">性别</th>
		        <th align="center">手机号</th>
		        <th align="center">身份证号码</th>
		        <th align="center">余额</th>
		        <th align="center">操作</th>
		    </tr>
		    </thead>
		    <tbody>
		   	</tbody>
	   	</table>
   	</div>
   	<div class="admin_Pagination" style="width:100%;">
   		<table width="100%" align="center" style="font-size:12px;">
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
</body>
</html>