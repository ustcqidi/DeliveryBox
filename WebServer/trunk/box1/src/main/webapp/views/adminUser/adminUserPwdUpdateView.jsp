<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.AdminUser"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	AdminUser adminUser = (AdminUser) request.getAttribute("adminUser");
	Integer result = (Integer)request.getAttribute("result");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>修改密码</title>
<script type="text/javascript">
	var validator;
	$(function(){
		if(<%=result%>==1){
			window.close();
		}
		validateSubmit();
		$.validator.addMethod("adminUserPassOldCheck", function(value, element) {
			var id = $("#id").val();
			var pwdOld = $(element).val();
			var pwd;
			$.ajax({
				url:"adminUser/adminUserPassCheck.shtml",
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
               		pwd=data.result;
				}
			});
			if(pwdOld!=pwd){
       			return false;
       		}else{
       			return true;
       		}
	    }, "原密码错误！");
	});
	function validateSubmit(){
		validator=$("#saveForm").validate({
            rules:{
            	adminUserPassOld:{
                    required:true,
                    adminUserPassOldCheck:true
                },
                adminUserPassNew:{
                    required:true
                },
                adminUserPass:{
                    required:true,
                    equalTo: "#adminUserPassNew"
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">修改密码</a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="saveForm" name="saveForm" action="<%=basePath%>adminUser/adminUserPwdUpdate.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=adminUser!=null?adminUser.getId():"" %>" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	    	<td width="45%" align="right" bgcolor="#FFFFFF">帐号：</td>
	      	<td bgcolor="#FFFFFF">
	      		<%=adminUser!=null?adminUser.getAdminUserName():"" %>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;原密码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="password" class="Atext" id="adminUserPassOld" name="adminUserPassOld" maxlength="20" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;新密码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="password" class="Atext" id="adminUserPassNew" name="adminUserPassNew" maxlength="20" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;确认密码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="password" class="Atext" id="adminUserPass" name="adminUserPass" maxlength="20" /><em></em>
	      	</td>
      	</tr>
      	<tr>
      		<td align="center" bgcolor="#FFFFFF" colspan="2">
	      		<input type="submit" class="CBtext" id="saveBtn" value="提交" style="width: 80px;" />&nbsp;&nbsp;
	      		<input type="button" class="CBtext" value="关闭" style="width: 80px;" onclick="window.close()" />
	      	</td>
      	</tr>
	</table>
	</form>
	</div>
</body>
</html>