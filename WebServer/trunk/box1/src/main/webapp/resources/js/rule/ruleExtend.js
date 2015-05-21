function onTypeChange(typeId) {
	$.ajax({
		url : './listExtendedRuleValue',
		type : 'POST',
		data : {typeId : typeId},
		timeout : 20000,
		cache : false,
		dataType : "json",
		success : function(data) {
				$("#_extendedRuleValues option[value!='']").remove();
				$.each(data, function(i, a) {
					if($.inArray(a.id,valueIds)<0) {
						$('#_extendedRuleValues').append(
								"<option value='" + a.id + "'>" + a.valuez + "</option>");
					}else {
						$('#_extendedRuleValues').append(
								"<option selected='selected' value='" + a.id + "'>" + a.valuez + "</option>");
					}
				});
				$("#_extendedRuleValues").multiselect('refresh');
		}
	});
	
	var desc = $("#_extendedRuleType").children('option:selected').attr('desc');
	$("#_description").text(desc);
}

function onBusiChange(busiId){
	$.ajax({
		url : './listRuleByBusiId',
		type : 'POST',
		data : {busiId : busiId},
		timeout : 20000,
		cache : false,
		dataType : "json",
		success : function(data) {
				$("#_rule option[value!='']").remove();
				$.each(data, function(i, a) {
						$('#_rule').append(
								"<option value='" + a['id'] + "'>" + a['code'] + "</option>");
				});
				onRuleChange($("#_rule").val());
		}
	});
}

function onRuleChange(ruleId){
	if(!ruleId){
		resetValueIdsInput();
		$("#_extendedRuleValues option").each(function(i,a){
			$(a).prop('selected',false);
		});
		$("#_extendedRuleValues").multiselect('refresh');
		return;
	}
	$("#_ruleId").val(ruleId);
	$.ajax({
		url : './listExtendValueByRuleId',
		type : 'POST',
		data : {ruleId : ruleId},
		timeout : 20000,
		cache : false,
		dataType : "json",
		success : function(data) {
				valueIds = data;
				resetValueIdsInput();
				$("#_extendedRuleValues option").each(function(i,a){
					var val = $(a).attr('value');
					val = parseInt(val);
					var sel = $.inArray(val,valueIds)>=0;
					$(a).prop('selected',sel);
				});
				$("#_extendedRuleValues").multiselect('refresh');
		}
	});
}

function onValueChange(valueId,checked) {
	valueId = parseInt(valueId);
	if($.inArray(valueId,valueIds)<0){
		if(checked) {
			valueIds.push(valueId);
		}
	}else {
		if(!checked) {
			valueIds.splice($.inArray(valueId,valueIds),1);
		}
	}
	resetValueIdsInput();
}

function resetValueIdsInput(){
	var _valueIds = '';
	$.each(valueIds,function(i,a){
		_valueIds += a;
		if(i < valueIds.length -1 ){
			_valueIds += ',';
		}
	});
	$('#_valueIds').val(_valueIds);
}

$(function() {
	
	//取消按钮
	$("#_btn_cancel").click(function(){
	     window.location.href=Global.webRoot+"/rule/rulelist?cancel=cancelEx";
	});
	
	$("#_description").attr('disabled','disabled');
	
	$("#_extendedRuleValues").multiselect({
		checkAllText : "全选",
		uncheckAllText : "全部取消",
		noneSelectedText : "请选择",
		selectedText : "已选择#项",
		minWidth: 120,
		selectedList : 4
	});
	$("#_extendedRuleType").change(function(){
		onTypeChange($("#_extendedRuleType").val());
	});
	onTypeChange($("#_extendedRuleType").val());
	
	$("#_business").change(function(){
		onBusiChange($("#_business").val());
	});
	if(!$("#_rule").val()){
		onBusiChange($("#_business").val());
	}
	
	$("#_rule").change(function(){
		onRuleChange($("#_rule").val());
	});
	$("#_extendedRuleValues").on("multiselectclick", function(event, ui){
		onValueChange(ui.value,ui.checked);
	});
	resetValueIdsInput();
	$("#_extend_form").validate({
		rules : {
			rule : {
				required : true
			}
		}
	});
});