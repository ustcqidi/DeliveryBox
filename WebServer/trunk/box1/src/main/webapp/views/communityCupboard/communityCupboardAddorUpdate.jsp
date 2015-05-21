<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.CommunityCupboard"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	CommunityCupboard communityCupboard = (CommunityCupboard) request.getAttribute("communityCupboard");
	Integer communityId = (Integer) request.getAttribute("communityId");
	Integer cupboardId = (Integer) request.getAttribute("cupboardId");
	String flag = (String) request.getAttribute("flag");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>社区柜子<%=communityCupboard!=null?"修改":"新增" %></title>
<script type="text/javascript">
	$(function(){
		$.validator.addMethod("cupboardNameCheck", function(value, element) {
			var id = $("#id").val();
			var cupboardName = $(element).val();
			var result=0;
			$.ajax({
				url:"communityCupboard/cupboardNameCheck.shtml",
               	data:{
               		id:id,communityId:<%=communityId%>,cupboardName:cupboardName
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
	    }, "柜子已存在！");
		validate();
	});
	function validate(){
		$("#saveForm").validate({
            //此处是各种表单元素的规则，对应表单的name属性
            rules:{
            	cupboardName:{
            		required:true,
            		cupboardNameCheck:true
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
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">宝柜管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">柜子信息<%=communityCupboard!=null?"修改":"新增" %></a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="saveForm" action="<%=basePath%>communityCupboard/communityCupboardSave.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=communityCupboard!=null?communityCupboard.getId():"" %>" />
	<input type="hidden" id="communityId.id" name="communityId.id" value="<%=communityId %>" />
	<input type="hidden" id="flag" name="flag" value="<%=flag %>" />
	<input type="hidden" id="isDelete" name="isDelete" value="<%=communityCupboard!=null?communityCupboard.getIsDelete():"1" %>" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
	    <tr>
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;柜子名称：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="cupboardName" name="cupboardName" maxlength="100" value="<%=communityCupboard!=null?communityCupboard.getCupboardName():"" %>" /><em></em>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">责任人：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="cupboardType" name="cupboardType" maxlength="20" value="<%=communityCupboard!=null?communityCupboard.getCupboardType():"" %>" />
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">备注：</td>
	      	<td bgcolor="#FFFFFF">
	      		<textarea id="cupboardBlank" name="cupboardBlank" maxlength="500" style="border:1px solid #ccc;" cols="70" rows="5" ><%=communityCupboard!=null?communityCupboard.getCupboardBlank():"" %></textarea>
	      	</td>
      	</tr>
      	<tr>
      		<td align="center" bgcolor="#FFFFFF" colspan="2">
	      		<input type="submit" id="subBtn" name="subBtn" class="CBtext" value="保存" style="width: 80px;" />&nbsp;&nbsp;
	      		<input type="button" class="CBtext" value="返回" style="width: 80px;" onclick="location.href='<%=basePath %>community/communityManage.shtml?objId=<%="2".equals(flag)?cupboardId:communityId %>&flag=<%=flag %>'" />
	      	</td>
      	</tr>
	</table>
	</form>
	</div>
</body>
</html>