$(function() {

			// 返回按钮
			$("#btnCancel").click(function() {
				window.location.href = Global.webRoot
						+ "/boxInfo/boxInfoManage.do";
			});

			$("#btnSave").click(function() {
						if (!$("#cabinetName").val()) {
							$("#cabinetName").next('span').append("柜子名称不能为空");
							window.location.hash = "#cabinetName";
							return false;
						} else {
							$("#cabinetName").next('span').empty();
						}

						if ($('#smallCount').val() > 10) {
							return false;
						}

						$('#smallCount').attr("disabled", false);
						$('#btnSave').attr("disabled", true);
						$("#form").submit();
					});

			$("#panel2").click(function() {
						getBoxInfo();
					});

			$('#smallCount').keyup(function() {
						if (isNaN($(this).val())) {
							$(this).next('span').empty();
							$(this).next('span').append("请输入数字");
						} else if ($(this).val() > 10) {
							$(this).next('span').empty();
							$(this).next('span').append("柜子数量更新不容许超过10");
						} else {
							$(this).next('span').empty();
							$(this).next('span').append("");
						}
					})

			$("#lock").click(function() {
						if ($(this).attr('class') == 'icon-lock') {
							$(this).removeClass();
							$(this).addClass('icon-unlock');
							$('#smallCount').attr("disabled", false);
						} else {
							$(this).removeClass();
							$(this).addClass('icon-lock');
							$('#smallCount').attr("disabled", true);
						}
					})

			if (operation == 'view') {
				$("input[type!='button'], textarea").attr("disabled", true);
			}

			$("#panel").dialog({
						autoOpen : false,
						modal : true,
						height : 400,
						width : 450,
						resizable : false,
						buttons : {
							"保存" : function() {
								save();
							},
							"取消" : function() {
								$(this).dialog("close");
							}
						}
					});

			$(document).bind("mousedown", onBodyMouseDown);

		});

function getBoxType(type) {
	if (type == '1')
		return "大柜";
	else if (type == '2')
		return "中柜";
	else
		return "小柜";

}

function getBoxInfo() {
	$.ajax({
		"type" : "post",
		"data" : {
			"id" : id
		},
		"async" : false,
		"url" : Global.webRoot + "/boxInfo/getBoxOpenInfo.json",
		"dataType" : "json",
		"success" : function(json) {
			var fhtml = '';
			var zhtml = '';
			$("#fugui").empty();
			$.each(json, function(i, d) {
				if (d[1] <= 20) {
					zhtml += '<tr class="odd"><td>'
							+ d[1]
							+ '</td><td>'// 柜子编号
							+ getBoxType(d[2])
							+ '</td><td>'// 柜子类型
							+ (d[3] == '1' ? '<font color="red">是</font>' : '否')
							+ '</td><td>'// 是否占用
							+ (d[5] == "" ? "" : new Date(d[5])
									.format("yyyy-MM-dd hh:mm:ss"))
							+ '</td><td>' + d[6] + '</td></tr>';
					if (d[1] == 20) {
						$("#main tbody").empty();
						$("#main tbody").append(zhtml);
					}
				} else {
					fhtml += '<tr class="odd"><td>'
							+ d[1]
							+ '</td><td>'// 柜子编号
							+ getBoxType(d[2])
							+ '</td><td>'// 柜子类型
							+ (d[3] == '1' ? '<font color="red">是</font>' : '否')
							+ '</td><td>'// 是否占用
							+ (d[5] == "" ? "" : new Date(d[5])
									.format("yyyy-MM-dd hh:mm:ss"))
							+ '</td><td>' + d[6] + '</td></tr>';
					if (d[1] % 20 == 0) {
						var appendhtml = gerfugui(fhtml);
						console.log(appendhtml);
						$("#fugui").append(appendhtml);
					}

				}
			});

		}
	});

}

function gerfugui(tbody) {
	var html = '';
	html += '<div style="width: 45%;float: left;margin-left: 3%">';
	html += '	<label class="control-label" >副柜：</label><table id="main"';
	html += '	class="table table-hover table-bordered table-condensed table-striped">';
	html += '<thead>';
	html += '<tr>';
	html += '<th style="width: 9%;">格号</th>';
	html += '<th style="width: 9%;">柜子类型</th>';
	html += '<th style="width: 9%;">是否占用</th>';
	html += '<th style="width: 9%;">占用时间</th>';
	html += '<th style="width: 9%;">占用人</th>';
	html += '</tr>';
	html += '</thead><tbody>' + tbody + '</tbody></table></div>';
	return html;
}

// 初始化得到数据，根据用户操作
function getBrandStoryInfo() {
	if (operate != 'add' && id != '') {

		$.ajax({
					"type" : "post",
					"data" : {
						"tgId" : id
					},
					"async" : false,
					"url" : Global.webRoot + "/biz/bizView.json",
					"dataType" : "json",
					"success" : function(json) {
						businessinfo = json.businessinfo;
						$("input[name='weixin_name']").val(businessinfo.wname);
						$("input[name='creator_name']")
								.val(businessinfo.creator_name);
						$("input[name='user_name']")
								.val(businessinfo.user_name);
						$("input[name='profession']")
								.val(businessinfo.profession);
						if (businessinfo.sex == '男') {
							$("#sex_male").attr("checked", true);
						} else {
							$("#sex_female").attr("checked", true);
						}
						$("#desc").html(businessinfo.desc);
						$("input[name='phone']").val(businessinfo.phone);
					}
				});
	}

	if (operate == 'view') {
		$("input,textarea,select,.ui-multiselect").attr("disabled", true);
		$("#btnCancel").attr("disabled", false);

	}
}

function hideMenu() {
	$("#actionMenu").css({
				"visibility" : "hidden"
			});
}

function showMenu(x, y,id) {
	$("#actionMenu").css({
				"visibility" : "visible",
				"top" : y + "px",
				"left" : x + "px"
			});
   $("#actionMenu").data('id',id);
}

function onBodyMouseDown(event) {
	var $dom = $(event.target);
	if (event.target.nodeName == 'TD') {
		var x = $dom.offset().left;
		var y = $dom.offset().top;
		var id = $dom.closest('tr').find('td:eq(0)').text();
		showMenu(x, y,id);
	} else if (event.target.nodeName == 'LI') {
		return;
	} else {
		hideMenu();
	}
}

function editBox() {
       
}
