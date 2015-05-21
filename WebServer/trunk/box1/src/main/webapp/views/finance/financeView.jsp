<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.Finance"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Finance finance = (Finance) request.getAttribute("finance");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>查看</title>
</head>
<body style="background:#FFF">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">财务管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">收入记录</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查看</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
   		<tr>
	    	<td width="15%" align="right" bgcolor="#FFFFFF">社区：</td>
	      	<td width="35%" bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getCommunityId()!=null?finance.getCommunityId().getCommunityName():"" %>
	      	</td>
	      	<td width="15%" align="right" bgcolor="#FFFFFF">柜号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getCupboardId()!=null?finance.getCupboardId().getCupboardName():"" %>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">格号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getCabinetId()!=null?finance.getCabinetId().getCabinetName():"" %>
	      	</td>
	      	<td align="right" bgcolor="#FFFFFF">代收合计：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getFinanceType()==1?finance.getFinanceMoney():0 %>￥
	      	</td>
	    </tr>
      	<tr>
	      	<td align="right" bgcolor="#FFFFFF">超期合计：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getFinanceType()==2?finance.getFinanceMoney():0 %>￥
	      	</td>
	    	<td align="right" bgcolor="#FFFFFF">到柜时间：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null?finance.getCupboardTime().toString().substring(0,10):"" %>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">派件员姓名：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getDeliveryUserId()!=null?finance.getDeliveryUserId().getDeliveryTrueName():"" %>
	      	</td>
	    	<td align="right" bgcolor="#FFFFFF">派件员手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getDeliveryUserId()!=null?finance.getDeliveryUserId().getDeliveryContact():"" %>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">收件人手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null?finance.getPhoneNumber():"" %>
	      	</td>
	      	<td align="right" bgcolor="#FFFFFF">收费时间：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=finance!=null?finance.getFinanceDate().toString().substring(0,10):"" %>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">公司：</td>
	      	<td colspan="3" bgcolor="#FFFFFF">
	      		<%=finance!=null&&finance.getDeliveryUserId()!=null?finance.getDeliveryUserId().getDeliveryCompany():"" %>
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