    var fordiv;

    function handler(event) {
    	//获取id= test1的元素并转换成jquery对象
    	 var e = $("#"+event.data.e)[0];
    	 
////    	 
    	 //获取e对象的fordiv属性值，这个值为menu1,定位了id =menu1的div元素
    	 fordiv = $(e).attr("fordiv");	
    	 
    	 var $menu1 = $("#"+fordiv);
    	 var $children = $menu1.children("div:eq(0)");
		
    	 var $forDiv_children = $("#"+fordiv).children("div:eq(0)");
    	

//    	 $children.children("input[name='url']").val(url);
//    	 $children.children("input[name='id']").val(id);
//    	 $children.children("input[name='name']").val(name);
		     	
//    	 var $role = $("#role");//角色
//	     if($role.text().trim()==""||$role.text().trim()==null){//若为空
//	    	 $.ajax({
//		 	  		"type":"get",
//		 	  		"url":"./recordsListRole.json",
//		 	  		"dataType": "json",
//		 	  		"success":function(json){
//		 	  			var v_html = "";
//		 	  			$.each(json,function(index,item){
//		 	  				
//		 	  				v_html +=  "<label class='checkbox'><input type='checkbox' name='"+item.roleId+"'   value='"+item.roleId+"'/>&nbsp;&nbsp;"+item.roleName+"</label>";
//		 	  			});
//		 	  			
//
//		 	  			$role.html(v_html+"<div style = 'clear:both;'></div>");
//		 	  		}//end success
//		 	  	});//end ajax
//	     }//end if
	     
	     var $user = $("#user");//角色
	     var text = $user.text();
	     var trimTxt = $.trim(text);
	     if(trimTxt==""||trimTxt==null){//若为空	
	    	 $.ajax({
		 	  		"type":"get",
		 	  		"url":Global.webRoot+"/recordsListUser.json",
		 	  		"dataType": "json",
		 	  		"success":function(json){
		 	  			var v_html = "";
		 	  			$.each(json,function(index,item){
		 	  				if(item){
		 	  					v_html += "<label class='checkbox'><input type='checkbox' name='"+item+"'   value='"+item+"'/>&nbsp;&nbsp;"+item+"</label>";
		 	  				}
		 	  				
		 	  			});
		 	  			
		 	  			$user.html(v_html+"<div style = 'clear:both;'></div>");
		 	  		}//end success
		 	  	});//end ajax
	     }//end if
	     
	   	  var test1 = $(e);
	      var testOffset = test1.prev().offset();
	      $menu1.css({left:testOffset.left + "px", top:testOffset.top + test1.outerHeight() + "px",width:test1.outerWidth(true)+test1.prev().outerWidth(true)*2+"px"}).slideDown("fast");
          $("body").bind("mousedown", onBodyDown);
//          $(e).unbind("click");
	}//end handler
		
		   	
	function hideMenu(divId) {
		$("#"+divId).fadeOut("fast");
//		table.fnDraw();//刷新数据
	
	}
	
	function onBodyDown(event) {
	  
		if (!(event.target.id == fordiv || $(event.target).parents("#"+fordiv).length>0)) {//$(event.target).parents("#"+fordiv)判断id为fordiv的元素是是否存在
//			var roles = new Array();		
//			$("#role").children().children("input:checked").each(function(){
//				roles.push($(this).val());
//			});			
//			$("#roleSec").val(roles.join());			
			
			var users = new Array();		
			$("#user").children().children("input:checked").each(function(){
				users.push($(this).val());
			});			
			$("#userSec").val(users.join());
			
			var opts = new Array();		
			$("#opt").children().children("input:checked").each(function(){
				opts.push($(this).val());
			});			
			$("#optSec").val(opts.join());
			
			var results = new Array();		
			$("#result").children().children("input:checked").each(function(){
				results.push($(this).val());
			});			
			$("#resultSec").val(results.join());
			hideMenu(fordiv);		
		}//end if
	}//end onBodyDown
	
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
	
		var roles = new Array();		
		$("#role").children().children("input:checked").each(function(){
			roles.push($(this).val());
		});			
		$("#roleSec").val(roles.join());			
		
		var users = new Array();		
		$("#user").children().children("input:checked").each(function(){
			users.push($(this).val());
		});			
		$("#userSec").val(users.join());
		
		var opts = new Array();		
		$("#opt").children().children("input:checked").each(function(){
			opts.push($(this).val());
		});			
		$("#optSec").val(opts.join());
		
		var results = new Array();		
		$("#result").children().children("input:checked").each(function(){
			results.push($(this).val());
		});			
		$("#resultSec").val(results.join());
		// var url = $("#"+fordiv).children("div:eq(0)").children("input[name='url']").val();
		// var id = $("#"+fordiv).children("div:eq(0)").children("input[name='id']").val();
		// var name = $("#"+fordiv).children("div:eq(0)").children("input[name='name']").val();
		// $("button[fordiv='"+fordiv+"']").bind("click",{url:url,id:id,name:name,e:$("button[fordiv='"+fordiv+"']").attr("id")},handler);
		// $("body").unbind("mousedown", onBodyDown);
		$(e).parent().parent().fadeOut("fast");
		table.fnDraw();//刷新数据
		// $("body").unbind("mousedown", onBodyDown);
  }//end hideDiv
	
  function cancel(e){
		$(e).parent().parent().fadeOut("fast");		
		// $("body").unbind("mousedown", onBodyDown);
  }//end hideDiv
	
	