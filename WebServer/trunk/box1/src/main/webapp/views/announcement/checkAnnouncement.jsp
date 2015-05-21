<%@ include file="../../../include/include.jsp"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" import="org.springframework.validation.ObjectError"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.xsz.edu.jpkc.entity.Announcement" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	Announcement Announcement= (Announcement)request.getAttribute("announcement");
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="<%=basePath%>resources/css/style1.css" rel="stylesheet" type="text/css"/>
        <title>查看公告</title>
    </head>
    <body style="background:#FFF">
    	<div class="navi_area">
        	<span>
            	&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)">当前位置</a>：&nbsp;<a href="javascript:void(0)">公告管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查看</a>
            </span>
        </div>
        <div class="clear"></div>
		<div class="query_area">
		<form action="announcement/updateAnnouncement.shtml" method="post" id="updateAnnouncementForm" >
               <table id="queryTable" width="100%" border="0" cellspacing="1" cellpadding="6" align="left" bgcolor="#DDDDDD" style="font-size: 12px;">
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%">公告标题：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <%=Announcement.getAnnouncementTitle() %>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%">公告内容：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <%=Announcement.getAnnouncementContent()%>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%">发布人：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <%=Announcement.getAdminUserId()!=null?Announcement.getAdminUserId().getAdminTrueName():"" %>
                                </label>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%">发布时间：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <%=Announcement.getAddDte()!=null?Announcement.getAddDte().toString().substring(0,10):"" %>
                                </label>
                            </td>
                        </tr>
                         <tr>
                            <td height="30" align="right" align="right" bgcolor="#FFFFFF" width="30%">备注：</td>
                            <td bgcolor="#FFFFFF">
                                <label>
                                    <%=Announcement.getRemarks()%>
                                </label>
                            </td>
                        </tr>
                        <tr align="center">
                            <td colspan="2" bgcolor="#FFFFFF">
                                <input type="button" onclick="window.close();" value="关闭" class="CBtext" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </body>
</html>