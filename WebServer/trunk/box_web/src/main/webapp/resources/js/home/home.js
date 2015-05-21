$(function(){

	$( document ).ajaxStart(function() {
		$("#ajaxWait").css("display", "block");
		//alert(1);
		});
	
	$(document).ajaxStop(function(){
		//$("#ajaxWait").modal('hide');
		$("#ajaxWait").css("display", "none");
	});	
	$(document).tooltip();
	
	/*var nn=new Date();
	endTime=nn.addDay(-1).shortFormat();
	startTime=nn.addDay(-31).shortFormat();
	startTime.val(startTime);
	*/
     
		
	$("#totleNumberBtn").click(function () { 
		$("#totleNumberBtn").addClass("active");
		$("#totlePeopleBtn").removeClass("active");
		$("#failBtn").removeClass("active");
		$("#repTimeBtn").removeClass("active");
		freshChart('./getActiveDataChart.json','totleNumber');
	 	});
	 $("#totlePeopleBtn").click(function () { 
			$("#totleNumberBtn").removeClass("active");
			$("#totlePeopleBtn").addClass("active");
			$("#failBtn").removeClass("active");
			$("#repTimeBtn").removeClass("active");
			freshChart('./getActiveDataChart.json','totlePeople');
		 });
	 $("#failBtn").click(function () { 
			$("#totleNumberBtn").removeClass("active");
			$("#totlePeopleBtn").removeClass("active");
			$("#failBtn").addClass("active");
			$("#repTimeBtn").removeClass("active");
			freshChart(Global.webRoot+'/getFailDataChart.json','fail');
		 	});
	 $("#repTimeBtn").click(function () { 
			$("#totleNumberBtn").removeClass("active");
			$("#totlePeopleBtn").removeClass("active");
			$("#failBtn").removeClass("active");
			$("#repTimeBtn").addClass("active");
			freshChart(Global.webRoot+'/getFailDataChart.json','time');
		 });
	
	
	


})
var k=1;

var initFirstLine=function(){
	refreshCharts();
	//freshChart(Global.webRoot+'/getBaseActiveDataChart','active');	
};

var freshChart = function(sSource,chartType){
	startTime = $("#startDate").val();
	endTime = $("#endDate").val();
	bizInfoSec = $("#bizInfoSec").val();//产品
	platSec = $("#platSec").val();
	versionSec = $("#versionSec").val();
	BusinessInfoSec = $("#BusinessInfoSec").val();//业务
	infoSourceSec = $("#infoSourceSec").val();//信源 
	
	var aoData=[];
	aoData.push({name: "startTime", value: startTime ? startTime : ""},
		{name: "endTime", value: endTime ? endTime : ""},
		{name: "bizInfoSec", value: bizInfoSec ? bizInfoSec : ""},
		{name: "platSec", value: platSec ? platSec : ""},
		{name: "versionSec", value: versionSec ? versionSec : ""},
		{name: "BusinessInfoSec", value: BusinessInfoSec ? BusinessInfoSec : ""},
		{name: "infoSourceSec", value: infoSourceSec ? infoSourceSec : ""},
		{name: "chartType", value: chartType ? chartType : "totlePeople"}
		);
	$.ajax( {
		"type": "post",
		"url": sSource,
		"data": aoData,
		"success": function(result) {
			var subTileName="";
			if(startTime!=""&&endTime==""){
				subTileName="大于日期"+startTime;
			}
			else if(startTime==""&&endTime!=""){
				subTileName="小于日期"+endTime;
			}
			else if(startTime!=""&&endTime!=""){
				subTileName=startTime+"~"+endTime;
			}
			var data =result.data;
			var categories=result.categories;
			var datas=[];
			for(var i=0;i<data.length;i++){
				//构建折线图数据
				datas.push({ 
                    name: data[i].name,  
                    data: data[i].data
                });
			}
			if(datas.length>0){
				var stepSize= Math.floor(categories.length/8);
				drawLine(datas,categories,subTileName,stepSize);
				$("#line").show();
			}else{
				$("#line").hide();
			}			
		},
		"dataType": "json",
		"cache": false,
		"error": function (xhr, error, thrown) {
			if ( error == "parsererror" ) {
				Login();
			}
		}
	} );
} ;

//查询条件的变量
var startTime;
var endTime;
//筛选条件对象
var bizInfoSec;
var platSec;
var versionSec;
var BusinessInfoSec;
var infoSourceSec;

var chart;
var drawLine =function(data,categoriess,subTileName,stepSize) {
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'line',
			defaultSeriesType: 'line',
			marginRight: 0
		},
		title: {
			text: '',
			x: -20 //center
		},
		subtitle: {
			text: '',
			x: -20
		},
		xAxis: {
			categories: categoriess,
			labels:{ 
                step:stepSize 
            } 
		},
		yAxis: {
			title: {
				text: ''	
			},
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}],
			min:0
		},
		legend: {
			borderWidth: 1,
			verticalAlign: 'bottom',
			maxHeight : 100
		},
		series:  data
	});
};

function refreshCharts(){
 $("#_nav").children("li").each(function(){
	      var nav_id = $(this).attr("id");
	      var nav_class = $(this).attr("class");
	      if(nav_id =='failBtn'&nav_class =='active'){
	      $("#failBtn").click();
	      }
	      else if(nav_id =='repTimeBtn'&nav_class =='active'){
	       $("#repTimeBtn").click();
	      }
	      else if(nav_id =='totlePeopleBtn'&nav_class =='active'){
	      $("#totlePeopleBtn").click();
	      }
	      else if(nav_id =='totleNumberBtn'&nav_class =='active'){
	       $("#totleNumberBtn").click();
	      }
	      })
}

