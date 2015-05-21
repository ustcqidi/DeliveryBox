<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.DeliveryUser"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	DeliveryUser deliveryUser = (DeliveryUser) request.getAttribute("deliveryUser");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>派件员查看</title>
</head>
<body style="background:#FFF">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">派件员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查看</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
   		<tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">公司：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryCompany():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">姓名：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryTrueName():"" %>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">性别：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%
	      			if(deliveryUser!=null&&deliveryUser.getDeliverySex()!=null){
		      			if(deliveryUser.getDeliverySex()==1){
   				%>
   					男
   				<%
		      			}else if(deliveryUser.getDeliverySex()==2){
   				%>
   					女
   				<%
		      			}
	      			}
   				%>
	      	</td>
	      	<td align="right" bgcolor="#FFFFFF">手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryContact():"" %>
	      	</td>
	    </tr>
      	<tr>
	      	<td align="right" bgcolor="#FFFFFF">身份证号码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getIdentityNumber():"" %>
	      	</td>
	    	<td align="right" bgcolor="#FFFFFF">剩余金额：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryMoney():0 %>￥
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">帐号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryUserName():"" %>
	      	</td>
	    	<td align="right" bgcolor="#FFFFFF">密码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryUserPass():"" %>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">家庭地址：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryAddress():"" %>
	      	</td>
	      	<td align="right" bgcolor="#FFFFFF">备用手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryPhone():"" %>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">备注：</td>
	      	<td colspan="3" bgcolor="#FFFFFF">
	      		<%=deliveryUser!=null?deliveryUser.getDeliveryRemarks():"" %>
	      	</td>
      	</tr>
      	<tr>
      		<td align="center" bgcolor="#FFFFFF" colspan="4">
	      		<input type="button" class="CBtext" value="关闭" style="width: 80px;" onclick="window.close();" />
	      	</td>
      	</tr>
	</table>
	</div>
</body>
</html>