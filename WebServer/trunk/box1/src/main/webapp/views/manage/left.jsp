<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String sys_type= request.getParameter("sys_type");
   
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet"  type="text/css"/>
<script type="text/javascript" src="<%=basePath%>resources/js/jquery-1.4.3.js"></script>
<body>
<table width="214" border="0" cellspacing="0" cellpadding="0" align="center" height="100%" style="">
	<tr>
       	<td width="5px"><input name="sys_input" id="sys_input" value="" type="hidden" /></td>
        <td width="200" valign="top">
			<div class="left">
				<div style="display: none;" class="left_top"  id="showname1">快件管理</div>
              	<div style="display: none;" class="left_center" id="showname11">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('recordInfo/recordInfoManage.shtml')">查询</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname2">派件员管理</div>
              	<div style="display: none;" class="left_center" id="showname22">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('deliveryUser/deliveryUserManage.shtml')">查询</a></span>
			            <span><a href="javascript:void(0)" onclick="ulrjump('deliveryUser/deliveryUserAddorUpdate.shtml?id=0')">注册</a></span>
			            <span><a href="javascript:void(0)" onclick="ulrjump('views/recharge/addRecharge.jsp')">充值</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname3">宝柜管理</div>
              	<div style="display: none;" class="left_center" id="showname33">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('community/communityManage.shtml')">查询</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname4">财务管理</div>
              	<div style="display: none;" class="left_center" id="showname44">
	              	<div id="showmenu">
	              		<span><a href="javascript:void(0)" onclick="ulrjump('views/recharge/rechargeManage.jsp')">充值记录</a></span>
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/finance/financeManage.jsp')">收入记录</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname5">管理员管理</div>
              	<div style="display: none;" class="left_center" id="showname55">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('adminUser/adminUserAddorUpdate.shtml?id=0')">注册</a></span>
		              	<span><a href="javascript:void(0)" onclick="ulrjump('adminUser/adminUserManage.shtml')">查询</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname6">系统管理</div>
              	<div style="display: none;" class="left_center" id="showname66">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('parameters/parametersAddorUpdate.shtml?id=0')">参数设置</a></span>
		              	<span><a href="javascript:void(0)" onclick="ulrjump('parameters/parametersManage.shtml')">查询</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname7">公告管理</div>
              	<div style="display: none;" class="left_center" id="showname77">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/announcement/announcementManage.jsp')">查询</a></span>
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/announcement/addAnnouncement.jsp')">注册</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname8">财务管理</div>
              	<div style="display: none;" class="left_center" id="showname88">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/recharge/rechargeManage.jsp')">充值记录</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname9">快件管理</div>
              	<div style="display: none;" class="left_center" id="showname99">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('recordInfo/recordInfoManage.shtml')">查询</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname10">派件员管理</div>
              	<div style="display: none;" class="left_center" id="showname1010">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('deliveryUser/deliveryUserManage.shtml')">查询</a></span>
			            <span><a href="javascript:void(0)" onclick="ulrjump('deliveryUser/deliveryUserAddorUpdate.shtml?id=0')">新增</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname14">宝柜管理</div>
              	<div style="display: none;" class="left_center" id="showname1414">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('community/communityManage.shtml')">查询</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname12">财务管理</div>
              	<div style="display: none;" class="left_center" id="showname1212">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/finance/financeManage.jsp')">收入记录</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname13">公告管理</div>
              	<div style="display: none;" class="left_center" id="showname1313">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/announcement/announcementManage.jsp')">查询</a></span>
		              	<span><a href="javascript:void(0)" onclick="ulrjump('views/announcement/addAnnouncement.jsp')">注册</a></span>
	              	</div>
           	  		<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
              	<div style="display: none;" class="left_top"  id="showname15">派件员管理</div>
              	<div style="display: none;" class="left_center" id="showname1515">
	              	<div id="showmenu">
		              	<span><a href="javascript:void(0)" onclick="ulrjump('deliveryUser/deliveryUserMoneyManage.shtml')">余额管理</a></span>
	              	</div>
	              	<div class="left_bottom"><img src="<%=basePath%>views/manage/images/left_2.jpg" width="186" height="4" /></div>
              	</div>
            </div>
   		</td>
	</tr>
</table>
<script type="text/javascript">
$(document).ready(function(){
	showDiv(); 
});
function showDiv(){
	var sys_type = "<%=sys_type%>";
	if(sys_type == "1" ){
		$("#showname1").show();
		$("#showname11").show();
	}else if(sys_type == "2" ){
		$("#showname2").show();
		$("#showname22").show();
		ulrjump('deliveryUser/deliveryUserManage.shtml')
	}else if(sys_type == "3" ){
		$("#showname3").show();
		$("#showname33").show();
		ulrjump('community/communityManage.shtml')
	}else if(sys_type == "4" ){
		$("#showname4").show();
		$("#showname44").show();
		ulrjump('views/recharge/rechargeManage.jsp')
	}else if(sys_type == "5" ){
		$("#showname5").show();
		$("#showname55").show();
		ulrjump('adminUser/adminUserAddorUpdate.shtml?id=0')
	}else if(sys_type == "6" ){
		$("#showname6").show();
		$("#showname66").show();
		ulrjump('parameters/parametersAddorUpdate.shtml?id=0')
	}else if(sys_type == "7" ){
		$("#showname7").show();
		$("#showname77").show();
		ulrjump('views/announcement/announcementManage.jsp')
	}else if(sys_type == "8" ){
		$("#showname8").show();
		$("#showname88").show();
		ulrjump('views/recharge/rechargeManage.jsp')
	}else if(sys_type == "9" ){
		$("#showname9").show();
		$("#showname99").show();
		ulrjump('recordInfo/recordInfoManage.shtml')
	}else if(sys_type == "10" ){
		$("#showname10").show();
		$("#showname1010").show();
		ulrjump('deliveryUser/deliveryUserManage.shtml')
	}else if(sys_type == "14" ){
		$("#showname14").show();
		$("#showname1414").show();
		ulrjump('community/communityManage.shtml')
	}else if(sys_type == "12" ){
		$("#showname12").show();
		$("#showname1212").show();
		ulrjump('views/finance/financeManage.jsp')
	}else if(sys_type == "13" ){
		$("#showname13").show();
		$("#showname1313").show();
		ulrjump('views/announcement/announcementManage.jsp')
	}else if(sys_type == "15" ){
		$("#showname15").show();
		$("#showname1515").show();
		ulrjump('deliveryUser/deliveryUserMoneyManage.shtml')
	}
}
function ulrjump(url){
	var url = "<%=basePath%>"+url;/*删除basepath*/
	parent.jump(url);
}
</script>
</body>
</html>