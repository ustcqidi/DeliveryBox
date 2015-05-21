<%@ include file="../../../include/include.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="org.springframework.validation.ObjectError"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <link href="<%=basePath%>resources/css/style1.css" rel="stylesheet"  type="text/css"/>
         <script type="text/javascript" src="<%=basePath%>resources/js/commonQueryAndPaged.js"></script>
        <title>财务管理</title>
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
                for(var i=0;i<data.financeList.length;i++){
                	var financeType = data.financeList[i].financeType;
                	var financeMoneyds=0.0;
                	var financeMoneycq=0.0;
                	if(financeType == '1'){
                		financeMoneyds = data.financeList[i].financeMoney;
                	}
                	if(financeType == '2'){
                		financeMoneycq = data.financeList[i].financeMoney;
                	}
                    tempHtml += "<tr bgcolor=\"#FFFFFF\">"+
                        "<td>"+parseInt(++index)+"</td>"+
                        "<td title='"+data.financeList[i].communityId.communityName+"'>"+data.financeList[i].communityId.communityName+"</td>"+
                        "<td title='"+data.financeList[i].cupboardId.cupboardName+"'>"+data.financeList[i].cupboardId.cupboardName+"</td>"+
                        "<td title='"+data.financeList[i].cabinetId.cabinetName+"'>"+data.financeList[i].cabinetId.cabinetName+"</td>"+
                        "<td title='"+financeMoneyds+"￥'>"+financeMoneyds+"￥</td>"+
                        "<td title='"+financeMoneycq+"￥'>"+financeMoneycq+"￥</td>"+
                        "<td title='"+data.financeList[i].cupboardTime.substring(0,10)+"'>"+data.financeList[i].cupboardTime.substring(0,10)+"</td>"+
                        "<td><a href=\"javascript:void(0)\"onclick=\"viewModel("+data.financeList[i].id+");\">查看</a></td>"+
                       "</tr>";
                }
                $("#infoTable>tbody").html(tempHtml);
                $("#totalCount").html(data.totalCount);
                $("#je1").html(data.je1+"￥");
                $("#je2").html(data.je2+"￥");
                $("#je3").html(data.je3+"￥");
                setTableStyle();
            }
            
          	//查看
        	function viewModel(id){
               	var url = "<%=basePath%>finance/financeView.shtml?id="+id;
               	window.open (url, '查看', 'height=400px, width=800px,resizable=yes,scrollbars');
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
        </script>
    </head>
    <body style="background:#FFF;">
    	<div class="navi_area" >
			<span>&nbsp;&nbsp;&nbsp;&nbsp;当前位置： <a href="javascript:void(0)">财务管理</a>&nbsp; >> &nbsp;<a href="javascript:void(0)">收入记录</a> &nbsp;</span>
		</div>
		<div class="clear"></div>
		<div class="query_area">
	        <form action="<%=basePath%>finance/financeManage.shtml"  method="post" id="queryForm">
	            <table id="queryTable" cellspacing="1" cellpadding="6" style="font-size: 12px">
	            	<tr>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">社区：</td>
       					<td width="26%" bgcolor="#FFFFFF"><input class="Atext" type="text" name="communityName" id="communityName"/></td>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">柜号：</td>
       					<td bgcolor="#FFFFFF"><input class="Atext" type="text" name="cupboardName" id="cupboardName"/></td>
      				</tr>
      				<tr>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">格号：</td>
       					<td width="26%" bgcolor="#FFFFFF"><input class="Atext" type="text" name="cabinetName" id="cabinetName"/></td>
       					<td width="16%" align="right" bgcolor="#FFFFFF">派件员手机号：</td>
       					<td bgcolor="#FFFFFF"><input class="Atext" type="text" name="deliveryContact" id="deliveryContact"/></td>
      				</tr>
      				<tr>
     					<td width="16%" align="right" bgcolor="#FFFFFF">到柜日期：</td>
       					<td width="26%" bgcolor="#FFFFFF">
       						<input type="text" class="Wdate" name="cupboardTimeStart" id="cupboardTimeStart" readonly="readonly" onFocus="WdatePicker({isShowClear:true,maxDate:'#F{$dp.$D(\'cupboardTimeEnd\',{d:-1})}'})" onchange="changeSqksStart(this.value)" size="10" />
       						<font class="font18 fontA">~</font>
							<input type="text" class="Wdate" name="cupboardTimeEnd" id="cupboardTimeEnd" readonly="readonly" onFocus="WdatePicker({isShowClear:true,minDate:'#F{$dp.$D(\'cupboardTimeStart\',{d:1})}'})" onchange="changeSqksEnd(this.value)" size="10" />
       					</td>
       					<td width="16%" align="right" bgcolor="#FFFFFF">收费日期：</td>
       					<td bgcolor="#FFFFFF">
       						<input type="text" class="Wdate" name="financeDateStart" id="financeDateStart" readonly="readonly" onFocus="WdatePicker({isShowClear:true,maxDate:'#F{$dp.$D(\'financeDateEnd\',{d:-1})}'})" onchange="changeSqksStart(this.value)" size="10" />
       						<font class="font18 fontA">~</font>
							<input type="text" class="Wdate" name="financeDateEnd" id="financeDateEnd" readonly="readonly" onFocus="WdatePicker({isShowClear:true,minDate:'#F{$dp.$D(\'financeDateStart\',{d:1})}'})" onchange="changeSqksEnd(this.value)" size="10" />
       					</td>
	                </tr>
      				 <tr>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">收费类型：</td>
       					<td colspan="3" bgcolor="#FFFFFF">
       						<select name="financeType" id="financeType" class="Aselect">
			                    <option value="">--全部--</option>
			                    <option value="1">派件收费</option>
			                    <option value="2">超期收费</option>
		               		</select>
       						&nbsp;&nbsp;&nbsp;
       						<input type="button" name="entSub" value="查 询" id="queryFormSubmit" class="CBtext"  />
       					</td>
      				</tr>
      				<tr>
	                    <td width="16%" align="right" bgcolor="#FFFFFF">金额：</td>
       					<td width="26%" bgcolor="#FFFFFF" colspan="3" >
       						代收合计：<span style="color: red;" id='je1'></span>&nbsp;&nbsp;&nbsp;&nbsp;
       						超期合计：      <span style="color: red;" id='je2'></span>&nbsp;&nbsp;&nbsp;&nbsp;
       						合计：<span style="color: red;" id='je3'></span>&nbsp;&nbsp;&nbsp;&nbsp;
       					</td>
      				 </tr>
	            </table>
	        </form>
		</div>
		<div class="oper_area">
		    <table>
		      <tr>
		      	<td width="80px">
	        	</td>
		      </tr>
		    </table>
		</div>
		<div class="list_area">
	        <table id="infoTable" cellspacing="1" cellpadding="5" style="font-size: 12px" class="showTable">
	            <thead >
	            	<tr class="ttr" height="25">
			            <th align="center" width="4%">序号</th>
			            <th align="center">社区</th>
			            <th align="center">柜号</th>
			            <th align="center">格号</th>
			            <th align="center">代收合计</th>
			            <th align="center">超期合计</th>
			            <th align="center">到柜时间</th>
			            <th align="center">操作</th>
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