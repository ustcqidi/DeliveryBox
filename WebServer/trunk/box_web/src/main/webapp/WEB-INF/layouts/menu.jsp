<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="http://lingxi.voicecloud.cn/favicon.ico" />
<title><decorator:title default="首页" /></title> 
    <decorator:head /> 
<style>
a {
	font-family: 微软雅黑;
	font-size: 14px;
}

.ui-accordion .ui-accordion-header a {
	font-size: 14px;
}
.ui-widget-content a {
color: #0088CC;
text-decoration: none;
}

body > .left{
 float:left; 
 width:13%;
 padding-left: 0.8%;
 padding-top: 20px;
}
body > .right{
 float:left; 
 width: 84%;
 margin-left: 15px;
 padding-top: 20px;
}

.ui-widget-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #aaaaaa ;
        opacity: .3;
        filter: Alpha(Opacity=30);
}
</style>
<%-- <jsp:include page="./base.jsp"/> --%>

</head>

<body>


    <div class="tap" style="height: 30px;"><jsp:include page="top.jsp" /></div> 
	<div class="left" >
		<div id="menu-collapse" class="ui-accordion ui-widget ui-helper-reset"
			role="tablist">
			
			<div class="">
				<h3><a
						href="#">业务管理</a>
				</h3>
				<ul class="nav nav-list bs-docs-sidenav">
					<li class="submenu_li"><a url="bizList.do"
						href="<c:url value='/biz/bizList.do'/>"><i
							class="icon-chevron-right"></i>客户信息管理</a></li>
					<li class="submenu_li"><a url="bizadd.do"
						href="<c:url value='/biz/bizAdd.do'/>"><i
							class="icon-chevron-right"></i>客户新增</a></li>
					<li class="submenu_li"><a url="managelist.json"
						href="<c:url value='/phone/managelist.json'/>"><i
							class="icon-chevron-right"></i>号码管理</a></li>
				</ul>
			</div>
			
		<div class="">
				<h3><a
						href="#">权限管理</a>
				</h3>
				<ul	class="nav nav-list bs-docs-sidenav" >
				<shiro:hasRole name="admin">  
					<li class="submenu_li"><a url="list.json"
						href="<c:url value='/auth/list.json'/>"><i
							class="icon-chevron-right"></i>用户管理</a></li>
			   </shiro:hasRole>
				<li class="submenu_li"><a url="updatePassWd.json"
					href="<c:url value='/auth/updatePassWd.json'/>"><i
						class="icon-chevron-right"></i>修改密码</a></li>
			</ul>
				
			</div>
			
		
		
		</div>
	</div>
	<decorator:body /> 
	<%-- <div ><jsp:include page="bottom.jsp" /></div>  --%>
</body>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
							$("#menu-collapse").accordion({
							    header: "h3",
							    heightStyle: "content",
							    collapsible: true
							});
						//$( "#menu-collapse" ).accordion( "option", "active", 1 );
						var request_uri = '<%=request.getRequestURI()%>';
						$("#menu-collapse h3").each(function(i,a){
							$(a).parent().find("ul li a").each(function(ii,aa){
								if(request_uri.indexOf($(aa).attr('href'))>=0) {
									$("#menu-collapse").accordion('option','active',i);
									$(aa).parent().addClass('active');
									return false;
								}
							});
						});
						//$("a[href$='/rule/rulelist']").parent().addClass('active');
							
						$(".submenu_li").click(function() {
								$(".submenu_li").each(function(index, li) {
									$(li).removeClass("active");
								});
								$(this).addClass("active");
							});


	});
</script>
</html>
