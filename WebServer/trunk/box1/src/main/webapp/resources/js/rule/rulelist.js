$.fn.dataTableExt.oApi.fnMultiFilter = function(oSettings, oData) {
	for ( var key in oData) {
		if (oData.hasOwnProperty(key)) {
			for ( var i = 0, iLen = oSettings.aoColumns.length; i < iLen; i++) {
				if (oSettings.aoColumns[i].sTitle == key) {
					/* Add single column filter */
					oSettings.aoPreSearchCols[i].sSearch = oData[key];
					break;
				}
			}
		}
	}
	// oSettings._iDisplayStart = 0;
	oSettings._iDisplayStart = oData["pageIndex"] ? (oData["pageIndex"] - 1)
			* oSettings._iDisplayLength : 0;
	this.oApi._fnDraw(oSettings);
};

$.fn.dataTableExt.oApi.fnReloadAjax = function(oSettings) {
	// oSettings.sAjaxSource = sNewSource;
	this.fnClearTable(this);
	this.oApi._fnProcessingDisplay(oSettings, true);
	var that = this;

	$.getJSON(oSettings.sAjaxSource, null, function(json) {
		/* Got the data - add it to the table */
		for ( var i = 0; i < json.aaData.length; i++) {
			that.oApi._fnAddData(oSettings, json.aaData[i]);
		}

		oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
		that.fnDraw(that);
		that.oApi._fnProcessingDisplay(oSettings, false);
	});
};



var businesses = {};
$.ajax({
	url : './listBusiType',
	type : 'POST',
	timeout : 20000,
	cache : false,
	async : false,
	dataType : "json",
	success : function(data) {
		$.each(data, function(i, a) {
			businesses[a.Fid] = a;
		});
	}
});

var table;






$(document)
		.ready(
				function() {
					$.each(businesses, function(i, a) {
						$('#_business').append(
								"<option value='" + a.Fid + "'>" + a.Name + "</option>");
					});
					$.each(dataDict['policy'], function(i, a) {
						$('#_policy').append(
								"<option value='" + i + "'>" + a + "</option>");
					});
					$.each(dataDict['status'], function(i, a) {
						$('#_status').append(
								"<option value='" + i + "'>" + a + "</option>");
					});
					
					$("#_business").multiselect({
						checkAllText : "全选",
						uncheckAllText : "全部取消",
						noneSelectedText : "请选择BFocus名称",
						selectedText : "已选择#项",
						minWidth: 120,
						selectedList : 4
					});
					
					table = $('#_rule_table')
							.myDataTable(
									{
										"sAjaxSource" : './rules',
										"paramSelector" : '#_business,#_policy,#_status',
										"bStateSave": true,
										bCut : false,
										"aoColumns" : [{
											"bSortable" : true,
											"sName" : "code"
										}, {
											"bSortable" : false,
											"sName" : "businessid",
											"fnRender" : function(obj) {
												var busi = businesses[obj.aData[1]];
												return busi ? busi.Name : '';
											}
										}, {
											"bSortable" : false
											//产品
											//,sClass : 'cut'
										}, {
											"bSortable" : false
											//平台
											//,sClass : 'cut'
										}, {
											"bSortable" : false
											//版本
											,sClass : 'cut'
											,"fnRender" : function(obj) {
												var text = obj.aData[obj.iDataColumn].text;
												var title = obj.aData[obj.iDataColumn].title;
												if(title) {
													return "<span title='{1}'>{0}</span>".format(text,title);
												}
												return text;
											}
										}, {
											"bSortable" : false,
											"fnRender" : function(obj) {
												var name = '';
												$.each(obj.aData[5],function(i,a){
													var p = dataDict['operator'][a];
													if(name.length>0){
														name += p?','+p:'';
													}else {
														name += p?p:'';
													}
												});
												
												return name;
											}
										}, {
											"bSortable" : false
											//用户地域
											,sClass : 'cut'
											,"fnRender" : function(obj) {
												var text = obj.aData[obj.iDataColumn].text;
												var title = obj.aData[obj.iDataColumn].title;
												return "<span title='{1}'>{0}</span>".format(text,title);
											}
										}, {
											"bSortable" : false
											//号码地域
											,sClass : 'cut'
											,"fnRender" : function(obj) {
												var text = obj.aData[obj.iDataColumn].text;
												var title = obj.aData[obj.iDataColumn].title;
												return "<span title='{1}'>{0}</span>".format(text,title);
											}
										},{
											"bSortable" : true,
											"sName" : "policy",
											"fnRender" : function(obj) {
												var p = dataDict['policy'][obj.aData[8]];
												return p ? p : '';
											}
										},{
											"bSortable" : false
										}, {
											"bSortable" : true,
											"sName" : "status",
											"fnRender" : function(obj) {
												var p = dataDict['status'][obj.aData[10]];
												return p ? p : '';
											}
										}, {
											"bSortable" : false,
											"fnRender" : function(obj) {
												if(obj.aData[10]!=dataDict['status'][2]&&isCanXg()){
													return "<a href='javascript:window.location.href=\"./ruleEdit?id="+obj.aData[11]+"\"'>修改</a>";
												}else {
													return '';
												}
											}
										}, {
											"bSortable" : false,
											"fnRender" : function(obj) {
												if(obj.aData[10]!=dataDict['status'][2]&&isCanSc()){
													return "<a href='javascript:deleteRule("+obj.aData[12]+",\""+obj.aData[10]+"\")'>删除</a>";
												}else {
													return '';
												}
											}
										}, {
											"bSortable" : false,
											"fnRender" : function(obj) {
												if(obj.aData[10]==dataDict['status'][0]&&isCanBqy()){
													return "<a href='javascript:unactive(\""+obj.aData[13]+"\")'>"+dataDict['status'][1]+"</a>";
												}else {
													return '';
												}
											}
										}, {
											"bSortable" : false,
											"fnRender" : function(obj) {
												if(obj.aData[10]==dataDict['status'][1]&&isCanQy()){
													return "<a href='javascript:active(\""+obj.aData[14]+"\")'>"+dataDict['status'][0]+"</a>";
												}else {
													return '';
												}
											}
										}, {
											"bSortable" : false,
											"fnRender" : function(obj) {
												if(obj.aData[10]!=dataDict['status'][2]&&isCanKzgz()){
													return "<a href='./ruleExtend?id="+obj.aData[15]+"'>扩展规则</a>";
												}else {
													return '';
												}
											}
										} ]
									});
				});



function deleteRule(id,status){
	if(dataDict['status'][0]==status){
		myalert("","请将该笔规则停用后删除");
		return;
	}
	myConfirm("","请确认是否删除该笔规则",function(){
		$.ajax({
			url:'./delete',
			type:'POST',
			data:{id:id},
			timeout : 20000,
			cache : false,
			dataType : "json",
			success : function(data) {
				table.fnDraw(false);
			}
		});
	});
}

function unactive(id) {
	$.ajax({
		url:'./unactive',
		type:'POST',
		data:{id:id},
		timeout : 20000,
		cache : false,
		dataType : "json",
		success : function(data) {
			if(data.success){
				table.fnDraw(false);
				myalert("","停用成功");
			}else {
				myalert("",data.msg);
			}
		}
	});
}
function active(id) {
	$.ajax({
		url:'./active',
		type:'POST',
		data:{id:id},
		timeout : 20000,
		cache : false,
		dataType : "json",
		success : function(data) {
			if(data.success){
				table.fnDraw(false);
				myalert("","启用成功");
			}else {
				myalert("",data.msg);
			}
		}
	});
}