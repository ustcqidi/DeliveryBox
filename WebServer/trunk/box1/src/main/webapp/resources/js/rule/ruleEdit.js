var businesses = {};

var products = {};

var platforms = {};

var versions = {};

var infosources = {};

var areas = {};

var provinces = {};

var countryArea = {};

$.ajax({
	url : './listDatas',
	type : 'POST',
	timeout : 20000,
	cache : false,
	async : false,
	dataType : "json",
	success : function(data) {
			businesses = data["businesses"];
			products = data["products"];
			versions = data["versions"];
			platforms = data["platforms"];
			infosources = data["infosources"];
			areas = data["areas"];
			provinces = data["provinces"];
			countryArea = data["countryArea"];
	}
});

function clearInfosource() {
	$("#_add_form #_infosource").val('');
	$("#_add_form #__infosource").text('');
	$("#_add_form #_priority").val('');
	$("#_add_form #_weight").val('');
}

function onProvinceChange(callback){
	var pid = $("#_province").val();
	$.ajax({
		url : './listChildArea',
		type : 'POST',
		data : [{name:'pids',value:pid}],
		dataType : 'json',
		success : function(data) {
			$("#_city").empty();
			$.each(data, function(i, a) {
				if($.inArray(a.aiid,$.map(rule_areas,function(n){return n.id}))<0) {
					$('#_city').append(
							"<option value='" + a.aiid + "' parent='"+a.parentId+"'>" + a.name + "</option>");
				}else {
					$('#_city').append(
							"<option selected='selected' value='" + a.aiid + "' parent='"+a.parentId+"'>" + a.name + "</option>");
				}
			});
			$("#_city").multiselect('refresh');
			renderArea();
			if(callback) {
				callback.call();
			}
		}
	});
}
function onPhoneProvinceChange(callback){
	var pid = $("#_phone_province").val();
	$.ajax({
		url : './listChildArea',
		type : 'POST',
		data : [{name:'pids',value:pid}],
		dataType : 'json',
		success : function(data) {
			$("#_phone_city").empty();
			$.each(data, function(i, a) {
				if($.inArray(a.aiid,$.map(rule_phone_areas,function(n){return n.id}))<0) {
					$('#_phone_city').append(
							"<option value='" + a.aiid + "' parent='"+a.parentId+"'>" + a.name + "</option>");
				}else {
					$('#_phone_city').append(
							"<option selected='selected' value='" + a.aiid + "' parent='"+a.parentId+"'>" + a.name + "</option>");
				}
			});
			$("#_phone_city").multiselect('refresh');
			renderPhoneArea();
			if(callback){
				callback.call();
			}
		}
	});
}

function onCountryAreaChange(){
	if($("#_country_area").prop("checked")){
		$("#_province,#_city").multiselect("disable");
	}else {
		$("#_province,#_city").multiselect("enable");
	}
	renderArea();
}

function onPhoneCountryAreaChange(){
	if($("#_phone_country_area").prop("checked")){
		$("#_phone_province,#_phone_city").multiselect("disable");
	}else {
		$("#_phone_province,#_phone_city").multiselect("enable");
	}
	renderPhoneArea();
}

function onCommonMutilselectChange(el){
	$(el).parent().find(".info:last").remove();
	var text = "";
	var boxs = $(el).multiselect("getChecked");
	$.each(boxs,function(i,a){
		text += $(a).parent().text() + ", ";
	});
	if(boxs.length>0) {
		text = text.substring(0,text.length -2);
	}
	var span = $("<span class='info' style='padding-left:10px;'></span>").text(text);
	$(el).parent().append(span);
	span.bind('mouseenter', function () {
    var $this = $(this);
    if (el.parentNode.offsetWidth < el.parentNode.scrollWidth){
    	$this.attr('title', $this.text()); 
    }
  });
}

function onVersionMutilselectChange(el){
	$(el).parent().find(".info:last").remove();
	var text = "";
	var boxs = $(el).multiselect("getChecked");
	$.each(boxs,function(i,a){
		text += $(a).parent().text() + ", ";
	});
	if(boxs.length>0) {
		text = text.substring(0,text.length -2);
	}
	var span = $("<span class='info' style='padding-left:10px;'></span>").text(text);
	$(el).parent().append(span);
	span.bind('mouseenter', function () {
    var $this = $(this);
    if (el.parentNode.parentNode.offsetWidth < el.parentNode.parentNode.scrollWidth){
    	$this.attr('title', $this.text()); 
    }
  });
}

function onBusiChange() {
	var busi = $("#_business").val();
	if(!$("#_id").val()) {
		$.ajax({
			url : './generateRuleCode',
			type : 'POST',
			data : {business : busi},
			success : function(data) {
				$("#_code").val(data);
			}
		});
	}
	$('#_infosource_table0 tr:gt(0)').remove();
	$('#_infosource_table1 tr:gt(0)').remove();
	$('#_infosource_table2 tr:gt(0)').remove();
	clearInfosource();
	
	$.each(infosources, function(i, a) {
		if(a.business_id != busi) {
			return;
		}
		if($.inArray(a.id,rule_infosources)<0) {
			$('#_infosource_table0').append(
					"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
							+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputPriority(event);'></td></tr>");
			$('#_infosource_table1').append(
					"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
					+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputWeight(event);'></td></tr>");
			$('#_infosource_table2').append(
					"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
					+ "</label></td></tr>");
		}else {
			var _priority = rule_priorities[a.id];
			var _weight = rule_weights[a.id];
			_priority = _priority==null?'':_priority;
			_weight = _weight==null?'':_weight;
			
			if(policy==0){
				$('#_infosource_table0').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' checked='checked' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' value='"+_priority+"' style='width:60px;' onkeypress='return inputPriority(event);'></td></tr>");
				$('#_infosource_table1').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputWeight(event);'></td></tr>");
				$('#_infosource_table2').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td></tr>");
			}else if(policy==1){
				$('#_infosource_table0').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputPriority(event);'></td></tr>");
				$('#_infosource_table1').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' checked='checked' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' value='"+_weight+"' style='width:60px;' onkeypress='return inputWeight(event);'></td></tr>");
				$('#_infosource_table2').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td></tr>");
			}else if(policy==2){
				$('#_infosource_table0').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputPriority(event);'></td></tr>");
				$('#_infosource_table1').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputWeight(event);'></td></tr>");
				$('#_infosource_table2').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' checked='checked' value='" + a.id + "'> " + a.name
						+ "</label></td></tr>");
			}else {
				$('#_infosource_table0').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' data-title='xxx' style='width:60px;' onkeypress='return inputPriority(event);'></td></tr>");
				$('#_infosource_table1').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td><td><input type='text' style='width:60px;' onkeypress='return inputWeight(event);'></td></tr>");
				$('#_infosource_table2').append(
						"<tr><td style='text-align:left'><label class='checkbox inline'><input type='checkbox' value='" + a.id + "'> " + a.name
						+ "</label></td></tr>");
			}
		}
	});
	$("#_infosource_table0 input[type='text']").tooltip({
		title : '输入整数',
		placement: "right",
    trigger: "manual"
	});
	
	$("#_infosource_table1 input[type='text']").tooltip({
		title : '输入数字[0 - 100]',
		placement: "right",
		trigger: "manual"
	});
}

function checkActiveRuleExist(){
	var ret = true;
	$.ajax({
		url : './checkActiveRuleExist',
		type : 'POST',
		timeout : 20000,
		data : $("#_add_form").formToArray(),
		cache : false,
		async : false,
		dataType : "json",
		success : function(data) {
				if(!data.success){
					ret = false;
					myalert("",data.msg);
				}
		}
	});
	return ret;
}

function renderArea() {
	$("#_area").parent().find(".info").remove();
	$("#_area").val("");
	var text = "";
	var val = "";
	var areas = [];
	if($("#_country_area").prop("checked")){
		areas.push($("<option value='"+$("#_country_area").val()+"'>"+$("#_country_area").parent().text()+"</option>"));
	}else {
		$("#_province option:selected").each(function(i,a){
			areas.push($(a));
		});
	  $("#_city option:selected").each(function(i,a){
	  	var vals = $.map(areas,function(n){
	  		return n.val();
	  	});
	  	var idx = $.inArray($(a).attr("parent"),vals);
	  	if(idx >=0) {
	  		areas.splice(idx,1);
	  	}
	  	areas.push($(a));
	  });
	}
	
	$.each(areas,function(i,a){
		var name = a.text();
		text += name + ", ";
		val += a.val() + ",";
	});
	if(areas.length > 0) {
		text = text.substring(0,text.length - 2);
		val = val.substring(0,val.length - 1);
	}
	
	var span = $("<span class='info' style='padding-left:10px;'></span>").text(text);
	$("#_area").parent().append(span);
	span.bind('mouseenter', function () {
    var $this = $(this);
    if (this.parentNode.offsetWidth < this.parentNode.scrollWidth){
    	$this.attr('title', $this.text()); 
    }
  });
	$("#_area").val(val);
}
function renderPhoneArea() {
	$("#_phone_area").parent().find(".info").remove();
	$("#_phone_area").val("");
	var text = "";	
	var val = "";
	var areas = [];
	if($("#_phone_country_area").prop("checked")){
		areas.push($("<option value='"+$("#_phone_country_area").val()+"'>"+$("#_country_area").parent().text()+"</option>"));
	}else {
		$("#_phone_province option:selected").each(function(i,a){
			areas.push($(a));
		});
	  $("#_phone_city option:selected").each(function(i,a){
	  	var vals = $.map(areas,function(n){
	  		return n.val();
	  	});
	  	var idx = $.inArray($(a).attr("parent"),vals);
	  	if(idx >=0) {
	  		areas.splice(idx,1);
	  	}
	  	areas.push($(a));
	  });
	}
  $.each(areas,function(i,a){
		var name = a.text();
		text += name + ", ";
		val += a.val() + ",";
	});
	if(areas.length > 0) {
		text = text.substring(0,text.length - 2);
		val = val.substring(0,val.length - 1);
	}
	var span = $("<span class='info' style='padding-left:10px;'></span>").text(text);
	$("#_phone_area").parent().append(span);
	span.bind('mouseenter', function () {
    var $this = $(this);
    if (this.parentNode.offsetWidth < this.parentNode.scrollWidth){
    	$this.attr('title', $this.text()); 
    }
  });
	$("#_phone_area").val(val);
}

function getHighVersion(){
	var start_version = $("#start_version").val();
	$("#end_version option[value!='']").remove();
	$.ajax({
		url : './listVersionById',
		type : 'POST',
		data : [{name:'id',value:start_version}],
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, a) {
				$('#end_version').append(
						"<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
			});
		}
	});	
}

function getLowVersion(){
	var end_version = $("#end_version").val();
	$("#start_version option[value!='']").remove();
	$.ajax({
		url : './listVersionById',
		type : 'POST',
		data : [{name:'id',value:end_version}],
		dataType : 'json',
		success : function(data) {
			$.each(data, function(i, a) {
				$('#start_version').append(
						"<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
			});
		}
	});	
}
function compareVersion(ver1,ver2){
	var version1 = ver1.replace(/[a-zA-Z]+/g, "");
	var version2 = ver2.replace(/[a-zA-Z]+/g, "");
	var verArr1 = version1.split('.');
	var verArr2 = version2.split('.');
	var minLength = Math.min(verArr1.length,verArr2.length);
	if (minLength == 0) {
        return 0;
    }
	 for (var i = 0; i < minLength; i++) {
     	var flag = 0;
     	if(i!=minLength-1){
     	flag = parseInt(verArr1[i]) - parseInt(verArr2[i]);
     	}else{
     		if(verArr1[i]>verArr2[i]){
     			return 1;
     		}else{
     			return -1;
     		};
     	}
         if (flag != 0) {
             return flag;
         }
     }
	
}

$(document).ready(
		function() {
			if(msg) {
				myalert("",msg);
			}
			//取消按钮
			$("#_btn_cancel").click(function(){
			     window.location.href=Global.webRoot+"/rule/rulelist?cancel=cancel";
			     return false;
			});
			
			
			$.validator.addMethod("atLeastOne", function(value, element) {
            var ret = $(element).multiselect("getChecked").length > 0;
            element = $(element).next();
            return ret;
			},"至少选择一项");
			
			$.validator.addMethod("version_validator", function(value, element) {
				var ret = true ;;
	             if($("#version_select").val()=='3'){
	            	 if($("#start_version").val()==''||$("#end_version").val()=='')
	            		 ret = false;
	             }else if($("#version_select").val()=='4'){
	            	  if($("#_version").multiselect("getChecked").length == 0){
	            		  ret =false;
	            	  } 
	             }else if($("#version_select").val()=='1'){
	            	 if($("#start_version").val()==''){
	            		 ret =false;
	            	 }
	             }
	             else if($("#version_select").val()=='2'){
	            	 if($("#end_version").val()==''){
	            		 ret =false;
	            	 }
	             }
	            return ret;
				},"请选择版本区间");
			
			$("#_add_form").validate({
				onfocusout: false,
			    onkeyup: false,
			    onclick: false,
			    focusInvalid: false,
				rules : {
					business : "required",
					policy : "required",
					version_select : {
						version_validator : true
					},
					operator : {
						atLeastOne : true
					},
					area : "required",
					phoneArea : "required",
					infosource : "required",
					description : "required"
				},
//				errorPlacement: function(error, element) {
//					var elText = element.parentsUntil("form",".control-group").find(".control-label").text();
//					myalert("",elText + " " + error.text());
//					return false;
//			  },
				showErrors : function(errorMap,errorList){
					if(errorList.length) {
						var err = errorList[0];
						var label = $(err.element).parentsUntil("form",".control-group").find(".control-label").text();
						myalert("",label + " " + err.message);
					}
				},
				ignore : 'hidden:not("select[multiple=\'multiple\'],#_infosource,#_area,#_phone_area")',
				submitHandler : function(form) {
					$("#_btn_save").attr("disabled","disabled");
					form.submit();
				}
			});
			
			$.each(businesses, function(i, a) {
				$('#_business').append(
						"<option value='" + a.Fid + "'>" + a.Name + "</option>");
			});
			$("#_business").change(function(){
				onBusiChange();
			});
			
			$("#end_version").change(function(){
				if($("#start_version").val()!=''&&$("#version_select").val()=='3'){
				//	var endVersion = $(this).val();
					var startVersion = $("#start_version").find('option:selected').text().split("-");
					var endVersion = $("#end_version").find('option:selected').text().split("-");
					if(startVersion[0]!=endVersion[0]|startVersion[1]!=endVersion[1]){
						$("#start_version").val("");
					}else if(compareVersion(startVersion[2],endVersion[2])>=0){
						myalert("","结束版本不能小于开始版本");
						$("#end_version").val("");
					}
				}
			});
			
		/*	$("#start_version").change(function(){
				if($("#end_version").val()!=''&&$("#version_select").val()=='3'){
					var startVersion = $("#start_version").find('option:selected').text().split("-");
					var endVersion = $("#end_version").find('option:selected').text().split("-");
					if(compareVersion(startVersion[2],endVersion[2])>=0){
						myalert("","开始版本不能大于结束版本");
						$("#start_version").val("");
					}
				}
			});*/
			
			if(business) {
				$('#_business').val(business);
			}
			$.each(dataDict['policy'], function(i, a) {
				$('#_policy').append(
						"<option value='" + i + "'>" + a + "</option>");
			});
			$.each(products, function(i, a) {
				$('#_product').append(
						"<option value='" + a.BizId + "'>" + a.BizCnName + "</option>");
			});
			$.each(platforms, function(i, a) {
				$('#_platform').append(
						"<option value='" + a.OSID + "'>" + a.OSName + "</option>");
			});
			$.each(versions, function(i, a) {
				if($.inArray(a.PctVerID,rule_versions)<0) {
				    $('#_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					$('#start_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					$('#end_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
				}else {
				    $('#_version').append("<option selected='selected' value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					/*$('#start_version').append("<option selected='selected' value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					$('#end_version').append("<option selected='selected' value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");*/
				}
						
			});
			
			
			$.each(dataDict['operator'], function(i, a) {
				if($.inArray(parseInt(i),rule_operators)<0) {
					$('#_operator').append(
							"<option value='" + i + "'>" + a + "</option>");
				}else {
					$('#_operator').append(
							"<option selected='selected' value='" + i + "'>" + a + "</option>");
				}
			});
			//初始化省
			$.each(provinces,function(i,a){
				var idxSelf = $.inArray(a.aiid,$.map(rule_areas,function(n){return n.id}));
				var idxParent = $.inArray(a.aiid,$.map(rule_areas,function(n){return n.pid}));
				if(idxSelf>=0 || idxParent>=0) {
					$("#_province").append("<option selected='selected' value='" + a.aiid + "'>" + a.name + "</option>");
				}else {
					$("#_province").append("<option value='" + a.aiid + "'>" + a.name + "</option>");
				}
				idxSelf = $.inArray(a.aiid,$.map(rule_phone_areas,function(n){return n.id}));
				idxParent = $.inArray(a.aiid,$.map(rule_phone_areas,function(n){return n.pid}));
				if(idxSelf>=0 || idxParent >=0) {
					$("#_phone_province").append("<option selected='selected' value='" + a.aiid + "'>" + a.name + "</option>");
				}else {
					$("#_phone_province").append("<option value='" + a.aiid + "'>" + a.name + "</option>");
				}
			});
			if(countryArea) {
				$("#_country_area").val(countryArea.aiid);
				$("#_country_area").click(function(){
					onCountryAreaChange();
				});
				$.each(rule_areas,function(i,a){
					if(a.id == countryArea.aiid) {
						$("#_country_area").prop("checked",true);
					}
				});
				$("#_phone_country_area").val(countryArea.aiid);
				$("#_phone_country_area").click(function(){
					onPhoneCountryAreaChange();
				});
				$.each(rule_phone_areas,function(i,a){
					if(a.id == countryArea.aiid) {
						$("#_phone_country_area").prop("checked",true);
					}
				});
			}
			
			//初始化版本区间
			if(version_select=="1"){
				   $("#version_select").val(version_select);
				   $("#start_version").val(start_version);
				   $("#start_version").show();
				   $("#mul_version").hide();
			}else if(version_select=="2"){
				 $("#version_select").val(version_select);
				 $("#end_version").val(end_version);
				 $("#end_version").show();
				 $("#mul_version").hide();
			}
			else if(version_select=="3"){
				 $("#version_select").val(version_select);
				 $("#start_version").val(start_version);
				 $("#end_version").val(end_version);
				 $("#start_version").show();
				 $("#end_version").show();
				 $("#mul_version").hide();
				}else if(version_select=='4'){
					$("#version_select").val(version_select);
					$("#start_version").hide();
					$("#end_version").hide();
				}
			
			
			$("#version_select").change(function(){
				if($(this).val()=='1'){
				/*	if($("#_product").multiselect("getChecked").length==0||$("#_platform").multiselect("getChecked").length==0){
						myalert("","请选择产品和平台");
						$("#version_select").val("");
					}*/
					$("#start_version").show();
					$("#end_version").hide();
					$("#mul_version").hide();
					$("#start_version option[value!='']").remove();
					$.each(versions, function(i, a) {
						$('#start_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					})
				}
				else if($(this).val()=='2'){
				/*	if($("#_product").multiselect("getChecked").length==0||$("#_platform").multiselect("getChecked").length==0){
						myalert("","请选择产品和平台");
						$("#version_select").val("");
					}*/
					$("#end_version").show();
					$("#start_version").hide();
					$("#mul_version").hide();
					$("#end_version option[value!='']").remove();
					$.each(versions, function(i, a) {
						$('#end_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					})
				}
				else if($(this).val()=='3'){
					$("#start_version").show();
					$("#end_version").show();
					$("#mul_version").hide();
					$("#end_version").val('');
					$("#start_version").val('');
					
				}else if($(this).val()=='4'){
					$("#mul_version").show();
					$("#end_version").hide();
					$("#start_version").hide();
					$("#start_version option[value!='']").remove();
					$("#end_version option[value!='']").remove();
					$.each(versions, function(i, a) {
						$('#start_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
						$('#end_version').append("<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					})
				/*	$("#mul_version").multiselect("uncheckAll");*/
				}else{
					$("#end_version").val('');
					$("#start_version").val('');
					$("#start_version").show();
					$("#end_version").show();
					$("#mul_version").hide();
				}
			})
			
			$("#_province").change(function(){
				onProvinceChange(function(){
				});
			});
			$("#_phone_province").change(function(){
				onPhoneProvinceChange(function(){
				});
			});
			$("#_city").change(function(){
				renderArea();
			});
			$("#_phone_city").change(function(){
				renderPhoneArea();
			});
			
			$("#_policy").change(function(){
				clearInfosource();
			});
			$("#start_version").change(function(){
				if($("#version_select").val()=='3'&&$("#start_version").val()!=''){
					 var start = $("#start_version option:selected").text();
					 var end = $("#end_version option:selected ").text();
					 if($("#end_version").val()!=''){
						 var startVer = start.split('-');
						 var endVer = end.split('-');
						 if(($.trim(startVer[0])!=$.trim(endVer[0]))|($.trim(startVer[1])!=$.trim(endVer[1]))){
							 getHighVersion();
						 }else if(compareVersion(startVer[2],endVer[2])>=0){
							 myalert("","开始版本不能大于结束版本");
								$("#start_version").val("");
						 }
					 }else{
						 getHighVersion();
					 }
					
				}
			})
		/*	$("#end_version").change(function(){
				if($("#version_select").val()=='3'&&$("#end_version").val()!=''){
				 var end = $(this).val();
				 var start = $("#start_version").val();
				 if(end!=''&&start!=''){
					 var startVer = start.split('.');
					 var endVer = end.split('.');
					 if(compareVersion(startVer[2],endVer[2])>0){
						 getLowVersion();
					 }
				 }
				
				}
			})*/
			
			if(policy){
				$('#_policy').val(policy);
			}
			
			$('select[multiple="multiple"]').multiselect({
				checkAllText : "全选",
				uncheckAllText : "全部取消",
				noneSelectedText : "请选择",
				selectedText : "已选择#项",
				minWidth: 170,
				selectedList : 1
			});
			
			$('#_product,#_platform').change(function(){
				reloadVersions();
			});
			$("div .modal").draggable({
		    handle: ".modal-header"
			});
			
			$("#_product,#_platform,#_operator").change(function(){
				onCommonMutilselectChange(this);
			});
			
			$("#_version").change(function(){
				onVersionMutilselectChange(this);
			})
			
			$("#_product,#_platform,#_operator").each(function(i,a){
				onCommonMutilselectChange(a);
			});
			$("#_version").each(function(i,a){
				onVersionMutilselectChange(a);
			});
			onBusiChange();
			onProvinceChange();
			onPhoneProvinceChange();
			onCountryAreaChange();
			onPhoneCountryAreaChange();
			initInfoSource();
			
			$("#_btn_save").click(function(){
				if($("#_id").val() && $("#_status").val()=='0' && !checkActiveRuleExist()){
					return false;
				}
			});
		});