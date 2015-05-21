/**
 * 文字溢出,裁剪掉尾部文字,拼接...鼠标悬停用浮层显示
 */
;
(function($) {
	$.fn.ellipsis = function() {

		this.css({
			'overflow' : 'hidden',
			'text-overflow' : 'ellipsis',
			'white-space' : 'nowrap'
		});

		this.bind('mouseenter', function() {
			var $this = $(this);
			if (this.offsetWidth < this.scrollWidth) {
				$this.attr('title', $this.text());
			}
		});
	};
})(jQuery);

/**
 * 扩展dataTables -> myDataTable 1.提供默认配置：布局、分页、汉化、服务端数据设置、表格长字符串截短
 * 2.扩展配置参数paramSelector 用于获取查询参数 3.使用示例(需要先引入jquery.dataTables.js):
 * 
 * var table = $("#_rule_table").myDataTable({ "sAjaxSource" : './rules',
 * //服务端数据请求url "paramSelector" : '#_business,#_policy,#_status', //查询参数
 * "aoColumns" : [...] //列配置不变 });
 */
;
(function($) {

	var methods = {
		"init" : function(cfg) {
			var default_settings = {
				"bInfo" : true,
				"bServerSide" : true,
				"bPaginate" : true,
				"bFilter" : false,
				"aaSorting" : [],
				"bProcessing" : false,
				"bLengthChange" : true,
				"iDisplayLength" : 10,
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
					var table = this;
					this.find('tr td,tr th').each(function() {
						$(this).ellipsis();
					});
				}
			};
			var settings = $.extend(default_settings, cfg);
			if (settings['bServerSide'] && !settings.fnServerData && settings.paramSelector) {
				settings.fnServerData = function(sSource, aoData, fnCallback) {
					$(settings.paramSelector).each(function() {
						var name = $(this).attr("name");
						var value = $(this).val();
						name = name ? name : '';
						if ($(this).attr("type") == "radio") {
							value = $("[name='" + name + "']:checked").val();
						} else if ($(this).attr("type") == "checkbox") {
							// value = $("[name='"+name+"']:checked").val();
							value = $(this).prop("checked");
						} else {
							value = value ? value : '';
						}
						aoData.push({
							name : name,
							value : value
						});
					});
					$.ajax({
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
								alert("DataTables warning: JSON data from server could not be parsed. " + "This is caused by a JSON formatting error.");
							}
						}
					});
				};
			}
			if (!settings['bServerSide']) {
				settings.fnServerData = function(sSource, aoData, fnCallback) {
				};
			}
			if (settings['aaData']) {
				$.each(settings['aaData'], function(i, row) {
					if (row) {
						$.each(row, function(j, data) {
							if (data == undefined || data == null) {
								settings['aaData'][i][j] = '';
							}
						});
					}
				});
			}
			
			var table = this.dataTable(settings);
			this.on('dblclick', '.sorting,.sorting_desc,.sorting_asc', function(e) {
				table.fnSort([]);
				return false;
			});
			
			return table;
		}
	};

	$.fn.myDataTable = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.myDataTable');
		}
	};
})(jQuery);

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
				swf : window.uploadify_swf_url || ctx + "/static/uploadify/uploadify.swf",
				uploader : window.uploadify_upload_url || ctx + "/globalUpload/upload",
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
						cfg.callback.call($this, file, data, response, $.renderSize(file.size));
					}
					var bindInputId = $this.attr('bind');
					if (bindInputId) {
						$('#' + bindInputId).attr("value", data);
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

			if (!/.*jsessionid.*/.test(settings.uploader) && typeof jsessionid !== 'undefined') {
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
				var bindInputId = $this.attr('bind');
				if (bindInputId) {
					$('#' + bindInputId).attr("value", "");
				}
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
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
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
				swf : window.uploadify_swf_url || ctx + "/static/uploadify/uploadify.swf",
				uploader : window.uploadify_upload_url || ctx + "/globalUpload/upload",
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
						cfg.callback.call($this, file, data, response, $.renderSize(file.size));
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

			if (!/.*jsessionid.*/.test(settings.uploader) && typeof jsessionid !== 'undefined') {
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
			$.each(src, function(i, a) {
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
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
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
				swf : window.uploadify_swf_url || ctx + "/static/uploadify/uploadify.swf",
				uploader : window.uploadify_upload_url || ctx + "/globalUpload/upload",
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
						cfg.callback.call($$this, file, data, response, $.renderSize(file.size));
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
			if (!/.*jsessionid.*/.test(settings.uploader) && typeof jsessionid !== 'undefined') {
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
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
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
			var tempNumber = parseInt((numberRound * Math.pow(10, roundDigit) + 0.5)) / Math.pow(10, roundDigit);
			return tempNumber;
		} else {
			numberRound1 = -numberRound
			var tempNumber = parseInt((numberRound1 * Math.pow(10, roundDigit) + 0.5)) / Math.pow(10, roundDigit);
			return -tempNumber;
		}
	}

	$.renderSize = function(rawSize, decimal) {
		if (typeof rawSize == 'undefined' || null == rawSize || rawSize == '' || rawSize < 0) {
			return "未知";
		}
		if (typeof decimal == 'undefined' || !decimal) {
			decimal = 0;
		}
		if (rawSize < 1024 * 50) {
			return "50K以下";
		}
		var unitArr = new Array("Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB");
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
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
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
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
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
				url : ctx + '/globalVersion/getVersionNos',
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
			this.autocomplete("widget").css({
				'max-height' : 120,
				'max-width' : this.outerWidth(),
				'overflow-y' : 'auto'
			});
			return this;
		}
	};

	$.fn.versionAutoComplete = function(method) {
		if (methods[method]) {
			return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
		} else if (typeof method === 'object' || !method) {
			return methods.init.apply(this, arguments);
		} else {
			$.error('Method ' + method + ' does not exist on jQuery.versionAutoComplete');
		}
	};
})(jQuery);


