<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.Community"%>
<%@ page import="com.xsz.edu.jpkc.entity.CommunityCupboard"%>
<%@ page import="com.xsz.edu.jpkc.entity.CommunityCabinet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Community com = (Community) request.getAttribute("community");
	CommunityCupboard comc = (CommunityCupboard) request.getAttribute("communityCupboard");
	List<Community> cList = (List) request.getAttribute("cList");
	Integer cupboardCount=(Integer)request.getAttribute("cupboardCount");//总柜数
	Integer cabinetCount=(Integer)request.getAttribute("cabinetCount");//总格数
	Integer cabinetYesCount=(Integer)request.getAttribute("cabinetYesCount");//存件合计
	Integer cabinetNoCount=(Integer)request.getAttribute("cabinetNoCount");//未存件合计
	Double deliveryToMoneySum=(Double)request.getAttribute("deliveryToMoneySum");//代收合计
	Double pickupToMoneySum=(Double)request.getAttribute("pickupToMoneySum");//超期合计
	String flag = (String)request.getAttribute("flag");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=basePath%>resources/js/commonQueryAndPaged.js"></script>
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>宝柜管理</title>
<script type="text/javascript">
	function buildTable(data){
	    var tempHtml ="";
	    if(data.totalCount==0){
	        tempHtml ="<tr bgcolor=\"#FFFFFF\" align=\"center\"><td colspan=\"15\" style=\"color:red;\">查询无数据</td></tr>";
	    }
	    var index = (currentPage - 1) * recordPerPage;
	    if(data.listInfo!=null && data.listInfo.length>0){
		    for(var i=0;i<data.listInfo.length;i++){
		    	if(data.flag==2){
			    	var cabinetTypeStr="大";
			    	if(data.listInfo[i].cabinetType!=null&&data.listInfo[i].cabinetType==2){
			    		cabinetTypeStr="中";
			    	}else if(data.listInfo[i].cabinetType!=null&&data.listInfo[i].cabinetType==3){
			    		cabinetTypeStr="小";
			    	}
			    	var cabinetIsBlankStr="空";
			    	if(data.listInfo[i].cabinetIsBlank!=null&&data.listInfo[i].cabinetIsBlank==1){
			    		cabinetIsBlankStr="不为空";
			    	}
			    	var cabinetIsLockStr="正常";
			    	if(data.listInfo[i].cabinetIsLock!=null&&data.listInfo[i].cabinetIsLock==0){
			    		cabinetIsLockStr="锁定";
			    	}
			        tempHtml +=
			            "<tr id='tableTr"+data.listInfo[i].id+"' bgcolor=\"#FFFFFF\">"+
			            "<td align=\"center\" style=\"display:none;\">"+"<input type=\"checkbox\" />"+"</td>"+
			            "<td align=\"center\">"+parseInt(++index)+"</td>"+
			            "<td align=\"left\">"+data.listInfo[i].cabinetName+"</td>"+
			            "<td>"+cabinetTypeStr+"</td>"+
			            "<td>"+cabinetIsBlankStr+"</td>"+
			            "<td>"+data.listInfo[i].cabinetMoney+"</td>"+
			            "<td>"+data.listInfo[i].maxDate+"</td>"+
			            "<td>"+data.listInfo[i].cabinetPrice+"</td>"+
			            "<td>"+cabinetIsLockStr+"</td>"+
			            "<td align=\"center\">"+
			            "<a href=\"javascript:void(0)\" onclick=\"addorUpdateModel(3,"+data.listInfo[i].id+","+data.listInfo[i].communityId.id+","+data.listInfo[i].cupboardId.id+",2);\">修改</a>&nbsp;" +
			            "<a href=\"javascript:void(0)\" onclick=\"delModel(3,"+data.listInfo[i].id+","+data.listInfo[i].cupboardId.id+",2);\">删除</a>" +
			            "</td>"+
			            "</tr>";
		    	}else{
		    		tempHtml +=
			            "<tr id='tableTr"+data.listInfo[i].id+"' bgcolor=\"#FFFFFF\">"+
			            "<td align=\"center\" style=\"display:none;\">"+"<input type=\"checkbox\" />"+"</td>"+
			            "<td align=\"center\">"+parseInt(++index)+"</td>"+
			            "<td align=\"left\">"+data.listInfo[i].cupboardName+"</td>"+
			            "<td>"+data.listInfo[i].cupboardType+"</td>"+
			            "<td>"+data.listInfo[i].cabinetCount+"</td>"+
			            "<td>"+data.listInfo[i].cabinetNoCount+"</td>"+
			            "<td>"+data.listInfo[i].cupboardBlank+"</td>"+
			            "<td align=\"center\">"+
			            "<a href=\"javascript:void(0)\" onclick=\"addorUpdateModel(2,"+data.listInfo[i].id+","+data.listInfo[i].communityId.id+",0,1);\">修改</a>&nbsp;" +
			            "<a href=\"javascript:void(0)\" onclick=\"delModel(2,"+data.listInfo[i].id+","+data.listInfo[i].communityId.id+",1);\">删除</a>" +
			            "</td>"+
			            "</tr>";
		    	}
	    	}
	    }
	    $("#infoTable>tbody").html(tempHtml);
	    $("#totalCount").html(data.totalCount);
	    $("#deliveryToMoneySum").html(data.deliveryToMoneySum);
	    $("#pickupToMoneySum").html(data.pickupToMoneySum);
	    setTableStyle();
	}
	//树点击打开列表
	function menus(str,picId){
		var obj, pic;
		// 返回 str 对象和 picId 对象是否存在
		if (document.getElementById(str) && document.getElementById(picId)){
			obj=document.getElementById(str); //obj 为 DIV 对象
			pic=document.getElementById(picId); //pic 为 图片对象
			if (obj.style.display == "none"){ //如果DIV对象的 display 样式值为 none 的话
				obj.style.display = ""; //就将 display 的样式清空
				pic.src = "resources/images/-.jpg"; //更改图片对象的路径
			}else{
				obj.style.display = "none";
				pic.src = "resources/images/+.jpg";
			}
		}
	}
	//点击树中社区事件
	function communityManage(flag,id){
		window.location.href="<%=basePath%>community/communityManage.shtml?objId="+id+"&flag="+flag+"&_t="+new Date();
	}
	//添加或修改 obj(第几级菜单) id(当前数据的ID) commuityId(社区ID) commuityId(柜子ID)
	function addorUpdateModel(obj, id, communityId, cupboardId, flag){
		if(obj==1){
			window.location.href="<%=basePath%>community/communityAddorUpdate.shtml?id="+id+"&communityId="+communityId+"&flag="+flag+"&_t="+new Date();
		}else if(obj==2){
			window.location.href="<%=basePath%>communityCupboard/communityCupboardAddorUpdate.shtml?id="+id+"&communityId="+communityId+"&cupboardId="+cupboardId+"&flag="+flag+"&_t="+new Date();
		}else{
			window.location.href="<%=basePath%>communityCabinet/communityCabinetAddorUpdate.shtml?id="+id+"&communityId="+communityId+"&cupboardId="+cupboardId+"&flag="+flag+"&_t="+new Date();
		}
		
	}
	//删除
	function delModel(obj, id, objId, flag){
		if(obj==1){
			if(confirm("删除社区会把社区下所有柜子和格子一起删掉，您确定要删除社区吗？")) {
				window.location.href="<%=basePath%>community/communityDel.shtml?id="+id+"&flag="+flag+"&_t="+new Date();
			}
		}else if(obj==2){
			if(confirm("删除柜子会把柜子下所有格子一起删掉，您确定要删除柜子吗？")) {
				window.location.href="<%=basePath%>communityCupboard/communityCupboardDel.shtml?objId="+objId+"&id="+id+"&flag="+flag+"&_t="+new Date();
			}
		}else{
			if(confirm("您确定要删除格子吗？")) {
				window.location.href="<%=basePath%>communityCabinet/communityCabinetDel.shtml?objId="+objId+"&id="+id+"&flag="+flag+"&_t="+new Date();
			}
		}
	}
</script>
<style type="text/css">
.nk1080 {
	width: 100%;
	height: 100%;
	margin-right: auto;;
	margin-left: auto;
}
.div1 {
	cursor: hand;
	width: 190px;
	margin-right: auto;
	margin-left: auto;
	background-image: url(resources/images/bigbj.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	cursor: pointer;
} 
.div1 img {
	float: left;
	margin-top: 8px;
}
.div1 span {
	line-height: 27px;
	margin-left: 6px;
}

.div1_1{
	cursor: hand;
	background-image: url(resources/images/smallbj.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	cursor: pointer;
}
.div1_1 img {
	float: left;
	margin-top: 6px;
	margin-left: 17px;
}
.div1_1 span {
	line-height: 22px;
	margin-left: 6px;
}
.div2 {
	width: 190px;
	margin-right: auto;
	margin-left: auto;
} 
.div1_1_1_1 {
	background-image: url(resources/images/xiaobj.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.div1_1_1_1 span {
	line-height: 23px;
	margin-left: 48px;
}
</style>
</head>
<body style="background:#FFF;">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">宝柜管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查询</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
		<table width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
			<tr bgcolor="#FFFFFF">
				<td width="20%" style="vertical-align: top;">
					<div class="nk1080">
						<%
							if(cList!=null && cList.size()>0){
								for(int i=0;i<cList.size();i++){
									Community community = (Community)cList.get(i);
									String comName=community.getCommunityName();
									if(comName.length()>14){
										comName=comName.substring(0,13)+"...";
									}
						%>
						<div class="div1"><img src="resources/images/<%=community.getCommunityCupboardList()!=null && community.getCommunityCupboardList().size()>0?"+":"-" %>.jpg" onClick="menus('menu<%=community.getId() %>', 'pic<%=community.getId() %>')" align="absmiddle" id="pic<%=community.getId() %>"><span onclick="communityManage(1,<%=community.getId() %>)" title="<%=community.getCommunityName()%>"><%=comName %></span></div>
						<%
							if(community.getCommunityCupboardList()!=null && community.getCommunityCupboardList().size()>0){
						%>
						<div id="menu<%=community.getId() %>" class="div2" style="display:none">
						<%
								for(int j=0;j<community.getCommunityCupboardList().size();j++){
									CommunityCupboard communityCupboard = (CommunityCupboard)community.getCommunityCupboardList().get(j);
									String ccName=communityCupboard.getCupboardName();
									if(ccName.length()>12){
										ccName=ccName.substring(0,11)+"...";
									}
						%>
					    	<div class="div1_1"><img src="resources/images/<%=communityCupboard.getCommunityCabinetList()!=null && communityCupboard.getCommunityCabinetList().size()>0?"+":"-" %>.jpg" onClick="menus('menu<%=community.getId() %>_<%=communityCupboard.getId() %>', 'pic<%=community.getId() %>_<%=communityCupboard.getId() %>')" align="absmiddle" id="pic<%=community.getId() %>_<%=communityCupboard.getId() %>"><span onclick="communityManage(2,<%=communityCupboard.getId() %>)" title="<%=communityCupboard.getCupboardName()%>"><%=ccName %></span></div>
					    <% 
					    	if(communityCupboard.getCommunityCabinetList()!=null && communityCupboard.getCommunityCabinetList().size()>0){
				    	%>
				   			<div id="menu<%=community.getId() %>_<%=communityCupboard.getId() %>" class="div1_1_1" style="display:none">
				   		<%
								for(int n=0;n<communityCupboard.getCommunityCabinetList().size();n++){
									CommunityCabinet communityCabinet = (CommunityCabinet)communityCupboard.getCommunityCabinetList().get(n);
									String cccName=communityCabinet.getCabinetName();
									if(cccName.length()>10){
										cccName=cccName.substring(0,9)+"...";
									}
						%>
							<div class="div1_1_1_1"><span title="<%=communityCabinet.getCabinetName()%>"><%=cccName %></span></div>
						<%} %>
				   			</div>
					    <%}} %>
					    </div>
					  	<%}}} %>
				  	</div>
				</td>
				<td style="vertical-align: top;">
					<%if("1".equals(flag)){ %>
					<fieldset>
   					<legend><b>社区信息</b></legend>
					<div class="clear"></div>
					<div class="oper_area">
					    <table style="background:#FFF;">
					      <tr>
					        <td>
					        	<input type="button" class="CBtext" value="新增" onclick="addorUpdateModel(1,0,<%=com!=null?com.getId():0 %>,0,1)" />&nbsp;&nbsp;
					        	<input type="button" class="CBtext" value="修改" onclick="addorUpdateModel(1,<%=com!=null?com.getId():0 %>,<%=com!=null?com.getId():0 %>,0,1)" />&nbsp;&nbsp;
					        	<input type="button" class="CBtext" value="删除" onclick="delModel(1,<%=com!=null?com.getId():0 %>,0,1)" />
					       	</td>
					      </tr>
					    </table>
					</div>
					<div class="clear"></div>
					<div class="query_area">
					   	<table width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
						    <tr>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区名称：</td>
						      	<td width="30%" bgcolor="#FFFFFF">
						      		<%=com!=null?com.getCommunityName():"" %>
						      	</td>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区编码：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=com!=null?com.getCommunityCode():"" %>
						      	</td>
						    </tr>
						    <tr>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区联系人：</td>
						      	<td width="30%" bgcolor="#FFFFFF">
						      		<%=com!=null?com.getCommunityUser():"" %>
						      	</td>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区联系方式：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=com!=null?com.getCommunityContact():"" %>
						      	</td>
						    </tr>
						    <tr>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区物业公司：</td>
						      	<td width="30%" bgcolor="#FFFFFF">
						      		<%=com!=null?com.getCommunityPropertyCompany():"" %>
						      	</td>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区物业人员：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=com!=null?com.getCommunityPropertyStaff():"" %>
						      	</td>
						    </tr>
						    <tr>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">社区地址：</td>
						      	<td bgcolor="#FFFFFF" colspan="3">
						      		<%=com!=null?com.getCommunityAddress():"" %>
						      	</td>
						    </tr>
						    <tr>
						      	<td align="right" bgcolor="#FFFFFF">总柜数：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=cupboardCount %>个
						      	</td>
						      	<td align="right" bgcolor="#FFFFFF">总格数：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=cabinetCount %>个
						      	</td>
				      	 	</tr>
				      	 	<tr>
						      	<td align="right" bgcolor="#FFFFFF">存件合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=cabinetYesCount %>个
						      	</td>
						      	<td align="right" bgcolor="#FFFFFF">未存件合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=cabinetNoCount %>个
						      	</td>
						    </tr>
						    <tr>
						      	<td align="right" bgcolor="#FFFFFF">代收合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=deliveryToMoneySum %>￥
						      	</td>
						      	<td align="right" bgcolor="#FFFFFF">超期合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=pickupToMoneySum %>￥
						      	</td>
						    </tr>
						</table>
					</div>
					</fieldset>
					<fieldset>
   					<legend><b>柜子信息</b></legend>
   					<div class="clear"></div>
					<div class="query_area">
						<form id="queryForm" action="<%=basePath%>community/communityCupboardList.shtml" method="post">
						<input type="hidden" id="communityId.id" name="communityId.id" value="<%=com!=null?com.getId():"" %>" />
					   	<table width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
						    <tr>
						      	<td width="15%" align="right" bgcolor="#FFFFFF">柜子名称：</td>
						      	<td width="25%" bgcolor="#FFFFFF">
						      		<input class="Atext" type="text" name="cupboardName" id="cupboardName"/>
						      	</td>
						      	<td width="15%" align="right" bgcolor="#FFFFFF">责任人：</td>
						      	<td bgcolor="#FFFFFF">
						      		<input class="Atext" type="text" name="cupboardType" id="cupboardType"/>&nbsp;&nbsp;
	      							<input type="button" class="CBtext" id="querySubmit" value="查询" style="width: 80px;" />
						      	</td>
						    </tr>
						    <tr>
						      	<td align="right" bgcolor="#FFFFFF">代收合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<label id="deliveryToMoneySum"></label>￥
						      	</td>
						      	<td align="right" bgcolor="#FFFFFF">超期合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<label id="pickupToMoneySum"></label>￥
						      	</td>
						    </tr>
						</table>
						</form>
					</div>
   					<%if(com!=null){ %>
					<div class="clear"></div>
					<div class="oper_area">
					    <table style="background:#FFF;">
					      <tr>
					        <td>
					        	<input type="button" class="CBtext" value="新增" onclick="addorUpdateModel(2,0,<%=com!=null?com.getId():0 %>,0,1)" />&nbsp;
					       	</td>
					      </tr>
					    </table>
					</div>
					<%} %>
					<div class="clear"></div>
					<div class="list_area">
					    <table id="infoTable" width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#DDDDDD" class="showTable" style="font-size:12px">
							<thead>
						    <tr class="ttr" height="25">
						       	<th align="center" style="display:none;"><input type="checkbox" id="allSelect" /></th>
						        <th align="center">序号</th>
						        <th align="center">柜子名称</th>
						        <th align="center">责任人</th>
						        <th align="center">总格数</th>
						        <th align="center">未存件格数</th>
						        <th align="center">备注</th>
						        <th align="center">操作</th>
						    </tr>
						    </thead>
						    <tbody>
						   	</tbody>
					   	</table>
				   	</div>
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
					</fieldset>
					<%}else{ %>
					<fieldset>
   					<legend><b>柜子信息</b></legend>
					<div class="clear"></div>
					<div class="oper_area">
					    <table style="background:#FFF;">
					      <tr>
					        <td>
					        	<input type="button" class="CBtext" value="新增" onclick="addorUpdateModel(2,0,<%=comc!=null&&comc.getCommunityId()!=null?comc.getCommunityId().getId():0 %>,<%=comc!=null?comc.getId():0 %>,2)" />&nbsp;&nbsp;
					        	<input type="button" class="CBtext" value="修改" onclick="addorUpdateModel(2,<%=comc!=null?comc.getId():0 %>,<%=comc!=null&&comc.getCommunityId()!=null?comc.getCommunityId().getId():0 %>,<%=comc!=null?comc.getId():0 %>,2)" />&nbsp;&nbsp;
					        	<input type="button" class="CBtext" value="删除" onclick="delModel(2,<%=comc!=null?comc.getId():0 %>,<%=comc!=null&&comc.getCommunityId()!=null?comc.getCommunityId().getId():0 %>,1)" />
					       	</td>
					      </tr>
					    </table>
					</div>
					<div class="clear"></div>
					<div class="query_area">
					   	<table width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
						    <tr>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">柜子名称：</td>
						      	<td width="30%" bgcolor="#FFFFFF">
						      		<%=comc!=null?comc.getCupboardName():"" %>
						      	</td>
						      	<td width="20%" align="right" bgcolor="#FFFFFF">责任人：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=comc!=null?comc.getCupboardType():"" %>
						      	</td>
						    </tr>
						    <tr>
						      	<td align="right" bgcolor="#FFFFFF">代收合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=deliveryToMoneySum %>￥
						      	</td>
						      	<td align="right" bgcolor="#FFFFFF">超期合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<%=pickupToMoneySum %>￥
						      	</td>
						    </tr>
						</table>
					</div>
					</fieldset>
					<fieldset>
   					<legend><b>格子信息</b></legend>
   					<div class="clear"></div>
					<div class="query_area">
						<form id="queryForm" action="<%=basePath%>community/communityCabinetList.shtml" method="post">
						<input type="hidden" id="cupboardId.id" name="cupboardId.id" value="<%=comc!=null?comc.getId():"" %>" />
					   	<table width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
						    <tr>
						      	<td width="15%" align="right" bgcolor="#FFFFFF">格子名称：</td>
						      	<td width="25%" bgcolor="#FFFFFF">
						      		<input class="Atext" type="text" name="cabinetName" id="cabinetName"/>
						      	</td>
						      	<td width="15%" align="right" bgcolor="#FFFFFF">是否为空：</td>
						      	<td bgcolor="#FFFFFF">
						      		<select id="cabinetIsBlank" name="cabinetIsBlank" class="Aselect">
						      			<option value=""></option>
						      			<option value="0">空</option>
						      			<option value="1">不为空</option>
						      		</select>&nbsp;&nbsp;
	      							<input type="button" class="CBtext" id="querySubmit" value="查询" style="width: 80px;" />
						      	</td>
						    </tr>
						    <tr>
						      	<td align="right" bgcolor="#FFFFFF">代收合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<label id="deliveryToMoneySum"></label>￥
						      	</td>
						      	<td align="right" bgcolor="#FFFFFF">超期合计：</td>
						      	<td bgcolor="#FFFFFF">
						      		<label id="pickupToMoneySum"></label>￥
						      	</td>
						    </tr>
						</table>
						</form>
					</div>
					<div class="clear"></div>
					<div class="oper_area">
					    <table style="background:#FFF;">
					      <tr>
					        <td>
					        	<input type="button" class="CBtext" value="新增" onclick="addorUpdateModel(3,0,<%=comc!=null&&comc.getCommunityId()!=null?comc.getCommunityId().getId():0 %>,<%=comc!=null?comc.getId():0 %>,2)" />&nbsp;
					       	</td>
					      </tr>
					    </table>
					</div>
					<div class="clear"></div>
					<div class="list_area">
					    <table id="infoTable" width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="#DDDDDD" class="showTable" style="font-size:12px">
							<thead>
						    <tr class="ttr" height="25">
						       	<th align="center" style="display:none;"><input type="checkbox" id="allSelect" /></th>
						        <th align="center">序号</th>
						        <th align="center">柜子名称</th>
						        <th align="center">类型</th>
						        <th align="center">是否为空</th>
						        <th align="center">价值</th>
						        <th align="center">最大超期天数</th>
						        <th align="center">格子单价</th>
						        <th align="center">锁定状态</th>
						        <th align="center">操作</th>
						    </tr>
						    </thead>
						    <tbody>
						   	</tbody>
					   	</table>
				   	</div>
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
					</fieldset>
					<%} %>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>