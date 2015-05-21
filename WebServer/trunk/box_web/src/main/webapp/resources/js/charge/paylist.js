var table1,table2;
$(document).ready(function() {

	$("#btnQuery").bind("click", function() {
				table1.fnDraw();
				table2.fnDraw();
				return false;
			});

	table1 = $('#table1').myDataTable({
		"sAjaxSource" : ctx + '/account/payTotalList.json',
		"paramSelector" : '#cabinetId,#startTime,#endTime',
		"aoColumns" : [{
					"sTitle" : "柜子ID",
					"bSortable" : true,
					"sName" : "r.cabinetId"
				}, {
					"sTitle" : "副柜个数",
					"bSortable" : false
				}, {
					"sTitle" : "使用次数",
					"bSortable" : false
				}, {
					"sTitle" : "收入合计(单位：元)",
					"bSortable" : false
				}]
	});
	
		table2 = $('#table2').myDataTable({
		"sAjaxSource" : ctx + '/account/payDetailList.json',
		"paramSelector" : '#cabinetId,#startTime,#endTime',
		"aoColumns" : [{
					"sTitle" : "柜子ID",
					"bSortable" : true,
					"sName" : "r.cabinetId"
				}, {
					"sTitle" : "时间",
					"bSortable" : true,
					"sName" : "c.addDate",
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
					"sName" : "c.tel"
				}, {
					"sTitle" : "金额(单位：元)",
					"bSortable" : true,
					"sName" : "c.money"
				}, {
					"sTitle" : "格子编号",
					"bSortable" : false
				}]
	});

});
