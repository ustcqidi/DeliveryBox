$(document).ready(function() {

	$("#panelOpCode").dialog({
				autoOpen : false,
				modal : true,
				height : 300,
				width : 500,
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

	$("#btnQuery").bind("click", function() {
				ruleTable.fnDraw();
				return false;
			});
	$("#btnAdd").bind("click", function() {
		         chearForm();
		          $("#id").val('')
		          $(".ui-dialog-buttonset button:eq(0)").show();
				$("#panelOpCode").dialog("open");
			});

	var ruleTable = $('.table').myDataTable({
		"sAjaxSource" : './userlist.json',
		"paramSelector" : '#userName',
		"aoColumns" : [{
					"sTitle" : "编号",
					"bSortable" : true
				}, {
					"sTitle" : "名称",
					"bSortable" : true
				}, {
					"sTitle" : "描述",
					"bSortable" : true
				},{
					"sTitle" : "密码重置",
					"bSortable" : false,
					"fnRender" : function(obj) {
						return "<a href='javascript:void(0);' onclick='resert(\""
								+ obj.aData[0] + "\")'>重置密码</a>";
					}
				},  {
					"sTitle" : "查看",
					"bSortable" : false,
					"fnRender" : function(obj) {
						return "<a href='javascript:void(0);' onclick='view(\""
								+ obj.aData[0] + "\")'>查看</a>";
					}
				}, {
					"sTitle" : "修改",
					"bSortable" : false,
					"fnRender" : function(obj) {
						return "<a href='javascript:void(0);' onclick='update(\""
								+ obj.aData[0] + "\")'>修改</a>";
					}
				}, {
					"sTitle" : "删除",
					"bSortable" : false,
					"fnRender" : function(obj) {
						return "<a href='javascript:void(0);' onclick='deleteInfo(\""
								+ obj.aData[0] + "\")'>删除</a>";
					}
				}]
	});

});

function view(id) {
	$.ajax({
				url : ctx + '/auth/getUserById.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					chearForm();
					$("#name").val(data.name);
                    $("#desc").val(data.des);
                    $("#panelOpCode input").attr("disabled",true);
                    $(".ui-dialog-buttonset button:eq(0)").hide();
                    $("#panelOpCode").dialog("open");
				}
			});
}

function update(id) {
	 $("#id").val(id)
	$.ajax({
				url : ctx + '/auth/getUserById.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					chearForm();
					$("#name").val(data.name);
                    $("#desc").val(data.des);
                     $(".ui-dialog-buttonset button:eq(0)").show();
                     $("#panelOpCode input").attr("disabled",false);
                    $("#panelOpCode").dialog("open");
                    
				}
			});
}


function save() {
	if($("#name").val()==''){
		alert('用户名不能为空');
		return false;
	}
	
	$.ajax({
				url : ctx + '/auth/saveOrUpdate.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : $("#id").val()
						},{
							name : 'name',
							value : $("#name").val()
						},{
							name : 'desc',
							value : $("#desc").val()
						}],
				success : function(data) {
					if(data == '0'){
					  alert("该用户名已经存在");
					}else if(data == '2'){
					  alert("admin用户无法新增")
					}else if(data == '1'){
					   $("#panelOpCode").dialog("close");
					   $("#btnQuery").click();
					}
                   
				}
			});
}

function deleteInfo(id) {
	myConfirm("确定删除该用户？","删除用户",function(){
		$.ajax({
				url : ctx + '/auth/deleteUserById.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					if(data=='0'){
					alert("admin用户无法删除");
					}else{
						alert("删除成功");
						$("#btnQuery").click();
					}
				}
			});
	})

}

function resert(id) {
	myConfirm("确定重置用户密码？","重置密码为初始密码123456",function(){
		$.ajax({
				url : ctx + '/auth/resertPasswd.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : id
						}],
				success : function(data) {
					if(data=='1'){
						alert("密码重置成功");
						$("#btnQuery").click();
					}
				}
			});
	})

}


function chearForm(){
    $("#name").val('');
     $("#desc").val('');
}