  var checkCode = true;   
  var checkName = true;
$(function(){	 
	  $("button[name='btnSave']").click(function(){
		  var code = $("#code").val();
		  var name = $("#name").val();
		  var area = $("#area").val();
		  var provider = $("#provider").val();
		  var contact = $("#contact").val();
		  if($.trim(code)==""){
			  myalert("","请填写信源编码");
			  return false;
		  }
		  if($.trim(name)==""){
			  myalert("","请填写信源名称");
			  return false;
		  }
		  if($.trim(area)==""){
			  myalert("","请填写服务地域");
			  return false;
		  }
		  if($.trim(provider)==""){
			  myalert("","请填写提供方");
			  return false;
		  }
		 
		  if($.trim(contact)==""){
			  myalert("","请填写提供方对接人");
			  return false;
		  }
		  if(!checkCode||!checkName){
			  return false;
		  }
		  
		  this.disabled = true;
		  var operate = $("#operate").val();
		  if(operate=='update'){
		    $("#form1").attr("action",Global.webRoot+"/infoSourceUpdateInfoSource.auth");
		    $("#form1").submit();
		  }else{
			  $("#form1").attr("action",Global.webRoot+"/infoSourceSave.auth");  
		      $("#form1").submit();
		  }
	  });
	  
	  
	  $("#btnCancel").click(function(){
         // window.history.go(-1);
	       window.location.href=Global.webRoot+"/infoSourceList.auth?cancel=cancel";
	  });
	  
   });
   
   
   function checkCodeRepeat(e){
	   var id = $("#id").val();
	   var fid = $("#businessId").val();
	   $.ajax({
			"type" : "post",
			"url" : Global.webRoot+"/infoSourceCheckCodeRepeat.json",
			"data" : {code:e.value?e.value:"",id:id?id:"",fid:fid?fid:""},
			"async":false,
			"success" : function(json) {
				if(json>0){
					$(e).parent().children("span").html("该BFocus已存在该信源编码！");
					//$(e).parent().append("<span  style = 'margin-top:0; '><font color='#FF0000'>信源编码不能重复</font></span>");
					checkCode = false;
				}else{
					checkCode = true;
				}
			},
			"dataType" : "text"
			
		});
   }
   
   function checkNameRepeat(e){
	   var id = $("#id").val();
	   var fid = $("#businessId").val();
	   $.ajax({
			"type" : "post",
			"url" : Global.webRoot+"/infoSourceCheckNameRepeat.json",
			"data" : {name:e.value?e.value:"",id:id?id:"",fid:fid?fid:""},
			"async":false,
			"success" : function(json) {
				if(json>0){
					$(e).parent().children("span").html("该BFocus下已存在该信源名称！");
					//$(e).parent().append("<span  style = 'margin-top:0; '><font color='#FF0000'>信源名称不能重复</font></span>");
					checkName = false;
				}else{
					checkName = true;
				}
			},
			"dataType" : "text"
			
		});
   }
   
   function removeSpan(e){
	   $(e).parent().children("span").html("*");
	   
   }