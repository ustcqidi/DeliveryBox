<%@ page contentType="text/html" pageEncoding="UTF-8" import="org.springframework.validation.ObjectError"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="../../../include/include.jsp"%>
        <link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css"/>
        <title>充值</title>
    </head>
    <body style="background:#FFF">
   		<div class="navi_area">
           	<span>
               	&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">派件员管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">充值</a>
            </span>
        </div>
        <div class="clear"></div>
		<div class="query_area">
		<form action="<%=basePath%>recharge/addRecharge.shtml" method="post" id="addRechargeForm" >
               <table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size: 12px;">
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%"><span style="color: red;">*</span>充值帐号：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <input name="deliveryUserName" id="deliveryUserName" value="" type="text" size="15" maxlength="30" class="b12" style="border:#999999 1px solid; height:21px; padding-top:3px" />
                                    <em></em>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%"><span style="color: red;">*</span>确认帐号：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <input name="deliveryUserNameCheck" id="deliveryUserNameCheck" value="" type="text" size="15" maxlength="30" class="b12" style="border:#999999 1px solid; height:21px; padding-top:3px" />
                                    <em></em>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%"><span style="color: red;">*</span>充值金额：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <input name="rechargeMoney" id="rechargeMoney" value="" type="text" size="10" maxlength="10" class="b12" style="border:#999999 1px solid; height:21px; padding-top:3px" /> 单位：元
                                    <em></em>
                                </label>
                            </td>
                        </tr>
                        <tr align="center">
                            <td colspan="2" bgcolor="#FFFFFF">
                                <input type="button" onclick="sub();" name="submitButton" id="submitButton" value="确认" class="CBtext" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
<script type="text/javascript">
$(function(){
    validateOrg();
    <%
    	if(null != request.getAttribute("rs") && request.getAttribute("rs").toString().equals("1")){
    		%>	
    		window.opener.goPage(1);
    		window.close();
    		<%
    	}
    %>
});
function validateOrg(){
    $("#addRechargeForm").validate({
        //此处是各种表单元素的规则，对应表单的name属性
        rules:{
        	deliveryUserName:{
                required:true
            },
            deliveryUserNameCheck:{
                required:true,
                equalTo:deliveryUserName
            },
            rechargeMoney:{
                required:true,
                positiveInteger:true
            }
        },
        //此处是各种表单元素规则对应的提示信息，常用的提示信息我已经写好了，你们也可以在这里覆盖
       
        //是否在用户输入的时候就验证，一般为false，默认为false
        onkeyup:false,
        //在这里设置提示信息的位置，根据你们表单的结构，自己编写
        errorPlacement: function(error,element){
            if(element.is(":radio"))
                error.appendTo(element.next().next("em"));
            else
                error.appendTo(element.nextAll("em").eq(0));
        },
        //验证通过的提示信息
        success: function(label){
            label.html("&nbsp;").addClass("checked");
        }
    });
}
function sub(){
	if($('#deliveryUserName').val() != '' && $('#deliveryUserNameCheck').val() != '' && $('#rechargeMoney').val() != ''){
		if($('#deliveryUserName').val()==$('#deliveryUserNameCheck').val()){
		 $.getJSON("<%=basePath%>recharge/selectName.shtml", { deliveryUserName: $("#deliveryUserName").val(),random:new Date().getTime()}, function(result) {
			 if(result.rs == "1"){
				 if(confirm("派件人帐号："+result.name+"，派件人姓名："+$('#deliveryUserName').val()+"，充值金额："+$('#rechargeMoney').val()+"￥。是否确认提交？")){
					$("#addRechargeForm").submit();
				}  
			 }else{
				 alert('帐号有误！');
			 }
         });
		}else{
			alert("确认账号和充值账号不一致！");
		}
	}
}
function deliveryUserList(){
	var deliveryUserId = "deliveryUserId="+$('#deliveryUserId').val();
	var deliveryUserName = "&deliveryUserName="+$('#deliveryUserName').val();
	var url = "<%=basePath%>views/recharge/deliveryUserList.jsp?";
	window.open (url+deliveryUserId+deliveryUserName, '选择派件人', 'height=600px, width=1000px,resizable=yes,scrollbars');
}
</script>
</html>