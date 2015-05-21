<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		 
	</head>

	<body>
        <!-- Navbar
        ================================================== -->
        <div class="navbar navbar-inverse navbar-fixed-top" style="background-color:#57B1EF;">
            <div class="navbar-inner" style="background-color:#57B1EF;">
                <div class="container-fluid" style="background-color:#57B1EF;">
                    <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="brand" id="main" href="#" style=" color: white;">东骏汇泰CRM</a>
                    <div class="nav-collapse collapse">
                    <shiro:user>
                        <ul class="nav pull-right">
			                <li style=" color: white;"><a href="#" style=" color: white;"><shiro:principal property="name"/>，您好！</a></li>
			                <li style=" color: white;"><a href="<c:url value='/logout.do'/>" style=" color: white;"><i class="icon-trash"></i> 注销</a></li>
			                <!-- <li><a href="#"><i class="icon-pencil"></i> 修改密码</a></li> -->
                        </ul>
                    </shiro:user>
                    </div>
                </div>
            </div>
        </div>
        <%--  <decorator:body />  --%>
	</body>
		
</html>
