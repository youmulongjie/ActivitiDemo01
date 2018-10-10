/**
 * 固定审批人配置 JS
 */
$(function() {
	var work = new $.managerWork("fixed_approver", {}, {});

	/**
	 * 保存按钮 事件
	 */
	$(".buttons").on('click', '.save2', function() {
		var array = [];
		$("input").each(function() {
			var $this = $(this);
			array.push($this.attr("title") + "," + $.trim($this.val()));
		});
		var param = array.join(";");
		// 保存参数
		var saveOption = {
			url : "fixed_approver.do",
			data : {
				"param" : param,
			},
			href : "/loanSys/fixed_approver.do",
		};
		work.setSaveOption(saveOption).saveOrUpdateEntity(function() {
			return checkForm();
		});

	});
});

/**
 * 检查是否有空值
 */
function checkForm() {
	var flag = true;
	$("input").each(function() {
		var $this = $(this);
		if ($.trim($this.val()) == '') {
			layer.msg("【" + $this.attr("title") + "】不能为空！");
			$this.focus();
			flag = false;
			return false;
		}
	});
	return flag;
}