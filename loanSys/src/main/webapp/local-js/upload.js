/**
 * 附件查看，上传 JS
 */
$(function() {
	/**
	 * 上传
	 */
	$("#upload").click(function() {
		layer.load("正在提交中...");
		if($("#uploadFile").val() == ""){
			layer.msg("请选择上传文件！");
			return;
		}
		$("#upload_form").submit();
	});

	/**
	 * 删除
	 */
	$(".buttons").on('click', '.delete_draft', function() {
		new $.confirm("是否确定删除？", function() {
			var arr = new Array();
			$(".data :checkbox").each(function() {
				var $this = $(this);
				if ($this.prop("checked")) {
					// 按 ID键 删除
					arr.push($this.attr("id"));
				}
			});

			if (arr.length == 0) {
				layer.msg("删除所选项为空！");
				return;
			} else {
				new $.confirm("删除已选记录？", function() {
					var ids = arr.join(",");
					$.ajax({
						type : "POST",
						dataType : "json",
						data : {
							"ids" : ids
						},
						url : "deleteAttach.do",
						success : function(data) {
							if (data.code == "success") {
								layer.alert(data.result, 1);
								location.reload();
							} else {
								layer.msg(data.result);
								return;
							}
						},
						error : function(textStatus) {
							console.error(textStatus);
						},
						complete : function(XMLHttpRequest, status) {
							XMLHttpRequest = null;
						}
					});
				});
			}
		});
	});

	/**
	 * 下载
	 */
	$(".buttons").on('click', '.download', function() {
		var arr = new Array();
		$(".data :checkbox").each(function() {
			var $this = $(this);
			if ($this.prop("checked")) {
				// 按 value 值下载
				arr.push($this.val());
			}
		});

		if (arr.length == 0) {
			layer.msg("下载所选项为空！");
			return;
		} else {
			var ids = arr.join(",");
			window.location.href = "download.do?ids=" + ids;
		}
	});
});