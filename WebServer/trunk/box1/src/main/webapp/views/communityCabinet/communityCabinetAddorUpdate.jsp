<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../include/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.xsz.edu.jpkc.entity.CommunityCabinet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	CommunityCabinet communityCabinet = (CommunityCabinet) request.getAttribute("communityCabinet");
	Integer communityId = (Integer) request.getAttribute("communityId");
	Integer cupboardId = (Integer) request.getAttribute("cupboardId");
	String flag = (String) request.getAttribute("flag");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css" />
<title>社区格子<%=communityCabinet!=null?"修改":"新增" %></title>
<script type="text/javascript">
	$(function(){
		$.validator.addMethod("cabinetNameCheck", function(value, element) {
			var id = $("#id").val();
			var cabinetName = $(element).val();
			var result=0;
			$.ajax({
				url:"communityCabinet/cabinetNameCheck.shtml",
               	data:{
               		id:id,cupboardId:<%=cupboardId%>,cabinetName:cabinetName
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
	    }, "格子已存在！");
		jQuery.validator.addMethod("numZero", function(value, element) {
	        if($(element).val() == "" || $(element).val() == null){
	            $(element).val("0");
	            return false;
	        }else{
	            return true;
	        }
	    }, "必填项！");
		jQuery.validator.addMethod("cabinetName_num2Check", function(value, element) {
			var startNum=$("#cabinetName_num1").val();
			var endNum=$(element).val();
	        if(parseInt(endNum) <= parseInt(startNum)){
	            return false;
	        }else{
	            return true;
	        }
	    }, "后一个数必须大于前一个数！");
		validate();
		checkSc(1);
	});
	function validate(){
		$("#saveForm").validate({
            //此处是各种表单元素的规则，对应表单的name属性
            rules:{
            	cabinetName:{
    				required:true,
                    cabinetNameCheck:true
    			},
    			cabinetName_num1:{
    				required:true,
    				digits:true
    			},
    			cabinetName_num2:{
    				required:true,
    				digits:true,
                    cabinetName_num2Check:true
    			},
                cabinetMoney:{
                	numZero:true,
                	number:true
                },
                maxDate:{
                	numZero:true,
                	digits:true
                },
                cabinetPrice:{
                	numZero:true,
                	number:true
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
	function checkSc(obj){
		if(obj==1){
			$("#scfs_dg").show();
			$("#scfs_pl").hide();
			$("#cabinetName").val('');
			$("#cabinetName_num1").val(1);
			$("#cabinetName_num2").val(10);
			$("#scfsCheck").val(1);
		}else{
			$("#scfs_dg").hide();
			$("#scfs_pl").show();
			$("#cabinetName").val(new Date());
			$("#cabinetName_num1").val('');
			$("#cabinetName_num2").val('');
			$("#scfsCheck").val(2);
		}
	}
</script>
</head>
<body style="background:#FFF">
	<div class="navi_area">
	    <span>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>： &nbsp;<a href="javascript:void(0)">宝柜管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">格子信息<%=communityCabinet!=null?"修改":"新增" %></a>
		</span>
	</div>
	<div class="clear"></div>
	<div class="query_area">
	<form id="saveForm" action="<%=basePath%>communityCabinet/communityCabinetSave.shtml" method="post">
	<input type="hidden" id="id" name="id" value="<%=communityCabinet!=null?communityCabinet.getId():"" %>" />
	<input type="hidden" id="communityId.id" name="communityId.id" value="<%=communityId %>" />
	<input type="hidden" id="cupboardId.id" name="cupboardId.id" value="<%=cupboardId %>" />
	<input type="hidden" id="flag" name="flag" value="<%=flag %>" />
	<input type="hidden" id="isDelete" name="isDelete" value="<%=communityCabinet!=null?communityCabinet.getIsDelete():"1" %>" />
   	<table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size:12px;">
   		<%if(communityCabinet==null) {%>
   		<input type="hidden" id="scfsCheck" name="scfsCheck" value="1" />
   		<tr>
	      	<td width="25%" align="right" bgcolor="#FFFFFF">格子新增方式：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="radio" id="sc_dg" name="scfs" value="1" checked="checked" onclick="checkSc(1)" /><label for="sc_dg">单个</label>
	      		<input type="radio" id="sc_pl" name="scfs" value="2" onclick="checkSc(2)" /><label for="sc_pl">批量</label>
	      	</td>
	    </tr>
	    <%} %>
	    <tr id="scfs_dg">
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;格子名称：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="cabinetName" name="cabinetName" maxlength="100" value="<%=communityCabinet!=null?communityCabinet.getCabinetName():"" %>" /><em></em>
	      	</td>
	    </tr>
	    <tr id="scfs_pl">
	      	<td width="25%" align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;格子名称：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" id="cabinetName_num1" name="cabinetName_num1" maxlength="100" value="" /><em></em>
	      		&nbsp;-&nbsp;
	      		<input type="text" id="cabinetName_num2" name="cabinetName_num2" maxlength="100" value="" /><em></em>
	      	</td>
	    </tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">格子类型：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="cabinetType" name="cabinetType" class="Aselect">
	      			<option value="1" <%=communityCabinet!=null&&communityCabinet.getCabinetType()==1?"selected":"" %>>大</option>
	      			<option value="2" <%=communityCabinet!=null&&communityCabinet.getCabinetType()==2?"selected":"" %>>中</option>
	      			<option value="3" <%=communityCabinet!=null&&communityCabinet.getCabinetType()==3?"selected":"" %>>小</option>
	      		</select>
	      	</td>
      	</tr>
	    <tr>
	    	<td align="right" bgcolor="#FFFFFF">格子是否为空：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="cabinetIsBlank" name="cabinetIsBlank" class="Aselect">
	      			<option value="0" <%=communityCabinet!=null&&communityCabinet.getCabinetIsBlank()==0?"selected":"" %>>空</option>
	      			<option value="1" <%=communityCabinet!=null&&communityCabinet.getCabinetIsBlank()==1?"selected":"" %>>不为空</option>
	      		</select>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;格子价值：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="cabinetMoney" name="cabinetMoney" value="<%=communityCabinet!=null?communityCabinet.getCabinetMoney():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;最大超期天数：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="maxDate" name="maxDate" value="<%=communityCabinet!=null?communityCabinet.getMaxDate():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF"><span style="color: red;">*</span>&nbsp;格子单价：</td>
	      	<td bgcolor="#FFFFFF">
	      		<input type="text" class="Atext" id="cabinetPrice" name="cabinetPrice" value="<%=communityCabinet!=null?communityCabinet.getCabinetPrice():"" %>" /><em></em>
	      	</td>
      	</tr>
      	<tr>
	    	<td align="right" bgcolor="#FFFFFF">格子锁定状态：</td>
	      	<td bgcolor="#FFFFFF">
	      		<select id="cabinetIsLock" name="cabinetIsLock" class="Aselect">
	      			<option value="0" <%=communityCabinet!=null&&communityCabinet.getCabinetIsLock()==0?"selected":"" %>>锁定</option>
	      			<option value="1" <%=communityCabinet!=null&&communityCabinet.getCabinetIsLock()==1?"selected":"" %>>正常</option>
	      		</select>
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