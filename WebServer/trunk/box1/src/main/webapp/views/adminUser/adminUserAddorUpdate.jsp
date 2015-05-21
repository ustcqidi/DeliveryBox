<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.AdminUser"%>
<%@ page import="com.xsz.edu.jpkc.entity.Community"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	AdminUser adminUser = (AdminUser) request.getAttribute("adminUser");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>管理人员<%=adminUser!=null?"修改":"新增" %></title>
<script type="text/javascript">
	var validator;
	var isAdminUserLevel = false;
	$(function(){
		$.validator.addMethod("adminUserNameCheck", function(value, element) {
			var id = $("#id").val();
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
		$.validator.addMethod("deliveryUserNameCheck", function(value, element) {
			var id = "";
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
		validateSubmit();
	});
	function validateSubmit(){
		validator=$("#saveForm").validate({
            rules:{
            	adminTrueName:{
                    required:true
                },
                adminUserName:{
                    required:true,
                    adminUserNameCheck:true,
                    deliveryUserNameCheck:true
                },
                adminUserPass:{
                    required:true
                },
                adminUserPhone:{
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">管理员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)"><%=adminUser!=null?"修改":"注册" %></a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">	
	<form id="saveForm" name="saveForm" action="<%=basePath%>adminUser/adminUserSave.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=adminUser!=null?adminUser.getId():"" %>" />
	<input type="hidden" id="isDelete" name="isDelete" value="<%=adminUser!=null?adminUser.getIsDelete():"1" %>" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;姓名：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="adminTrueName" name="adminTrueName" maxlength="20" value="<%=adminUser!=null?adminUser.getAdminTrueName():"" %>" /><em></em>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">性别：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="adminUserSex" name="adminUserSex" class="Aselect">
	      			<option value="1" <%=adminUser!=null&&adminUser.getAdminUserSex()!=null&&adminUser.getAdminUserSex()==1?"selected":"" %>>男</option>
	      			<option value="2" <%=adminUser!=null&&adminUser.getAdminUserSex()!=null&&adminUser.getAdminUserSex()==2?"selected":"" %>>女</option>
	      		</select>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;帐号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="adminUserName" name="adminUserName" maxlength="20" value="<%=adminUser!=null?adminUser.getAdminUserName():"" %>" <%=adminUser!=null?"readonly":"" %> /><em></em>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;密码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="password" class="Atext" id="adminUserPass" name="adminUserPass" maxlength="20" value="<%=adminUser!=null?adminUser.getAdminUserPass():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;手机号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="adminUserPhone" name="adminUserPhone" maxlength="50" value="<%=adminUser!=null?adminUser.getAdminUserPhone():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">级别：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%if(adminUser!=null){%>
	      			<input type="hidden" id="adminUserLevel" name="adminUserLevel" value="<%=adminUser.getAdminUserLevel() %>" />
	      			<%=adminUser.getAdminUserLevel()==1?"超级管理员":adminUser.getAdminUserLevel()==2?"充值人员":adminUser.getAdminUserLevel()==3?"管理员":"" %>
	      		<%}else{ %>
		      		<select id="adminUserLevel" name="adminUserLevel" class="Aselect">
		      			<option value="1">超级管理员</option>
		      			<option value="2">充值人员</option>
		      			<option value="3">管理员</option>
		      		</select>
	      		<%} %>
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