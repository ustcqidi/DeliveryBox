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
<title>快件管理</title>
<script type="text/javascript">
	function buildTable(data){
	    var tempHtml ="";
	    if(data.totalCount==0){
	        tempHtml ="<tr bgcolor=\"#FFFFFF\" align=\"center\"><td colspan=\"12\" style=\"color:red;\">查询无数据</td></tr>";
	    }
	    var index = (currentPage - 1) * recordPerPage;
	    if(data.listInfo!=null && data.listInfo.length>0){
		    for(var i=0;i<data.listInfo.length;i++){
		    	var cupboardName="";
		    	if(data.listInfo[i].cupboardId!=null&&data.listInfo[i].cupboardId.cupboardName!=null){
		    		cupboardName=data.listInfo[i].cupboardId.cupboardName;
		    	}
		    	var deliveryDate="";
		    	if(data.listInfo[i].pickupDate!=null){
		    		deliveryDate=data.listInfo[i].pickupDate;
		    	}
		    	
		        tempHtml +=
		            "<tr id='tableTr"+data.listInfo[i].id+"' bgcolor=\"#FFFFFF\">"+
		            "<td align=\"center\" style=\"display:none;\">"+"<input type=\"checkbox\" />"+"</td>"+
		            "<td align=\"center\">"+parseInt(++index)+"</td>"+
		            "<td align=\"center\">"+new Date(deliveryDate).format('yyyy-MM-dd hh:mm:ss')+"</td>"+
		            "<td>"+data.listInfo[i].expressNumber+"</td>"+
		            "<td>"+data.listInfo[i].pickupContact+"</td>"+
		            "<td>"+data.listInfo[i].verificationCode+"</td>"+
		            "<td>"+data.listInfo[i].deliveryId.deliveryContact+"</td>"+
		            "<td>"+cupboardName+"</td>"+
		            "<td>"+data.listInfo[i].cabinetId.cabinetName+"</td>"+
		            "<td align=\"center\">"+
		            "<a href=\"javascript:void(0)\" onclick=\"viewModel("+data.listInfo[i].id+");\">查看</a>&nbsp;&nbsp;" +
		            "<a href=\"javascript:void(0)\" onclick=\"sendModel("+data.listInfo[i].id+");\">发送短信通知</a>" +
		            "</td>"+
		            "</tr>";
		    }
	    }
	    $("#infoTable>tbody").html(tempHtml);
	    $("#totalCount").html(data.totalCount);
	    $("#deliveryToMoneySum").html(data.deliveryToMoneySum);
	    $("#pickupToMoneySum").html(data.pickupToMoneySum);
	    $("#moneySum").html(data.moneySum);
	    setTableStyle();
	}
	
	//收件提醒
	function sendModel(id){
		$.ajax({
			url:"recordInfo/recordInfoSendCheck.shtml",
           	data:{
           		id:id
           	},
           	type:"POST",
          	contentType:"application/x-www-form-urlencoded;charset=utf-8",
           	dataType:"json",
           	async:false,
           	error:function(e){
           		
           	},
           	success:function(dataFirm){
           		if(confirm(dataFirm.result)){
	           		$.ajax({
	        			url:"recordInfo/recordInfoSend.shtml",
	                   	data:{
	                   		id:id
	                   	},
	                   	type:"POST",
	                  	contentType:"application/x-www-form-urlencoded;charset=utf-8",
	                   	dataType:"json",
	                   	async:false,
	                   	error:function(e){
	                   		
	                   	},
	                   	success:function(data){
	                   		if(data.result){
	                   			if(data.msg == "OK"){
	                   				alert("发送成功！");
	                   			}else{
	                   				alert("发送失败！");
	                   			}
	                   		}else{
	                   			alert("联系电话错误！");
	                   		}
	        			}
	        		});
           		}
			}
		});
	}
	
	//查看
	function viewModel(id){
		var url="<%=basePath%>recordInfo/recordInfoView.shtml?id="+id+"&_t="+new Date();
    	window.open (url, '查看', 'height=400px, width=800px,resizable=yes,scrollbars');
	}
</script>
</head>
<body style="background:#FFF;">
	<div class="navi_area" >
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">快件管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查询</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="queryForm" action="<%=basePath%>recordInfo/recordInfoList.shtml" method="post">
	<input type="hidden" id="isDelete" name="isDelete" value="1" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
   		<tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">单号：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="expressNumber" name="expressNumber" value="" />
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">收件人手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="pickupContact" name="pickupContact" value="" />
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">派件人员手机号：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryId.deliveryContact" name="deliveryId.deliveryContact" value="" />
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">到柜日期：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Wdate" name="deliveryDateStart" id="deliveryDateStart" readonly="readonly" onFocus="WdatePicker({isShowClear:true,maxDate:'#F{$dp.$D(\'deliveryDateEnd\',{d:-1})}'})" onchange="changeSqksStart(this.value)" size="10" />
				<font class="font18 fontA">~</font>
				<input type="text" class="Wdate" name="deliveryDateEnd" id="deliveryDateEnd" readonly="readonly" onFocus="WdatePicker({isShowClear:true,minDate:'#F{$dp.$D(\'deliveryDateStart\',{d:1})}'})" onchange="changeSqksEnd(this.value)" size="10" />
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">社区：</td>
	      	<td width="25%" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityId.communityName" name="communityId.communityName" value="" />
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">柜号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="cupboardId.cupboardName" name="cupboardId.cupboardName" value="" />
	      	</td>
	    </tr>
	    <tr>
            <td width="16%" align="right" bgcolor="#FFFFFF">格号：</td>
			<td width="26%" bgcolor="#FFFFFF"><input class="Atext" type="text" name="cabinetId.cabinetName" id="cabinetId.cabinetName" value="" /></td>
            <td width="16%" align="right" bgcolor="#FFFFFF">物业公司：</td>
			<td width="26%" bgcolor="#FFFFFF"><input class="Atext" type="text" name="communityId.communityPropertyCompany" id="communityId.communityPropertyCompany" value="" /></td>
		</tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">快递公司：</td>
	      	<td colspan="3" bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryId.deliveryCompany" name="deliveryId.deliveryCompany" value="" />&nbsp;&nbsp;
	      		<input type="button" class="CBtext" id="querySubmit" value="查询" style="width: 80px;" />
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">金额：</td>
	      	<td colspan="3" width="25%" bgcolor="#FFFFFF">
	      		总派件支付：<label id="deliveryToMoneySum" style="color: red;"></label>￥&nbsp;&nbsp;&nbsp;&nbsp;
	      		总取件支付：<label id="pickupToMoneySum" style="color: red;"></label>￥&nbsp;&nbsp;&nbsp;&nbsp;
	      		合计：<label id="moneySum" style="color: red;"></label>￥
	      	</td>
	    </tr>
	</table>
	</form>
	</div>
	<div class="clear"></div>
		<div class="query_area">
	    <table id="infoTable" width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#DDDDDD" class="showTable" style="font-size:12px">
			<thead>
		    <tr class="ttr" height="25">
		       	<th align="center" style="display:none;"><input type="checkbox" id="allSelect" /></th>
		        <th align="center">序号</th>
		        <th align="center">到柜时间</th>
		        <th align="center">单号</th>
		        <th align="center">收件人手机号</th>
		        <th align="center">验证码</th>
		        <th align="center">派件员手机号</th>
		        <th align="center">柜号</th>
		        <th align="center">格号</th>
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