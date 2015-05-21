<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
<!--
$(function() {
	var href = "account-tab";
	$("#"+href).parent().parent().parent().addClass("in");
});
//-->
</script>

<div id="leftbar" class="span2 accordion">
	<div class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle" data-toggle="collapse"
				data-parent="#leftbar" href="#collapseOne"> 日志报表 </a>
		</div>
		<div id="collapseOne" class="accordion-body collapse">
			<ul>
				<li><a id="pageLog-tab" href="#">页面操作日志</a></li>
				<li><a id="pageLogReport-tab" href="#">页面操作报表</a></li>
			</ul>
		</div>
	</div>
	<div class="accordion-group">
		<div class="accordion-heading">
			<a class="accordion-toggle" data-toggle="collapse"
				data-parent="#leftbar" href="#collapseTwo"> 权限管理 </a>
		</div>
		<div id="collapseTwo" class="accordion-body collapse">
			<ul>
				<li><a id="account-tab" href="${ctx}/account/user/">帐号管理</a></li>
				<li><a id="role-tab" href="${ctx}/account/role/">角色管理</a></li>
			</ul>
		</div>
	</div>
</div>