$(function() {
     findActStat(0);
	$("#_actName").keydown(function(e) {
		if (e.keyCode == 13) {
			findActStat();
		}
	});
	$("#_btn_query").click(function() {
		findActStat(0);
	});
	
	$(".selectform .item").click(function(){
	   var isActived = $(this).attr("class").indexOf("actived")
	  var that = $(this);
	  that.parent().find('li').each(function(){
	       $(this).removeClass("actived");
	  })
	  if(isActived =='-1'){
	  $(that).addClass("actived");
	  }else{
	  $(that).removeClass("actived");
	  }
	  getQueryParams();
	  findActStat(0);
	})
	
	/*$(".houseInfoId").mouseover(function(){
		alert('ss')
	$(this).css("background-color","#3276b1");
	})*/
	
});

function getQueryParams(){
   var params = [];
   var areaParams = [];
   var priceParams = [];
   var spaceParams = [];
   var house_typeParams = [];
   $("#Area .item").each(function(){
   	if($(this).attr("class").indexOf("actived")!='-1')
      areaParams.push($(this).text());
   })
   $("#Price .item").each(function(){
   	if($(this).attr("class").indexOf("actived")!='-1')
      priceParams.push($(this).val());
   })
   $("#space .item").each(function(){
   	if($(this).attr("class").indexOf("actived")!='-1')
      spaceParams.push($(this).val());
   })
   $("#house_type .item").each(function(){
   	if($(this).attr("class").indexOf("actived")!='-1')
      house_typeParams.push($(this).val());
   })
      params.push("area:"+areaParams);
      params.push("price:"+priceParams);
      params.push("space:"+spaceParams);
      params.push("house_type:"+house_typeParams);
  return params;
}

function findActStat(page_index) {
//	var limit = $("#_pager #_limit").val();
//	if (!limit) {
//		limit = $("#_pager_demo #_limit").val();
//	}
//	limit = parseInt(limit);
//	if (isNaN(limit)) {
//		limit = 5;
//	}
	var queryParams = getQueryParams();
	/*$('#_msgs #_empty_info > div').html(
			"<span style='font-style:italic;'>正在加载  ...</span>");
	$('#_msgs #_empty_info').show();*/
	//var current_page = current_page ? parseInt(current_page) : 1;
	var start = (page_index ) * limit;
	$.ajax({
		type : 'POST',
		url : Global.webRoot + '/findHouseInfo.do',
		dataType : 'json',
		data : [ {
			name : 'limit',
			value : limit
		}, {
			name : 'start',
			value : start
		},
		 {
			name : 'queryParams',
			value : queryParams
		}],
		success : function(json) {
			var count = json.count;
			var limit = json.limit;
			var max_page = (count + limit - 1) / limit;
			max_page = parseInt(max_page);
			var actDatas = json['data'];
			if (actDatas.length > 0) {
				$("#content").empty();
				$.each(actDatas, function(i, actData) {
					renderAct(actData,$("#houseId").html());
				});
				if($.trim($(".Pagination").html()) == ''){
					$("#pagination").pagination(max_page, {
						    num_edge_entries: 2,
						    num_display_entries: 10,
						    current_page: page_index, 
						    callback: pageselectCallback,
						    items_per_page:1,
						    prev_text : "上一页",  
                            next_text : "下一页"
						}
						)
				}
			}else{
			    $("#content").empty();
			    $("#content").html("<span>没有数据<span>")
			    
			}
				/*$("#_msgs #_empty_info").hide();
				$.each(actDatas, function(i, actData) {
					renderAct(actData);
				});
				renderPager(limit, current_page, max_page);
			} else {
				$("#_msgs #_empty_info > div").html("<span>没有数据<span>");
			}
			$("#_msgs .msg_toggle").click(function() {
				var item = $(this).parent().next();
				item.toggle();
				return false;
			});
			$('.msg_header .title-cut,.keywords-cut').bind('mouseenter', function() {
				var $this = $(this);
				if (this.offsetWidth < this.scrollWidth) {
					$this.attr('title', $this.text());
				} else {
					$this.removeAttr('title');
				}
			});
			$('#_btn_query').removeAttr('disabled');*/
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('', '请求失败');
		}
	});
}


	
	
	


function renderAct(houseInfo,html){
	var id = houseInfo['id'];
	html = "<div id='houseId_{0}' class='houseInfoId'>".format(id) + html + "</div>";
	//alert(html)
   /* var h =	html.replace(/\s+/g,"");*/
   /* html = html.replaceAll("#houseInfoTitle#","ssss")
    alert(html)*/
	  for(var item in houseInfo){ 
      html =  html.replaceAll("#"+item+"#",houseInfo[item]);
      html = html.replaceAll("#slider#","slider_"+id);
      if(houseInfo.houseImgs!=null){
       var imgs = houseInfo.houseImgs.split(",")
       var imgHtml="";
       for(var i = 0 ;i < imgs.length; i ++){
         imgHtml = imgHtml+"<img src="+imgs[i]+" />"
       }
      html = html.replaceAll("#imgHtml#",imgHtml);
      }
 } 
 $("#content").append(html);
 $("houseId_"+id).show();
 $('#slider_'+id).nivoSlider();

	
};
	/*var actId = actInfo.actId;
	var divId = '_msg_{0}'.format(actId);
	var chartId = '_chart_{0}'.format(actId);
	var userChartId = '_user_chart_{0}'.format(actId);
	var cntChartId = '_cnt_chart_{0}'.format(actId);
	var userTabId = '_user_tab_{0}'.format(actId);
	var cntTabId = '_cnt_tab_{0}'.format(actId);
	var t1 = publishTime ? new Date(publishTime).format('yyyy-MM-dd') : ' ';
	var t2 = endTime ? new Date(endTime).format(' ~ yyyy-MM-dd') : ' ';
	html = "<div id='{0}' class='msg'>".format(divId) + html + "</div>";
	html = html.replaceAll("#title#", actInfo.title).replaceAll("#type#", type)
			.replaceAll("#keywords#", keywords);
	html = html.replaceAll("#publishTime#", t1).replaceAll("#endTime#", t2)
			.replaceAll("#actId#", actId);
		html.replaceAll("#ifabled#", "disabled");
	$("#_msgs").append(html);
	if(endTime>new Date().getTime()){
		$("#" + divId + " button:eq(0)").attr('disabled', 'true');
	}*/
	
	/*var aaData = [];
	for(var i=0;i<data.length;i++) {
		var a = data[i];
		aaData.push([ a['resName'], a['visitUserCnt'], a['visitCnt'] ]);
	}*/
	/*$("#_table_" + actId).myDataTable({
		"bServerSide" : false,
		"sDom" : '<"datatable-scroll"t>',
		aaData : aaData,
		"aaSorting" : [],
		"aoColumns" : [ {
			sWidth : '120px',
			bSortable : false,
			"fnRender" : function(obj) {
				return obj.aData[0];
			}
		}, {
			bSortable : false,
			"fnRender" : function(obj) {
				return obj.aData[1];
			}
		}, {
			bSortable : false,
			"fnRender" : function(obj) {
				return obj.aData[2];
			}
		} ]
	});*/

	

var limit = 10;
/*
function renderPager( max_page,current_page) {
	var html = $("#_pager_demo").html();
	html = "<div style='margin:10px;' id='_pager'>" + html + "</div>";
	$("#_page").append(html);
	$("#_pager #_limit").val(limit);
	alert(limit)
	$("#_pager #_limit").change(function() {
		alert('s')
		findActStat();
	});
//	$("#_limit").change(function() {
//		findActStat();
//	});
);*/

function pageselectCallback(page_index, jq){  
    findActStat(page_index);
} 
//	$('.pagination').jqPagination({
//		current_page : current_page,
//		max_page : max_page,
//		page_string : '{current_page} / {max_page}',
//		paged : function(page) {
//			findActStat(page);
//		}
//	});

/*function initProvince() {
	var province = $("#query_province");
	province.empty();
	province.append("<option value=''>选择省份</option>");
	$.ajax({
		"type" : "get",
		"url" : Global.webRoot + "/province.json",
		"dataType" : "json",
		"success" : function(json) {
			if (json.length == 1) {
				province.empty();
			}
			for ( var i = 0; i < json.length; i++) {
				province.append("<option value='" + json[i].aiid + "'>" + json[i].name
						+ "</option>");
			}
			initCity();
		}
	});
	province.change(function() {
		initCity();
	});
}

function initCity() {
	var city = $("#query_city");
	var province = $("#query_province");
	city.empty();
	city.append("<option value=''>选择城市</option>");
	if (province.val() && province.val() != 0) {
		$.ajax({
			"type" : "get",
			"url" : Global.webRoot + "/city.json?aiid=" + province.val(),
			"dataType" : "json",
			"success" : function(json) {
				if (json.length == 1) {
					city.empty();
				}
				for ( var i = 0; i < json.length; i++) {
					city.append("<option value='" + json[i].aiid + "'>" + json[i].name
							+ "</option>");
				}
			}
		});
	}
}*/



