/**
 * 此js文件中部分过程依赖date.js
 */

//给Date类型增加格式化原型方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}

Date.prototype.toMyDateString = function (cycleFlag) {
    var myDateString
    if (cycleFlag == 'w' || cycleFlag == '2' || cycleFlag=='Week') {
        var date = new Date(this.getTime());
        date.setMonth(0);
        date.setDate(1);
        var weekNo = parseInt((this.getTime() - date.getTime() + (date.getDay() == 0 ? 6 : (date.getDay() - 1)) * 86400000) / 604800000 + 1);   //7*24*3600*1000 = 604800000  //24*3600*1000=86400000
        var weekStart = new Date(this.getTime() - (this.getDay() == 0 ? 6 : (this.getDay() - 1)) * 86400000);
        var weekEnd = new Date(weekStart.getTime() + 518400000); //518400000=6*24*3600*1000
        myDateString = this.getFullYear() + '年第' + weekNo + '周（' + (weekStart.getMonth() + 1) + '/' + weekStart.getDate() + '-' + (weekEnd.getMonth() + 1) + '/' + weekEnd.getDate() + '）';
    } else if (cycleFlag == 'm' || cycleFlag == '3' || cycleFlag=='Month') {
        myDateString = this.getFullYear() + '年' + (this.getMonth() + 1) + '月';
    } else {
        myDateString = this.format(_date_format_show);
    }
    return myDateString;
}

Date.prototype.toWeekDate = function () {
    return (new Date(this.getTime() - (this.getDay() == 0 ? 6 : (this.getDay() - 1)) * 86400000));
}

Date.prototype.toMonthDate = function () {
    this.setDate(1);
    return this;
}

String.prototype.format = function()
{
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,                
        function(m,i){
            return args[i];
        });
}

var _date_format_show = "yyyy-MM-dd";
var _date_format_value = "yyyy-MM-dd";
var _date_format_parse = "yyyy/MM/dd";

var _date_format_show_month = "yyyy-MM";

//----begin---CycleDatePicker.js
(function ($) {
    var _day_picker_id_fmt = "d_p_{0}";
    var _day_picker_selector_fmt = "#d_p_{0}";

    var _week_picker_id_fmt = "w_p_{0}";
    var _week_picker_selector_fmt = "#w_p_{0}";

    var _month_picker_id_fmt = "m_p_{0}";
    var _month_picker_selector_fmt = "#m_p_{0}";

    /**
    控件选中日期事件
    */
    var picked = function (target, dp) {
        var eleId = target.id.substr(target.id.indexOf("_p_") + "_p_".length);
        var date_select = new Date(dp.cal.getNewDateStr(_date_format_parse));
        $("#{0}".format(eleId)).attr("value", date_select.toMyDateString(target.id.split('_')[0]));
        $(_day_picker_selector_fmt.format(eleId)).attr("value", date_select.format(_date_format_show));
        $(_week_picker_selector_fmt.format(eleId)).attr("value", (new Date(date_select.getTime() - (date_select.getDay() == 0 ? 6 : (date_select.getDay() - 1)) * 86400000)).format(_date_format_show)); //86400000=24*3600*1000
        date_select.setDate(1);
        $(_month_picker_selector_fmt.format(eleId)).attr("value", date_select.format(_date_format_show));
    };

    var defaultSetting = {
        daySetting: { isShowClear: false },
        weekSetting: {
            disabledDays: [1, 2, 3, 4, 5, 6],
            firstDayOfWeek: 1,
            isShowWeek: true,
            isShowClear: false
        },
        monthSetting: {
            opposite: true,
            disabledDates: ['....-..-01'],
            firstDayOfWeek: 1,
            isShowWeek: true,
            isShowClear: false
        },
        initDate: undefined
    }

    /**
    创建提供时间选择的日期控件
    */
    function createPickerEle(idfmt, setting, eleId) {
        var element = $("#{0}".format(eleId));
        setting.el = idfmt.format(eleId);
        var settOnpicked = setting.onpicked;
        setting.onpicked = function (dp) {
            picked(this, dp);
            settOnpicked.call(this, dp.cal.getNewDateStr(_date_format_show));
            setting.afterPicked.call(this);
        }
        setting.oncleared = function () {
            var eleId = this.id.substr(this.id.indexOf("_p_") + "_p_".length);
            $("#{0}".format(eleId)).attr("value", "");
            settOnpicked.call(this, "");
            setting.afterPicked.call(this);
        }
        $("<input type='hidden'>")
        .attr("id", idfmt.format(eleId))
        .insertAfter(element);

        $("#{0}".format(idfmt.format(eleId))).data("setting", setting);
    }
    /**
    显示
    */
    function show(target, cycleFlag) {
        var setting = $.data(target, 'options');

        if (cycleFlag == 1 || cycleFlag.toString().toLowerCase() == "day" ) {
            WdatePicker($(_day_picker_selector_fmt.format(target.id)).data("setting"));
        }
        else if (cycleFlag == 2 || cycleFlag.toString().toLowerCase() == "week") {
            WdatePicker($(_week_picker_selector_fmt.format(target.id)).data("setting"));
        }
        else if (cycleFlag == 3 || cycleFlag.toString().toLowerCase() == "month") {
            WdatePicker($(_month_picker_selector_fmt.format(target.id)).data("setting"));
        }

        /**var eleId = target.id;
        if (cycleFlag.toString().toLowerCase() == "day" || cycleFlag == 1) {
        $(_day_picker_selector_fmt.format(eleId))
        .attr("value", $("#{0}".format(eleId)).val())
        .trigger("focus");
        }
        else if (cycleFlag.toString().toLowerCase() == "week" || cycleFlag == 2) {
        $(_week_picker_selector_fmt.format(eleId))
        .attr("value", $("#{0}".format(eleId)).val())
        .trigger("focus");
        }
        else if (cycleFlag.toString().toLowerCase() == "month" || cycleFlag == 3) {
        $(_month_picker_selector_fmt.format(eleId))
        .attr("value", $("#{0}".format(eleId)).val())
        .trigger("focus");
        }*/
    }
    /**
    创建picker的html内容，根据cycleType的不同创建不同的picker
    */
    function createPicker(target) {
        var setting = $.data(target, 'options');
        var eleId = target.id;
        var pickerSetting;

        pickerSetting = setting.daySetting
        pickerSetting.afterPicked = setting.afterPicked;
        createPickerEle(_day_picker_id_fmt, pickerSetting, eleId);
        pickerSetting = setting.weekSetting;
        pickerSetting.afterPicked = setting.afterPicked;
        createPickerEle(_week_picker_id_fmt, pickerSetting, eleId);

        pickerSetting = setting.monthSetting;
        pickerSetting.afterPicked = setting.afterPicked;
        createPickerEle(_month_picker_id_fmt, pickerSetting, eleId);
        /**
        注册事件，当控件被选中时，调用配置提供的计算CycleFlag的方法，并根据返回值显示对应的日期选择控件
        */
        $("#{0}".format(eleId)).bind("click", function () {
            if (setting.getCycleFlag) {
                var cycleFlag = setting.getCycleFlag.call(this);
                if (cycleFlag) {
                    show(target, cycleFlag);
                }
            }
        });
    }

    var methods = {
        /**
        初始化
        分别创建 日 自然周 自然月 的日期选择控件，并注册事件，显示根据CycleFlg的值决定显示不同的日期选择控件
        */
        init: function (options) {
            return this.each(function () {
                var setting = $.extend(true, {}, defaultSetting, options);
                $.data(this, 'options', setting);
                var date = setting.initDate;
                if (date) {
                    var cycle = 1;
                    var defaultDate;
                    if (cycle == 1) {
                        defaultDate = date;
                        this.value = date.format(_date_format_show);
                        setting.daySetting.startDate = date.format(_date_format_show);
                        setting.daySetting.onpicked.call(this, this.value);
                    } else if (cycle == 2) {
                        defaultDate = new Date(date.getTime() - date.getDay() * 1000 * 3600 * 24);
                    } else if (cycle == 3) {
                        defaultDate = date.setDate(1);
                    }
                }
                createPicker(this);
            });
        },
        /**
        显示控件
        */
        show: function (cycleFlag) {
            return this.each(function () {
                show(this, cycleFlag);
            });
        }
    };

    $.fn.CycleDatePicker = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.call(this, method);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.DateIntervalPicker');
        }
    }
})(jQuery);
//----end---CycleDatePicker.js
//----begin---DateIntervalPicker.js
//DateIntervalPicker 定义开始

 var _beginDate = new Date(2011,00,01);
(function ($) {
    var _picker_id_fmt = "dateIntervalPicker_{0}";
    var _picker_id_selector_fmt = "#dateIntervalPicker_{0}";

    var _picker_beginDateInput_id_fmt = "dateIntervalPicker_{0}_beginDateInput";
    var _picker_beginDateInput_id_selector_fmt = "#dateIntervalPicker_{0}_beginDateInput";


    var _picker_endDateInput_id_fmt = "dateIntervalPicker_{0}_endDateInput";
    var _picker_endDateInput_id_selector_fmt = "#dateIntervalPicker_{0}_endDateInput";

    /**
        创建picker的html内容，根据cycleType的不同创建不同的picker
    */
    function createPicker(target) {
        var setting = $.data(target, 'options');
        var pcikerId = _picker_id_fmt.format(target.id);
        $('<div class="dateIntervalPicker"></div>')
            .append('<a class="shotcutPicker" days="1">昨天</a>')
            .append('<a class="shotcutPicker" days="7">近7天</a>')
            .append('<a class="shotcutPicker" days="30">近30天</a>')
            .append('<a class="lastShotcutPicker" days="all">所有</a>')
            .append('<span>自定义：从</span><span class="input"><input class="beginDateInput" id="{0}_beginDateInput" type="text" value="{1}"/></span>'.format(pcikerId,setting.beginDate))
            .append('<span style="padding:0px 5px;">到</span><span class="input"><input class="endDateInput" id="{0}_endDateInput" type="text" value="{1}"/></span>'.format(pcikerId,setting.endDate))
            .append('<span class="okBtn"><input type="button" class="btn btn-small btn-primary" value="确定"></span>')
            .append('<span class="cancleBtn"><input type="button" class="btn btn-small btn-primary" value="取消"></span>')
            .insertAfter($(target)).attr("id", pcikerId);
    }
    /**
        绑定事件，如快捷方式事件，确定、取消按钮事件，输入框事件等
    */
    function bindEvent(target) {
        var setting = $.data(target, 'options');
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));
            $targetPicker.find(".shotcutPicker,.lastShotcutPicker").click(function (event) {
                var days = $(event.srcElement||event.target).attr("days");
                if (days) {
                    var beginDate;
                    var endDate = new Date((new Date().getTime()-1000*3600*24));
                    if (days == "all") { 
                        beginDate = _beginDate;
                    }else {
                        beginDate = new Date(endDate.getTime() - days * 1000 * 3600 * 24);
                    }

                     $(event.srcElement||event.target).siblings(".input")
                     .each(function (i, ite) {
                        var item = $(ite).children()[0];
                        var className = item.className;
                        if (className.indexOf("beginDateInput") > -1) {
                            item.value = beginDate.format(_date_format_show);
                        } else if (className.indexOf("endDateInput") > -1) {
                            item.value = endDate.format(_date_format_show);
                        }
                     
                    });
                    picked(target,beginDate,endDate);
                } else { 
                    return false;
                }
            });
        $targetPicker.find("input.beginDateInput").focus(function () {
             WdatePicker({ 
                 firstDayOfWeek: 1,
                 maxDate:'%y-%M-%d'
                 ,onpicked :function(dp){
                       var date = dp.cal.getDateStr();
                       var endEl = document.getElementById(dp.el.id.replace("beginDateInput","endDateInput"));
                       $targetPicker.find("input.beginDateInput").blur();
                       if(date > endEl.value) {
                            endEl.focus();
                       }
                 }   
             });
        });
        $targetPicker.find("input.endDateInput").focus(function () {
             WdatePicker({
                firstDayOfWeek: 1,
                maxDate:'%y-%M-%d'
                ,onpicked :function(dp){
                    var date = dp.cal.getDateStr();
                    var beginEl = document.getElementById(dp.el.id.replace("endDateInput","beginDateInput"));
                    $targetPicker.find("input.endDateInput").blur();
                    if(date < beginEl.value) {
                        beginEl.focus();
                    }
                } 
            });
        });
        $targetPicker.find("span.cancleBtn").click(function(){
           $targetPicker.fadeOut("normal");
        });
        $targetPicker.find("span.okBtn").click(function (event) {
                var beginDate = null, endDate = null;
                //确定按钮同级元素中开始时间输入框和结束时间输入框
                $(event.srcElement||event.target).parent().siblings(".input")
                .each(function (i, ite) {
                    var item = $(ite).children()[0];
                    var value = item.value;
                    var className = item.className;
                    if ($.trim(value).length >= 4) {

                        var ymd = value.split("-");
                        if (className.indexOf("beginDateInput") > -1) {
                            beginDate = new Date(ymd[0], ymd[1] - 1, ymd[2]);
                        } else if (className.indexOf("endDateInput") > -1) {
                            endDate = new Date(ymd[0], ymd[1] - 1, ymd[2]);
                        }
                    }
                });

                if (beginDate == null || endDate == null) {
                    alert("查询日期不能为空");
                } else if(beginDate>endDate){
                    alert("您选择结束日期小于起始日期");
                }
                else{
                    picked(target,beginDate, endDate);
                }

            });
    }
    /**
        处理选定日期区间，包括设置显示内容、回调初始化选项中的picked函数、通知对比时间选择器
    */
    function picked(target,beginDate,endDate){
   
        $(target).val(beginDate.format(_date_format_show) + "~" + endDate.format(_date_format_show));
        $(target).DateIntervalPicker("hide");
        $.data(target,"beginDate",beginDate);
        $.data(target,"endDate",endDate);
         
        var setting = $.data(target, 'options');
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));

        ///回调初始化选项中的picked函数
        setting.picked.call($targetPicker,beginDate,endDate); 
        ///通知对比时间选择器
        var list = $.data(target,"contrastSelectorList");
        $(list).each(function(i,item){
            $(item).ContrastDateIntervalPicker("changeInterval");
        });
        
    }

    var methods = {
        /**
            初始化，分别创建开始时间选择器、结束时间选择器、快捷选择方式、确定按钮、关闭按钮等，并注册事件
        */
        init: function (options) {
            var defaultSetting = {
                beginDate: new Date(new Date().getTime() - 31 * 1000 * 3600 * 24).format("yyyy-MM-dd"),
                endDate: new Date(new Date().getTime() - 1000 * 3600 * 24).format("yyyy-MM-dd"),
                picked: function (beginDate, endDate) {
                    $(".dateSearchForm>input[name='BeginDate']").val(beginDate.format(_date_format_value));
                    $(".dateSearchForm>input[name='EndDate']").val(endDate.format(_date_format_value));
                },
                contrastId: "",
                cycleType: "day"
            };
            return this.each(function () {
                var setting = $.extend(defaultSetting, options);
                $.data(this, 'options', setting);
                createPicker(this);

                $(this).click(function () {
                    $(this).DateIntervalPicker("show");
                });
                bindEvent(this);

                //默认选择初始化选项的开始时间和结束时间
                picked(this,new Date(Date.parse(setting.beginDate.replace(/-/g, "/"))),new Date(Date.parse(setting.endDate.replace(/-/g, "/"))));
            });
        },
        ///供外部模拟选择时间
        picked: function (beginDate, endDate) {
            return this.each(function () {
                picked(this,beginDate,endDate);
            });
        },
        ///返回选择器时间区间长度，毫秒计
        interval:function(targetId){
            var interval = -1;
            if($("#" + targetId).data("endDate")) {
                interval = $("#" + targetId).data("endDate").getTime() - $("#" + targetId).data("beginDate").getTime();
            }
            return interval;
        },
        ///返回周期类型，目前只支持day week month
        cycleType:function(targetId){
            return $("#" + targetId).data("options").cycleType;
        },
        ///显示
        show: function () {
            return this.each(function () {
                $(_picker_id_selector_fmt.format(this.id)).fadeIn("normal");
            });
        },
        ///隐藏
        hide:function(){
            return this.each(function(){
                $(_picker_id_selector_fmt.format(this.id)).fadeOut("normal");
            });
        },
        ///增加对比时间选择器，是对比时间选择器的Jquery selector 如"#contrastPicker"
        addContrast:function(selector){
            return this.each(function(){
                var list = $.data(this,"contrastSelectorList");
                if(!list) {
                    list = new Array();
                    $.data(this,"contrastSelectorList",list);
                }
                list.push(selector);
            });
        }
    };

    $.fn.DateIntervalPicker = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.call(this, method);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.DateIntervalPicker');
        }
    }
})(jQuery);
//----end---DateIntervalPicker.js
//----begin---CycleDateIntervalPicker.js
//CycleDateIntervalPicker 定义开始

var _beginDate = new Date(2013,00,01);
(function ($) {
    var _picker_id_fmt = "dateIntervalPicker_{0}";
    var _picker_id_selector_fmt = "#dateIntervalPicker_{0}";

    var _picker_beginDateInput_id_fmt = "dateIntervalPicker_{0}_beginDateInput";
    var _picker_beginDateInput_id_selector_fmt = "#dateIntervalPicker_{0}_beginDateInput";


    var _picker_endDateInput_id_fmt = "dateIntervalPicker_{0}_endDateInput";
    var _picker_endDateInput_id_selector_fmt = "#dateIntervalPicker_{0}_endDateInput";

    var _day_picker_id_fmt = "d_p_{0}";
    var _day_picker_selector_fmt = "#d_p_{0}";

    var _week_picker_id_fmt = "w_p_{0}";
    var _week_picker_selector_fmt = "#w_p_{0}";

    var _month_picker_id_fmt = "m_p_{0}";
    var _month_picker_selector_fmt = "#m_p_{0}";
    
    /**
    控件选中日期事件
    */
    var picked2 = function (target, dp) {
        var eleId = target.id.substr(target.id.indexOf("_p_") + "_p_".length);
        var date_select = new Date(dp.cal.getNewDateStr(_date_format_parse));
        //$("#{0}".format(eleId)).attr("value", date_select.toMyDateString(target.id.split('_')[0]));
        $("#{0}".format(eleId)).attr("value",date_select.format(_date_format_show));
        $(_day_picker_selector_fmt.format(eleId)).attr("value", date_select.format(_date_format_show));
        $(_week_picker_selector_fmt.format(eleId)).attr("value", (new Date(date_select.getTime() - (date_select.getDay() == 0 ? 6 : (date_select.getDay() - 1)) * 86400000)).format(_date_format_show)); //86400000=24*3600*1000
        date_select.setDate(1);
        $(_month_picker_selector_fmt.format(eleId)).attr("value", date_select.format(_date_format_show));
    };
    
    /**
    显示
    */
    function show2(target, eleId, cycleFlag) {
        var str="setting";

        $(_day_picker_selector_fmt.format(eleId)).data(str).el=_day_picker_id_fmt.format(eleId);
        $(_week_picker_selector_fmt.format(eleId)).data(str).el=_week_picker_id_fmt.format(eleId);
        $(_month_picker_selector_fmt.format(eleId)).data(str).el=_month_picker_id_fmt.format(eleId);
        
        if (cycleFlag == 0 || cycleFlag == 1 || cycleFlag.toString().toLowerCase() == "day" ) {
            WdatePicker($(_day_picker_selector_fmt.format(eleId)).data(str));
        }
        else if (cycleFlag == 2 || cycleFlag.toString().toLowerCase() == "week") {
            WdatePicker($(_week_picker_selector_fmt.format(eleId)).data(str));
        }
        else if (cycleFlag == 3 || cycleFlag.toString().toLowerCase() == "month") {
            WdatePicker($(_month_picker_selector_fmt.format(eleId)).data(str));
        }
    }
    
    /**
    创建提供时间选择的日期控件
    */
    function createPickerEle(idfmt, setting, eleId) {
        var element = $("#{0}".format(eleId));
        setting.el = idfmt.format(eleId);
        //var settOnpicked = setting.onpicked;
        setting.onpicked = function (dp) {
            picked2(this, dp);
            var date = dp.cal.getDateStr();
            var beginEl = document.getElementById(dp.el.id.substr(dp.el.id.indexOf("_p_") + "_p_".length).replace("endDateInput","beginDateInput"));
            var endEl = document.getElementById(dp.el.id.substr(dp.el.id.indexOf("_p_") + "_p_".length).replace("beginDateInput","endDateInput"));
            $("#{0}".format(dp.el.id)).blur();
            if(date > endEl.value) {
                 endEl.focus();
            }
            if(date < beginEl.value) {
                beginEl.focus();
           }
            //settOnpicked.call(this, dp.cal.getNewDateStr(_date_format_show));
            //setting.afterPicked.call(this);
        }
        setting.oncleared = function () {
            var eleId = this.id.substr(this.id.indexOf("_p_") + "_p_".length);
            $("#{0}".format(eleId)).attr("value", "");
            //settOnpicked.call(this, "");
            //setting.afterPicked.call(this);
        }
        $("<input type='hidden'>")
        .attr("id", idfmt.format(eleId))
        .insertAfter(element);

        $("#{0}".format(idfmt.format(eleId))).data("setting", setting);
    }
    
    /**
        创建picker的html内容，根据cycleType的不同创建不同的picker
    */
    function createPicker(target) {
        var setting = $.data(target, 'options');
        var pickerId = _picker_id_fmt.format(target.id);
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));

        $('<div class="dateIntervalPicker"></div>')
                .append($('<ul class="nav nav-tabs"></ul>').append("<input type='hidden' id='{0}_cycleType' value='1' />".format(pickerId))
                        .append('<li>&nbsp;&nbsp;&nbsp;</li>')
                      /*  .append('<li id="{0}_HOUR" class="active cyclePicker" value="0"><a href="#">按时统计</a></li>'.format(pickerId))*/
                        .append('<li id="{0}_DAY" class="active cyclePicker" value="1"><a href="#">按日统计</a></li>'.format(pickerId))
                        .append('<li id="{0}_WEEK" class="cyclePicker" value="2"><a href="#">按周统计</a></li>'.format(pickerId))
                        .append('<li id="{0}_MONTH" class="cyclePicker" value="3"><a href="#">按月统计</a></li>'.format(pickerId)))
                .append($('<div id="PickerContent"></div>').append('<a class="shotcutPicker" days="1">昨天</a>')
                        .append('<a class="shotcutPicker" days="7">近7天</a>')
                        .append('<a class="shotcutPicker" days="30">近30天</a>')
                        .append('<a class="lastShotcutPicker" days="all">所有</a>')
                        .append('<span>自定义：从</span><span class="input"><input class="beginDateInput" id="{0}_beginDateInput" type="text" value="{1}"/></span>'.format(pickerId, setting.beginDate))
                        .append('<span style="padding:0px 5px;">到</span><span class="input"><input class="endDateInput" id="{0}_endDateInput" type="text" value="{1}"/></span>'.format(pickerId, setting.endDate))
                        .append('<span class="okBtn"><input type="button" class="btn btn-small btn-primary" value="确定"></span>')
                        .append('<span class="cancleBtn"><input type="button" class="btn btn-small btn-primary" value="取消"></span>'))
                .insertAfter($(target)).attr("id", pickerId);
        
        

        var eleId = "{0}_beginDateInput".format(pickerId);
        var eleId2 = "{0}_endDateInput".format(pickerId);
        var pickerSetting;

        pickerSetting = setting.daySetting;
        pickerSetting.afterPicked = setting.afterPicked;
        createPickerEle(_day_picker_id_fmt, pickerSetting, eleId);
        createPickerEle(_day_picker_id_fmt, pickerSetting, eleId2);
        pickerSetting = setting.weekSetting;
        pickerSetting.afterPicked = setting.afterPicked;
        createPickerEle(_week_picker_id_fmt, pickerSetting, eleId);
        createPickerEle(_week_picker_id_fmt, pickerSetting, eleId2);
        pickerSetting = setting.monthSetting;
        pickerSetting.afterPicked = setting.afterPicked;
        createPickerEle(_month_picker_id_fmt, pickerSetting, eleId);
        createPickerEle(_month_picker_id_fmt, pickerSetting, eleId2);
        
    }
    /**
        绑定事件，如快捷方式事件，确定、取消按钮事件，输入框事件等
    */
    function bindEvent(target) {
        var setting = $.data(target, 'options');
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));
            $targetPicker.find(".cyclePicker").click(function(){
                $("#{0}".format(this.id)).siblings().removeClass("active");
                $("#{0}".format(this.id)).addClass("active");
                updateContent(target);
                $(target).trigger("cyclePickerClick");
            });
            $targetPicker.find(".shotcutPicker,.lastShotcutPicker").click(function (event) {
                var cycleFlag = getCycleType(target);
                var beginDate;
                var endDate;
                var tmpDate;
                
                if (cycleFlag == 0 || cycleFlag == 1 || cycleFlag.toString().toLowerCase() == "day" ) {
                    var days = $(event.srcElement||event.target).attr("days");
                    if (days) {
                        endDate = Date.today().addDays(-1);
                        beginDate = endDate.clone();
                        if (days == "all") {
                            beginDate = _beginDate;
                        }else {
                            beginDate.addDays(-days);
                        }
                    } else { 
                        return false;
                    }
                }
                else if (cycleFlag == 2 || cycleFlag.toString().toLowerCase() == "week") {
                    var weeks = $(event.srcElement||event.target).attr("weeks");
                    if (weeks) {
                        endDate = Date.today().addDays(-1).toWeekDate();
                        beginDate = endDate.clone();
                        if (weeks == "all") {
                            beginDate = _beginDate.toWeekDate();
                        }else {
                            beginDate.addWeeks(-weeks);
                        }

                    } else { 
                        return false;
                    }
                }
                else if (cycleFlag == 3 || cycleFlag.toString().toLowerCase() == "month") {
                    var months = $(event.srcElement||event.target).attr("months");
                    if (months) {
                        endDate = Date.today().addDays(-1).toMonthDate();
                        beginDate = endDate.clone();
                        if (months == "all") {
                            beginDate = _beginDate.toMonthDate();
                        }else {
                            beginDate.addMonths(-months);
                        }

                    } else { 
                        return false;
                    }
                }

                $(event.srcElement||event.target).siblings(".input")
                .each(function (i, ite) {
                   var item = $(ite).children()[0];
                   var className = item.className;
                   if (className.indexOf("beginDateInput") > -1) {
                       item.value = beginDate.format(_date_format_show);
                   } else if (className.indexOf("endDateInput") > -1) {
                       item.value = endDate.format(_date_format_show);
                   }
               });
                
                picked(target,beginDate,endDate,true);
                
            });
        $targetPicker.find("input.beginDateInput").focus(function () {
            var pickerId = _picker_id_fmt.format(target.id);
            var eleId = "{0}_beginDateInput".format(pickerId);
            var cycleFlag = getCycleType(target);
            if (typeof(cycleFlag)=="number") {
                show2(target,eleId, cycleFlag);
            }
        });
        $targetPicker.find("input.endDateInput").focus(function () {
            var pickerId = _picker_id_fmt.format(target.id);
            var eleId = "{0}_endDateInput".format(pickerId);
            var cycleFlag = getCycleType(target);
            if (typeof(cycleFlag)=="number") {
                show2(target,eleId, cycleFlag);
            }
        });
        $targetPicker.find("span.cancleBtn").click(function(){
           $targetPicker.fadeOut("normal");
        });
        $targetPicker.find("span.okBtn").click(function (event) {
                var beginDate = null, endDate = null;
                //确定按钮同级元素中开始时间输入框和结束时间输入框
                $(event.srcElement||event.target).parent().siblings(".input")
                .each(function (i, ite) {
                    var item = $(ite).children()[0];
                    var value = item.value;
                    var className = item.className;
                    if ($.trim(value).length >= 4) {

                        var ymd = value.split("-");
                        if (className.indexOf("beginDateInput") > -1) {
                            beginDate = new Date(ymd[0], ymd[1] - 1, ymd[2]);
                        } else if (className.indexOf("endDateInput") > -1) {
                            endDate = new Date(ymd[0], ymd[1] - 1, ymd[2]);
                        }
                    }
                });

                if (beginDate == null || endDate == null) {
                    alert("查询日期不能为空");
                } else if(beginDate>endDate){
                    alert("您选择结束日期小于起始日期");
                }
                else{
                    picked(target,beginDate, endDate, true);
                }
                var pcikerId = _picker_id_fmt.format(target.id);
                $("#{0}_cycleType".format(pcikerId)).val(getCycleType(target));

            });
        

        
        
    }
    /**
        处理选定日期区间，包括设置显示内容、回调初始化选项中的picked函数、通知对比时间选择器
    */
    function picked(target,beginDate,endDate,flag){
   
        $(target).val(beginDate.format(_date_format_show) + "~" + endDate.format(_date_format_show));
        if (flag)
            $(target).CycleDateIntervalPicker("hide");
        $.data(target,"beginDate",beginDate);
        $.data(target,"endDate",endDate);
         
        var setting = $.data(target, 'options');
        setting.beginDate=beginDate.format(_date_format_show);
        setting.endDate=endDate.format(_date_format_show);
        
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));

        ///回调初始化选项中的picked函数
        setting.picked.call($targetPicker,beginDate,endDate); 
        ///通知对比时间选择器
        var list = $.data(target,"contrastSelectorList");
        $(list).each(function(i,item){
            $(item).ContrastDateIntervalPicker("changeInterval");
        });
        
        
        beginDate=beginDate.clone();
        var pickerId = _picker_id_fmt.format(target.id);
        var eleId = "{0}_beginDateInput".format(pickerId);
        $("#{0}".format(eleId)).attr("value",beginDate.format(_date_format_show));
        $(_day_picker_selector_fmt.format(eleId)).attr("value", beginDate.format(_date_format_show));
        $(_week_picker_selector_fmt.format(eleId)).attr("value", (new Date(beginDate.getTime() - (beginDate.getDay() == 0 ? 6 : (beginDate.getDay() - 1)) * 86400000)).format(_date_format_show)); //86400000=24*3600*1000
        beginDate.setDate(1);
        $(_month_picker_selector_fmt.format(eleId)).attr("value", beginDate.format(_date_format_show));
        
        endDate=endDate.clone();
        var eleId2 = "{0}_endDateInput".format(pickerId);
        $("#{0}".format(eleId2)).attr("value",endDate.format(_date_format_show));
        $(_day_picker_selector_fmt.format(eleId2)).attr("value", endDate.format(_date_format_show));
        $(_week_picker_selector_fmt.format(eleId2)).attr("value", (new Date(endDate.getTime() - (endDate.getDay() == 0 ? 6 : (endDate.getDay() - 1)) * 86400000)).format(_date_format_show)); //86400000=24*3600*1000
        endDate.setDate(1);
        $(_month_picker_selector_fmt.format(eleId2)).attr("value", endDate.format(_date_format_show));
        
    }
    
    /*
     * 获取当前选中类型
     */
    function getCycleType(target)
    {
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));
        var value=$targetPicker.find("ul>li.active").val();
        return value;
    }
    
    /**
        根据cycleType更新内容
    */
    function updateContent(target)
    {
        var cycleType=getCycleType(target);
        var $targetPicker = $(_picker_id_selector_fmt.format(target.id));
        var dayContent=[{key:"昨日",value:"1"},{key:"近7日",value:"7"},{key:"近30日",value:"30"},{key:"所有",value:"all"}];
        var weekContent=[{key:"上周",value:"1"},{key:"近4周",value:"4"},{key:"近24周",value:"24"},{key:"所有",value:"all"}];
        var monthContent=[{key:"上月",value:"1"},{key:"近6月",value:"6"},{key:"近12月",value:"12"},{key:"所有",value:"all"}];
        if (cycleType == 3) {
            $targetPicker.find("#PickerContent").find("a").each(function(i,n){
                n.innerHTML=monthContent[i].key;
                $(n).attr("months",monthContent[i].value);
            });
            $(target).data("options").cycleType=3;
        }
        else if (cycleType == 2) {
            $targetPicker.find("#PickerContent").find("a").each(function(i,n){
                n.innerHTML=weekContent[i].key;
                $(n).attr("weeks",weekContent[i].value);
            });
            $(target).data("options").cycleType=2;
        }
        else if (cycleType == 1) {
            $targetPicker.find("#PickerContent").find("a").each(function(i,n){
                n.innerHTML=dayContent[i].key;
                $(n).attr("days",dayContent[i].value);
            });
            $(target).data("options").cycleType=1;
        }
        else if (cycleType == 0)
        {
            $targetPicker.find("#PickerContent").find("a").each(function(i,n){
                n.innerHTML=dayContent[i].key;
                $(n).attr("days",dayContent[i].value);
            });
            $(target).data("options").cycleType=0;
            
        }
        updateDate(target);
    }
    
    function updateDate(target)
    {
        var cycleType=getCycleType(target);
        var beginDate=$.data(target,"beginDate");
        var endDate=$.data(target,"endDate");
        if (cycleType==3)
        {
            beginDate.setDate(1);
            endDate.setDate(1);
        }
        else if (cycleType==2)
        {
            beginDate=new Date(beginDate.getTime() - (beginDate.getDay() == 0 ? 6 : (beginDate.getDay() - 1)) * 86400000);
            endDate=new Date(endDate.getTime() - (endDate.getDay() == 0 ? 6 : (endDate.getDay() - 1)) * 86400000);
        }
        
        picked(target, beginDate, endDate);
    }

    var methods = {
        /**
            初始化，分别创建开始时间选择器、结束时间选择器、快捷选择方式、确定按钮、关闭按钮等，并注册事件
        */
        init: function (options) {
            var defaultSetting = {
                beginDate: new Date(new Date().getTime() - 16 * 1000 * 3600 * 24).format("yyyy-MM-dd"),
                endDate: new Date(new Date().getTime() - 1000 * 3600 * 24).format("yyyy-MM-dd"),
                picked: function (beginDate, endDate) {
                    $(this).find("input.beginDateInput").val(beginDate.format(_date_format_value));
                    $(this).find("input.endDateInput").val(endDate.format(_date_format_value));
                },
                contrastId: "",
                cycleType: 0,
                daySetting: { isShowClear: false },
                weekSetting: {
                    disabledDays: [1, 2, 3, 4, 5, 6],
                    firstDayOfWeek: 1,
                    isShowWeek: true,
                    isShowToday:false,
                    isShowClear: false
                },
                monthSetting: {
                    dateFmt:'yyyy-MM',
                    opposite: true,
                    disabledDates: ['....-..-01'],
                    firstDayOfWeek: 1,
                    isShowWeek: true,
                    isShowToday:false,
                    isShowClear: false
                },
                initDate: undefined
            };
            return this.each(function () {
                var setting = $.extend(defaultSetting, options);
                $.data(this, 'options', setting);
                createPicker(this);

                $(this).click(function () {
                    $(this).CycleDateIntervalPicker("show");
                });
                bindEvent(this);

                var pickerId = _picker_id_fmt.format(this.id);
                var eleId = "{0}_beginDateInput".format(pickerId);
                var eleId2 = "{0}_endDateInput".format(pickerId);
                $("#{0}".format(eleId)).value=setting.beginDate.format(_date_format_show);
                $("#{0}".format(eleId2)).value=setting.endDate.format(_date_format_show);
                //默认选择初始化选项的开始时间和结束时间
                picked(this,new Date(Date.parse(setting.beginDate.replace(/-/g, "/"))),new Date(Date.parse(setting.endDate.replace(/-/g, "/"))),true);
            });
        },
        ///供外部模拟选择时间
        picked: function (beginDate, endDate) {
            return this.each(function () {
                picked(this,beginDate,endDate,true);
            });
        },
        ///返回选择器时间区间长度，毫秒计
        interval:function(targetId){
            var interval = -1;
            if($("#" + targetId).data("endDate")) {
                interval = $("#" + targetId).data("endDate").getTime() - $("#" + targetId).data("beginDate").getTime();
            }
            return interval;
        },
        ///返回周期类型，目前只支持day week month
        cycleType:function(targetId){
            return $("#" + targetId).data("options").cycleType;
        },
        ///显示
        show: function () {
            return this.each(function () {
                $(_picker_id_selector_fmt.format(this.id)).fadeIn("normal");
            });
        },
        ///隐藏
        hide:function(){
            return this.each(function(){
                $(_picker_id_selector_fmt.format(this.id)).fadeOut("normal");
            });
        },
        ///增加对比时间选择器，是对比时间选择器的Jquery selector 如"#contrastPicker"
        addContrast:function(selector){
            return this.each(function(){
                var list = $.data(this,"contrastSelectorList");
                if(!list) {
                    list = new Array();
                    $.data(this,"contrastSelectorList",list);
                }
                list.push(selector);
            });
        },
        
        //返回选择的时间
        getDate : function() {
            return {
                beginDate : $(this).data("options").beginDate,
                endDate : $(this).data("options").endDate
            };
        }
    };

  $.fn.CycleDateIntervalPicker = function (method) {
      if (methods[method]) {
          return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
      } else if (typeof method === 'object' || !method) {
          return methods.init.call(this, method);
      } else {
          $.error('Method ' + method + ' does not exist on jQuery.CycleDateIntervalPicker');
      }
  }
})(jQuery);
//----end---CycleDateIntervalPicker.js
//----begin---ContrastDateIntervalPicker
(function ($) {
    /**处理选中事件
    包括回调初始化选项中的picked函数
    设置显示内容
    */
    function picked(target, pickedDate) {
        $.data(target, 'beginDate', pickedDate);
        var setting = $.data(target, 'options');
        var cycleType = $("#" + setting.sourcePickerId).DateIntervalPicker("cycleType", setting.sourcePickerId);
        var interval = $("#" + setting.sourcePickerId).DateIntervalPicker("interval", setting.sourcePickerId);

        var endDate = new Date(pickedDate.getTime() + interval);new Date(pickedDate.getTime() + interval)
        $.data(target, 'endDate', endDate);

        setting.picked.call(target, pickedDate, endDate);

        if (cycleType == 'day') {
            target.value = pickedDate.format(_date_format_show) + "~" + endDate.format(_date_format_show);
        }
    }
    /**
        清除事件
    */

    function clear(target) {
        var setting = $.data(target, 'options');
        var cycleType = $("#" + setting.sourcePickerId).DateIntervalPicker("cycleType", setting.sourcePickerId);
        
        $(target).removeData("beginDate");
        setting.cleared.call(target);
        if (cycleType == 'day') {
            target.value = "";
        }
    }
    var methods = {
        /**
        初始化，创建时间选择器，并制定对应的查询时间选择器，在选中时间后，根据对应的查询时间自动计算时间段
        */
        init: function (setti) {
            var defaultSetting = {
                picked: function (beginDate, endDate) {
                    $(".contrastDateSearchForm>input[name='BeginDate']").val(beginDate.format(_date_format_value));
                    $(".contrastDateSearchForm>input[name='EndDate']").val(endDate.format(_date_format_value));
                },
                cleared: function () {
                    $(".contrastDateSearchForm>input[name='BeginDate']").val("");
                    $(".contrastDateSearchForm>input[name='EndDate']").val("");
                },
                sourcePickerId: ''
            };
            var selector = this.selector;
            return this.each(function () {
                var setting = $.extend(true,defaultSetting, setti);
                $.data(this, 'options', setting);
                this.readOnly = 'readOnly';
                ///向源时间选择器注册
                $("#" + setting.sourcePickerId).DateIntervalPicker("addContrast", selector);

                var wdate = $("<input type=\"hidden\" />");
                $(this).after(wdate);
                ///注册事件
                $(this).click(function (event) {
                    var interval = $("#" + setting.sourcePickerId).DateIntervalPicker("interval", setting.sourcePickerId);
                    ///如果时间间隔长度大于0 则说明源时间选择器已经选择时间，可以
                    if (interval >= 0) {
                        var cycleType = $("#" + setting.sourcePickerId).DateIntervalPicker("cycleType", setting.sourcePickerId);

                        if (cycleType == 'day') {
                            WdatePicker({ firstDayOfWeek: 1,
                                el: wdate[0],
                                onpicked: function (dp) {
                                    var pickedDateStr = dp.cal.getNewDateStr(_date_format_parse);
                                    var pickedDate = new Date(Date.parse(pickedDateStr));
                                    return picked(event.srcElement||event.target, pickedDate);
                                },
                                oncleared: function () {
                                    clear(event.srcElement||event.target);
                                }
                            });
                        } else if (cycleType == "week") {

                        } else if (cycleType == "month") {

                        }

                    } else {
                        alert("查询时间不能为空");
                    }
                });
            });
        },
        picked: function (beginDate, endDate) {
            return this.each(function () {
                //picked(this, beginDate, endDate);
            });
        },
        changeInterval: function () {
            return this.each(function () {
                if ($.data(this, 'beginDate')) {
                    picked(this, $.data(this, 'beginDate'));
                }
            });
        }
    };

    $.fn.ContrastDateIntervalPicker = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.call(this, method);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.ContrastDateIntervalPicker');
        }
    }
})(jQuery);
//----end---ContrastDateIntervalPicker