<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="common/base.jsp"/>
	</head>

	
		<frameset id="topframeset" rows="40,10,*,30"  frameborder="0" border=0 framespacing="0" noresize="noresize">
			<frame  name="topFrame"   src="<c:url value='/top.do'/>"> 
			<frame scrolling="no" name="topSwitchFrame" src="<c:url value='/topBar.do'/>">  
			<frameset cols="200,10,*" frameborder="0" border=0 framespacing="2" noresize="noresize" id="attachucp">
				<frame id="menuFrame" name="menuFrame" src="<c:url value='/menu.do'/>">
				<frame id="switch" scrolling="no" name="switchImage" src="<c:url value='/switchImg.do'/>"> 
				<frame id="contentFrame" name="contentFrame" src="<c:url value='/dashboard.do'/>"> 
			</frameset> 
			<frame name="bottomFrame" src="<c:url value='/bottom.do'/>"> 
		</frameset>
		
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</html>
