var table;
$(function() {
	
	initPage();
	initDialog() ;
	$("#import").click(function(){
	  $("#modal_channel").dialog("open");
	});
	$("#getPhone").click(function(){
		getPhone();
	  $("#selectPhone").dialog("open");
	})

   	$("#size").click(function(){
		   getPhone();
		})
	
	$("#_query").on('click', function() {

		table.fnDraw();
	});
	
	$("#file_upload").uploadify({
				swf : ctx + "/resources/third-party/uploadify/uploadify.swf",
				cancelImg : ctx + "/resources/third-party/uploadify/uploadify-cancel.png",
				uploader : Global.webRoot+'/phone/upload.do',
				formData : {
					'modelname' : 'other',
					'objectid' : '1',
					'albumid' : '0'
				},
				simUploadLimit : 1,
				multi : false,
				fileTypeExts : '*.txt;*.TXT',
				fileObjName : "file",
				buttonText : '选择文件',
				removeCompleted : true,
				onUploadSuccess : function(file, data, response) {
					if (data == "110") {
						alert( "文件格式不正确！");
						return false;
					}
					if (data == "111") {
						alert( "号码格式不正确！");
						$("#" + file.id).remove();
						$('#file_upload').uploadify('settings',
								'uploadLimit', ++uploadLimit);
						return false;
					}
					if(data = '1'){
						alert( "上传成功！");
						$("#_query").click();
					}
				},
				onUploadError : function(file, errorCode, errorMsg, errorString) {
					alert( '文件 ' + file.name + ' 上传失败: ' + errorString);
				}
			});
});



function initDialog() {
	$("#modal_channel").dialog({
		        autoOpen : false,
				modal : true,
				height : 300,
				width : 500,
				resizable : false,
				buttons : {
					"取消" : function() {
						$(this).dialog("close");
					}
				}

	});
	
	$('#selectPhone').dialog({
		autoOpen : false,
		resizable : false,
		bgiframe : true,
		height : 422,
		width : 400,
		modal : true,
		buttons : {
			"保存" : function() {
				savephone();
			},
			"关闭" : function() {
				$(this).dialog("close");
			}
		}
	});
}

function selectAll() {
	$("input[name='phone']").each(function() {
		$(this).prop("checked", 'checked');
	});
}


function savephone(){
	var listno =new Array();
	$("#phoneArea div span").each(function(){
			listno.push($(this).text());
	});
	$.ajax({
		url : ctx + '/phone/putPhone.do',
		dataType : 'json',
		type:'POST',
		data : [{
					name : 'listno',
					value : listno
				}],
		success : function(data) {
			if(data =='1'){
				table.fnDraw();
				$("#selectPhone").dialog("close");
			}
		}
	});
}

function initPage() {

	table = $("table")
			.myDataTable(
					{
						"sAjaxSource" : Global.webRoot
								+ '/phone/list.json',
						"paramSelector" : '#number,#creator,#status',
						"aoColumns" : [
								{
									"sTitle" : "编号",
									"sName" : "id"
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
									"sTitle" : "删除",
									"bSortable" : false,
									"fnRender" : function(obj) {
										if(loginName=='admin'){
										return "<a href='javascript:void(0);' onclick='deleteInfo(\""
										+ obj.aData[0] + "\")'>删除</a>";
										}else{
										  if(obj.aData[3] == '否'){
										 return "<a href='javascript:void(0);' onclick='blackInfo(\""
										+ obj.aData[0] + "\")'>加入黑名单</a>";
										  }else{
										   return "<a href='javascript:void(0);' onclick='blackInfo(\""
										+ obj.aData[0] + "\")'>加入白名单</a>";
										  
										  }
										}
									}
								} ]
					});
}

function deleteInfo(id) {
	myConfirm("确定删除该号码？","删除用户",function(){
		$.ajax({
				url : ctx + '/phone/deletePhone.json',
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
						table.fnDraw();
					}
				}
			});
	})
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

function update(id,status) {
	myConfirm("确定删除该号码？","删除用户",function(){
		$.ajax({
				url : ctx + '/phone/updatePhone.json',
				dataType : 'json',
				type:'POST',
				data : [{
							name : 'id',
							value : id
						},{
							name : 'status',
							value : status
						}],
				success : function(data) {
					if(data!='1'){
					alert("操作失败");
					}else{
						alert("删除成功");
						table.fnDraw();
					}
				}
			});
	})
}

function getPhone() {
	$("#downFromNo").val('');
	$.ajax({
		type : "POST",
		data : [ {
			name : 'size',
			value : $('#size').val()
		} ],
		url : ctx + '/phone/geSelectPhone.do',
		success : function(result) {
			var html = "";
			$.each(result, function(i, a) {
				html += '<div><span>'
						+ a
						+ '</span></div>';
			});
			$("#phoneArea").empty();
		    $("#phoneArea").append(html);
		}
	});
	
}

