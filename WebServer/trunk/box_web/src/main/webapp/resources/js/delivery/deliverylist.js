var ruleTable;
$(document).ready(function() {

	$("#panel").dialog({
				autoOpen : false,
				modal : true,
				height : 470,
				width : 450,
				resizable : false,
				buttons : {
					"保存" : function() {
						saveOrUpdate();
					},
					"取消" : function() {
						$(this).dialog("close");
					}
				}
			});

	$("#btnQuery").bind("click", function() {
				ruleTable.fnDraw();
				return false;
			});
	$("#btnAdd").bind("click", function() {
				chearForm();
				$("#id").val('');
				$("#panelOpCode input, textarea").attr("disabled", false);
				$(".ui-dialog-buttonset button:eq(0)").show();
				$("#panelOpCode").dialog("open");
			});
	$("#charge").bind("click", function() {
	
		window.location.href = ctx+'/account/charge.do?tel='+$("#ptel").val();
		
	});	
			
			

	ruleTable = $('.table').myDataTable({
		"sAjaxSource" : './deliverylist.json',
		"paramSelector" : '#username,#company,#tel',
		"aoColumns" : [{
					"sTitle" : "快递员姓名",
					"bSortable" : true,
					"sName" : "username"
				}, {
					"sTitle" : "公司",
					"bSortable" : true,
					"sName" : "company"
				}, {
					"sTitle" : "手机号",
					"bSortable" : true,
					"sName" : "tel"
				}, {
					"sTitle" : "余额",
					"bSortable" : true,
					"sName" : "balance"
				}, {
					"sTitle" : "寄件价格说明",
					"bSortable" : true,
					"sName" : "delivery_price_desc"
				}, {
					"sTitle" : "备注",
					"bSortable" : true,
					"sName" : "user_desc"
				}, {
					"sTitle" : "操作",
					"bSortable" : false,
					"fnRender" : function(obj) {
						return "<a href=\"javascript:view('"
								+ obj.aData[6]
								+ "');\">查看</a>&nbsp&nbsp<a href=\"javascript:update('"
								+ obj.aData[6]
								+ "');\">修改</a>&nbsp&nbsp<a href=\"javascript:deleteInfo('"
								+ obj.aData[6] + "');\">删除</a>";
					}
				}]
	});

});

function view(id) {
	$("#id").val(id);
	$.ajax({
				url : ctx + '/delivery/getUserAppById.json',
				dataType : 'json',
				type : 'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					chearForm();
					$("#pusername").val(data.username);
					$("#ptel").val(data.tel);
					$("#pcompany").val(data.company);
					$("#pbalance").val(data.balance);
					$("#pdelivery_price_desc").val(data.delivery_price_desc);
					$("#puser_desc").val(data.user_desc);
					$(".ui-dialog-buttonset button:eq(0)").hide();
					$("#panel input, textarea").attr("disabled", true);
					$("#panel").dialog("open");

				}
			});
}

function update(id) {
	$("#id").val(id);
	$.ajax({
				url : ctx + '/delivery/getUserAppById.json',
				dataType : 'json',
				type : 'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					chearForm();
					$("#pusername").val(data.username);
					$("#ptel").val(data.tel);
					$("#pcompany").val(data.company);
					$("#pbalance").val(data.balance);
					$("#pdelivery_price_desc").val(data.delivery_price_desc);
					$("#puser_desc").val(data.user_desc);
					$(".ui-dialog-buttonset button:eq(0)").show();
					$("#panel input, textarea").attr("disabled", false);
					$("#panel #pbalance").attr("disabled", true);
					$("#panel").dialog("open");

				}
			});
}

function saveOrUpdate() {
	if ($("#pusername").val() == '') {
		alert('用户名不能为空');
		return false;
	}
	if ($("#ptel").val() == '') {
		alert('号码不能为null');
		return false;
	}

	var appUser = {};
	appUser.id = $("#id").val();
	appUser.username = $("#pusername").val();
	appUser.tel = $("#ptel").val();
	appUser.company = $("#pcompany").val();
	/*appUser.balance = $("#pbalance").val();*/
	appUser.delivery_price_desc = $("#pdelivery_price_desc").val();
	appUser.user_desc = $("#puser_desc").val();

	$.ajax({
				url : ctx + '/delivery/saveOrUpdate.json',
				dataType : 'json',
				type : 'POST',
				data : [{
							name : 'json',
							value : JSON.stringify(appUser)
						}],
				success : function(data) {
					if (data == '1') {
						$("#panel").dialog("close");
						$("#btnQuery").click();
					}
				}
			});
}

function deleteInfo(id) {
	myConfirm("确定删除该用户？", "删除用户", function() {
				$.ajax({
							url : ctx + '/delivery/deleteUserById.json',
							dataType : 'json',
							type : 'POST',
							data : [{
										name : 'id',
										value : id
									}],
							success : function(data) {
								if (data == '1') {
									alert("删除成功");
									$("#btnQuery").click();
								}
							}
						});
			})

}

function chearForm() {
	$("#panel input").val('');
	$("#panel textarea").val('');
}