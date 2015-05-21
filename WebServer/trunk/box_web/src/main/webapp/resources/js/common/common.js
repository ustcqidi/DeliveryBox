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

String.prototype.replaceAll = function stringReplaceAll(AFindText, ARepText) {
	  raRegExp = new RegExp(AFindText, "g");
	  return this.replace(raRegExp, ARepText);
	};

function iModal(cfg) {
	var modal = $("<div class='modal hide fade'></div>");
	if (cfg.width) {
		modal.css("width", cfg.width);
	}
	var header = $("<div class='modal-header' style='cursor:move;'></div>");
	header
			.append('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>');
	header.append($('<h3></h3>').text(cfg.title));
	var body = $("<div class='modal-body'></div>");
	body.html(cfg.content);
	var footer = $("<div class='modal-footer'></div>");

	modal.append(header);
	modal.append(body);
	modal.append(footer);
	$('body').append(modal);
	modal.draggable({
		handle : ".modal-header",
		cursor : 'move'
	});
	modal.on('hidden', function() {
		modal.remove();
	});
	modal.modal('show');
	return modal;
}
function myConfirm(title, content, callback) {

	// clear comfirm dialog which already exists
	$("#dialog-myconfirm").remove();

	if (title == '') {
		title = "确认对话框";
	}

	// create comfirm dialog
	var html = "<div id='dialog-myconfirm' style='z-index:2000' title='"
			+ title + "'>";
	html += "<p><span class='ui-icon ui-icon-alert' style='float: left; margin: 0 7px 20px 0;'></span>"
			+ content + "</p>";
	html += "</div>";
	$('body').append(html);

	var result = false;

	// Dialog message
	$("#dialog-myconfirm").dialog({
		autoOpen : false,
		modal : true,
		// position:{ my: "center", at: "center", of: window },
		buttons : {
			确定 : function() {
				callback();
				$(this).dialog("close");
			},
			取消 : function() {
				$(this).dialog("close");
			}
		}
	});
	$('#dialog-myconfirm').dialog('open');
}

function iConfirm(cfg) {
	var modal = iModal(cfg);
	modal.find(".modal-footer").append(
			'<a href="#" class="btn" data-dismiss="modal">取消</a>');
	modal.find(".modal-footer").append(
			'<a href="#" class="btn btn-primary" data-dismiss="modal">确定</a>');
	modal.find("a:contains('确定')").click(function() {
		cfg.callback.call();
	});
}

function iAlert(cfg) {
	var modal = iModal(cfg);
	modal.find(".modal-footer").append(
			'<a href="#" class="btn btn-primary" data-dismiss="modal">关闭</a>');
}

$(function() {
	$(document).ajaxError(function(evt, req, opts, ex) {
		if (req.status === 0 || req.readyState === 0) {
			location.reload(true);
			return;
		}
		if (req.status === 404) {
			alert('','没有找到');
			return;
		}
//		try{
//			var result = $.parseJSON(req.responseText);
//			myalert('',result.msg);
//		}catch(e){
//			myalert('', '系统内部错误');
//		}
	});
});


/**
 * 扩展dataTables -> myDataTables 1.提供默认配置：布局、分页、汉化、服务端数据设置、表格长字符串截短
 * 2.扩展配置参数paramSelector 用于获取查询参数 3.使用示例(需要先引入jquery.dataTables.js):
 * 
 * var table = $("#_rule_table").myDataTable({ "sAjaxSource" : './rules',
 * //服务端数据请求url "paramSelector" : '#_business,#_policy,#_status', //查询参数
 * "aoColumns" : [...] //列配置不变 });
 */
(function($) {

    var methods = {
        "init" : function(cfg) {
            var default_settings = {
                "bInfo" : true,
                "bServerSide" : true,
                "bPaginate" : true,
                "bFilter" : false,
                "aaSorting" : [ [ 0, 'desc' ] ],
                "bProcessing" : false,
                "bLengthChange" : true,
                "aLengthMenu" : [[10, 20, 50], [10, 20, 50]],
                "sPaginationType" : "full_numbers",
                "bStateSave" : true,
                "bAutoWidth" : false,
                "sDom" : '<"top"f>r<"datatable-scroll"t><"datatable-bottom"lip>',
                "oLanguage" : {
                    "sLengthMenu" : "<div style='margin-top:0px;height:30px;float:left;line-height:30px'>每页显示数量</div><div style='float:left;margin-left:7px;'>_MENU_</div>",
                    "sZeroRecords" : "抱歉， 没有找到",
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
                },
                
               
            };
            var settings = $.extend(default_settings, cfg);
            if (settings['bServerSide'] && !settings.fnServerData
                    && settings.paramSelector) {
                settings.fnServerData = function(sSource, aoData, fnCallback) {
                    $(settings.paramSelector).each(function() {
                        var name = $(this).attr("name");
                        var value = $(this).val();
                        name = name ? name : '';
                        if($(this).attr("type")=="radio"){
                            value = $("[name='"+name+"']:checked").val();
                        }else{
                            value = value ? value : '';
                        }
                        aoData.push({
                            name : name,
                            value : value
                        });
                    });
               
                    if(settings.myParams){
                   	 $.each(settings.myParams,function(i,a){
                   		 aoData.push(a);
                   	 });
                   }
                    
                    $.ajax({
                                "type" : "post",
                                "url" : sSource,
                                "data" : aoData,
                                maskTarget : $(this).parents('.dataTables_wrapper')[0],
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
            if (!settings['bServerSide']) {
                settings.fnServerData = function(sSource, aoData, fnCallback) {
                };
            }
            if (settings['aaData']) {
                $.each(settings['aaData'], function(i, row) {
                    if (row) {
                        $.each(row, function(j, data) {
                            if (data == undefined || data == null) {
                                settings['aaData'][i][j] = '';
                            }
                        });
                    }
                });
            }
            return this.dataTable(settings);
        }
    };

    $.fn.myDataTable = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments,
                    1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.myDataTable');
        }
    };
})(jQuery);


(function($) {
	$.fn.ellipsis = function() {

		this.css({
			'overflow' : 'hidden',
			'text-overflow' : 'ellipsis',
			'white-space' : 'nowrap'
		});

		this.bind('mouseenter', function() {
			var $this = $(this);
			if (this.offsetWidth < this.scrollWidth) {
				$this.attr('title', $this.text());
			}
		});
	};
})(jQuery);

$( document ).ajaxStart(function() {
	ajaxWait();
 });

 $(document).ajaxStop(function(){
	 	$("#ajaxWait").remove();
 });

 function ajaxWait(){
	 if($("#ajaxWait").length==0){			
		 $('body').append('<div id="ajaxWait" style="position:fixed;top: 50%;left: 50%;z-index: 1110;" ><img src="'+ctx+'/resources/images/load.gif" /></div>');		
	 }		 
 }