function autoPanel(item, id) {
	var resultStr = "";
	var resultTextStr = "";
	var $autoPanel = $("#" + id);
	var $wordInput = $("#" + item.id);
	var $autoName = $("#" + $autoPanel.attr("alt"));
	var wordInputOffset = $wordInput.offset();
	$autoPanel.hide().css("border", "1px black solid").css("position",
			"absolute");
	$autoPanel.css("top", wordInputOffset.top + $wordInput.height() + 6 + "px");
	$autoPanel.css("left", wordInputOffset.left + "px");
	$autoPanel.css("overflow-y", "auto").css("z-index", "100");
	$autoPanel.css("background-color", "white").width($wordInput.width() + 250)
			.height($wordInput.height() + 250);

	var selectType = "checkbox";
	selectType = $autoPanel.attr("selectType");
	// 复选框选择之后需要确认的div
	var $result = $("#result");
	var $ul = null;
	initSearch();
	init();
	function init() {
		$autoPanel.show();
		find();
	}

	// 初始化查询组件
	function initSearch() {
		var hr = "<hr color='#E6F2FE' style='border: .1em #E6F2FE solid;margin:0px'/>";
		$autoPanel.empty().append(hr);
		var $t = $("<table>", {
			id : 1,
			cellspacing : "0",
			cellpadding : "0",
			border : 0
		});
		$t.css("width", "100%").css("margin-bottom", "0px");
		var $tr = $("<tr>");
		var $td = $("<td>");
		$("#searchWord" + item.id).val("");
		var tmp = "<input type='text' style='margin:0px 4px 0 3px' size='20' id='searchWord"
				+ item.id
				+ "' name='searchWord"
				+ item.id
				+ "' maxlength='100' value=''>";
		$td.html(tmp).appendTo($t).css("padding", "0px").css("align", "center");
		var $f = $("<input>", {
			id : "f",
			type : "button"
		});
		if (!window.ActiveXObject) {
			$f.addClass("submit").css("margin", "0px 4px 0 3px").css(
					"padding-left", "4px").css("border-style", "none");
		} else {
			$f.addClass("submit").css("margin", "0px").css("padding",
					"5px 4 4 5").css("border-style", "none");

		}
		$f.attr("value", "查询");
		// 查询
		$f.bind("click", function() {
			find();
		});
		$f.appendTo($td);
		var $close = $("<input>", {
			id : "close",
			type : "button"
		});
		// 关闭面板
		$close.bind("click", function() {
			$autoPanel.hide();
		});
		if (!window.ActiveXObject) {// 非IE浏览器
			$close.addClass("reset").css("margin", "0px 4px 0 3px").css(
					"padding-left", "4px").css("border-style", "none");
		} else {
			$close.addClass("reset").css("margin", "0px").css("padding",
					"5px 4 4 5").css("border-style", "none");
		}
		$close.attr("value", "关闭");
		$close.appendTo($td);
		var $app = $("<input>", {
			id : "app",
			type : "button"
		});
		if (!window.ActiveXObject) {// 非IE浏览器
			$app.addClass("submit").css("margin", "0px 4px 0 3px").css(
					"padding-left", "4px").css("border-style", "none");
		} else {
			$app.addClass("submit").css("margin", "0px").css("padding",
					"5px 4 4 5").css("border-style", "none");
		}
		$app.attr("value", "应用");
		// 产出最终的数据
		$app
				.bind("click", function() {
					resultStr = "";
					resultTextStr = "";
					// 复选框
						if ("checkbox" == selectType
								|| "checkBox" == selectType) {
							var $t = $result.find("li");
							$t.each(function(index) {
								if (index == $t.size() - 1) {
									resultTextStr += $(this).attr("alt");
									resultStr += this.value;
								} else {
									resultStr += this.value + ",";
									resultTextStr += $(this).attr("alt") + ",";
								}
							});
						}
						// 单选
						else {
							resultStr = $("input[name='findResult']:checked")
									.val();
							resultTextStr += $(
									"input[name='findResult']:checked").attr(
									"alt");
						}
						$autoName.val(resultStr == undefined ? "" : resultStr);
						$wordInput.val(resultTextStr == undefined ? ""
								: resultTextStr);
						$autoPanel.hide();
					});
		$app.appendTo($td);
		$td.appendTo($tr);
		$tr.appendTo($t);
		$autoPanel.prepend($t);
		// 是复选框
		if ("checkbox" == selectType || "checkBox" == selectType) {
			var $result = $("<div>", {
				"id" : "result"
			});
			$result.css("margin", "0px").css("padding", "0px").css("height",
					"40px").css("overflow-y", "auto");
			$ul = $("<ul>");
			$ul.css("list-style-type", "none").css("margin", "0px").css(
					"padding", "0px");

			// 获取文本框数据
			var k = $autoName.val().split(",");
			var v = $wordInput.val().split(",");
			if (k != "" && k != null && k.length>0 && k!=0) {
				for ( var i = 0; i < k.length; i++) {
					// 添加之前选择的数据
					var $li = $("<li>");
					$li.attr("value", k[i]).attr("id", "li" + k[i]).attr("alt",
							v[i]);
					$li.css("float", "left").css("background-color", "#FEF6CE")
							.css("margin", "5px").css("color", "#5B5B5B").css(
									"border", "1px #EAD483 solid");
					$li.html(v[i] + "  ").appendTo($ul);
					$("<a>", {
						href : "#"
					}).html("X").addClass("").bind("click", function() {
						$(this).parent().remove();
						// 取消复选框选择
							$("#fd" + k[i]).checked = false;
						}).appendTo($li);
				}
			}
			// ul添加
			$ul.appendTo($result);
			$autoPanel.append($result);
			$autoPanel.append(hr);
		}
	}

	function find() {
		var sw = $("#searchWord" + item.id).val();
		// 清除panel 组件
		// $autoPanel.empty();
		// initSearch();
		var action_url = $autoPanel.attr("remote");
		$.post(action_url, {
			"searchWord" : sw
		}, function(data) {
			wt(data);
		}, "json");
	}

	function wt(data) {
		$("#abc" + item.id).remove();
		var $table = $("<table>", {
			cellpadding : "0",
			cellspacing : "0"
		});
		$table.attr("id", "abc" + item.id);
		$table.css("width", "100%");
		var $tr = $("<tr>");
		$tr.css("border-bottom", "1px #D2D2D2 solid");
		var id = "", name = "";
		for ( var i = 0; i < data.length; i++) {
			var $td = $("<td>");
			$td.css("border", "1px #D2D2D2 solid");
			if (i % 4 == 0) {
				$tr.appendTo($table);
				$tr = $("<tr>");
			}
			var $input = $("<input >");
			$input.attr("type", selectType);
			var k = 0;
			for (j in data[i]) {
				if (k == 1) {
					name = data[i][j];
				} else {
					id = data[i][j];
				}
				k++;
			}
			$input.attr("value", id);
			$input.attr("alt", name);
			$input.attr("name", "findResult");
			$input.attr("id", "fd" + id);
			// 是复选框
			if ("checkbox" == selectType || "checkBox" == selectType) {
				$input.attr("name", "findResultC");
				$input.bind("click", function() {
					var $this = this;
					// 选中则添加
						if ($this.checked) {
							var $li = $("<li>");
							$li.attr("value", $this.value).attr("id",
									"li" + $this.value).attr("alt", $this.alt);
							$li.css("float", "left").css("background-color",
									"#FEF6CE").css("margin", "5px").css(
									"color", "#5B5B5B").css("border",
									"1px #EAD483 solid");
							$li.html($this.alt + "  ").appendTo($ul);
							$("<a>", {
								href : "#"
							}).html("X").addClass("").bind("click", function() {
								$(this).parent().remove();
								// 取消复选框选择
									$this.checked = false;
								}).appendTo($li);
						}
					});
			}
			$input.appendTo($td);
			$td.append(name).appendTo($tr);
		}
		$tr.appendTo($table);
		$table.appendTo($autoPanel);
	}

}