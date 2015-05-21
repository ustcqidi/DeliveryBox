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
<title>社区分配</title>
<script type="text/javascript">
	function buildTable(data){
	    var tempHtml ="";
	    if(data.totalCount==0){
	        tempHtml ="<tr bgcolor=\"#FFFFFF\" align=\"center\"><td colspan=\"10\" style=\"color:red;\">暂无社区</td></tr>";
	    }
	    var index = (currentPage - 1) * recordPerPage;
	    if(data.listInfo!=null && data.listInfo.length>0){
		    for(var i=0;i<data.listInfo.length;i++){
		        tempHtml +=
		            "<tr id='tableTr"+data.listInfo[i].id+"' bgcolor=\"#FFFFFF\">"+
		            "<td align=\"center\">"+"<input type=\"checkbox\" class=\"chbox\" />"+"</td>"+
		            "<td align=\"center\" style=\"display: none;\">"+data.listInfo[i].id+"</td>"+
		            "<td align=\"center\">"+parseInt(++index)+"</td>"+
		            "<td>"+data.listInfo[i].community.communityName+"</td>"+
		            "<td>"+data.listInfo[i].community.communityCode+"</td>"+
		            "<td>"+data.listInfo[i].community.communityUser+"</td>"+
		            "<td>"+data.listInfo[i].community.communityContact+"</td>"+
		            "<td>"+data.listInfo[i].community.communityPropertyCompany+"</td>"+
		            "<td>"+data.listInfo[i].community.communityPropertyStaff+"</td>"+
		            "</tr>";
		    }
	    }
	    $("#infoTable>tbody").html(tempHtml);
	    $("#totalCount").html(data.totalCount);
	    setTableStyle();
	}
	
	function addCommunity(userId){
		var url="<%=basePath%>userCommunity/userCommunityAdd.shtml?userId="+userId;
	    window.showModalDialog(url,window,"dialogWidth:1000px;dialogHeight:600px;*_dialogWidth:900px;*_dialogHeight:950px; margin:0px; padding:0px;resizable:yes;scroll:yes;center:yes;status:no;help:no;");
   	}
	function delCommunity(){
		if($("#infoTable>tbody").find("input:checked").size()==0){
            alert("请先选择社区！");
            return;
        }
		var ids="";
		$("#infoTable>tbody").find("input:checked").each(function(i){
       		var selectRow = $(this).parent().parent();
        	ids += selectRow.find("td").eq(1).text()+",";
        });
		if(ids.length>0){
			ids=ids.substring(0, ids.length-1);
			$.ajax({
				url:"userCommunity/userCommunityDel.shtml",
	           	data:{
	            	ids:ids
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
	            		goPage(1);
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">管理员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">社区分配</a>
		</span>
	</div>
	<div class="oper_area">
	    <table>
	    	<tr>
		        <td width="500px" align="left">
		        	<input type="button" value="添加社区" class="CBtext" onclick="addCommunity(${userId })" />
		        	<input type="button" value="删除社区" class="CBtext" onclick="delCommunity()" />
		        	<input type="button" value="返回" class="CBtext" onclick="location.href='<%=basePath%>adminUser/adminUserManage.shtml'" />
	        	</td>
	        	<td>&nbsp;</td>
	      	</tr>
	    </table>
	</div>
	<div class="clear"></div>
	<div class="list_area">
	    <table id="infoTable" width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#DDDDDD" class="showTable" style="font-size:12px">
			<thead>
		    <tr class="ttr" height="25">
		       	<th align="center"><input type="checkbox" id="allSelect" /></th>
		        <th align="center">序号</th>
		        <th align="center">社区名称</th>
		        <th align="center">社区编码</th>
		        <th align="center">社区联系人</th>
		        <th align="center">社区联系方式</th>
		        <th align="center">社区物业公司</th>
		        <th align="center">社区物业人员</th>
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
 	<form action="<%=basePath%>userCommunity/userCommunityList.shtml" method="post" id="queryForm">
		<input type="hidden" id="userId" name="userId" value="${userId }" />
	</form>
</body>
</html>