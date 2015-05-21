var dataDict = {};

$.ajax({
	url : './listDataDict',
	type : 'POST',
	timeout : 20000,
	cache : false,
	async : false,
	dataType : "json",
	success : function(data) {
			dataDict = data;
	}
});

function initInfoSource() {
	var _infos = '',__infos = '',_priority = '',count=0,_weight = '';
	$.each(rule_infosources,function(i,a){
			_infos += a+',';
			var name = '';
			$.each(infosources,function(ii,aa){
				if(aa.id == a) {
					name = aa.name;
					return false;
				}
			});
			__infos += name+',';
			count++;
			if(policy==0){
				_priority += rule_priorities[a]+',';
			}else if(policy==1) {
				_weight += rule_weights[a]+',';
			}
	});
	if(_infos.length>0){
		_infos = _infos.substring(0,_infos.length - 1);
	}
	if(count>0) {
		__infos = __infos.substring(0,__infos.length-1);
	}
	$("#_add_form #__infosource").removeAttr("title");
	if(count > 4){
		$("#_add_form #__infosource").attr("title",__infos);
		__infos = "已选 " + count + " 个信源";
	}
	if(_priority.length>0) {
		_priority = _priority.substring(0,_priority.length -1);
	}
	if(_weight.length>0) {
		_weight = _weight.substring(0,_weight.length -1);
	}
	$("#_add_form #_infosource").val(_infos);
	$("#_add_form #__infosource").text(__infos);
	$("#_add_form #_priority").val(_priority);
	$("#_add_form #_weight").val(_weight);
}

function saveInfoSource0(){
	var result = validatePrioritys();
	if(!result.validate) {
		return;
	}
	$("#_policy0").modal('hide');
}

function saveInfoSource1(){
	var result = validateWeights();
	if(!result.validate) {
		return;
	}
	$("#_policy1").modal('hide');
}

function saveInfoSource2(){
	var _infos = '',__infos = '',count = 0;
	$("#_policy2 table input[type='checkbox']").each(function(i,a){
		if($(a).prop('checked')) {
			_infos += $(a).val()+',';
			__infos += $(a).parent().text()+',';
			count++;
		}
	});
	if(_infos.length>0){
		_infos = _infos.substring(0,_infos.length - 1);
	}
	if(count>0) {
		__infos = __infos.substring(0,__infos.length-1);
	}
	$("#_add_form #__infosource").removeAttr("title");
	if(count > 4){
		$("#_add_form #__infosource").attr("title",__infos);
		__infos = "已选 " + count + " 个信源";
	}
	$("#_add_form #_infosource").val(_infos);
	$("#_add_form #__infosource").text(__infos);
	$("#_add_form #_weight").val('');
	$("#_add_form #_priority").val('');
	$("#_policy2").modal('hide');
}

function inputPriority(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode;
	var target = evt.target ? evt.target : evt.srcElement;
	var val = $(target).val();
  if ((charCode !=45 || val.length>0) &&charCode > 31 && (charCode < 48 || charCode > 57)){
  	showTooltip(target);
  	return false;
  }
  return true;
}

function showTooltip(target) {
	if(!(target instanceof jQuery)) {
		target = $(target);
	}
	target.tooltip('show');
	setTimeout(function(){
		target.tooltip('hide');
	},1500);
}

function inputWeight(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode;
	var target = evt.target ? evt.target : evt.srcElement;
	var val = $(target).val();
  if ((charCode != 46 || val.indexOf('.') != -1) && charCode > 31 && (charCode < 48 || charCode > 57)){
  	showTooltip(target);
  	return false;
  }
  return true;
	
}

function validatePrioritys() {
	var result = {
			validate : true,
			_infos : '',
			__infos : '',
			_priority : '',
			vals : []
	};
	$("#_policy0 table input[type='checkbox']").each(function(i,a){
		if($(a).prop('checked')) {
			result._infos += $(a).val()+',';
			result.__infos += $(a).parent().text()+',';
			var input = $(a).parent().parent().next().find("input");
			if(!validatePriority(input)) {
				result.validate = false;
				return false;
			}
			result.vals.push(parseInt(input.val()));
			result._priority += input.val() + ',';
		}
	});
	if(!result.validate){
		return result;
	}
	result.vals.sort(function(a,b){
		return a-b;
	});
	$.each(result.vals,function(i,a){
		if(a != i + 1) {
			result.validate = false;
			alert("调度优先级必须从1开始,并且连续!");
			return false;
		}
	});
	if(result.vals.length>0) {
		result._infos = result._infos.substring(0,result._infos.length-1);
		result.__infos = result.__infos.substring(0,result.__infos.length-1);
		result._priority = result._priority.substring(0,result._priority.length-1);
	}
	if(result.vals.length>4 && result.validate){
		$("#_add_form #__infosource").attr("title",result.__infos);
		result.__infos = "已选 " + result.vals.length + " 个信源";
	}
	if(result.validate) {
		$("#_add_form #_infosource").val(result._infos);
		$("#_add_form #__infosource").text(result.__infos);
		$("#_add_form #_weight").val('');
		$("#_add_form #_priority").val(result._priority);
	}
	return result;
}

function validatePriority(input) {
	var val = input.val();
	if(isNaN(val)) {
		showTooltip(input);
		return false;
	}
	return true;
}

function validateWeights(){
	var result = {
			validate : true,
			_infos : '',
			__infos : '',
			_weight : '',
			vals : []
	};
	$("#_policy1 table input[type='checkbox']").each(function(i,a){
		if($(a).prop('checked')) {
			result._infos += $(a).val()+',';
			result.__infos += $(a).parent().text()+',';
			var input = $(a).parent().parent().next().find("input");
			if(!validateWeight(input)) {
				result.validate = false;
				return false;
			}
			result.vals.push(parseFloat(input.val()));
			result._weight += input.val() + ',';
		}
	});
	if(!result.validate){
		return result;
	}
	if(result.vals.length>0) {
		var count = 0;
		$.each(result.vals,function(i,a){
			count += a;
		});
		if(count != 100) {
			alert("概率调度和必须为100!");
			result.validate = false;
		}
		result._infos = result._infos.substring(0,result._infos.length-1);
		result.__infos = result.__infos.substring(0,result.__infos.length-1);
		result._weight = result._weight.substring(0,result._weight.length-1);
	}
	if(result.vals.length>4 && result.validate){
		$("#_add_form #__infosource").attr("title",result.__infos);
		result.__infos = "已选 " + result.vals.length + " 个信源";
	}
	if(result.validate) {
		$("#_add_form #_infosource").val(result._infos);
		$("#_add_form #__infosource").text(result.__infos);
		$("#_add_form #_weight").val(result._weight);
		$("#_add_form #_priority").val('');
	}
	return result;
}

function validateWeight(input) {
	var val = input.val();
	if(isNaN(val)|| parseFloat(val)<0 || parseFloat(val) > 100) {
		showTooltip(input);
		return false;
	}
	return true;
}

function showPolicyWin(){
	var policy = $("#_policy").val();
	$("#_policy"+policy).modal('toggle');
}

function reloadVersions() {
	var __product = $("#_product").val();
	var __platform = $("#_platform").val();
	__product = __product ? __product+'' : '';
	__platform = __platform ? __platform+'' : '';
	$.ajax({
		url : './listVersion',
		type : 'POST',
		data : {bizId : __product,osId : __platform},
		timeout : 20000,
		cache : false,
		dataType : "json",
		success : function(data) {
				versions = data;

				$("#_version option[value!='']").remove();
				$("#_version").parent().find(".info:last").remove();
				$.each(versions, function(i, a) {
					$('#_version').append(
							"<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
				});
				$("#_version").multiselect('refresh');
				
				$("#start_version option[value!='']").remove();
				$("#end_version option[value!='']").remove();
				$.each(versions, function(i, a) {
					$('#end_version').append(
							"<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
					$('#start_version').append(
							"<option value='" + a.PctVerID + "'>" + a.VersionNo + "</option>");
				});
		}
	});
}