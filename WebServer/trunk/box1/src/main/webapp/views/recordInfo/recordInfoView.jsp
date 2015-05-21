<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.RecordInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	RecordInfo recordInfo = (RecordInfo) request.getAttribute("recordInfo");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>取件派件管理查看</title>
</head>
<body style="background:#FFF">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">快件管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查看</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">到柜时间：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=recordInfo.getDeliveryDate()!=null?recordInfo.getDeliveryDate().toString().substring(0,10):"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">单号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getExpressNumber()!=null?recordInfo.getExpressNumber():"" %>
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">收件人手机号：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=recordInfo.getPickupContact()!=null?recordInfo.getPickupContact():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">验证码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getVerificationCode()!=null?recordInfo.getVerificationCode():"" %>
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">派件员手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getDeliveryId()!=null&&recordInfo.getDeliveryId().getDeliveryContact()!=null?recordInfo.getDeliveryId().getDeliveryContact():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">柜号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getCupboardId()!=null&&recordInfo.getCupboardId().getCupboardName()!=null?recordInfo.getCupboardId().getCupboardName():"" %>
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">格号：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=recordInfo.getCabinetId()!=null&&recordInfo.getCabinetId().getCabinetName()!=null?recordInfo.getCabinetId().getCabinetName():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">快递公司：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getDeliveryId()!=null&&recordInfo.getDeliveryId().getDeliveryCompany()!=null?recordInfo.getDeliveryId().getDeliveryCompany():"" %>
	      	</td>
	    </tr>
	    <tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">物业：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=recordInfo.getCommunityId()!=null&&recordInfo.getCommunityId().getCommunityPropertyCompany()!=null?recordInfo.getCommunityId().getCommunityPropertyCompany():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">社区：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getCommunityId()!=null&&recordInfo.getCommunityId().getCommunityName()!=null?recordInfo.getCommunityId().getCommunityName():"" %>
	      	</td>
	    </tr>
	    <tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">取件时间：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getPickupDate()!=null?recordInfo.getPickupDate().toString().substring(0,10):"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">取件状态：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%if(recordInfo.getPickupType()!=null){ %>
	      			<%=recordInfo.getPickupType()==0?"取件人已取":recordInfo.getPickupType()==1?"快递员取回":"未取" %>
	      		<%} %>
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">快递条形码：</td>
	      	<td colspan="3" bgcolor="#FFFFFF">
	      		<img src="<%=recordInfo.getExpressCode()!=null?recordInfo.getExpressCode():"" %>" onerror="this.src='<%=basePath%>resources/images/no_pic.jpg'" width="200px" height="100px" >
	      	</td>
	    </tr>
	    <tr>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">超期金额：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=recordInfo.getPickupToMoney()!=null?recordInfo.getPickupToMoney():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">代收金额：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=recordInfo.getDeliveryToMoney()!=null?recordInfo.getDeliveryToMoney():"" %>
	      	</td>
	    </tr>
	    <tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">已超期天数：</td>
	      	<td colspan="3" bgcolor="#FFFFFF">
	      		<%=recordInfo.getDateCount()!=null?recordInfo.getDateCount():"" %>
	      	</td>
	    </tr>
      	<tr>
      		<td align="center" bgcolor="#FFFFFF" colspan="4">
	      		<input type="button" class="CBtext" value="关闭" style="width: 80px;" onclick="window.close()" />
	      	</td>
      	</tr>
	</table>
	</div>
</body>
</html>