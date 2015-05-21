<%@ include file="../../../include/include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="org.springframework.validation.ObjectError"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link href="<%=basePath%>resources/css/style1.css" rel="stylesheet"  type="text/css"/>
        <title>公告管理</title>
        <style>
            .error{
                color: #ff0000;
                font-style: italic;
            }
        </style>
        <script type="text/javascript">
            var start = 0;
            var pageSize = 15;
            var end = start+pageSize;
            var currentPage =1;
            var pageCount;
            var loadingBar = "<tr bgcolor=\"#FFFFFF\"><td colspan=11><div><img src=\"<%=basePath%>resources/images/submit-bar.gif\"></div></td></tr>";
            var options={
                data:{start:start,end:end},
                //数据返回格式
                dataType:"json",
                //是否同步
                async:false,
                //成功回调函数
                beforeSubmit:function(){
                    $("#infoTable>tbody").html(loadingBar);
                },
                success:function(data){
                    pageCount = setPageCount(data.totalCount);
                    //alert(data.totalCount);
                    buildTable(data);
                    buildPageSpan();
                }
            };
            $(function(){
                $("#go").click(jumpPage);
                $("#queryFormSubmit").click(function(){
                    goPage(1);
                });
                $("#queryForm").submit(function(){
                    $(this).ajaxSubmit(options);
                    return false;
                });
             
				$("#add").click(function(){
               		window.location.href="<%=basePath%>views/announcement/addAnnouncement.jsp";
                });
                goPage(1);
            });
            function loadUserInfo(){
                options.data.start = start;
                options.data.end = end;
                $("#queryForm").submit();
            }

            function buildPageSpan(){
                var tempHtml ="";
                if(pageCount==0)
                    tempHtml = "("+0+"/"+pageCount+")";
                else
                    tempHtml = "("+currentPage+"/"+pageCount+")";
                $("#pageSpan").html(tempHtml);
            }

            function buildTable(data){
                var tempHtml ="";
                if(data.totalCount==0){
                    tempHtml ="<tr bgcolor=\"#FFFFFF\" ><td colspan=\"12\" style=\"color:red;\" align=\"center\">查询无数据</td></tr>";
                }
                var index = start;
                for(var i=0;i<data.announcementList.length;i++){
                	var remarks = data.announcementList[i].remarks;
                	if(remarks.length > 15){
                		remarks = remarks.substring(0,15);
                	}
                	
                    tempHtml += "<tr bgcolor=\"#FFFFFF\">"+
                        "<td>"+parseInt(++index)+"</td>"+
                        "<td title='"+data.announcementList[i].announcementTitle+"'>"+data.announcementList[i].announcementTitle+"</td>"+
                        "<td title='"+data.announcementList[i].adminUserId.adminTrueName+"'>"+data.announcementList[i].adminUserId.adminTrueName+"</td>"+
                        "<td title='"+data.announcementList[i].addDte.substring(0,10)+"'>"+data.announcementList[i].addDte.substring(0,10)+"</td>"+
                        "<td title='"+data.announcementList[i].remarks+"'>"+remarks+"</td>"+
                        "<td>" +
                        " <a href =\"javascript:void(0)\" onclick='checkAnnouncement(\""+data.announcementList[i].id+"\");'>查看</a> " +
                        " <a href =\"javascript:void(0)\" onclick='updateAnnouncement(\""+data.announcementList[i].id+"\");'>修改</a> " +
                        " <a href =\"javascript:void(0)\" onclick='deleteAnnouncement(\""+data.announcementList[i].id+"\");'>删除</a> " +
                        "</td>"+
                       "</tr>";
                }
                $("#infoTable>tbody").html(tempHtml);
                $("#totalCount").html(data.totalCount);
                setTableStyle();
            }
                
            function setPageCount(totalCount){
                var pageCount = 0;
                if(totalCount%pageSize==0){
                    pageCount = parseInt(totalCount/pageSize);
                }else{
                    pageCount = parseInt(totalCount/pageSize)+1;
                }
                return pageCount;
            }
                
            function nextPage(){
                if(currentPage>=pageCount)
                    currentPage = pageCount-1;
                start = currentPage*pageSize;
                end = (parseInt(currentPage)+parseInt(1))*pageSize;
                currentPage++;
                loadUserInfo();
            }

            function prevPage(){
                if(currentPage<=1)
                    currentPage = 1;
                start = (currentPage-2)*pageSize<0?0:(currentPage-2)*pageSize;
                end = (currentPage-1)*pageSize==0?pageSize:(currentPage-1)*pageSize;
                currentPage==1?1:currentPage--;
                loadUserInfo();
            }

            function goPage(pageIndex){
                if(pageIndex>=pageCount)
                    pageIndex = pageCount;
                if(pageIndex<=1)
                    pageIndex = 1;
                start = (pageIndex-1)*pageSize;
                end = pageIndex*pageSize;
                currentPage = pageIndex;
                loadUserInfo();
            }

            function topPage(){
                start = 0;
                end = start+pageSize;
                currentPage = 1;
                loadUserInfo();
            }

            function endPage(){
                goPage(pageCount);
            }

            function jumpPage(){
                var pageIndex = $.trim($("#pageIndex").val());
                if(isNaN(pageIndex)){
                    alert("请输入合法的页码");
                    return;
                }
                if(pageIndex==""||pageIndex==null)
                    return;
                goPage(pageIndex);
            }

            function setPageSize(){
                var seletPageSize = $("#pageSize").val();
                pageSize =  seletPageSize;
                start = 0;
                end = start+pageSize;
                currentPage =1;
                loadUserInfo();
            }

            function setTableStyle(){
            	$(".showTable>tbody>tr").hover(function(){
                	$(this).removeClass("trColor");
                    $(this).addClass("hover");
                }, function(){
                    $(this).removeClass("hover");
                    $(".showTable>tbody>tr:odd").addClass("trColor");
                });
                $(".showTable>tbody>tr:odd").addClass("trColor");
            }
            
            function updateAnnouncement(id){
            	var url = "<%=basePath%>announcement/updateAnnouncement.shtml?id="+id;
            	window.location.href=url;
            }
            
            function deleteAnnouncement(id){
            	if(confirm("您确定要删除该条公告？")){
            		window.location.href="<%=basePath%>announcement/deleteAnnouncement.shtml?id="+id;
            	}
            }
            
            function checkAnnouncement(id){
            	var url = "<%=basePath%>announcement/checkAnnouncement.shtml?id="+id;
            	window.open (url, '查看公告', 'height=400px, width=800px,resizable=yes,scrollbars');
            }
        </script>
    </head>
    <body style="background:#FFF;">
    	<div class="navi_area">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;当前位置： <a href="javascript:void(0)">公告管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">查询</a> &nbsp;</span>
		</div>
		<div class="clear"></div>
		<div class="query_area">
	        <form action="<%=basePath%>announcement/announcementManage.shtml"  method="post" id="queryForm">
	            <table id="queryTable" cellspacing="1" cellpadding="6" style="font-size: 12px">
	                <tr>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">公告标题：</td>
       					<td width="26%" bgcolor="#FFFFFF"><input class="Atext" type="text" name="announcementTitle" id="announcementTitle"/></td>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">发布人：</td>
       					<td bgcolor="#FFFFFF"><input class="Atext" type="text" name="adminUserId" id="adminUserId" /></td>
      					</tr>
      					<tr>
      					 <td width="16%" align="right" bgcolor="#FFFFFF">发布时间：</td>
       					<td width="26%" bgcolor="#FFFFFF" colspan="3">
       						<input type="text" class="Wdate" name="proDeadlineStart" id="proDeadlineStart" readonly="readonly" onFocus="WdatePicker({isShowClear:true,maxDate:'#F{$dp.$D(\'proDeadlineEnd\',{d:-1})}'})" onchange="changeSqksStart(this.value)" size="10" />
       						<font class="font18 fontA">~</font>
							<input type="text" class="Wdate" name="proDeadlineEnd" id="proDeadlineEnd" readonly="readonly" onFocus="WdatePicker({isShowClear:true,minDate:'#F{$dp.$D(\'proDeadlineStart\',{d:1})}'})" onchange="changeSqksEnd(this.value)" size="10" />
       						&nbsp;&nbsp;&nbsp;
       						<input type="button" name="entSub" value="查 询" id="queryFormSubmit" class="CBtext"  />
       					</td>
	                </tr>
	            </table>
	        </form>
		</div>
		<div class="clear"></div>
		<div class="list_area">
	        <table id="infoTable" cellspacing="1" cellpadding="5" style="font-size: 12px" class="showTable">
	            <thead >
	            	<tr class="ttr" height="25">
			            <th align="center" width="5%">序号</th>
			            <th align="center" width="15%">公告标题</th>
			            <th align="center" width="10%">发布人</th>
			            <th align="center" width="15%">发布时间</th>
			            <th align="center" width="15%">备注</th>
			            <th align="center" width="10%">操作</th>
		            </tr>
		        </thead>
		        <tbody ></tbody>
		    </table>
		    <div class="admin_Pagination" style="width:100%;">
		   		<table width="100%" align="center" style="font-size:12px;background:#FFF;">
		   			<tr>
		   				<td align="center">
		      					<span>
					         	<span style="padding-left:5px;">共有</span>
					         	<span style="color:red;padding-left:5px;"><label id="totalCount">0</label></span>
					         	<span style="padding-left:5px;">条记录</span>
					        </span>
		      					<a href="javascript:void(0)" id="first" name="first" onclick="topPage();">首页 </a>
		      					<a href="javascript:void(0)" id="prev" name="prev" onclick="prevPage();">上一页</a>
					        <a href="javascript:void(0)" id="next" name="next" onclick="nextPage();">下一页</a>
					        <a href="javascript:void(0)" id="last" name="last" onclick="endPage();">末页</a>
		      					<input type="text" class="Ftext" id="pageIndex" name="pageIndex" />
		      					<span id="pageSpan" style="color: red;"></span>
		      					<input type="button" class="MNbutton" id="go" name="go" value="GO" />
		      					<input type="hidden" id="pageCount" name="pageCount" value="15" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</body>
</html>