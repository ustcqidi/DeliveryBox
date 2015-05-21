<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.Community"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Community community = (Community) request.getAttribute("community");
	Integer communityId = (Integer) request.getAttribute("communityId");
	String flag = (String) request.getAttribute("flag");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>社区<%=community!=null?"修改":"新增" %></title>
<script type="text/javascript">
	$(function(){
		$.validator.addMethod("communityCodeCheck", function(value, element) {
			var id = $("#id").val();
			var communityCode = $(element).val();
			var result=0;
			$.ajax({
				url:"community/communityCodeCheck.shtml",
               	data:{
               		id:id,communityCode:communityCode
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
	    }, "社区编码已存在！");
		validate();
	});
	function validate(){
		$("#saveForm").validate({
            //此处是各种表单元素的规则，对应表单的name属性
            rules:{
            	communityName:{
                    required:true
                },
                communityCode:{
                    required:true,
                    communityCodeCheck:true
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>： &nbsp;<a href="javascript:void(0)">宝柜管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">社区信息<%=community!=null?"修改":"新增" %></a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="saveForm" action="<%=basePath%>community/communitySave.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=community!=null?community.getId():"" %>" />
	<input type="hidden" id="flag" name="flag" value="<%=flag %>" />
	<input type="hidden" id="isDelete" name="isDelete" value="<%=community!=null?community.getIsDelete():"1" %>" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;社区名称：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityName" name="communityName" maxlength="50" value="<%=community!=null?community.getCommunityName():"" %>" /><em></em>
	      	</td>
	    </tr>
	    <tr>
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;社区编码：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityCode" name="communityCode" maxlength="50" value="<%=community!=null?community.getCommunityCode():"" %>" /><em></em>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">社区地址：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityAddress" name="communityAddress" maxlength="50" value="<%=community!=null?community.getCommunityAddress():"" %>" />
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">社区联系人：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityUser" name="communityUser" maxlength="10" value="<%=community!=null?community.getCommunityUser():"" %>" />
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">社区联系方式：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityContact" name="communityContact" maxlength="50" value="<%=community!=null?community.getCommunityContact():"" %>" />
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">社区物业公司：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityPropertyCompany" name="communityPropertyCompany" maxlength="50" value="<%=community!=null?community.getCommunityPropertyCompany():"" %>" />
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">社区物业人员：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="communityPropertyStaff" name="communityPropertyStaff" maxlength="50" value="<%=community!=null?community.getCommunityPropertyStaff():"" %>" />
	      	</td>
      	</tr>
      	<tr>
      		<td align="center" bgcolor="#FFFFFF" colspan="2">
	      		<input type="submit" class="CBtext" value="保存" style="width: 80px;" />&nbsp;&nbsp;
	      		<input type="button" class="CBtext" value="返回" style="width: 80px;" onclick="location.href='<%=basePath %>community/communityManage.shtml?objId=<%=communityId %>&flag=<%=flag %>'" />
	      	</td>
      	</tr>
	</table>
	</form>
	</div>
</body>
</html>