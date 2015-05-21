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
<title>派件员<%=deliveryUser!=null?"修改":"新增" %></title>
<script type="text/javascript">
	$(function(){
		$.validator.addMethod("identityNumberCheck", function(value, element) {
			var id = $("#id").val();
			var identityNumber = $(element).val();
			var result=0;
			$.ajax({
				url:"deliveryUser/identityNumberCheck.shtml",
               	data:{
               		id:id,identityNumber:identityNumber
               	},
               	type:"POST",
              	contentType:"application/x-www-form-urlencoded;charset=utf-8",
               	dataType:"json",
               	async:false,
               	error:function(e){
               		
               	},
               	success:function(data){
               		result=data.result;
				}
			});
			if(result>0){
       			return false;
       		}else{
       			return true;
       		}
	    }, "身份证号已存在！");
		$.validator.addMethod("deliveryUserNameCheck", function(value, element) {
			var id = $("#id").val();
			var deliveryUserName = $(element).val();
			var result=0;
			$.ajax({
				url:"deliveryUser/deliveryUserNameCheck.shtml",
               	data:{
               		id:id,deliveryUserName:deliveryUserName
               	},
               	type:"POST",
              	contentType:"application/x-www-form-urlencoded;charset=utf-8",
               	dataType:"json",
               	async:false,
               	error:function(e){
               		
               	},
               	success:function(data){
               		result=data.result;
				}
			});
			if(result>0){
       			return false;
       		}else{
       			return true;
       		}
	    }, "帐号已存在！");
		$.validator.addMethod("adminUserNameCheck", function(value, element) {
			var id = "";
			var name = $(element).val();
			var result=0;
			$.ajax({
				url:"adminUser/adminUserNameCheck.shtml",
               	data:{
               		id:id,name:name
               	},
               	type:"POST",
              	contentType:"application/x-www-form-urlencoded;charset=utf-8",
               	dataType:"json",
               	async:false,
               	error:function(e){
               		
               	},
               	success:function(data){
               		result=data.result;
				}
			});
			if(result>0){
       			return false;
       		}else{
       			return true;
       		}
	    }, "帐号已存在！");
		validate();
	});
	function validate(){
		$("#saveForm").validate({
            rules:{
            	deliveryCompany:{
                    required:true
                },
            	identityNumber:{
                    required:true,
                    identityNumberCheck:true
                },
                deliveryContact:{
                    required:true
                },
                deliveryUserName:{
                    required:true,
                    deliveryUserNameCheck:true,
                    adminUserNameCheck:true
                },
                deliveryUserPass:{
                    required:true
                },
                deliveryTrueName:{
                    required:true
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">派件员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)"><%=deliveryUser!=null?"修改":"注册" %></a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="saveForm" name="saveForm" action="<%=basePath%>deliveryUser/deliveryUserSave.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=deliveryUser!=null?deliveryUser.getId():"" %>" />
	<input type="hidden" id="isDelete" name="isDelete" value="<%=deliveryUser!=null?deliveryUser.getIsDelete():"1" %>" />
	<input type="hidden" id="deliveryLogin" name="deliveryLogin" value="<%=deliveryUser!=null?deliveryUser.getDeliveryLogin():"0" %>" />
	<input type="hidden" id="deliveryMoney" name="deliveryMoney" value="<%=deliveryUser!=null?deliveryUser.getDeliveryMoney():"0" %>" />
	
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
   		<tr>
	    	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;公司：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryCompany" name="deliveryCompany" maxlength="100" value="<%=deliveryUser!=null?deliveryUser.getDeliveryCompany():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;真实姓名：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryTrueName" name="deliveryTrueName" maxlength="20" value="<%=deliveryUser!=null?deliveryUser.getDeliveryTrueName():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">性别：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="deliverySex" name="deliverySex" class="Aselect">
	      			<option value="1" <%=deliveryUser!=null&&deliveryUser.getDeliverySex()!=null&&deliveryUser.getDeliverySex()==1?"selected":"" %>>男</option>
	      			<option value="2" <%=deliveryUser!=null&&deliveryUser.getDeliverySex()!=null&&deliveryUser.getDeliverySex()==2?"selected":"" %>>女</option>
	      		</select>
	      	</td>
      	</tr>
      	<tr>
	      	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;身份证号码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="identityNumber" name="identityNumber" maxlength="20" value="<%=deliveryUser!=null?deliveryUser.getIdentityNumber():"" %>" /><em></em>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;联系方式：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryContact" name="deliveryContact" maxlength="50" value="<%=deliveryUser!=null?deliveryUser.getDeliveryContact():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">备用联系方式：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryPhone" name="deliveryPhone" maxlength="50" value="<%=deliveryUser!=null?deliveryUser.getDeliveryPhone():"" %>" />
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">家庭住址：</td>
	      	<td bgcolor="#FFFFFF">
	      		<textarea id="deliveryAddress" name="deliveryAddress" style="border:1px solid #ccc;" maxlength="100" cols="70" rows="5" ><%=deliveryUser!=null?deliveryUser.getDeliveryAddress():"" %></textarea>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;帐号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="deliveryUserName" name="deliveryUserName" maxlength="20" value="<%=deliveryUser!=null?deliveryUser.getDeliveryUserName():"" %>" <%=deliveryUser!=null?"readonly":"" %> /><em></em>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;密码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="password" class="Atext" id="deliveryUserPass" name="deliveryUserPass" maxlength="20" value="<%=deliveryUser!=null?deliveryUser.getDeliveryUserPass():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">备注：</td>
	      	<td bgcolor="#FFFFFF">
	      		<textarea id="deliveryRemarks" name="deliveryRemarks" maxlength="500" style="border:1px solid #ccc;" cols="70" rows="5" ><%=deliveryUser!=null?deliveryUser.getDeliveryRemarks():"" %></textarea>
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