<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
	String indexUrl="views/manage/userManage/user_manage.jsp";
	String sys_url=request.getSession().getAttribute("sys_url").toString();
	String sys_type=request.getSession().getAttribute("sys_type").toString();
	if(sys_type.trim().equals("1")){
		sys_type = "1";
	}else if(sys_type.trim().equals("2")){
		sys_type = "8";
	}else if(sys_type.trim().equals("3")){
		sys_type = "9";
	}else if(sys_type.trim().equals("4")){
		sys_type = "1";
	}
%>
<html>
    <head>
        <base href="<%=basePath%>"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="<%=basePath%>resources/js/jquery-1.4.3.js"></script>
        <title>后台管理框架</title>
        <style type="text/css">
            html,body{
                height: 100%;
                overflow: hidden;
            }         
            #mainContent{
                height:80%;           
            }
        </style>
       
    </head>
    <script type="text/javascript">	
    IndexPage=function(){};
    IndexPage.prototype = {
        showLeft : function() {
            midFrame.cols="217,7,*";
            sepFrame.imgSep.src="styles/theme/images/left/sep_but1.gif";
        },
        hideLeft : function() {
            midFrame.cols="0,7,*";
            sepFrame.imgSep.src="styles/theme/images/left/sep_but2.gif";
        }
    };
    var indexPage=new IndexPage();
    $(window).load(function(){
    	 $("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=<%=sys_type%>");   
         $("#leftFrame").show();
    });
    function showMenu(val){
    	if(val == '1'){
    		$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=1");
    	}
    	if(val == '2'){
    		$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=2");
    	}
    	if(val == '3'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=3");
    	}
    	if(val == '4'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=4");
    	}
    	if(val == '5'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=5");
    	}
    	if(val == '6'){
	   		$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=6");
	   	}
	   	if(val == '7'){
	   		$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=7");
	   	}
	   	if(val == '8'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=8");
	   	}
	   	if(val == '9'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=9");
	   	}
	   	if(val == '10'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=10");
	   	}
	   	if(val == '14'){
   		 	$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=14");
	   	}
	   	if(val == '12'){
   		 	$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=12");
	   	}
	   	if(val == '13'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=13");
	   	}
	   	if(val == '15'){
			$("#leftFrame").attr("src","<%=basePath%>views/manage/left.jsp?sys_type=15");
	   	}
        $("#leftFrame").show();   
    }
    function jump(url){
    	document.getElementById("mainFrame").src=url;
    }
</script>
<frameset rows="115,*" cols="*" frameborder="no" border="0" framespacing="0">
     <frame src="<%=basePath%>views/manage/manageHeader.jsp" name="topFrame" noresize scrolling="no" marginwidth="0" marginheight="0" id="topFrame"/>
     <frameset rows="*" cols="250,7,*" framespacing="0" frameborder="no" border="0" id="midFrame">
        <frame src="" name="leftFrame" noresize scrolling="YES" id="leftFrame"/>
        <frame src="<%=basePath%>views/manage/splitline.html" name="sepFrame" noresize scrolling="no" />
        <frame src="<%=basePath+sys_url%>" name="mainFrame" scrolling="" noresize id="mainFrame"/>
     </frameset>
   
</frameset>
<noframes><body scroll="no">  </body></noframes>
</html>
