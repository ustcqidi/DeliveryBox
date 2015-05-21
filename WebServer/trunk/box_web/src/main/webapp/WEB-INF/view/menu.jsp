<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>首页</title>
		
		<jsp:include page="common/base.jsp"/>
		<style>
		a{
			font-family: 微软雅黑;
			font-size: 14px;
		}
		.ui-accordion .ui-accordion-header a
		{
			font-size: 14px;
		}
		.ui-widget-content a {
		color: #0088CC;
		text-decoration: none;
		cursor: pointer;
		}
		
		</style>
	</head>

	<body>
        <div style="padding-left: 10px;">
		<div id="menu-collapse" class="ui-accordion ui-widget ui-helper-reset">
			<div class="">
				<h3><a
						href="#">快件管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
					<li class="submenu_li"><a url="/record/recordManage.do"><i
							class="icon-chevron-right"></i>快件信息</a></li>
			   
			</ul>
			</div>
			
			<div class="">
				<h3><a
						href="#">用户管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
					<li class="submenu_li"><a url="/delivery/list.json"><i
							class="icon-chevron-right"></i>用户管理</a></li>
			</ul>
			</div>
			
			<div class="">
				<h3><a
						href="#">宝柜管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
					<li class="submenu_li"><a url="/boxInfo/boxInfoManage.do"><i
							class="icon-chevron-right"></i>宝柜管理</a></li>
			   
			</ul>
			</div>
			
			<div class="">
				<h3><a
						href="#">财务管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
					<li class="submenu_li"><a url="/account/charge.do"><i
							class="icon-chevron-right"></i>快递员充值</a></li>
					<li class="submenu_li"><a url="/account/chargeRecord.do"><i
							class="icon-chevron-right"></i>充值记录</a></li>
					<li class="submenu_li"><a url="/account/payRecord.do"><i
							class="icon-chevron-right"></i>收入记录</a></li>
			   
			</ul>
			</div>
			
			
			
			<div class="">
				<h3><a
						href="#">管理员管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
					<li class="submenu_li"><a url="/auth/list.json"><i
							class="icon-chevron-right"></i>用户管理</a></li>
				<li class="submenu_li"><a url="/auth/updatePassWd.json"
					><i
						class="icon-chevron-right"></i>修改密码</a></li>
			   
			</ul>
			</div>
			
				<div class="">
				<h3><a
						href="#">系统管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
					<li class="submenu_li"><a url="/auth/list.json"><i
							class="icon-chevron-right"></i>参数管理</a></li>
				<li class="submenu_li"><a url="/auth/updatePassWd.json"
					><i
						class="icon-chevron-right"></i>参数设置</a></li>
			   
			</ul>
			</div>
			
		</div>
	</div>
	</body>
		
	<script type="text/javascript">
		$(document).ready(function() {
		
			
			$(".submenu_li").click(function(){
				$(".submenu_li").each(function(index,li){
					$(li).removeClass("active");
				});
				$(this).addClass("active");
			});
			
			$(".submenu_li a").click(function(){
				window.parent.document.getElementById("contentFrame").contentWindow.location.href = contextName + $(this).attr("url");
			});

		});
	</script>
</html>
