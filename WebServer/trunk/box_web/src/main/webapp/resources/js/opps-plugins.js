

/**
 * upload image component 依赖 uploadify
 */
;
(function($) {
	var methods = {
		"init" : function(cfg) {
			var $this = this;
			this.addClass("img-upload-wrap");
			var id = 'img-upload-' + new Date().getTime();
			this.data("upload-id", id);
			this.empty().append("<div id='{0}'></div>".format(id));

			var $clear = $("<div class='clear'><button class='btn btn-danger btn-mini' type='button'>删除</button></div>");
			this.append($clear);

			if (cfg && cfg.url) {
				cfg.uploader = cfg.url;
			}
			var ctx = window.ctx || "";

			var default_settings = {
				swf : ctx + "/static/uploadify/uploadify.swf",
				uploader : ctx + "/upload",
				width : 80,
				height : 80,
				multi : false,
				fileTypeExts : '*.jpg;*.jpeg;*.bmp;*.gif;*.png',
				fileTypeDesc : 'Image(*.jpg;*.bmp;*.gif;*.png)',
				fileObjName : "file",
				buttonText : '选择图片',
				removeTimeout : 0, // 完成时清除队列显示秒数
				removeCompleted : true,
				onInit : function() {
					$("#" + id + "-queue").hide();
					$('#' + id + '-button').remove();
					$('#' + id).css({
						position : 'absolute',
						width : '100%',
						height : '100%',
						left : 0,
						top : 0,
						margin : 0,
						cursor : 'pointer'
					}).attr("title", "点击上传").find(".swfupload").css({
						left : 0,
						top : 0
					});
				},
				onUploadStart : function() {
					$("#" + id + "-queue").show();
				},
				onUploadComplete : function() {
					$("#" + id + "-queue").hide();
				},
				onUploadSuccess : function(file, data, response) {
					if (typeof data == 'string') {
						var $img = $this.find("img");
						if ($img.size() == 0) {
							$img = $("<img>");
							$this.append($img);
						}
						$img.attr("src", data);
					}
					if (cfg && typeof cfg.callback == 'function') {
						cfg.callback.call($this, file, data, response, $
								.renderSize(file.size));
					}
				},
				onUploadError : function(file, errorCode, errorMsg, errorString) {
					alert('文件 ' + file.name + ' 上传失败: ' + errorString);
				}
			};
			var settings = $.extend(default_settings, cfg);
			this.css({
				width : settings.width,
				height : settings.height
			});

			if (!/.*jsessionid.*/.test(settings.uploader)
					&& typeof jsessionid !== 'undefined') {
				settings.uploader = settings.uploader + ';jsessionid=' + jsessionid;
			}
			$('#' + id).uploadify(settings);

			this.on("mouseover", function() {
				if ($this.hasClass("disabled"))
					return;
				if ($this.find("img").size() > 0) {
					var $btn = $this.find(".clear button");
					$btn.hide();
					$this.find(".clear").show('fast', function() {
						var clearHeight = $this.find(".clear").outerHeight();
						var btnHeight = $btn.outerHeight();
						$btn.css({
							'margin-top' : (clearHeight - btnHeight) / 2
						});
						$btn.show();
					});
				}
			})
			this.on("mouseleave", function() {
				if ($this.hasClass("disabled"))
					return;
				$this.find(".clear").hide('fast');
			})
			this.find(".clear button").click(function() {
				$this.find("img").remove();
				if (typeof settings.onDelete == 'function') {
					settings.onDelete.call($this);
				}
				return false;
			})

			return this;
		},
		"destroy" : function() {
			var $this = this;
			var id = this.data("upload-id");
			if (id) {
				$('#' + id).uploadify('destroy');
			}
			this.empty();
			this.removeData("upload-id");
			this.removeClass("img-upload-wrap");
			this.removeClass("disabled");
			return this;
		},
		"setReadOnly" : function(flag) {
			var $this = this;
			if (flag) {
				$this.addClass("disabled");
				var id = this.data("upload-id");
				if (id) {
					$('#' + id).hide();
				}
			} else {
				$this.removeClass("disabled");
				var id = this.data("upload-id");
				if (id) {
					$('#' + id).show();
				}
			}
			return this;
		},
		"setImage" : function(src) {
			var $this = this;
			if (!src) {
				$this.find("img").remove();
				return this;
			}
			var $img = $this.find("img");
			if ($img.size() == 0) {
				$img = $("<img>");
				$this.append($img);
			}
			$img.attr("src", src);
			return this;
		},
		"getImage" : function() {
			var $this = this;
			return $this.find("img").attr("src");
		}
	};

	$.fn.uploadImage = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.uploadImg');
		}
	};
})(jQuery);

/**
 * multi images upload 依赖 uploadify
 */

;
(function($) {
	var methods = {
		"init" : function(cfg) {
			var $this = this;
			this.addClass("imgs-upload-wrap");
			var id = 'imgs-upload-' + new Date().getTime();
			this.data("upload-id", id);
			this.empty().append("<div id='{0}'></div>".format(id));

			if (cfg && cfg.url) {
				cfg.uploader = cfg.url;
			}
			var ctx = window.ctx || "";

			var default_settings = {
				swf : ctx + "/static/uploadify/uploadify.swf",
				uploader : ctx + "/upload",
				width : 320,
				height : 80,
				multi : true,
				fileTypeExts : '*.jpg;*.jpeg;*.bmp;*.gif;*.png',
				fileTypeDesc : 'Image(*.jpg;*.bmp;*.gif;*.png)',
				fileObjName : "file",
				buttonText : '选择图片',
				removeTimeout : 0, // 完成时清除队列显示秒数
				removeCompleted : true,
				onInit : function() {
					$("#" + id + "-queue").hide();
					$('#' + id + '-button').remove();
					$('#' + id).css({
						width : 60,
						height : 30
					}).attr("title", "点击上传").addClass("imgs-upload-btn");
				},
				onUploadStart : function() {
					$("#" + id + "-queue").show();
				},
				onUploadComplete : function() {
					$("#" + id + "-queue").hide();
				},
				onUploadSuccess : function(file, data, response) {
					if (typeof data == 'string') {
						var $wrap = $("<div class='img-upload-wrap'></div>");
						var $clear = $("<div class='clear'><button class='btn btn-danger btn-mini' type='button'>删除</button></div>");
						var $img = $("<img>").attr("src", data);
						$wrap.append($clear).append($img);
						$this.append($wrap);
						$wrap.css({
							width : $this.innerHeight() - 5,
							height : $this.innerHeight() - 5
						});
					}
					if (cfg && typeof cfg.callback == 'function') {
						cfg.callback.call($this, file, data, response, $
								.renderSize(file.size));
					}
				},
				onUploadError : function(file, errorCode, errorMsg, errorString) {
					alert('文件 ' + file.name + ' 上传失败: ' + errorString);
				}
			};
			var settings = $.extend(default_settings, cfg);
			this.css({
				width : settings.width,
				height : settings.height
			});
			settings.width = 60;
			settings.height = 30;

			if (!/.*jsessionid.*/.test(settings.uploader)
					&& typeof jsessionid !== 'undefined') {
				settings.uploader = settings.uploader + ';jsessionid=' + jsessionid;
			}
			$('#' + id).uploadify(settings);

			this.on("mouseover", ".img-upload-wrap", function() {
				var $wrap = $(this);
				if ($this.hasClass("disabled"))
					return;
				if ($wrap.find("img").size() > 0) {
					var $btn = $wrap.find(".clear button");
					$btn.hide();
					$wrap.find(".clear").show('fast', function() {
						var clearHeight = $wrap.find(".clear").innerHeight();
						var btnHeight = $btn.outerHeight();
						$btn.css({
							'margin-top' : (clearHeight - btnHeight) / 2
						});
						$btn.show();
					});
				}
			})
			this.on("mouseleave", ".img-upload-wrap", function() {
				var $wrap = $(this);
				if ($this.hasClass("disabled"))
					return;
				$wrap.find(".clear").hide('fast');
			})
			this.on("click", ".img-upload-wrap .clear button", function() {
				$(this).closest(".img-upload-wrap").remove();
				if (typeof settings.onDelete == 'function') {
					settings.onDelete.call($this);
				}
				return false;
			})

			return this;
		},
		"destroy" : function() {
			var $this = this;
			var id = this.data("upload-id");
			if (id) {
				$('#' + id).uploadify('destroy');
			}
			this.empty();
			this.removeData("upload-id");
			this.removeClass("imgs-upload-wrap");
			this.removeClass("disabled");
			return this;
		},
		"setReadOnly" : function(flag) {
			var $this = this;
			if (flag) {
				$this.addClass("disabled");
				var id = this.data("upload-id");
				if (id) {
					$('#' + id).hide();
				}
			} else {
				$this.removeClass("disabled");
				var id = this.data("upload-id");
				if (id) {
					$('#' + id).show();
				}
			}
			return this;
		},
		"setImages" : function(src) {
			var $this = this;
			$this.find(".img-upload-wrap").remove();
			if (!src) {
				return this;
			}
			if (typeof src == 'string') {
				src = src.split(";");
			}
			if (!src.length)
				return;
			$
					.each(
							src,
							function(i, a) {
								if (!a)
									return true;
								var $wrap = $("<div class='img-upload-wrap'></div>");
								var $clear = $("<div class='clear'><button class='btn btn-danger btn-mini' type='button'>删除</button></div>");
								var $img = $("<img>").attr("src", a);
								$wrap.append($clear).append($img);
								$this.append($wrap);
								$wrap.css({
									width : $this.innerHeight() - 5,
									height : $this.innerHeight() - 5
								});
							});
			return this;
		},
		"getImages" : function() {
			var $this = this;
			var imgs = [];
			$this.find(".img-upload-wrap").each(function(i) {
				var src = $(this).find("img").attr("src");
				if (src)
					imgs.push(src);
			});
			return imgs;
		}
	};

	$.fn.uploadImages = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.uploadImages');
		}
	};
})(jQuery);

/**
 * upload file component 依赖 uploadify
 */
;
(function($) {
	var methods = {
		"init" : function(cfg) {
			var $this = this;
			var id = $this.attr("id");
			if (cfg && cfg.url) {
				cfg.uploader = cfg.url;
			}
			var ctx = window.ctx || "";
			var default_settings = {
				swf : ctx + "/static/uploadify/uploadify.swf",
				uploader : ctx + "/upload",
				fileObjName : "file",
				width : 440,
				buttonText : '选择文件',
				onInit : function() {
					var $$this = $("#" + id).addClass("upload-file");
					$("#" + id + "-queue").hide();
					var $btn = $("#" + id + "-button").css({
						width : 65
					});
					$btn.empty().addClass("upload-file-btn");
					var $f = $("<input name='fileUrl'>");
					$$this.append($f);
					$f.css({
						position : 'absolute',
						'z-index' : 1,
						left : 65,
						top : 0,
						width : $$this.width() - 70,
						height : $$this.innerHeight(),
						border : 0,
						padding : 0,
						color : '#808080',
						outline : 'none',
						'box-shadow' : 'none'
					});
					if (cfg && typeof cfg.editable == 'boolean' && !cfg.editable) {
						$f.prop("readonly", true);
					}
				},
				multi : false,
				removeCompleted : true,
				removeTimeout : 0,
				fileTypeExts : '*.*',
				fileTypeDesc : 'Please select a file',
				onUploadSuccess : function(file, data, response) {
					var $$this = $("#" + id);
					if (typeof data == 'string') {
						$$this.find("input[name=fileUrl]").val(data);
					}
					if (cfg && typeof cfg.callback == 'function') {
						cfg.callback.call($$this, file, data, response, $
								.renderSize(file.size));
					}

				},
				onUploadComplete : function() {
					$("#" + id).unmask();
				},
				onUploadStart : function() {
					$("#" + id).unmask();
					$("#" + id).mask("上传中,请稍候...", 500);
				},
				onUploadError : function(file, errorCode, errorMsg, errorString) {
					alert('文件 ' + file.name + ' 上传失败: ' + errorString);
				}
			};
			var settings = $.extend(default_settings, cfg);
			if (!/.*jsessionid.*/.test(settings.uploader)
					&& typeof jsessionid !== 'undefined') {
				settings.uploader = settings.uploader + ';jsessionid=' + jsessionid;
			}
			this.uploadify(settings);
			return $("#" + id);
		},
		"setFile" : function(file) {
			var $this = this;
			this.find("input[name=fileUrl]").val(file);
			return this;
		},
		"getFile" : function() {
			var $this = this;
			return this.find("input[name=fileUrl]").val();
		},
		"setReadOnly" : function(flag) {
			var $this = this;
			if (flag) {
				this.find(".upload-file-btn,object").hide();
				this.find("input[name=fileUrl]").css({
					left : 0,
					width : $this.width() - 5,
					'background-color' : '#eee'
				}).prop("readonly", true);
				this.css({
					'background-color' : '#eee'
				});
			} else {
				this.find("input[name=fileUrl]").css({
					left : 65,
					width : $this.width() - 70,
					'background-color' : '#fff'
				}).prop("readonly", false);
				this.find(".upload-file-btn,object").show();
				this.css({
					'background-color' : '#fff'
				});
			}
			return this;
		},
		"destroy" : function() {
			var $this = this;
			this.removeClass("upload-file");
			this.uploadify('destroy');
			return this;
		}
	};

	$.fn.uploadFile = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.uploadFile');
		}
	};
})(jQuery);

/**
 * 计算文件大小的插件
 */
;
(function($) {
	/*
	 * 四舍五入保留小数位数 numberRound 被处理的数 roundDigit 保留几位小数位
	 */
	function roundFun(numberRound, roundDigit) {
		if (numberRound >= 0) {
			var tempNumber = parseInt((numberRound * Math.pow(10, roundDigit) + 0.5))
					/ Math.pow(10, roundDigit);
			return tempNumber;
		} else {
			numberRound1 = -numberRound
			var tempNumber = parseInt((numberRound1 * Math.pow(10, roundDigit) + 0.5))
					/ Math.pow(10, roundDigit);
			return -tempNumber;
		}
	}

	$.renderSize = function(rawSize, decimal) {
		if (typeof rawSize == 'undefined' || null == rawSize || rawSize == ''
				|| rawSize < 0) {
			return "未知";
		}
		if (typeof decimal == 'undefined' || !decimal) {
			decimal = 0;
		}
		if (rawSize < 1024 * 50) {
			return "50K以下";
		}
		var unitArr = new Array("Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB",
				"YB");
		var index = 0;
		var srcsize = parseFloat(rawSize);
		var quotient = srcsize;
		while (quotient >= 1024) {
			index += 1;
			quotient = quotient / 1024;
		}
		return roundFun(quotient, decimal) + unitArr[index];
	};
})(jQuery);

/** number only text input */
;
(function($) {
	var methods = {

		"init" : function(cfg) {
			var $this = this;
			var default_settings = {
				type : 'integer',
				placeholder : '输入数字'
			};
			var settings = $.extend(default_settings, cfg);
			$this.attr("placeholder", settings.placeholder);
			this.on("keyup", function(e) {
				switch (settings.type) {
				case 'integer':
				case 'int':
					this.value = this.value.replace(/[^0-9]/g, '');
					break;
				case 'float':
				case 'double':
					this.value = this.value.replace(/[^0-9\.]/g, '');
					break;
				default:
					this.value = this.value.replace(/[^0-9]/g, '');
					break;
				}
			});
			return this;
		}

	};

	$.fn.inputNumber = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.inputNumber');
		}
	};
})(jQuery);

/**
 * $.flashColor 闪烁颜色
 */
;
(function($) {
	var methods = {
		"init" : function(cfg) {
			var $this = this;
			var default_settings = {
				target : 'background-color',
				color : '#E44',
				interval : 100,
				callback : function() {
				}
			};
			var settings = $.extend(default_settings, cfg);
			var old = this.css(settings.target);
			var flashColorTimeout = $this.data("flashColorTimeout");
			if (flashColorTimeout)
				return;
			flashColorTimeout = setTimeout(function() {
				$this.css(settings.target, settings.color);
				setTimeout(function() {
					$this.css(settings.target, old);
					setTimeout(function() {
						$this.css(settings.target, settings.color);
						setTimeout(function() {
							$this.css(settings.target, old);
							$this.removeData("flashColorTimeout");
							if (typeof settings.callback == 'function') {
								settings.callback.call($this);
							}
						}, 100);
					}, 100);
				}, 100);
			}, 100);
			$this.data('flashColorTimeout', flashColorTimeout);
			return this;
		}
	};

	$.fn.flashColor = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.flashColor');
		}
	};

})(jQuery);

/**
 * 将一个input text 变成版本选择框,并能够自动完成 依赖jquery UI
 */
;
(function($) {

	var methods = {
		"init" : function(cfg) {
			var $this = this;
			var default_settings = {
				url : ctx + '/getVersionNos',
				mustMatch : true
			};
			var settings = $.extend(default_settings, cfg);
			this.autocomplete({
				source : function(request, response) {
					var data = [ {
						name : 'versionNo',
						value : $this.val()
					} ];
					if (settings.productSelector) {
						data.push({
							name : 'bizId',
							value : $(settings.productSelector).val()
						});
					}
					if (settings.platformSelector) {
						data.push({
							name : 'osId',
							value : $(settings.platformSelector).val()
						});
					}
					$.ajax({
						url : settings.url,
						dataType : 'json',
						data : data,
						success : function(data) {
							var vers = $.map(data, function(n) {
								return n.versionNo;
							});
							response(vers);
						}
					});
				},
				autoFocus : true,
				change : function(e, ui) {
					if (settings.mustMatch && !ui.item) {
						$(this).val('');
					}
				}
			});
			this.autocomplete( "widget" ).css({
				'max-height' : 120,
				'max-width' : this.outerWidth(),
				'overflow-y' : 'auto'
			});
			return this;
		}
	};

	$.fn.versionAutoComplete = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method
					+ ' does not exist on jQuery.versionAutoComplete');
		}
	};
})(jQuery);

/*
 * channelDialog 将一个div 变成渠道选择框 依赖dataTables
 * 
 */
;
(function($) {
	var methods = {
		"init": function(cfg) {
			var $this = this;
			var default_settings = {
				autoOpen: false,
				modal: true,
				width: 800,
				height: 560,
				title: '选择渠道',
				url: ctx + '/getChannelList',
				allUrl: ctx + '/getAllChannelNo',
				multi: true
			};
			var settings = $.extend(default_settings, cfg);
			var allChannelNo = '';
			var allChannelName = '';

			var updateCount = function(count) {
				$this.dialog({
					title: '{0}(已选{1})'.format(settings.title, count)
				});
			};

			var q = $("<div class='form-inline'>" + "<div class='control-group inline'>" + "<label class='inline field'>渠道号：</label>" + "<input type='text' class='input-medium' name='queryText' placeholder='渠道号/名称'>" + "</div>" + "<div class='control-group inline'>" + "<label class='inline field'>选择状态：</label>" + "<select class='input-medium' name='queryStatus'>" + "<option value=''>全部</option>" + "<option value='1'>已选择</option>" + "<option value='0'>未选择</option>" + "</select>" + "</div>" + "<button type='button' class='btn btn-primary pull-right' name='queryBtn'>查询</button>" + "</div>");
			q.appendTo(this);
			var queryText = q.find("input[name='queryText']");
			var queryStatus = q.find("select[name='queryStatus']");
			var queryBtn = q.find("button[name='queryBtn']");

			var t = $("<table class='table table-hover table-bordered table-condensed table-striped'></table>");
			t.appendTo(this);
			// 初始化table
			var table = t.dataTable({
				"sAjaxSource": settings.url,
				"bInfo": true,
				"bServerSide": true,
				"bPaginate": true,
				"bFilter": false,
				"aaSorting": [],
				"bProcessing": false,
				"bLengthChange": true,
				"aLengthMenu": [[10, 20, 50, 500, 1000], [10, 20, 50, 500, 1000]],
				"sPaginationType": "full_numbers",
				"bStateSave": true,
				"bAutoWidth": false,
				"sDom": '<"top"f>r<"datatable-scroll"t><"datatable-bottom"lip>',
				"oLanguage": {
					"sLengthMenu": "<div style='float:left;margin-left:0px;'>_MENU_</div>",
					"sZeroRecords": "抱歉， 没有找到",
					"sInfo": "共_TOTAL_条 / _PAGENUM_页",
					"sInfoEmpty": "共 0 条数据/共 0 页！",
					"sInfoFiltered": "(filtered from _MAX_ total records)",
					"sProcessing": "加载中...",

					oPaginate: {
						sPrevious: "上一页",
						sNext: "下一页",
						sFirst: "首页",
						sLast: "尾页"
					}
				},
				"fnDrawCallback": function(oSettings) {
					t.find(".select-all input, .select-toggle input").prop("checked", false);
					t.find("tbody tr td input[type='checkbox']").change(function() {
						var checked = $(this).prop("checked");
						var g_dfNos = t.data("g_dfNos");
						var g_dfNames = t.data("g_dfNames");
						g_dfNos = g_dfNos ? g_dfNos.split(',') : [];
						g_dfNames = g_dfNames ? g_dfNames.split(',') : [];
						if (checked) {
							g_dfNos.push($(this).val());
							g_dfNames.push($(this).closest("tr").find("td:eq(2)").text());
							if (!settings.multi) {
								g_dfNos = [$(this).val()];
								g_dfNames = [$(this).closest("tr").find("td:eq(2)").text()];
								t.find("tbody tr td input[type=checkbox]").not(this).prop("checked", false);
							}
						} else {
							var idx = $.inArray($(this).val(), g_dfNos);
							g_dfNos.splice(idx, 1);
							g_dfNames.splice(idx, 1);
						}
						t.data("g_dfNos", g_dfNos.join(","));
						t.data("g_dfNames", g_dfNames.join(","));
						updateCount(g_dfNos.length);
						// $this.dialog("option", {
						// title: '{0}(已选{1})'.format(settings.title,
						// g_dfNos.length)
						// });
					});
					t.find('tr td,tr th').each(function() {
						$(this).ellipsis();
					});
					t.find('.sorting,.sorting_desc,.sorting_asc').dblclick(function(e) {
						table.fnSort([]);
						return false;
					});
				},
				"fnServerData": function(sSource, aoData, fnCallback) {
					aoData.push({
						name: 'queryText',
						value: $.trim(queryText.val())
					});
					if (queryStatus.val() != '') {
						aoData.push({
							name: 'queryStatus',
							value: queryStatus.val()
						});
						aoData.push({
							name: 'querySelected',
							value: t.data("g_dfNos")
						});
					}
					if (settings.productSelector) {
						aoData.push({
							name: 'bizId',
							value: $.trim($(settings.productSelector).val())
						});
					}
					if (settings.platformSelector) {
						aoData.push({
							name: 'osId',
							value: $.trim($(settings.platformSelector).val())
						});
					}
					$.ajax({
						"type": "post",
						"url": sSource,
						"data": aoData,
						maskTarget: $(this).parents('.dataTables_wrapper')[0],
						"success": function(json) {
							fnCallback(json);
						},
						"dataType": "json",
						"cache": false,
						"error": function(xhr, error, thrown) {
							if (error == "parsererror") {
								alert("DataTables warning: JSON data from server could not be parsed. " + "This is caused by a JSON formatting error.");
							}
						}
					});
				},
				"aoColumns": [{
					// 0 id
					bSortable: false,
					sWidth: 60,
					sTitle: settings.multi ? "<label class='checkbox inline select-all'>全选 <input type='checkbox'></label>" + "<label class='checkbox inline select-toggle'>反选 <input type='checkbox'></label>" : "选择",
					fnRender: function(obj) {
						var id = obj.aData[1];
						var g_dfNos = t.data("g_dfNos");
						g_dfNos = g_dfNos ? g_dfNos.split(',') : [];
						var idx = $.inArray(id, g_dfNos);
						if (idx >= 0) {
							return "<input type='checkbox' value='{0}' checked='checked'>".format(id);
						} else {
							return "<input type='checkbox' value='{0}'>".format(id);
						}
					}
				}, {
					// 1 no
					bSortable: true,
					sName: 'downFromNo',
					sWidth: 80,
					sTitle: '渠道号'
				}, {
					// 2 name
					bSortable: false,
					sWidth: 120,
					sTitle: '渠道名称'
				}, {
					// 3 desc
					bSortable: false,
					sWidth: 120,
					sTitle: '描述'
				}]
			});

			t.find(".select-all input[type='checkbox']").click(function() {
				var checked = $(this).prop("checked");
				var g_dfNos = t.data("g_dfNos");
				var g_dfNames = t.data("g_dfNames");
				g_dfNos = g_dfNos ? g_dfNos.split(',') : [];
				g_dfNames = g_dfNames ? g_dfNames.split(',') : [];
				if (checked) {// 全选
					t.find("tbody tr td input[type='checkbox']").each(function() {
						if ($(this).prop("checked") == false) {
							g_dfNos.push($(this).val());
							g_dfNames.push($(this).closest("tr").find("td:eq(2)").text());
						}
						$(this).prop({
							checked: checked
						});
					})
				} else {// 全不选
					t.find("tbody tr td input[type='checkbox']").each(function() {
						if ($(this).prop("checked") == true) {
							var idx = $.inArray($(this).val(), g_dfNos);
							g_dfNos.splice(idx, 1);
							g_dfNames.splice(idx, 1);
						}
						$(this).prop({
							checked: checked
						});
					})
				}
				t.data("g_dfNos", g_dfNos.join(","));
				t.data("g_dfNames", g_dfNames.join(","));
				updateCount(g_dfNos.length);
			});
			t.find(".select-toggle input[type='checkbox']").click(function() {
				var g_dfNos = t.data("g_dfNos");
				var g_dfNames = t.data("g_dfNames");
				g_dfNos = g_dfNos ? g_dfNos.split(',') : [];
				g_dfNames = g_dfNames ? g_dfNames.split(',') : [];
				t.find("tbody tr td input[type='checkbox']").each(function() {
					var checked = $(this).prop("checked");
					if (checked == false) {
						g_dfNos.push($(this).val());
						g_dfNames.push($(this).closest("tr").find("td:eq(2)").text());
					} else if (checked == true) {
						var idx = $.inArray($(this).val(), g_dfNos);
						g_dfNos.splice(idx, 1);
						g_dfNames.splice(idx, 1);
					}
					$(this).prop('checked', !checked);
				});
				t.data("g_dfNos", g_dfNos.join(","));
				t.data("g_dfNames", g_dfNames.join(","));
				updateCount(g_dfNos.length);
			});
			queryBtn.click(function() {
				table.fnDraw();
			});
			queryText.keyup(function(e) {
				if (e.keyCode == 13) {
					table.fnDraw();
				}
			});
			// 初始化对话框
			this.dialog({
				autoOpen: settings.autoOpen,
				modal: settings.modal,
				width: settings.width,
				height: settings.height,
				title: settings.title,
				open: function() {
					queryText.val('');
					queryStatus.val('');
					allChannelNo = '';
					allChannelName = '';
					var g_dfNos = $this.data("g_dfNos") || "";
					var g_dfNames = $this.data("g_dfNames") || "";
					if (settings.valueSelector) {
						g_dfNos = $(settings.valueSelector).val();
					}
					if (settings.textSelector) {
						g_dfNames = $(settings.textSelector).val();
					}
					t.data("g_dfNos", g_dfNos);
					t.data("g_dfNames", g_dfNames);
					g_dfNos = g_dfNos ? g_dfNos.split(',') : [];
					updateCount(g_dfNos.length);
					table.fnDraw();

					$.ajax({
						type: "post",
						url: settings.allUrl,
						data: {
							bizId: $.trim($(settings.productSelector).val()),
							osId: $.trim($(settings.platformSelector).val())
						},
						maskTarget: t.parents('.dataTables_wrapper')[0],
						dataType: "json",
						cache: false,
						async: false
					}).done(function(data) {
						var channelNo = [];
						var channelName = [];
						for (var i = 0, length = data.length; i < length; i++) {
							channelNo.push(data[i][0]);
							channelName.push(data[i][1]);
						}
						allChannelNo = channelNo.join(',');
						allChannelName = channelName.join(',');
					});
				},
				buttons: {
					"确定": function() {
						$this.data("g_dfNos", t.data("g_dfNos") || "");
						$this.data("g_dfNames", t.data("g_dfNames") || "");
						if (settings.valueSelector) {
							$(settings.valueSelector).val($this.data("g_dfNos") || "");
						}
						if (settings.textSelector) {
							$(settings.textSelector).val($this.data("g_dfNames") || "");
						}
						$this.dialog("close");
					},
					"取消": function() {
						$this.dialog("close");
					},
					"全选": function() {
						var currentDfNo = t.data("g_dfNos");
						var currentDfName = t.data("g_dfNames");
						if (currentDfNo == allChannelNo) {
							currentDfNo = '';
							currentDfName = '';
						} else {
							currentDfNo = allChannelNo;
							currentDfName = allChannelName;
						}
						t.data("g_dfNos", currentDfNo);
						t.data("g_dfNames", currentDfName);
						currentDfNo = currentDfNo ? currentDfNo.split(',') : [];
						updateCount(currentDfNo.length);
						t.fnDraw(false);
					},
					"反选": function() {
						var allDfNoArr = allChannelNo.split(',');
						var allDfNameArr = allChannelName.split(',');
						var currentDfNoArr = t.data("g_dfNos").split(',');
						var targetDfNoArr = [];
						var targetDfNameArr = [];
						for (var i = 0, length = allDfNoArr.length; i < length; i++) {
							if ($.inArray(allDfNoArr[i], currentDfNoArr) == -1) {
								targetDfNoArr.push(allDfNoArr[i]);
								targetDfNameArr.push(allDfNameArr[i]);
							}
						}
						t.data("g_dfNos", targetDfNoArr.join(','));
						t.data("g_dfNames", targetDfNameArr.join(','));
						updateCount(targetDfNoArr.length);
						t.fnDraw(false);
					}
				}
			});
			return this;
		},
		"setValue": function(val) {
			this.data("g_dfNos", val || "");
			this.find("table").data("g_dfNos", val || "");
			return this;
		},
		"setText": function(text) {
			this.data("g_dfNames", text || "");
			this.find("table").data("g_dfNames", val || "");
			return this;
		},
		"getValue": function() {
			return this.data("g_dfNos") || "";
		},
		"getText": function() {
			return this.data("g_dfNames") || "";
		}
	};

	$.fn.channelDialog = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.channelDialog');
		}
	};
})(jQuery);


/*
 * ugDialog 将一个div 变成用户群选择框 依赖dataTables
 * 
 */
;
(function($) {

	var methods = {
		"init" : function(cfg) {
			var $this = this;
			var default_settings = {
				autoOpen : false,
				modal : true,
				width : 800,
				height : 560,
				title : '选择用户群',
				url : ctx + '/getUgList',
				multi : true
			};
			var settings = $.extend(default_settings, cfg);
			var q = $("<div class='form-inline'></div>");
			q.appendTo(this);
			var cg = $("<div class='control-group inline'></div>");
			cg.appendTo(q);
			var queryText = $("<input type='text' class='input-medium' name='queryText' placeholder='名称'>");
			queryText.appendTo(cg);
			
			cg = $("<div class='control-group inline'><label class='inline field'>类型:</label></div>");
			cg.appendTo(q);
			var queryType = $("<select class='input-medium' name='type'></select>");
			queryType.append("<option value='0'>区域</option>");
			queryType.append("<option value='1'>清单</option>");
			queryType.appendTo(cg);
			var queryBtn = $("<button type='button' class='btn btn-primary' style='margin-left: 30px;'>查询</button>");
			queryBtn.appendTo(q);
			var t = $("<table class='table table-hover table-bordered table-condensed table-striped'></table>");
			t.appendTo(this);
			// 初始化table
			var table = t
					.dataTable({
						"sAjaxSource" : settings.url,
						"bInfo" : true,
						"bServerSide" : true,
						"bPaginate" : true,
						"bFilter" : false,
						"aaSorting" : [],
						"bProcessing" : false,
						"bLengthChange" : true,
						"aLengthMenu" : [ [ 10, 20, 50, 500, 1000 ], [ 10, 20, 50, 500, 1000 ] ],
						"sPaginationType" : "full_numbers",
						"bStateSave" : false,
						"bAutoWidth" : false,
						"sDom" : '<"top"f>r<"datatable-scroll"t><"datatable-bottom"lip>',
						"oLanguage" : {
							"sLengthMenu" : "<div style='float:left;margin-left:0px;'>_MENU_</div>",
							"sZeroRecords" : "抱歉， 没有找到",
							"sInfo" : "共_TOTAL_条 / _PAGENUM_页",
							"sInfoEmpty" : "共 0 条数据/共 0 页！",
							"sInfoFiltered" : "(filtered from _MAX_ total records)",
							"sProcessing" : "加载中...",

							oPaginate : {
								sPrevious : "上一页",
								sNext : "下一页",
								sFirst : "首页",
								sLast : "尾页"
							}
						},
						"fnDrawCallback" : function(oSettings) {
							t.find(".select-all input[type='checkbox']").prop({
								checked : false
							});
							t.find("tbody tr td input[type='checkbox']").change(
									function() {
										var checked = $(this).prop("checked");
										var ug_ids = t.data("ug_ids");
										var ug_names = t.data("ug_names");
										ug_ids = ug_ids ? ug_ids.split(',') : [];
										ug_names = ug_names ? ug_names.split(',') : [];

										if (checked) {
											ug_ids.push($(this).val());
											ug_names.push($(this).closest("tr").find("td:eq(1)")
													.text());
											if (!settings.multi) {
												ug_ids = [ $(this).val() ];
												t.find("tbody tr td input[type=checkbox]").not(this)
														.prop("checked", false);
											}
										} else {
											var idx = $.inArray($(this).val(), ug_ids);
											ug_ids.splice(idx, 1);
											ug_names.splice(idx, 1);
										}
										t.data("ug_ids", ug_ids.join(","));
										t.data("ug_names", ug_names.join(","))
										$this.dialog("option", {
											title : '{0}(已选{1})'
													.format(settings.title, ug_ids.length)
										});
									});
							t.find('tr td,tr th').each(function() {
								$(this).ellipsis();
							});
							t.find('.sorting,.sorting_desc,.sorting_asc').dblclick(
									function(e) {
										table.fnSort([]);
										return false;
									});
							if(typeof settings.drawCallback == 'function') {
								settings.drawCallback.call($this);
							}
						},
						"fnServerData" : function(sSource, aoData, fnCallback) {
							aoData.push({
								name : 'queryText',
								value : $.trim(queryText.val())
							}, {
								name : 'type',
								value : $.trim(queryType.val())
							});
							if (settings.productSelector) {
								aoData.push({
									name : 'bizId',
									value : $.trim($(settings.productSelector).val())
								});
							}
							if (settings.platformSelector) {
								aoData.push({
									name : 'osId',
									value : $.trim($(settings.platformSelector).val())
								});
							}
							$
									.ajax({
										"type" : "post",
										"url" : sSource,
										"data" : aoData,
										maskTarget : $(this).parents('.dataTables_wrapper')[0],
										"success" : function(json) {
											fnCallback(json);
										},
										"dataType" : "json",
										"cache" : false,
										"error" : function(xhr, error, thrown) {
											if (error == "parsererror") {
												alert("DataTables warning: JSON data from server could not be parsed. "
														+ "This is caused by a JSON formatting error.");
											}
										}
									});
						},
						"aoColumns" : [
								{
									// 0 id
									bSortable : false,
									sWidth : 60,
									sTitle : settings.multi ? "<label class='checkbox inline select-all'>全选 <input type='checkbox'></label>"
											: "选择",
									fnRender : function(obj) {
										var id = obj.aData[0] + '';
										var ug_ids = t.data("ug_ids");
										ug_ids = ug_ids ? ug_ids.split(',') : [];
										var idx = $.inArray(id, ug_ids);
										if (idx >= 0) {
											return "<input type='checkbox' value='{0}' checked='checked'>"
													.format(id);
										} else {
											return "<input type='checkbox' value='{0}'>".format(id);
										}
									}
								}, {
									// 1 name
									bSortable : false,
									sWidth : 80,
									sTitle : '用户群'
								}, {
									// 2 type
									bSortable : false,
									sWidth : 60,
									sTitle : '类型',
									fnRender : function(obj) {
										var type = obj.aData[2];
										switch (type) {
										case 1:
										case '1':
											return "清单";
										case 0:
										case '0':
											return "区域";
										}
										return type;
									}
								}, {
									// 3 desc
									bSortable : false,
									sWidth : 120,
									sTitle : '描述'
								} ]
					});

			t.find(".select-all input[type='checkbox']").click(function() {
				var checked = $(this).prop("checked");
				var ug_ids = t.data("g_dfNos");
				var ug_names = t.data("ug_names");
				ug_ids = ug_ids ? ug_ids.split(',') : [];
				ug_names = ug_names ? ug_names.split(',') : [];
				if (checked) {// 全选
					t.find("tbody tr td input[type='checkbox']").each(function() {
						if ($(this).prop("checked") == false) {
							ug_ids.push($(this).val());
							ug_names.push($(this).closest('tr').find('td:eq(1)').text());
						}
						$(this).prop({
							checked : checked
						});
					})
				} else {// 全不选
					t.find("tbody tr td input[type='checkbox']").each(function() {
						if ($(this).prop("checked") == true) {
							var idx = $.inArray($(this).val(), ug_ids);
							ug_ids.splice(idx, 1);
							ug_names.splice(idx, 1);
						}
						$(this).prop({
							checked : checked
						});
					})
				}
				t.data("ug_ids", ug_ids.join(","));
				t.data("ug_names", ug_names.join(","));
				$this.dialog("option", {
					title : '{0}(已选{1})'.format(settings.title, ug_ids.length)
				});
			});
			queryBtn.click(function() {
				table.fnDraw();
			});
			queryText.keyup(function(e) {
				if (e.keyCode == 13) {
					table.fnDraw();
				}
			});
			queryType.change(function(){
				table.fnDraw();
			});
			// 初始化对话框
			this.dialog({
				autoOpen : settings.autoOpen,
				modal : settings.modal,
				width : settings.width,
				height : settings.height,
				title : settings.title,
				open : function() {
					var ug_ids = $this.data("ug_ids") || "";
					var ug_names = $this.data("ug_names") || "";
					if (settings.valueSelector) {
						ug_ids = $(settings.valueSelector).val();
					}
					if (settings.textSelector) {
						ug_names = $(settings.textSelector).val();
					}
					t.data("ug_ids", ug_ids);
					t.data("ug_names", ug_names);
					ug_ids = ug_ids ? ug_ids.split(',') : [];
					$this.dialog("option", {
						title : '{0}(已选{1})'.format(settings.title, ug_ids.length)
					});
					table.fnDraw();
				},
				buttons : {
					"确定" : function() {
						$this.data("ug_ids", t.data("ug_ids") || "");
						$this.data("ug_names", t.data("ug_names") || "");
						if (settings.valueSelector) {
							$(settings.valueSelector).val($this.data("ug_ids") || "");
						}
						if (settings.textSelector) {
							$(settings.textSelector).val($this.data("ug_names") || "");
						}
						$this.dialog("close");
					},
					"取消" : function() {
						$this.dialog("close");
					}
				}
			});
			return this;
		},
		"setValue" : function(val) {
			this.data("ug_ids", val || "");
			this.find("table").data("ug_ids", val || "");
			return this;
		},
		"setText" : function(text) {
			this.data("ug_names",text||"");
			this.find("table").data("ug_names",text||"");
			return this;
		},
		"getValue" : function() {
			return this.data("ug_ids") || "";
		},
		"getText" : function(){
			return this.data("ug_names")||"";
		}
	};

	$.fn.ugDialog = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments,
					1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.ugDialog');
		}
	};
})(jQuery);
