var table;
$(function() {

			initPage();

			$("#btnQuery").on('click', function() {

						table.fnDraw();
					});

		});

function initPage() {

	table = $("table").myDataTable({
		"sAjaxSource" : Global.webRoot + '/record/recordManageList.json',
		"paramSelector" : '#expressNumber,#receiveTel,#deliveryTel,#cabinetId,#communityName,#pickupType,#dstartTime,#rstartTime,#dendTime,#rendTime',
		"aoColumns" : [{
			"sTitle" : "派送时间",
			"sName" : "deliveryDate",
			"fnRender" : function(obj) {
				if (obj.aData[0] != null) {
					return new Date(obj.aData[0]).format('yyyy-MM-dd hh:mm:ss');
				} else {
					return "";
				}

			}
		}, {
			"sTitle" : "快递单号",
			"sName" : "expressNumber"
		}, {
			"sTitle" : "收件人",
			"sName" : "receiveTel"
		}, {
			"sTitle" : "派件人",
			"sName" : "deliveryTel"
		}, {
			"sTitle" : "社区",
			"bSortable" : false
		}, {
			"sTitle" : "柜子ID",
			"sName" : "cabinetId"
		}, {
			"sTitle" : "格号",
			"sName" : "boxId"
		}, {
			"sTitle" : "柜子名称",
			"bSortable" : false
		}, {
			"sTitle" : "快递公司",
			"bSortable" : false
		}, {
			"sTitle" : "取件时间",
			"sName" : "pickupDate",
			"fnRender" : function(obj) {
				if (obj.aData[9] != "") {
					return new Date(obj.aData[9]).format('yyyy-MM-dd hh:mm:ss');
				} else {
					return "";
				}
			}
		}, {
			"sTitle" : "派件状态",
			"bSortable" : false
		}, {
			"sTitle" : "验证码",
			"bSortable" : false,
			"fnRender" : function(obj) {
				if (obj.aData[11] != null && obj.aData[11] != '') {
					return obj.aData[11] + "<a href='javascript:sendMsg("+obj.aData[12]+")'><br>发送</a>";
				}else{
				return "";
				       
				}
			}
		}, {
			"sTitle" : "删除",
			"bSortable" : false,
			"fnRender" : function(obj) {
				if (obj.aData[10] == '快递员取消返回') {
					return "<a href=\"javascript:deleteInfo('" + obj.aData[12]
							+ "');\">删除</a>";
				} else {
					return "";
				}

			}
		}]
	});
}

function deleteInfo(id) {
	myConfirm("确定删除该记录？", "删除", function() {
				$.ajax({
							url : ctx + '/record/deleteRecordInfoById.json',
							dataType : 'json',
							type : 'POST',
							data : [{
										name : 'id',
										value : id
									}],
							success : function(data) {
								if (data == true) {
									alert("删除成功");
									$("#btnQuery").click();
								}
							}
						});
			})

}

function sendMsg(id){
	 $.ajax({
		"type" : "post",
		"data":"recordId="+id,
		"url" : Global.webRoot + "/record/sendMsg.json",
		"dataType" : "json",
		"async" : false,
		"success" : function(json) {
			  if(json==true){
			  	alert("发送成功");
			  }else{
			    alert("发送失败");
			  }
			}
		});
}
