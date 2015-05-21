$(function() {

	// 筛选按钮点击事件，url后面是传递的参数给handler处理
	// bind(type,data,event)其中type可以是click,submit等，data作为event.data属性值传递给事件对象的额外数据对象
	$("#test1").bind("click", {
		e : 'test1'
	}, handler);

	selectAllOrNo();//全选/全不选
	
	defalutTime();//设置默认时间
	loadTables();
	
	
});

function changeStart(){
	table.fnDraw();//刷新表
}

function changeEnd(){
	table.fnDraw();
}
/*******************************************************************************
 * 页面加载时调动
 */
var table;
function loadTables() {
	
	table = $("#table1").myDataTable({
		"sAjaxSource" : Global.webRoot + "/recordsList.json",
		"paramSelector" : '#startDate,#endDate,#userSec,#optSec,#resultSec',
		"aoColumns" : [ {//1
			"sName" : "id",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[0];
			}
		}, {//2
			"sName" : "menu",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[1];
			}
		}, {//3
			"sName" : "nickName",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[2];
			}
		}, {//4
			"sName" : "opt",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[3];
			}
		}, {//5
			"sName" : "optTime",
			"bSortable" : false,
			"fnRender" : function(obj) {

				return obj.aData[4];
			}
		}, {//6
			"sName" : "optResult",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[5];
			}
		}, {//7
			"sName" : "optNumber",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[6];
			}
		}, {//8
			"sName" : "optName",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[7];
			}
		}, {//9
			"sName" : "beforeOpt",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[8];
			}
		}, {//10
			"sName" : "afterOpt",
			"bSortable" : false,
			"fnRender" : function(obj) {
				return obj.aData[9];
			}
		} ]
	    });
}

/***
 * 全选，全不选
 */
function selectAllOrNo(){

	// 点击全选事件
	$("#userAll").click(function() {

		// alert($(this).nextAll(".check-border").text());
		var $user = $("#user");
		$user.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked", true);
			});
		});
		
		$(this).removeClass("select");
		 $(this).prev().addClass("select");
	});//end #userALl click function

	// 点击全不选事件
	$("#userNo").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $user = $("#user");
		$user.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});

		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});//end #userNo click function

	

	// 点击全选事件
	$("#optAll").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $opt = $("#opt");	
		$opt.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",true);
			});
		});

		 $(this).removeClass("select");
		 $(this).prev().addClass("select");
	});

	// 点击全不选事件
	$("#optNo").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $opt = $("#opt");
		$opt.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});

		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});

	// 点击全选事件
	$("#resAll").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $res = $("#result");
		$res.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",true);
			});
		});
		 $(this).removeClass("select");
		 $(this).prev().addClass("select");
	});

	// 点击全不选事件
	$("#resNo").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $res = $("#result");
		$res.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});
		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});
}//end function

function defalutTime(){
	Date.prototype.format =function(format)
	{
		var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
		}
		if(/(y+)/.test(format))
			format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
		for(var k in o)if(new RegExp("("+ k +")").test(format))
		       format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
		return format;
	}
	var defaultTime = new Date().format('yyyy-MM-dd');
	var begintime = $("#startDate").val();
	if(begintime!=null&&begintime!=''){
		$("#startDate").val(begintime);
	}else{
		$("#startDate").val(defaultTime);
	}
	var endtime = $("#endDate").val();
	if(endtime!=null&&endtime!=''){
		$("#endDate").val(endtime);
	}else{
		$("#endDate").val(defaultTime);
		
	}
	
	
}