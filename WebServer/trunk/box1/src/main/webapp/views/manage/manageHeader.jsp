<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.xsz.edu.jpkc.entity.AdminUser" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String sys_type=request.getSession().getAttribute("sys_type").toString();
    AdminUser adminUser = (AdminUser)request.getSession().getAttribute("user");
%>
<html>
    <head>
        <title>JSP Page</title>
        <base href="<%=basePath%>"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=basePath%>resources/js/jquery-1.4.3.js"></script>
		<link href="<%=basePath%>resources/css/style1.css" rel="stylesheet"  type="text/css"/>
        <script type="text/javascript">
            $(document).ready(function(){
                disptime();
            });
             function disptime(){
            	var time = new Date( ); //获得当前时间     
            	var year = time.getFullYear();//获得年、月、日     
            	var month = time.getMonth( )+1;     
            	var day = time.getDate();              
            	var hour = time.getHours( ); //获得小时、分钟、秒      
            	var minute = time.getMinutes( );
            	var second = time.getSeconds( );      
            	var apm="AM"; //默认显示上午: AM           
            	if (hour>12) //按12小时制显示     
            	{       
            		hour=hour-12;       
            		apm="PM" ;      
            	}      
            	if (minute < 10) //如果分钟只有1位，补0显示       
            		minute="0"+minute;      
            	if (second < 10) //如果秒数只有1位，补0显示       
            		second="0"+second;      /*设置文本框的内容为当前时间*/      
            		var dateShow =year+"年"+month+"月"+day+"日   "+hour+":"+minute+":"+second+" "+apm;      /*设置定时器每隔1秒（1000毫秒），调用函数disptime()执行，刷新时钟显示*/      
            		$("#myclock").html(dateShow);
            		var myTime = setTimeout("disptime()",1000);          
            	}
             function pwdUpdate(){
           		var url = "<%=basePath%>adminUser/adminUserPwdUpdateView.shtml";
        		window.open (url, '修改密码', 'height=400px, width=800px,resizable=yes,scrollbars');
             }
        </script>
    </head>
    
 
    <body>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style=" background:url(<%=basePath%>resources/images/bj_1.jpg) repeat-x">
  <tr>
    <td class="top" width="100%">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="33">
        <tr valign="top">
          <td class="hd_left">
          	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
              	<tr>
                	<td height="22" valign="bottom">当前时间：<span id='myclock'></span></td>
              	</tr>
              </table>
          </td>
          <td align="left" valign="top" style="float:left"></td>
          <td align="right"  height="32" valign="middle" class="hd_right" style="float:right;">
          	<img src="<%=basePath%>resources/images/hd_2.jpg" width="10" height="10" />
          	&nbsp;&nbsp;欢迎您:<%=adminUser.getAdminTrueName() %> &nbsp; &nbsp;|&nbsp; 
          	<a href="exitSystem.shtml" style="color: red;">注销</a>&nbsp;&nbsp;|&nbsp; 
          	<a href="javascript:void(0)" onclick="pwdUpdate()" style="color: red;">修改密码</a>
          </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="237" align="right">
          	<span class="logo">
          		<img src="<%=basePath%>resources/images/logo.jpg" width="208" height="75" />
          	</span>
          </td>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr id="navMenu">
                	<td align="center" rowspan="2" width="20">&nbsp;</td>
                	<%
              			if(sys_type.equals("1")){
              		%>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(1)'   src="resources/images/menu_logo_2.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(2)'   src="resources/images/menu_logo_1.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(3)'   src="resources/images/menu_logo_4.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(4)'   src="resources/images/menu_logo_7.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(5)'   src="resources/images/menu_logo_8.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(6)'   src="resources/images/menu_logo_5.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(7)'   src="resources/images/menu_logo_6.jpg"  width="40" height="39" />
						</a>
					</td>
					<%
	              		}
	              	%>
					<%
              		if(sys_type.equals("2")){
              	%>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(8)'   src="resources/images/menu_logo_7.jpg"  width="40" height="39" />
						</a>
					</td>
					<%
              		}
              	%>
              	<%
              		if(sys_type.equals("3")){
              	%>
        			<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(9)'   src="resources/images/menu_logo_2.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(10)'   src="resources/images/menu_logo_1.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(14)'   src="resources/images/menu_logo_4.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(12)'   src="resources/images/menu_logo_7.jpg"  width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(13)'   src="resources/images/menu_logo_6.jpg"  width="40" height="39" />
						</a>
					</td>
              	<%
              		}
              	%>
				<%
              		if(sys_type.equals("4")){
              	%>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(1)' src="resources/images/menu_logo_2.jpg" width="40" height="39" />
						</a>
					</td>
					<td align="center" width="70px">
						<a href="javascript:void(0)">
							<img style="cursor:pointer" onclick='parent.showMenu(15)' src="resources/images/menu_logo_1.jpg" width="40" height="39" />
						</a>
					</td>
				<%
              		}
              	%>
					<td align="center" rowspan="2">&nbsp;</td>
              	<tr id="navMenu1">
              	<%
              		if(sys_type.equals("1")){
              	%>
              		<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(1)' >
           				<a href="javascript:void(0)">快件管理</a>
          				</span>
        			</td>
        			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(2)' >
           
           				<a href="javascript:void(0)">派件员管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(3)' >
           				<a href="javascript:void(0)">宝柜管理</a>
          				</span>
        			</td>
        			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(4)' >
           
           				<a href="javascript:void(0)">财务管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(5)' >
           				<a href="javascript:void(0)">管理员管理</a>
          				</span>
        			</td>
        			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(6)' >
           
           				<a href="javascript:void(0)">系统管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(7)' >
           
           				<a href="javascript:void(0)">公告管理</a>
          				</span>
          			</td>
              	<%
              		}
              	%>
              		<%
              		if(sys_type.equals("2")){
              	%>
        			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(8)' >
           				<a href="javascript:void(0)">财务管理</a>
          				</span>
          			</td>
              	<%
              		}
              	%>
              	<%
              		if(sys_type.equals("3")){
              	%>
        			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(9)' >
           				<a href="javascript:void(0)">快件管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(10)' >
           
           				<a href="javascript:void(0)">派件员管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(14)' >
           				<a href="javascript:void(0)">宝柜管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(12)' >
           
           				<a href="javascript:void(0)">财务管理</a>
          				</span>
          			</td>
          			<td align="center" ><span  style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(13)' >
           				<a href="javascript:void(0)">公告管理</a>
          				</span>
          			</td>
              	<%
              		}
              	%>
              	<%
              		if(sys_type.equals("4")){
              	%>
					<td align="center" ><span style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(1)' >
           				<a href="javascript:void(0)">快件管理</a>
          				</span>
        			</td>
        			<td align="center" ><span style="cursor:pointer" class="hd_menu_text" onclick='parent.showMenu(15)' >
           				<a href="javascript:void(0)">派件员管理</a>
          				</span>
        			</td>
				<%
              		}
              	%>
              	</tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
