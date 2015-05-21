<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="Content-Language" content="zh-CN"/>
<!--meta http-equiv="X-UA-Compatible" content="IE=8" /-->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-store, no-cache, must-revalidate,post-check=0, pre-check=0">
<meta http-equiv="expires" content="0">
<!-- <link rel="shortcut icon" href="http://lingxi.voicecloud.cn/favicon.ico" /> -->

<!-- Le styles -->
<link href="${ctx }/resources/third-party/bootstrap/css/bootstrap.css" rel="stylesheet"  type="text/css"/>
<link href="${ctx }/resources/third-party/jquery-ui/jquery-ui-1.10.0.custom.css" rel="stylesheet"  type="text/css"/>
<link href="${ctx }/resources/third-party/jquery-ui/font-awesome.min.css" rel="stylesheet"  type="text/css"/>
<link href="${ctx }/resources/third-party/dataTables/jquery.dataTables.css" rel="stylesheet"  type="text/css"/>
<link href="${ctx }/resources/third-party/jquery-ui-multiselect/jquery.multiselect.css" rel="stylesheet"  type="text/css"/>
<link href="${ctx }/resources/third-party/My97DatePicker/skin/WdatePicker.css" rel="stylesheet"  type="text/css"/>
<link href="${ctx }/resources/third-party/DateIntervalPicker/dateIntervalPicker.css" rel="stylesheet"  type="text/css"/>

 <link type="text/css" href="${ctx }/resources/css/main.css" rel="stylesheet">
<link type="text/css" href="${ctx }/resources/css/stat.css" rel="stylesheet"> 
<link type="text/css" href="${ctx }/resources/css/css.css" rel="stylesheet">
<link type="text/css" href="${ctx }/resources/css/common.css" rel="stylesheet">

<!-- jquery-1.8.3.min.js 必须引用 -->
<script src="${ctx }/resources/third-party/jquery-ui-bs/js/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/bootstrap/js/bootstrap.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/jquery-ui/jquery-ui-1.10.0.custom.min.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/placeholder/jquery.placeholder.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/dataTables/jquery.dataTables.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/jquery-ui-multiselect/jquery.multiselect.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/highcharts/js/highcharts.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/jqPagination/jquery.jqpagination.js" type="text/javascript"></script>
<script src="${ctx }/resources/js/common/common.js" type="text/javascript"></script>
<%-- <script src="${ctx }/resources/js/common/opps-plugins.js" type="text/javascript"></script> --%>
<script src="${ctx }/resources/third-party/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/DateIntervalPicker/dateIntervalPicker.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/jquery-ui-bs/third-party/jQuery-UI-Date-Range-Picker/js/date.js" type="text/javascript"></script>
<script src="${ctx }/resources/third-party/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<style>

</style>
<script type="text/javascript">
    var ctx = '${ctx}';
    var name = '<shiro:principal property="name"/>';
    var trueName = '<shiro:principal property="trueName"/>';
	var Global = {
		webRoot : "<%=request.getContextPath()%>"
		};
	//Web站点上下文名称
	var contextName = '<c:url value="/"/>';
	$(document).ready(function(){
		
		$('input, textarea').placeholder();
		
		
		$("#menu-collapse").accordion({
		    header: "h3",
		    active: 0 ,
		    heightStyle: "content",
		    collapsible: true
		});
		
		//$(":input[placeholder]").placeholder();
	});
</script>
