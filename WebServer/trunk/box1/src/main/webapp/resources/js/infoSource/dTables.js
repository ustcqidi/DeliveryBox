
var oTable;
$(document).ready(function() {
	 $.ajax({
	  		"type":"get",
	  		"url":Global.webRoot+"/infoSourceAll.json",
	  		"dataType": "json",
	  		"async":false,
	  		"success":function(json){
	  			var v_html_name = "";
	  			var v_html_code = "";
	  			$.each(json,function(i,n){
	  				v_html_name = v_html_name+"<option value='"+n[0]+"'>"+n[1]+"</option>";
	  			//	v_html_code = v_html_code+"<option value='"+n[0]+"'>"+n[2]+"</option>";
	  			});
	  			
	  			$("#xyName_select").html(v_html_name);
	  		//	$("#xyCode_select").html(v_html_code);
	  			$("#xyName_select").multiselect();
				$("#xyName_select").multiselect("refresh");
			//	$("#xyCode_select").multiselect();
			//	$("#xyCode_select").multiselect("refresh");
	  		}
	  	});
	
	$("#xyName_select").multiselect({
			checkAllText : "全选",
			uncheckAllText : "全部取消",
			noneSelectedText : "请选择信源名称",
			selectedText : "已选择#项",
			minWidth: 180,
			selectedList : 1
		});
	
	//$("#xyCode_select").multiselect({
	//	checkAllText : "全选",
	//	uncheckAllText : "全部取消",
	//	noneSelectedText : "请选择信源编码",
	//	selectedText : "已选择#项",
	//	minWidth: 180,
	//	selectedList : 1
	//});
	
	oTable = $('.table').myDataTable({
			"sAjaxSource" : './infoSourceList.json',
			"paramSelector" : '#xyName_select,#status',
			"aaSorting": [[2,'desc'], [3,'desc']],
			"aoColumns" : [
							{
								"sName" : "number",
								"bSortable" : false,
								"fnRender" : function(obj) {
								return obj.aData[0];
								}
							},
							{
								"sName" : "busFocus",
								"bSortable" : false,
								"fnRender" : function(obj) {
									return obj.aData[1];
								}
							},
							{
								"sName" : "name",
								"bSortable" : true,
								"fnRender" : function(obj) {
									return obj.aData[2];
								}
							},
							{
								sName : "code",
								bSortable : true,
								fnRender : function(obj) {
									return obj.aData[3];
								}
							},
							{
								"sName" : "status",
								"bSortable" : true,
								"fnRender" : function(obj) {
									
									if(obj.aData[4]==0){
										return "启用";
									}else if((obj.aData[4]==1)){
										return "不启用";
									}else{
										return "已删除";
									};
								}
							},
							{
								"sName" : "updateIs",
								"bSortable" : false,
								"fnRender" : function(obj) {
									if(obj.aData[4]!="已删除"&&isCanXg()){
									return "<a href='#' onclick='updateIs("+obj.aData[5]+")'>修改</a>";
									}
									return "";
								}
							},
							{
								"sName" : "deleteIs",
								"bSortable" : false,
								"fnRender" : function(obj) {
									if(obj.aData[4]!="已删除"&&isCanSc()){
									return "<a href='#' onclick='deleteIs("+obj.aData[6]+")'>删除</a>";
									}
									return "";
								}
							},
							{
								"sName" : "noStartUseIs",
								"bSortable" : false,
								"fnRender" : function(obj) {
									
									if(obj.aData[4]=="启用"&&isCanBqy()){
									return "<a href='#' onclick='noStartUseIs("+obj.aData[7]+")'>不启用</a>";
									}else{
										return "";
									}
								}
							},
							{
								"sName" : "startUseIs",
								"bSortable" : false,
								"fnRender" : function(obj) {
									if(obj.aData[4]=="不启用"&&isCanQy()){
									return "<a href='#' onclick='startUseIs("+obj.aData[8]+")'>启用</a>";
									}else{
										return "";
									}
									}
							}
					],
					"fnDrawCallback" : function(oSettings) {
						$(this.context).find('tr td,tr th').each(function() {
							$(this).bind('mouseenter', function() {
								var $this = $(this);
								if (this.offsetWidth < this.scrollWidth) {
									$this.attr('title', $this.text());
								}
							});
						});
					}
	         });
	   
	          
			
				  $("#btnQuery").click(function(){
			    	 
			    	   oTable.fnDraw();
			    	  
			       });
				});


function startUseIs(id){
	$.ajax({
		"type" : "post",
		"url" : Global.webRoot+"/infoSourceDealStartUse.json",
		"data" : {id:id,isUse:0},
		"success" : function(json) {
			if(json=="1"){
				myalert("","启用成功");
				oTable.fnDraw();
			}
		},
		"dataType" : "text"
		
	});
}
function deleteIs(id){
	$.ajax({
		"type" : "post",
		"url" : Global.webRoot+"/infoSourceCheckStatus.json" ,
		"data" : {id:id},
		"success" : function(json) {
			if(json==0){
				myalert("","请将该笔信源停用后删除");
				return;
			}else{
				myConfirm("", "请确认是否删除该信源",function(){
					$.ajax({
						"type" : "post",
						"async":false,
						"url" : Global.webRoot+"/infoSourceDeleteIs.json" ,
						"data" : {id:id},
						"success" : function(json) {
							if(json=="1"){
								myalert("","删除成功");
								oTable.fnDraw();
							}
						},
						"dataType" : "text"
										});
				});
			}
		},
		"dataType" : "text"
		
	});
}
function updateIs(id){
	window.location.href=Global.webRoot+"/infoSourceEdit.auth?id="+id;
}
function noStartUseIs(id){
	$.ajax({
		"type" : "get",
		"url" : Global.webRoot+"/infoSourceCheckRules.json" ,
		"data" : {id:id},
		"success" : function(json) {
			if(json==0){
				$.ajax({
					"type" : "get",
					"url" : Global.webRoot+"/infoSourceCheckIsLast.json",
					"data" : {id:id,isUse:1},
					"success" : function(json) {
						if(json=="1"){
							myalert("","该记录是该BFocus唯一启用的信源，不可停用");
							return false;
						}else{
							$.ajax({
								"type" : "get",
								"url" : Global.webRoot+"/infoSourceDealStartUse.json",
								"data" : {id:id,isUse:1},
								"success" : function(json) {
									if(json=="1"){
										myalert("","停用成功");
										oTable.fnDraw();
									}
								},
								"dataType" : "text"
								
							});
						}
					},
					"dataType" : "text"
					
				});
				
				
			
			}else{
				myalert("","请确认该信源所归属规则都已停用");
				return;
			}
		},
		"dataType" : "text"
		
	});
}
