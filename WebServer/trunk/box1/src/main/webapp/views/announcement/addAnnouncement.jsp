<%@ include file="../../../include/include.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="org.springframework.validation.ObjectError"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css"/>
        <title>新增公告</title>
    </head>
    <body style="background:#FFF">
    	<div class="navi_area">
        	<span>
            	&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">公告管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">注册</a>
            </span>
        </div>
        <div class="clear"></div>
		<div class="query_area">
		<form action="announcement/addAnnouncement.shtml" method="post" id="addAnnouncementForm" >
               <table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size: 12px;">
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%"><span style="color: red;">*</span> 公告标题：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <input name="announcementTitle" id="announcementTitle" value="" type="text" size="30" maxlength="30" class="b12" style="border:#999999 1px solid; height:21px; padding-top:3px"><em></em>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%"><span style="color: red;">*</span> 公告内容：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <textarea id="announcementContent" name="announcementContent"></textarea><em></em>
                                </label>
                            </td>
                        </tr>
                         <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%">备注：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <textarea id="remarks" name="remarks" cols="60" rows="5"></textarea><em></em>
                                </label>
                            </td>
                        </tr>
                        <tr align="center">
                            <td colspan="2" bgcolor="#FFFFFF">
                                <input type="button" onclick="sub();" name="submitButton" id="submitButton" value="保存" class="CBtext" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
<script type="text/javascript">
$(function(){
    var ckeditorConfig = {
        language : 'zh-cn',
        skin : 'kama',
        fontSize_defaultLabel : '14',
        font_defaultLabel : '宋体',
        font_names : '宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;Arial/Arial;Comic Sans MS/Comic Sans MS',
        resize_enabled : false,
        width : 500,
        height : 200,
        toolbar_Full : [
            ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
            ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
            ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
            '/',
            ['Styles','Format','Font','FontSize'],
            ['TextColor','BGColor']
        ],
        removePlugins:'elementspath'   //去除底部状态栏
    };
    $("#announcementContent").ckeditor(ckeditorConfig);
    validateOrg();
});
function validateOrg(){
    $("#addAnnouncementForm").validate({
        //此处是各种表单元素的规则，对应表单的name属性
        rules:{
        	announcementTitle:{
                required:true
            },
            announcementContent:{
                required:true
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
	 var ckeditorinstance= $('#announcementContent').ckeditorGet();
	 ckeditorinstance.updateElement();
	 $("#addAnnouncementForm").submit();
}

</script>
</html>