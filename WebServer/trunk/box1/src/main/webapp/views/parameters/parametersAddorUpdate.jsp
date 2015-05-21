<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.Parameters"%>
<%@ page import="com.xsz.edu.jpkc.entity.Community"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Parameters parameters = (Parameters) request.getAttribute("parameters");
	List<Object> comList = (List<Object>) request.getAttribute("comList");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>参数设置</title>
<script type="text/javascript">
	var validator;
	var isAdminUserLevel = false;
	$(function(){
		jQuery.validator.addMethod("numZero", function(value, element) {
	        if($(element).val() == "" || $(element).val() == null){
	            $(element).val("0");
	            return true;
	        }else{
	            return true;
	        }
	    }, "必填项！");
		validateSubmit();
	});
	function validateSubmit(){
		validator=$("#saveForm").validate({
            rules:{
            	communityIdObj:{
                    required:true
                },
            	userFreeTime:{
            		number:true,
            		numZero:true
                },
                overdue:{
                	digits:true,
            		numZero:true
                },
                dCabinetPrices:{
                	number:true,
            		numZero:true
                },
                zCabinetPrices:{
                	number:true,
            		numZero:true
                },
                xCabinetPrices:{
                	number:true,
            		numZero:true
                }
            },
            messages:{
        		
            },
            onkeyup:false,
            errorPlacement: function(error,element){
            	if(element.is(":radio"))
                    error.appendTo(element.next().next("em"));
                else
                    error.appendTo(element.nextAll("em").eq(0));
            },
            success: function(label){
                label.html("&nbsp;").addClass("checked");
            }
        });
	}
</script>
</head>
<body style="background:#FFF">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">系统管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)"><%=parameters!=null?"修改":"注册" %></a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">	
	<form id="saveForm" name="saveForm" action="<%=basePath%>parameters/parametersSave.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=parameters!=null?parameters.getId():"" %>" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;社区：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="communityIdObj" name="communityIdObj" class="Aselect">
	      			<option value=""></option>
	      			<%
	      				if(comList!=null&&comList.size()>0){
      						for (Iterator ite = comList.iterator(); ite.hasNext();) {
      							Object[] obj = (Object[]) ite.next();
	      						
					%>
					<option value="<%=obj[0] %>" <%=parameters!=null&&parameters.getCommunityId()!=null&&obj[0].equals(parameters.getCommunityId().getId())?"selected":"" %>><%=obj[1] %></option>
					<%
	      					}
	      				}
	      			%>
	      		</select><em></em>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">用户免费时间(小时)：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="userFreeTime" name="userFreeTime" maxlength="8" value="<%=parameters!=null?parameters.getUserFreeTime():"" %>" /><em></em>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">超期天数(天)：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="overdue" name="overdue" maxlength="11" value="<%=parameters!=null?parameters.getOverdue():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">大柜价格(角)：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="dCabinetPrices" name="dCabinetPrices" maxlength="8" value="<%=parameters!=null?parameters.getdCabinetPrices():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">中柜价格(角)：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="zCabinetPrices" name="zCabinetPrices" maxlength="8" value="<%=parameters!=null?parameters.getzCabinetPrices():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">小柜价格(角)：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="xCabinetPrices" name="xCabinetPrices" maxlength="8" value="<%=parameters!=null?parameters.getxCabinetPrices():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
      		<td align="center" bgcolor="#FFFFFF" colspan="2">
	      		<input type="submit" class="CBtext" id="saveBtn" name="saveBtn" value="保存" style="width: 80px;" />
	      	</td>
      	</tr>
	</table>
	</form>
	</div>
</body>
</html>