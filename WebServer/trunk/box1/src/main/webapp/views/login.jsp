<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
%>
<html>
    <head>
        <base href="<%=basePath%>"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登陆界面</title>
        <style type="text/css">          
            body {
                background-color: #078EEA;
                background-image: url(resources/images/loginbg.jpg);
            }
            .b12{
                border:#99CCCC 1px solid;
                height:21px;
                padding-top:3px;

            }
        </style>
        <link href="resources/css/login.css" rel="stylesheet"  type="text/css"/>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.4.3.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/validate/jquery.validate.js"/>"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/validate/jquery.validate.cn.js"/>"></script>

        <script type="text/javascript">
            if (top.location != self.location){top.location=self.location; }
            $(document).ready(function(){             
                $("#adminUser").validate( {
                    errorClass : "error",                  
                    submitHandler : function(form) {
                        if($("#checkCode").val().length>=1){
                            form.submit();
                        }else{
                            alert("请输入验证码！");
                        }
                    },
                    rules : {
                    	adminUserName : {
                            required : true,
                            minlength: 1,
                            maxlength : 15
                        },
                        adminUserPass : {
                            required : true,
                            minlength: 1,
                            maxlength:20
                        }
                    }

                });

            });

            function nav(){
                location.href="manage.shtml";
            }
        </script>

        <style type="text/css">
            error{
                border: 1px solid black;
                color:red;
            }
            input { border: 1px solid black; }
            input.error { border: 1px solid red; }
            label.error,span.error {
                background: url('<c:url value="/resources/images/unchecked.gif" />') no-repeat;
                padding-left: 16px;
                margin-left: .3em;
                font-size:smaller;
            }
            label.valid {
                background: url('<c:url value="/resources/images/checked.gif" />') no-repeat;
                width: 16px;
                height: 16px;
            }
        </style>

        <script type="text/javascript">
            $(document).ready(function(){
                $('#checkCodeImage').click(function () {//生成验证码
                    $(this).hide().attr('src', 'checkCode.shtml?' + Math.floor(Math.random()*100) ).fadeIn(); })
            });        
        </script>
    </head>
    <body>
        <form:form modelAttribute="adminUser" action="checkLogin.shtml" method="post">

            <table width="100%" border="0" cellspacing="0" cellpadding="0" style="background:url(resources/images/loginTable.jpg); background-position:center top; background-repeat:no-repeat">
                <tr>
                    <td height="600" align="center" style="padding-left:100px; padding-bottom:40px"><table width="570" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td height="100"><img src="resources/images/loginLogo.jpg" width="475" height="48"></td>
                            </tr>
                        </table>
                        <table width="365" height="114" border="0" cellpadding="3" cellspacing="0">
                            
                            <tr>                              
                                <td width="72" align="right">   <form:label for="adminUserName" path="adminUserName" >用户名：</form:label></td>
                                <td width="232"><label>
                                        <form:input path="adminUserName" class="b12" value=""/>
                                    </label></td>
                            </tr>
                            <tr>
                                <td width="72" align="right">   <form:label for="adminUserPass" path="adminUserPass" cssErrorClass="error">密&nbsp;&nbsp;&nbsp;&nbsp;码：</form:label></td>
                                <td>
                                    <form:password path="adminUserPass" class="b12" value=""></form:password>

                                </td>
                            </tr>

                            <tr>
                                <td width="72" align="right">验证码：</td>
                                <td><input type="text"  id="checkCode" name="checkCode" size="10" class="b12" style="border:#99CCCC 1px solid; height:21px; padding-top:3px">
                                    &nbsp;   
                                    <img  style=" cursor: pointer;padding: 0px; margin: -5px;" alt="checkcode" src="checkCode.shtml" width="58" height="20" id="checkCodeImage" />
                                
                                </td>
                            </tr>

                            <tr>
                                <td>&nbsp;</td>
                                <td><table width="80%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td><input  type="submit" value=""   style=" cursor: pointer; background-image: url(resources/images/loginButton.jpg); margin: 0px; padding: 0px; width: 66px; height: 26px; background-repeat: no-repeat; border-top: none;border-right: none;	border-bottom: none;border-left: none;" /></td>
                                            <td><input value="" type="reset"   style=" cursor: pointer; background-image: url(resources/images/cancelButton.jpg); margin: 0px; padding: 0px; width: 66px; height: 26px; background-repeat: no-repeat; border-top: none;border-right: none;	border-bottom: none;border-left: none;" /></td>
                                        </tr>
                                    </table></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td style="color:#F00;">
                                    <span id="errorMsg">
                                        <c:out value="${errorMsg}"></c:out>
                                    </span>
                                </td>
                            </tr>
                        </table></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>
