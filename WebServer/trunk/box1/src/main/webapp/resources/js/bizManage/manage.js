var table;
$(function() {
	
	initPage();
	
	$("#_query").on('click', function() {

		table.fnDraw();
	});
	
	$("#div_file").uploadFile({
		width : '140',
	    height : '30'
	});
	
		$('#panel').dialog({
		autoOpen : false,
		height : 580,
		width : 700,
		modal : true,
		buttons : {
			"保存" : function() {
				channelPanel_save();
			},
			"关闭" : function() {
				$(this).dialog("close");
			}
		}
	});

});

function initPage() {
	
	table = $("table")
			.myDataTable(
					{
						"sAjaxSource" : Global.webRoot
								+ '/biz/bizDatalist.json',
						"paramSelector" : '#weixin_name,#number,#creater,#query_begintime,#query_endtime',
						"aoColumns" : [
								{
									"sTitle" : "会员ID",
									"sName" : "id"
								},
								{
									"sTitle" : "用户名",
									"sName" : "loginName"
								},
								{
									"sTitle" : "企业名称",
									"sName" : "companyName"
								},
								{
									"sTitle" : "会员类型",
									"sName" : "sex"
								},
								{
									"sTitle" : "企业类型",
									"sName" : "companyType"
								},
								{
									"sTitle" : "经营范围",
									"sName" : "companyRegion"
								},{
									"sTitle" : "联系人姓名",
									"sName" : "contactName"
								},
								{
									"sTitle" : "联系手机",
									"sName" : "tel"
								},{
									"sTitle" : "固定电话",
									"sName" : "phone"
								},{
									"sTitle" : "联系邮箱",
									"sName" : "email"
								},{
									"sTitle" : "QQ号码",
									"sName" : "qq"
								},
								{
									"sTitle" : "更新时间",
									"sName" : "createTime",
									"fnRender" : function(obj) {
											return new Date(obj.aData[12])
													.format('yyyy-MM-dd hh:mm:ss');
									}
								},{
									"sTitle" : "密码初始化",
									"bSortable" : false,
									"fnRender" : function(obj) {
											return "<a href=\"javascript:view('view','"
													+ obj.aData[0]
													+ "');\">查看</a>";
									}
								},
								{
									"sTitle" : "查看",
									"bSortable" : false,
									"fnRender" : function(obj) {
											return "<a href=\"javascript:view('view','"
													+ obj.aData[0]
													+ "');\">查看</a>";
									}
								},
								
								
								{
									"sTitle" : "修改",
									"bSortable" : false,
									"fnRender" : function(obj) {
											return "<a href=\"javascript:view('update','"
													+ obj.aData[0]
													+ "');\">修改</a>";
									}
								},
								{
									"sTitle" : "删除",
									"bSortable" : false,
									"fnRender" : function(obj) {
											return "<a href=\"javascript:view('delete','"
													+ obj.aData[0]
													+ "');\">删除</a>";
									}
								} ]
					});
}



function view(operation, id) {
	$('#panel').dialog("open");
/*	if(operation == 'delete'){
		myConfirm("确定删除该用户？","删除用户",function(){
		$.ajax({
				url : ctx + '/biz/deleteBizById.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					if(data=='1'){
						alert("删除成功");
						$("#_query").click();
					}
				}
			});
	})
	}else{
		window.location.href = Global.webRoot + "/biz/bizView.do?operation="
			+ operation + "&tgId=" + id;
	}*/
	
	
}
