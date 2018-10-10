/*!
 * 分页 函数 Javascript Library
 * 
 **/

/**
 * 分页 主函数
 * 
 * @param option
 *            参数 <br>
 */
function page(option) {
	$.ajax({
		type : "post",
		url : option.url,
		data : option.param,
		dataType : "json",
		success : function(returndata) {
			// 调用分页
			if (returndata.totalCount == 0) {
				var nullTr = "<tr><td colspan='" + option.th_length
						+ "' class='tdMes text-center'>抱歉，暂时没有相关记录</td></tr>";
				$("tbody").html(nullTr);
				$("#paginator").empty();
				$("#totals").html("共计0条记录");
			} else {
				showPages(returndata.totalCount, returndata.currentPage,
						returndata.pageSize, function(pageNos) {
							// 带条件查询时，$("#form").serializeArray() 返回的是
							// Object的Array数组
							if (Array.isArray(option.param)) {
								$.each(option.param, function(i, field) {
									if (field.name == 'page') {
										// 删除并重新设置 当前页数
										option.param.splice(i, 1, {
											"name" : "page",
											"value" : pageNos
										});
										return false;
									}
								});
							} else {
								option.param.page = pageNos;
							}
							var option2 = {
								"url" : option.url,
								"param" : option.param,
								"th_length" : option.th_length
							};
							page(option2);
						});
				// 显示表格内容
				showTables(returndata);
			}

		}
	});
};

// 输出表格内容
function showTables(data) {
	var mainHtml = "";
	$.each(data.list, function(index, item) {
		var s = $("#tableTemplate").html();
		mainHtml += replace(s, item);
	});
	$("tbody").html(mainHtml);
	$("#totals").html("共计" + data.totalCount + "条记录");
};

// 
function showPages(total, pageNo, rows, callback) {
	totalPage = total % rows == 0 ? total / rows : (total / rows) + 1;
	var options = {
		bootstrapMajorVersion : 3, // bootstrap版本
		size : 'normal',
		numberOfPages : 6, // 显示“第几页”的选项数目
		currentPage : pageNo, // 当前页数
		totalPages : totalPage, // 总页数
		itemTexts : function(type, page, current) {
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页 ";
			case "next":
				return "下一页";
			case "last":
				return "末页";
			case "page":
				return page;
			}
		},
		tooltipTitles : function(type, page, current) {
			switch (type) {
			case "first":
				return "首页";
			case "prev":
				return "上一页";
			case "next":
				return "下一页";
			case "last":
				return "末页";
			case "page":
				return "第" + page + "页";
			}
		},
		onPageClicked : function(event, originalEvent, type, page) {
			callback(page);
		}
	};
	$('#paginator').bootstrapPaginator(options);
}
function replace(str, obj) {
	for (a in obj) {
		var _reg = new RegExp("\\{\\{" + a + "\\}\\}", "g");
		str = str.replace(_reg, obj[a] === null ? "" : obj[a]);
	}
	str = str.replace(/\$.*?\$/g, "");
	return str;
};
