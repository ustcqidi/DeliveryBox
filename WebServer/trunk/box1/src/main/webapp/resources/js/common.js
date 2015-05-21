/********************************************************/
/*公共的js文件，包括一些公用的方法和工具方法
/*
/********************************************************/



/**
 * 判断字符串是否为空
 */
function isEmpty(str){
    str = $.trim(str);
    if(str!=null&&str.length==0)
        return true;
    else if(str=="")
        return true
    else
        return false;
}

$(function(){
    addValidatorMethod();
});

/**
 * 初始化验证规则
 */
function addValidatorMethod(){
    //电话号码
    jQuery.validator.addMethod("tel",function(value,element){
        var tel = /(^\d{3,4}-?\d{7,9}-?\d{4})|(^\d{3,4}-?\d{7,9})$/;
        return this.optional(element) || (tel.test(value));
    },"请填写正确的电话号码");

    //手机
    jQuery.validator.addMethod("mobile",function(value,element){
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(14[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        return this.optional(element) || (mobile.test(value));
    },"请填写正确的手机号码");

    //邮编
    jQuery.validator.addMethod("postCode",function(value,element){
        var postcode = /^[0-9]{6}$/;
        return this.optional(element) || (postcode.test(value));
    },"请填写正确的邮编");
    
    //IP
    jQuery.validator.addMethod("ip",function(value,element){
        var ip = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
        return this.optional(element) || (ip.test(value)) && (RegExp.$1<256 &&  RegExp.$2<256  && RegExp.$3<256 &&  RegExp.$4<256  );
    },"请填写正确的IP");

    //money
    jQuery.validator.addMethod("money",function(value,element){
        var money = /^\d{1,12}(?:\.\d{1,2})?$/;
        return this.optional(element) || (money.test(value));
    },"请填写正确的金额");

    //username
    jQuery.validator.addMethod("username",function(value,element){
        var username = /^[A-Za-z0-9_]+$/;
        return this.optional(element) || (username.test(value));
    },"用户名不符合规则");

    //password
    jQuery.validator.addMethod("password",function(value,element){
        var password = /^[A-Za-z0-9]+$/;
        return this.optional(element) || (password.test(value));
    },"密码不符合规则");

    

}


/**
 *打开遮罩
 */
function openMask(){
    /**
     *覆盖层
     */
    var mask = "<div id=\"coverMask\" style=\"filter:Alpha(opacity=50);text-align: center;\">"+"<img src=\""+basePath+"resources/images/submit-bar.gif\"/>"+"</div>";
    $("body").append(mask);
    $("#coverMask").css({
        left:"0px",
        top:"0px",
        height:document.body.clientHeight+"px",
        width:document.body.clientWidth+"px",
        "z-index":"1000",
        position:"absolute",
        "background-color":"#ABABAB"
    });
    $("#coverMask>img").css({
        "margin-top":document.body.clientHeight/2+"px"
    });
}
//关闭遮罩
function closeMask(){
    $("#coverMask").remove();
}

function cutStr(str,len){ //字符串截取
    var str_length = 0;
    var str_len = 0;
    str_cut = new String();
    str_len = str.length;
    for(var i = 0;i<str_len;i++){
        a = str.charAt(i);
        str_length++;
        if(escape(a).length > 4){
            //中文字符的长度经编码之后大于4
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if(str_length>=len){
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
    return  str_cut;
}

