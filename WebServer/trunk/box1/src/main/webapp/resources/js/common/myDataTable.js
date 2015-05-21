//给Date类型增加格式化原型方法
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d+)\}/g, function(m, i) {
		return args[i];
	});
}

/**
 * DataTables刷新数据
 */
$.fn.dataTableExt.oApi.fnMultiFilter = function(oSettings, oData) {
	for ( var key in oData) {
		if (oData.hasOwnProperty(key)) {
			for ( var i = 0, iLen = oSettings.aoColumns.length; i < iLen; i++) {
				if (oSettings.aoColumns[i].sName == key) {
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

(function($){
	var methods = {
			init : function() {
				var text = $(this).text();
				var width = $(this).width();
				$(this).html("<span style='overflow:hidden;white-space:nowrap;'>"+text+"</span>");
				var span = $(this).children(":first-child");
				var start = 0, end = text.length;
				//二分法截短
				if(span.width() > width) {
					while((end - start) > 1) {
						var middle = Math.ceil((start + end) /2);
						var _text = text.slice(0,middle);
						span.html(_text + '<span class="ellipsis">&hellip;</span>');
						if(span.width() > width) {
							end = middle;
						}else {
							start = middle;
						}
					}
					var _text = text.slice(0,start);
					span.html(_text + '<span class="ellipsis">&hellip;</span>');
					span.attr('title',text);
				}
			}
	};
	
	$.fn.ellipsis = function (method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || ! method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' +  method + ' does not exist on jQuery.ellipsis');
		}
	};
})(jQuery);



/**
 * 扩展dataTables -> myDataTables 1.提供默认配置：布局、分页、汉化、服务端数据设置、表格长字符串截短
 * 2.扩展配置参数paramSelector 用于获取查询参数 3.使用示例(需要先引入jquery.dataTables.js):
 * 
 * var table = $("#_rule_table").myDataTable({ "sAjaxSource" : './rules',
 * //服务端数据请求url "paramSelector" : '#_business,#_policy,#_status', //查询参数
 * "aoColumns" : [...] //列配置不变 });
 */
(function ($) {
	var default_settings = {
			"bInfo": true,
			"bServerSide": true,
			"bPaginate": true,
			"bFilter":false,
			"bCut" : true,
			"aaSorting": [[0,'desc']],
			"bProcessing":false,
			"bLengthChange": true,
			"aLengthMenu": [[10, 20, 30], [10, 20, 30]],
			"sPaginationType": "full_numbers",
			"bStateSave": false,
			"sDom" : '<"top"f>r<"datatable-scroll"<"datatable-width-setter"t>><"datatable-bottom"lip>',
			"oLanguage" : {
				"sLengthMenu" : "<div style='margin-top:0px;height:30px;float:left;line-height:30px'>每页显示数量</div><div style='float:left;margin-left:7px;'>_MENU_</div>",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo" : "共_TOTAL_条记录/共_PAGENUM_页！",
				"sInfoEmpty" : "共 0 条数据/共 0 页！",
				"sInfoFiltered" : "(filtered from _MAX_ total records)",
				"sProcessing" : "加载中...",

				oPaginate : {
					sPrevious : "上一页",
					sNext : "下一页",
					sFirst : "首页",
					sLast : "尾页"
				}
			}
	};
	var methods = {
		"init": function (cfg) {
			var settings = $.extend(default_settings,cfg);
			if(settings['bServerSide'] && !settings.fnServerData && settings.paramSelector) {
				settings.fnServerData = function(sSource, aoData, fnCallback) {
	    		$(settings.paramSelector).each(function(){
	    			var name = $(this).attr("name");
	    			var value = $(this).val();
	    			name = name ? name : '';
	    			value = value ? value : '';
	    			aoData.push({
	    				name : name,
	    				value : value
	    			});
	    		});
					$.ajax({
								"type" : "post",
								"url" : sSource,
								"data" : aoData,
								"success" : function(json) {
									fnCallback(json);
								},
								"dataType" : "json",
								"cache" : false,
								"error" : function(xhr, error, thrown) {
									if (error == "parsererror") {
										alert("DataTables warning: JSON data from server could not be parsed. "
												+ "This is caused by a JSON formatting error.");
									}
								}
							});
				};
			}
			if(!settings['bServerSide']){
				settings.fnServerData = function(sSource, aoData, fnCallback) {};
			}
			if(settings['aaData']){
				$.each(settings['aaData'],function(i,row){
					if(row){
						$.each(row,function(j,data){
							if(data==undefined || data==null) {
								settings['aaData'][i][j]='';
							}
						});
					}
				});
			}
			if(settings['bCut'] && ! settings["fnDrawCallback"]){
				settings["fnDrawCallback"] = function( oSettings ) {
					var cutSel = settings["sCutSelector"];
					if(!cutSel) {
						$(this[0]).find('tbody > tr > td:not(:has("*"))').each(function(){
							$(this).ellipsis();
				    });
					}else {
						$(this[0]).find(cutSel).each(function(){
							$(this).ellipsis();
				    });
					}
		    }
			}
			return this.dataTable(settings);
		}
	};

	$.fn.myDataTable = function (method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || ! method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' +  method + ' does not exist on jQuery.myDataTable');
		}
	};
})(jQuery);