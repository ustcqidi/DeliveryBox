var currentPage =1;    //当前页
var recordPerPage = 15;   //每页显示的记录数
var toPage = 1;     
var pageCount;   //总记录数
//加载数据之前显示的加载中...图片.
var loadingBar = "<tr bgcolor='#FFFFFF' align='center'><td colspan='25'><div><img src=\"resources/images/submit-bar.gif\"></div></td></tr>";
var options={
    data:{toPage:toPage,recordPerPage:recordPerPage},
    //数据返回格式
    dataType:"json",
    //是否同步
    async:false,
    //成功回调函数
    beforeSubmit:function(){
        $("#infoTable>tbody").html(loadingBar);
    },
    success:function(data){
        currentPage = toPage;
        pageCount = setPageCount(data.totalCount);
        $("#totalCount").html(data.totalCount);
        buildTable(data);
        buildPageSpan();
    }
};

$(document).ready(function(){
    $("#go").click(jumpPage);
    toggleSelected();
    $("#querySubmit").click(function(){
    	toPage = 1;
        goPage(toPage);
    });
    $("#queryForm").submit(function(){
        $(this).ajaxSubmit(options);
        return false;
    });
    goPage(1);
});

function buildPageSpan(){
    var tempHtml ="";
    if(pageCount==0) {
        tempHtml = "("+0+"/"+pageCount+")";
    } else {
        tempHtml = "("+currentPage+"/"+pageCount+")";
    }        
    $("#pageSpan").html(tempHtml);
}

function setPageCount(totalCount){
    var totalPage = 0;
    if(totalCount%recordPerPage==0){
        totalPage = parseInt(totalCount/recordPerPage);
    }else{
        totalPage = parseInt(totalCount/recordPerPage)+1;
    }
    return totalPage;
}

function nextPage(){
    if(currentPage>=pageCount) {
        return;
    }
    toPage++;
    goPage(toPage);
}
function prevPage(){
    if(currentPage<=1) {
        return;
    }
    toPage--;
    goPage(toPage);
}
function goPage(pageIndex){
    if(pageIndex>=pageCount) {
        pageIndex = pageCount;
    }        
    if(pageIndex<=1) {
        pageIndex = 1;
    }        
    options.data.toPage = toPage;
    options.data.recordPerPage = recordPerPage;
    $("#queryForm").submit();
}
function topPage(){
    if(currentPage ==1){
        return;
    }
    toPage = 1;
    goPage(toPage);
}
function endPage(){
    if(currentPage == pageCount){
        return;
    }
    toPage = pageCount;
    goPage(toPage);
}
function jumpPage(){
    var pageIndex = $.trim($("#pageIndex").val());
    if(isNaN(pageIndex)){
        alert("请输入合法的页码");
        return;
    }
    if(pageIndex==""||pageIndex==null) {
        return;
    }
    if(pageIndex == currentPage || pageIndex < 1 || pageIndex > pageCount){
        return;
    }
    toPage = pageIndex;
    $("#pageIndex").val("");
    goPage(pageIndex);    
}

//各行换色,鼠标移动换色
function setTableStyle(){
	$(".showTable>tbody>tr").hover(function(){
    	$(this).removeClass("trColor");
        $(this).addClass("hover");
    }, function(){
        $(this).removeClass("hover");
        $(".showTable>tbody>tr:odd").addClass("trColor");
    });
    $(".showTable>tbody>tr:odd").addClass("trColor");
}

/**
 * 实现全选和取消全选
 */
function toggleSelected(){
    $("#allSelect").click(function(){
        if($(this).attr("checked")){
            $("#infoTable>tbody").find(".chbox").each(function(){
                $(this).attr("checked", true);
            });
        }
        else{
            $("#infoTable>tbody").find(".chbox").each(function(){
                $(this).attr("checked", false);
            });
        }
    });
}

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
