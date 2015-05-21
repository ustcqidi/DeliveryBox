var table;
$(function() {

			initPage();

			$("#btnQuery").on('click', function() {

						table.fnDraw();
					});

		});

function initPage() {

	table = $("table").myDataTable({
		"sAjaxSource" : Global.webRoot + '/boxInfo/boxManageList.json',
		"paramSelector" : '#province,#city,#region,#communityName,#cabinetId,#communityAddress',
		"aoColumns" : [{
			"sTitle" : "柜子id",
			"sName" : "cabinetId"
		}, {
			"sTitle" : "省份",
			"sName" : "province"
		}, {
			"sTitle" : "城市",
			"sName" : "city"
		}, {
			"sTitle" : "区域",
			"sName" : "region"
		}, {
			"sTitle" : "社区名称",
			"sName" : "cabinetName"
		}, {
			"sTitle" : "柜子名称",
			"sName" : "communityName"
		}, {
			"sTitle" : "地址",
			"sName" : "communityAddress"
		}, {
			"sTitle" : "联系方式",
			"sName" : "communityContact"
		}, {
			"sTitle" : "创建时间",
			"sName" : "update_time",
			"fnRender" : function(obj) {
				if (obj.aData[8] != "") {
					return new Date(obj.aData[8]).format('yyyy-MM-dd hh:mm:ss');
				} else {
					return "";
				}
			}
		}, {
			"sTitle" : "副柜个数",
			"sName" : "smallCount"
		}, {
			"sTitle" : "操作",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return "<a href=\"javascript:view('" + obj.aData[10]
						+ "');\">查看</a>&nbsp&nbsp<a href=\"javascript:update('" + obj.aData[10]
						+ "');\">修改</a>";
			}
		}]
	});
}

function view(id) {
	 window.location.href = Global.webRoot + "/boxInfo/view.do?operation=view&tgId=" + id;
}

function update(id) {
	 window.location.href = Global.webRoot + "/boxInfo/view.do?operation=update&tgId=" + id;
}

