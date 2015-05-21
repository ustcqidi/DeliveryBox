var ruleTable;
$(document).ready(function() {

	$("#btnQuery").bind("click", function() {
				ruleTable.fnDraw();
				return false;
			});

	ruleTable = $('.table').myDataTable({
		"sAjaxSource" : ctx + '/account/chargelist.json',
		"paramSelector" : '#username,#charge_type,#tel',
		"aoColumns" : [{
					"sTitle" : "序号",
					"bSortable" : true,
					"sName" : "id"
				}, {
					"sTitle" : "充值时间",
					"bSortable" : true,
					"fnRender" : function(obj) {
						if (obj.aData[1] != "") {
							return new Date(obj.aData[1])
									.format('yyyy-MM-dd hh:mm:ss');
						} else {
							return "";
						}
					}
				}, {
					"sTitle" : "账户",
					"bSortable" : true,
					"sName" : "tel"
				}, {
					"sTitle" : "金额",
					"bSortable" : true,
					"sName" : "balance"
				}, {
					"sTitle" : "公司",
					"bSortable" : false
				}, {
					"sTitle" : "操作人",
					"bSortable" : false
				}, {
					"sTitle" : "操作人",
					"bSortable" : false,	
					"fnRender" : function(obj) {
						if (obj.aData[6] == "0") {
							return "系统充值";
						} else if (obj.aData[6] == "1") {
							return "支付宝充值";
						}else if (obj.aData[6] == "-1") {
							return "取消退款";
						}else{
							return "";
						}
					}
				}]
	});

});
