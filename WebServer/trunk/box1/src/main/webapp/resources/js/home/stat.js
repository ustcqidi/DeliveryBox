 $(function() {
   
	// 筛选按钮点击事件，url后面是传递的参数给handler处理
	// bind(type,data,event)其中type可以是click,submit等，data作为event.data属性值传递给事件对象的额外数据对象
	$("#test1").bind("click", {
		e : 'test1'
	}, handler);

	selectAllOrNo();//全选/全不选
	$("#totlePeopleBtn").click();


	
});


function selectAllOrNo(){
	// 点击全选事件
	$("#BizInfoAll").click(function() {

		var $bizInfo = $("#bizInfo");
		$bizInfo.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked", true);
			});
		});
		setVersionInfo();
		$(this).removeClass("select");
		 $(this).prev().addClass("select");
	});
	
		// 点击全不选事件
	$("#noBizInfo").click(function() {
		var $bizInfo = $("#bizInfo");
		$bizInfo.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});
		setVersionInfo();
		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});


	// 点击全选事件
	$("#platAll").click(function() {

		var $plat = $("#plat");
		$plat.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked", true);
			});
		});
		setVersionInfo();
		$(this).removeClass("select");
		 $(this).prev().addClass("select");
	});

	// 点击全不选事件
	$("#platNo").click(function() {
		var $plat = $("#plat");
		$plat.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});
      setVersionInfo();
		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});

	
	
	// 点击全选事件
	$("#versionAll").click(function() {

		// alert($(this).nextAll(".check-border").text());
		var $version = $("#version");
		$version.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked", true);
			});
		});
		
		$(this).removeClass("select");
		 $(this).prev().addClass("select");
	});//end #userALl click function

	// 点击全不选事件
	$("#versionNo").click(function() {
		var $version = $("#version");
		$version.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});

		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});//end #userNo click function


	// 点击全不选事件
	$("#BusinessInfoNo").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $BusinessInfo = $("#BusinessInfo");	
		$BusinessInfo.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});
	var $infoSource = $("#infoSource");	
		$infoSource.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("disabled",false);
				$(this).prop("checked",false);
			});
		});
		
	});
	
	// 点击全选事件
	$("#infoSourceAll").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $infoSource = $("#infoSource");	
		$infoSource.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				if($(this).prop("disabled")!=true)
				$(this).prop("checked",true);
			});
		});

		 $(this).removeClass("select");
		 $(this).prev().addClass("select");
	});

	// 点击全不选事件
	$("#infoSourceNo").click(function() {
		// alert($(this).nextAll(".check-border").text());
		var $infoSource = $("#infoSource");	
		$infoSource.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("checked",false);
			});
		});

		 $(this).removeClass("select");
		 $(this).next().addClass("select");
	});

	
}//end function
 
 
 var fordiv;

    function handler(event) {
    	//获取id= test1的元素并转换成jquery对象
    	 var e = $("#"+event.data.e)[0];
////    	 
    	 //获取e对象的fordiv属性值，这个值为menu1,定位了id =menu1的div元素
    	 fordiv = $(e).attr("fordiv");	
    	 
    	 var $menu1 = $("#"+fordiv);
    	 var $menu2 = $("#"+fordiv);
    	 var $children = $menu1.children("div:eq(0)");
		
    	 var $forDiv_children = $("#"+fordiv).children("div:eq(0)");
    	

//    	 $children.children("input[name='url']").val(url);
//    	 $children.children("input[name='id']").val(id);
//    	 $children.children("input[name='name']").val(name);
		     	
   	 var $bizInfo = $("#bizInfo");//产品
	     if($bizInfo.text().trim()==""||$bizInfo.text().trim()==null){//若为空
	    	 $.ajax({
		 	  		"type":"get",
		 	  		"url":Global.webRoot+"/getBizInfo.json",
		 	  		"dataType": "json",
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				v_html +=  "<label class='checkbox'><input type='checkbox'  name='"+item.id+"' onclick='setVersionInfo()'  value='"+item.id+"'/>&nbsp;&nbsp;"+item.cnName+"</label>";
		 	  			});
		 	  			$bizInfo.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	     }//end if
	     
	     var $plat = $("#plat");//平台
	     var text = $plat.text();
	     var trimTxt = $.trim(text);
	     if(trimTxt==""||trimTxt==null){//若为空	
	    	 $.ajax({
		 	  		"type":"get",
		 	  		"url":Global.webRoot+"/rule/listPlatform",
		 	  		"dataType": "json",
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				if(item){
		 	  					v_html += "<label class='checkbox '><input type='checkbox'  name='"+item.OSID+"' onclick='setVersionInfo()'  value='"+item.OSID+"'/>&nbsp;&nbsp;"+item.OSName+"&nbsp;&nbsp;</label>";
		 	  				}
		 	  				
		 	  			});
		 	  			
		 	  			$plat.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	     }//end if
	     
	     
	   /*   var $version = $("#version");//版本
	     if($version.text().trim()==""||$version.text().trim()==null){//若为空
	    	 $.ajax({
		 	  		"type":"get",
		 	  		"url":Global.webRoot+"/getProductVersion.json",
		 	  		"dataType": "json",
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				v_html +=  "<label class='checkbox'><input type='checkbox'  name='"+item.id+"'   value='"+item.id+"'/>&nbsp;&nbsp;"+item.versionNo+"</label>";
		 	  			});
		 	  			$version.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	     }//end if
*/	     
	         var $BusinessInfo = $("#BusinessInfo");//业务
	     if($BusinessInfo.text().trim()==""||$BusinessInfo.text().trim()==null){//若为空
	    	 $.ajax({
		 	  		"type":"get",
		 	  		"url":Global.webRoot+"/rule/listBusiType",
		 	  		"dataType": "json",
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				v_html +=  "<label class='checkbox'><input type='radio' onclick='checkInfoSource("+item.Fid+")' name='Business'  style='margin-left:-20px;' value='"+item.Fid+"'/>&nbsp;&nbsp;"+item.Name+"</label>";
		 	  			});
		 	  			$BusinessInfo.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	     }//end if
	     
	    /*   var $infoSource = $("#infoSource");//信源
	     if($infoSource.text().trim()==""||$infoSource.text().trim()==null){//若为空
	    	 $.ajax({
		 	  		"type":"get",
		 	  		"url":Global.webRoot+"/rule/listInfoSource",
		 	  		"dataType": "json",
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				v_html +=  "<label class='checkbox'><input type='checkbox'  name='"+item.id+"'    value='"+item.id+"'/>&nbsp;&nbsp;"+item.name+"</label>";
		 	  			});
		 	  			$infoSource.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	     }//end if
*/	     
	   	  var test1 = $(e);
	      var testOffset = test1.prev().offset();
	      
	      var d =true;
	      $("#_nav").children("li").each(function(){
	      var nav_id = $(this).attr("id");
	      var nav_class = $(this).attr("class");
	      if(nav_id =='failBtn'&nav_class =='active'){
	       d= false;
	      }
	      if(nav_id =='repTimeBtn'&nav_class =='active'){
	      	 d= false;
	      }
	      
	      })
	      if(d){
	    	  $("#BizInfodiv").show();
		     $("#Platdiv").show();
		     $("#Versiondiv").show();
	      $menu1.css({left:testOffset.left + "px", top:testOffset.top + test1.outerHeight() + "px",width:test1.outerWidth(true)+test1.prev().outerWidth(true)*2+"px"}).slideDown();
	      }else{
	    	 $("#BizInfodiv").hide();
	    	 $("#Platdiv").hide();
	    	 $("#Versiondiv").hide();
	    	  $menu1.css({left:testOffset.left + "px", top:testOffset.top + test1.outerHeight() + "px",width:test1.outerWidth(true)+test1.prev().outerWidth(true)*2+"px"}).slideDown();
	      }
      //    $("body").bind("mousedown", onBodyDown);
//          $(e).unbind("click");
	}//end handler
		
		   	
	function hideMenu(divId) {
		$("#"+divId).fadeOut("fast");
//		table.fnDraw();//刷新数据
	
	}
	
//	function onBodyDown(event) {
//	  
//		if (!(event.target.id == fordiv || $(event.target).parents("#"+fordiv).length>0)) {//$(event.target).parents("#"+fordiv)判断id为fordiv的元素是是否存在
////			var roles = new Array();		
////			$("#role").children().children("input:checked").each(function(){
////				roles.push($(this).val());
////			});			
////			$("#roleSec").val(roles.join());			
//			
//			var users = new Array();		
//			$("#user").children().children("input:checked").each(function(){
//				users.push($(this).val());
//			});			
//			$("#userSec").val(users.join());
//			
//			var opts = new Array();		
//			$("#opt").children().children("input:checked").each(function(){
//				opts.push($(this).val());
//			});			
//			$("#optSec").val(opts.join());
//			
//			var results = new Array();		
//			$("#result").children().children("input:checked").each(function(){
//				results.push($(this).val());
//			});			
//			$("#resultSec").val(results.join());
//			hideMenu(fordiv);		
//		}//end if
//	}//end onBodyDown

	
	function selectAll(e){
		$(e).parent().prev().children("input").each(function(){
				   $(this).attr("checked",true);
		});//end each
	}//end selectAll
	
	function removeAll(e){
		$(e).parent().prev().children("input").each(function(){
				   $(this).attr("checked",false);
		});//end each
	}//end removeAll
	
	function hideDiv(e){
	
		var bizInfo = new Array();		
		$("#bizInfo").children().children("input:checked").each(function(){
			bizInfo.push($(this).val());
		});		
		$("#bizInfoSec").val(bizInfo.join());			
		
		var plat = new Array();		
		$("#plat").children().children("input:checked").each(function(){
			plat.push($(this).val());
		});			
		$("#platSec").val(plat.join());
		
		var version = new Array();		
		$("#version").children().children("input:checked").each(function(){
			version.push($(this).val());
		});			
		$("#versionSec").val(version.join());
	//	alert(version.join())
		
		var businessInfo = '';		
		$("#BusinessInfo").children().children("input:checked").each(function(){
			businessInfo = $(this).val();
		});			
		$("#BusinessInfoSec").val(businessInfo);
		
		var infoSource = new Array();		
		$("#infoSource").children().children("input:checked").each(function(){
			infoSource.push($(this).val());
		});			
		$("#infoSourceSec").val(infoSource.join());
		// var url = $("#"+fordiv).children("div:eq(0)").children("input[name='url']").val();
		// var id = $("#"+fordiv).children("div:eq(0)").children("input[name='id']").val();
		// var name = $("#"+fordiv).children("div:eq(0)").children("input[name='name']").val();
		// $("button[fordiv='"+fordiv+"']").bind("click",{url:url,id:id,name:name,e:$("button[fordiv='"+fordiv+"']").attr("id")},handler);
		// $("body").unbind("mousedown", onBodyDown);
		$(e).parent().parent().fadeOut("fast");
		
		//刷新数据
		refreshCharts();
		
		
		// $("body").unbind("mousedown", onBodyDown);
  }//end hideDiv
	
  function cancel(e){
		$(e).parent().parent().fadeOut("fast");		
		// $("body").unbind("mousedown", onBodyDown);
  }//end hideDiv
	
	
  Date.prototype.shortFormat = function() {
	var str=this.format("yyyy-MM-dd");
    return str;
};

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
	};
	if(/(y+)/.test(format))
		format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	       format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};
Date.prototype.addDay = function(time) {
	var t=this.getTime()+1000*60*60*24*time;
	var d1=new Date();
	d1.setTime(t);
    return d1;
};

function changeStart(){
	refreshCharts();
}

function changeEnd(){
	refreshCharts();;
}

function checkInfoSource(fid){
		/*var $infoSource = $("#infoSource");	
		$infoSource.children("label.checkbox").each(function(){
			$(this).children("input").each(function(){
				$(this).prop("disabled",true);
				$(this).prop("checked",false);
			});
		});*/
		var $infoSource = $("#infoSource");//信源
		$infoSource.html("");
		 $.ajax({
		 	  		"type":"post",
		 	  		"url":Global.webRoot+"/getInfoSourceByBusiId.json",
		 	  		"dataType": "json",
		 	  		"data":{BusiId:fid},
		 	  		"async":false,
		 	  		"success":function(json){
		 	  		var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				v_html +=  "<label class='checkbox'><input type='checkbox'  name='"+item.id+"'    value='"+item.id+"'/>&nbsp;&nbsp;"+item.name+"</label>";
		 	  			});
		 	  			$infoSource.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	
	
}

function setVersionInfo(){
	var bizInfoTmp  = new Array();		
		$("#bizInfo").children().children("input:checked").each(function(){
			bizInfoTmp.push($(this).val());
		});	
	var platTmp = new Array();		
		$("#plat").children().children("input:checked").each(function(){
			platTmp.push($(this).val());
		});	
		var $version = $("#version");//版本
		$version.html("");
		 $.ajax({
		 	  		"type":"post",
		 	  		"url":Global.webRoot+"/rule/listSimpleVersion",
		 	  		"dataType": "json",
		 	  		"data":{bizId:bizInfoTmp.join(),osId:platTmp.join()},
		 	  		"async":false,
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				v_html +=  "<label class='checkbox'><input type='checkbox'  name='"+item.id+"'   value='"+item.id+"'/>&nbsp;&nbsp;"+item.VersionNo+"</label>";
		 	  			});
		 	  			$version.html(v_html+"<div style = 'clear:both;'></div>");
		 	  				
		 	  		}//end success
		 	  	});//end ajax	
		
}