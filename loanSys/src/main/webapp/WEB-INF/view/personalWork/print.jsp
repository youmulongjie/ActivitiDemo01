<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>打印</title>
<style type="text/css">
body {
	font-family: '微软雅黑';
}

.print_t {
	font-size: 14px;
	line-height: 20px;
}

.print_t td {
	padding: 2px;
	vertical-align: top;
}

.print_t th {
	text-align: left;
	padding: 2px;
}

.font16b {
	font-size: 16px;
	font-weight: bold;
}

.print_tt {
	font-size: 20px;
	font-weight: bold;
	width: 800px;
	text-align: center;
	line-height: 80px;
}

.print_t1 {
	font-size: 16px;
	line-height: 50px;
}

.textCenter,.textCenter th {
	text-align: center;
}
</style>
</head>
<body>
	<div class="print_tt">${entity.person }的借款申请单</div>
	<table width="800" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><table class="print_t" width="100%" border="1"
					cellpadding="0" cellspacing="0">
					<tr>
						<td align="center"><span class="font16b">个人一般信息</span></td>
					</tr>
					<tr>
						<td>
							<table class="print_t" width="95%" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<td width="120">申请人：</td>
									<td width="150">${entity.person }</td>
									<td width="120">借款人职位：</td>
									<td width="150">${entity.position }</td>
								</tr>
								<tr>
									<td width="120">所属部门：</td>
									<td width="150">${entity.dept }</td>
									<td width="120">借款类型：</td>
									<td width="150">${entity.loantype }</td>
								</tr>
								<tr>
									<td width="120">申请日期：</td>
									<td width="150">${entity.applyDate }</td>
									<td width="120">所属分支机构：</td>
									<td width="150">${entity.branch }</td>
								</tr>
								<tr>
									<td width="120">本部门借款：</td>
									<td width="150">${entity.departmentLoan }</td>
									<td width="120">所属借款OU：</td>
									<td width="150">${entity.ou }</td>
								</tr>
								<c:if test="${entity.departmentLoan == '否'}">
									<tr>
										<td width="120">借款所属部门：</td>
										<td width="150">${entity.replaceDept }</td>
										<td width="120">借款所属业务部：</td>
										<td width="150">${entity.replaceBusiness }</td>
									</tr>
									<tr>
										<td width="120">借款所属产品线：</td>
										<td width="150">${entity.replaceLine }</td>
										<c:if
											test="${entity.loantype == '小额工程采购' || entity.loantype == '研发' || entity.loantype == '其他'}">
											<td width="120">是否有合同或预算：</td>
											<td width="150">${entity.contractOrBudget }</td>
										</c:if>
										<c:if test="${entity.loantype == '押金、质保金类'}">
											<td width="120">预计还款日项：</td>
											<td width="150">${entity.planDate }</td>
										</c:if>
									</tr>
								</c:if>
								<c:if test="${entity.departmentLoan == '是'}">
									<c:if
										test="${entity.loantype == '小额工程采购' || entity.loantype == '研发' || entity.loantype == '其他'}">
										<tr>
											<td width="120">是否有合同或预算：</td>
											<td width="150">${entity.contractOrBudget }</td>
											<td width="120"></td>
											<td width="150"></td>
										</tr>
									</c:if>
									<c:if test="${entity.loantype == '押金、质保金类'}">
										<tr>
											<td width="120">预计还款日项：</td>
											<td width="150">${entity.planDate }</td>
											<td width="120"></td>
											<td width="150"></td>
										</tr>
									</c:if>
								</c:if>
								<tr>
									<td width="120">币种：</td>
									<td width="150">${entity.cny }</td>
									<td width="120">申请单编号：</td>
									<td width="150">${entity.loanNumber }</td>
								</tr>
								<tr>
									<td width="120">借款单编号：</td>
									<td width="150">${entity.pzNumber }</td>
									<td width="120">借款用途及说明：</td>
									<td width="150">${entity.loanExplain }</td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td align="center"><span class="font16b">付款信息</span></td>
					</tr>
					<tr>
						<td>
							<table class="print_t" width="100%" border="0" cellspacing="0"
								cellpadding="0">
								<thead class="textCenter">
									<tr>
										<th>序号</th>
										<th>付款方式</th>
										<th>收款单位名称</th>
										<th>收款银行名称</th>
										<th>收款银行账户</th>
										<th>付款金额</th>
										<th>出纳付款说明</th>
										<th>备注</th>
									</tr>
								</thead>
								<tbody class="textCenter">
									<c:forEach items="${bursementEntities }" var="bursementEntitie"
										varStatus="index">
										<tr>
											<td>${index.index + 1 }</td>
											<td>${bursementEntitie.paymentMethod }</td>
											<td>${bursementEntitie.payee }</td>
											<td>${bursementEntitie.cashBank }</td>
											<td>${bursementEntitie.bankAccount }</td>
											<td>${bursementEntitie.loanMoney }</td>
											<td class="textwrap">${bursementEntitie.paymentExplain }</td>
											<td>${bursementEntitie.remarks }</td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="8" align="left">借款金额合计：${entity.totalMoney }&nbsp;&nbsp;
											大写：${totalMoneyBig }</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>

					<tr>
						<td align="center"><span class="font16b">流转历史</span></td>
					</tr>
					<tr>
						<td>
							<table class="print_t" width="100%" border="0" cellspacing="0"
								cellpadding="0">
								<thead class="textCenter">
									<tr>
										<th>序号</th>
										<th>处理人</th>
										<th>处理环节</th>
										<th>处理时间</th>
										<th>状态</th>
										<th>审批意见</th>
									</tr>
								</thead>
								<tbody class="textCenter">
									<c:forEach items="${approvalComments }" var="comment"
										varStatus="index">
										<!-- 打印单子时不显示 出纳（最后一行） -->
										<c:if test="${!index.last }">
											<tr>
												<td>${index.index +1 }</td>
												<td>${comment.approvor }</td>
												<td>${comment.taskName }</td>
												<td>${comment.approvorDate }</td>
												<td>${comment.status }</td>
												<td>${comment.desc }</td>
											</tr>
										</c:if>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table class="print_t1" width="100%" border="0" align="center"
								cellpadding="0" cellspacing="0">
								<tr>
									<td width="100"><strong>财务会计：</strong></td>
									<td width="100"><strong>领款人签字（现金）:</strong></td>
									<td width="100"><strong>经办人：</strong></td>
								</tr>
							</table>
						</td>
					</tr>
				</table></td>
		</tr>
	</table>
	<div id="btnprint">
		<button type="button" class="btn btn-warning"
			onclick="window.close();">
			<i class="fa fa-times"></i> 取消
		</button>
		&nbsp;
		<button type="button" class="btn btn-primary" onclick="printcld();">
			<i class="fa fa-print"></i> 打印
		</button>
	</div>


	<script>
		function printcld() {
			document.getElementById("btnprint").style.display = "none";
			window.print();
			document.getElementById("btnprint").style.display = "block";
		}
	</script>
</body>
</html>
