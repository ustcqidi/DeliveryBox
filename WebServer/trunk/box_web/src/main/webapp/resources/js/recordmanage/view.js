var  operate;
$(function(){
	
	  
	   getBrandStoryInfo();
	   //返回按钮
	     $("#btnCancel").click(function(){
	     	window.location.href=Global.webRoot+"/biz/bizList.do";
	     });
	     
	     $("#btnSave").click(function(){
	     clickParams();
	     })
	     
	     
	     
	     initPage();
	     
	     $(".icon-th-list").click(function(){
	         $('#channel_dialog').dialog("open");
	     })
});

/**
 * 提醒推送保存
 * @param $type
 */

function clickParams(){
	    var flag = true;
	    var form = "#form";
	    flag =	checkWeixinName(form,flag);
	    flag =	checkUserName(form,flag);
	    flag =	checkPhone(form,flag);
	    if(flag){
	    	$("#btnSave").prop("disabled",true);
	    	$(form).submit();
	    }
}



//初始化得到数据，根据用户操作
function getBrandStoryInfo(){
	if(operate != 'add'&&id!=''){
		
		$.ajax({
			"type" : "post",
			"data":{"tgId":id},
			"async" : false,
			"url" : Global.webRoot + "/biz/bizView.json",
			"dataType" : "json",
			"success" : function(json) {
				businessinfo = json.businessinfo;
				$("input[name='weixin_name']").val(businessinfo.wname);
				$("input[name='creator_name']").val(businessinfo.creator_name);
				$("input[name='user_name']").val(businessinfo.user_name);
				$("input[name='profession']").val(businessinfo.profession);
				if(businessinfo.sex =='男'){
					$("#sex_male").attr("checked",true);
				}else{
					$("#sex_female").attr("checked",true);
				}
				$("#desc").html(businessinfo.desc);
				$("input[name='phone']").val(businessinfo.phone);
				}
			});
	}
	
	
	if(operate == 'view'){
		$("input,textarea,select,.ui-multiselect").attr("disabled",true);
		$("#btnCancel").attr("disabled",false);
		
	}
}





//验证正文
function checkWeixinName(form,flag){
	if (flag) {
	 if($(form).find("[name='weixin_name']").val()==""){
		   alert("请填写微信名称");
		   return false;
	 }else{
	 $.ajax({
		"type" : "post",
		"data":{"id":id,"name":$(form).find("[name='weixin_name']").val()},
		"url" : Global.webRoot + "/biz/checkWeixinName.json",
		"dataType" : "text",
		"async" : false,
		"success" : function(json) {
			  if(json!='1'){
			    alert(json);
			    flag = false;
			  }
			}
		});}
	}

	return flag;
}


//验证正文
function checkUserName(form,flag){
	if (flag) {
	 if($(form).find("[name='user_name']").val()==""){
		   alert("请填写用户姓名");
		   return false;
	 }
	}
	return flag;
}

function checkPhone(form,flag){
	if (flag) {
	 if($(form).find("[name='phone']").val()==""){
		   alert("请填写电话号码");
		   return false;
	 }else{
	 	$.ajax({
			"type" : "post",
			"data":{"id":id,"phone":$(form).find("[name='phone']").val()},
			"async" : false,
			"url" : Global.webRoot + "/biz/checkPhone.json",
			"dataType" : "text",
			"success" : function(json) {
				  if(json!='1'){
				    alert(json);
				    flag = false;
				  }
				}
			});
	 
	 }
	}
	return flag;
}

var table;
function initPage() {

	    $("#queryBtn1").click(function(){
	      table.fnDraw();
	     })
	
		$('#channel_dialog').dialog({
						autoOpen : false,
						resizable : false,
						bgiframe : true,
						height : 532,
						width : 560,
						modal : true,
						buttons : {
							"保存" : function() {
								$("#phone").val($("input[name='phone1']:checked").parent().parent().find('td:eq(1)').text());
								$(this).dialog("close");
							},
							"关闭" : function() {
								$(this).dialog("close");
							}
						}
					});
					
	
	table = $(".table")
			.myDataTable(
					{
						"sAjaxSource" : Global.webRoot
								+ '/phone/list.json',
						"paramSelector" : '#number',
						"aoColumns" : [
								{
									"sTitle" : "编号",
									"sName" : "id",
									"fnRender" : function(obj) {
									    return '<input type="radio"  name="phone1" value="'+ obj.aData[0]+'" />';
									}
								},
								{
									"sTitle" : "号码",
									"sName" : "number"
								},
								{
									"sTitle" : "业务人员",
									"sName" : "creator"
								},
								{
									"sTitle" : "是否拉黑",
									"sName" : "status"
								},
								{
									"sTitle" : "黑名单",
									"bSortable" : false,
									"fnRender" : function(obj) {
										if(loginName=='admin'){
										return "<a href='javascript:void(0);' onclick='deleteInfo(\""
										+ obj.aData[4] + "\")'>删除</a>";
										}else{
										  if(obj.aData[3] == '否'){
										 return "<a href='javascript:void(0);' onclick='blackInfo(\""
										+ obj.aData[4] + "\")'>加入黑名单</a>";
										  }else{
										   return "<a href='javascript:void(0);' onclick='blackInfo(\""
										+ obj.aData[4] + "\")'>加入白名单</a>";
										  
										  }
										}
									}
								} ]
					});
}

function blackInfo(id) {
	$.ajax({
			url : ctx + '/phone/blackPhone.json',
			dataType : 'json',
			type:'POST',
			data : [{
						name : 'id',
						value : id
					}],
			success : function(data) {
				if(data=='1'){
					table.fnDraw();
				}
			}
		});
}
