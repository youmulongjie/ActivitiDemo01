/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.test<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import com.changhongit.HRModule.hr.CuxHrEmployeePositionRec;
import com.changhongit.HRModule.hr.CuxHrEmployeeRec;
import com.changhongit.HRModule.hr.CuxHrSupervisorRec;
import com.changhongit.HRModule.hr.ResultCuxHrOrgLowBean;
import com.changhongit.HRModule.hr.ResultCuxHrOrgLowHigRec;
import com.changhongit.bean.OperatingUnitsDataBean;
import com.changhongit.expense.bean.CuxOperatingUnits;
import com.changhongit.loan.bean.OrganizationLeaderBean;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.entity.LoanMainEntity;
import com.changhongit.loan.service.LoginService;
import com.changhongit.loan.util.HrInfoUtil;
import com.changhongit.loan.util.InitUtil;
import com.changhongit.loan.util.PingYinUtil;
import com.changhongit.orgServiceWS.client.SonOrgListBean;

/**
 * <p>
 * 描述：
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月8日下午2:49:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class HrInfoUtilTest {
	@Autowired
	private LoginService loginService;

	// 测试登陆
	@Test
	public void login() throws Exception {
		String username = "wanglongjie";
		String verifyStr = loginService.loginValid(username, "1234");
		System.out.println(verifyStr);
	}

	// 员工基本信息
	@Test
	public void getPersonInfo() {
		String erpNum = "4996";
		CuxHrEmployeeRec hr = HrInfoUtil.getPersonInfo(erpNum);
		System.out.println(hr);
	}

	// 部门和职位
	@Test
	public void getPersonJobInfo() {
		List<String> erpNumList = Arrays.asList("5215");
		for (String erpNum : erpNumList) {
			List<CuxHrEmployeePositionRec> list = HrInfoUtil
					.getPersonJobInfo(erpNum);
			System.out.println("erpNum = " + erpNum + " 的职位个数是：" + list.size());

			for (CuxHrEmployeePositionRec bean : list) {
				System.out.println("是否主职位：" + bean.getPrimaryFlag());
				System.out.println("中文名：" + bean.getLastName());
				System.out.println("组织类型：" + bean.getOrganizationType());
				System.out.println("所在部门：" + bean.getOrganizationName());
				System.out.println("组织ID：" + bean.getOrganizationId());
				System.out.println("所在职位：" + bean.getPositionName());
				System.out.println("分配编号：" + bean.getAssignmentNumber());
				System.out.println("-------");
			}
			System.out.println("****************");
		}
	}

	// 查询员工的领导信息
	@Test
	public void getPersonLeader() {
		String assignNum = "2598";// 分配编号
		List<CuxHrSupervisorRec> list = HrInfoUtil.getPersonLeader(assignNum,
				10L);
		System.out.println(list.size());
		for (CuxHrSupervisorRec bean : list) {
			System.out.print("组织类型：" + bean.getOrganizationTypeName());
			System.out.print("，组织ID：" + bean.getOrganizationId());
			System.out.print("，ERP Name：" + bean.getUserName());
			System.out.print("，中文名：" + bean.getLastName());
			System.out.print("，员工编号：" + bean.getPersonId());
			System.out.print("，职位：" + bean.getPositionName());
			System.out.print("，邮箱：" + bean.getEmailAddress());
			System.out.println("---------");
		}
	}

	// 查找 下级部门信息
	@Test
	public void getNextOrgStruc() {
		long organizationId = 9491;
		List<ResultCuxHrOrgLowBean> list = HrInfoUtil
				.getNextOrgStruc(organizationId);
		System.out.println(list.size());
		for (ResultCuxHrOrgLowBean bean : list) {
			System.out.println("组织名称：" + bean.getOrganizationName());
			System.out.println("组织ID：" + bean.getOrganizationId());
			System.out.println("组织类型：" + bean.getOrganizationType());
			System.out.println("上级组织ID：" + bean.getParentOrganizationId());
			System.out.println("------------");
		}
	}

	// 查找 上级部门信息
	@Test
	public void getUpOrgStruc() {
		long organizationId = 9489;
		ResultCuxHrOrgLowHigRec bean = HrInfoUtil.getUpOrgStruc(organizationId);
		System.out.println("组织名称：" + bean.getOrganizationName());
		System.out.println("组织ID：" + bean.getOrganizationId());
		System.out.println("组织类型：" + bean.getOrganizationType());
		System.out.println("上级组织ID：" + bean.getParentOrganizationId());
		System.out.println("------------");
	}

	// 查询 OU列表
	@Test
	public void getOUList() throws Exception {
		List<OperatingUnitsDataBean> list = InitUtil.getOulist();
		System.out.println(list.size());

		for (OperatingUnitsDataBean bean : list) {
			System.out.println("OU名称：" + bean.getOuName());
			System.out.println("组织ID：" + bean.getOrganizationId());
			System.out.println("------------");
		}
	}

	// 查询员工 所在的OU
	@Test
	public void getOuByErpNumber() throws Exception {
		long erpNum = 1047L;
		CuxOperatingUnits cou = HrInfoUtil.getOuByErpNumber(erpNum);
		System.out.println(cou);
		if (cou != null && cou.getOuList() != null) {
			System.out
					.println("OU全称：" + cou.getOuList().get(0).getOuFullName());
			System.out.println("OU简称：" + cou.getOuList().get(0).getOuName());
			System.out.println("OUID："
					+ cou.getOuList().get(0).getOrganizationId());
		}
	}

	// 测试业务部
	@Test
	public void getYeWuBus() throws Exception {
		String shiyebuName = "THINK事业部";
		SonOrgListBean bean = HrInfoUtil.getYeWuBus(shiyebuName);
		List<String> list = bean.getSonList();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	// 测试产品线
	@Test
	public void getProduceLines() throws Exception {
		String yewubuName = "THINK业务部";
		SonOrgListBean bean = HrInfoUtil.getProduceLines(yewubuName);
		List<String> list = bean.getSonList();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	// 测试生成凭证号
	@Test
	public void createPzNum() {
		User sessionUser = new User();
		sessionUser.setUsername("李媛");
		sessionUser.setUsernameJC(PingYinUtil.getPinYinHeadChar("李媛"));
		sessionUser.setErpNum("1234");
		sessionUser.setErpName("LIYUAN");
		LoanMainEntity mainEntity = new LoanMainEntity();
		mainEntity.setLoanNumber("JKSQ2018060002");
		mainEntity.setOu("信产");
		try {
			String createPzNum = HrInfoUtil
					.createPzNum(sessionUser, mainEntity);
			System.out.println(createPzNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 测试 部门名称 获取领导链信息
	@Test
	public void getLeaderByOrganizationName() {
		// String organizationName = "信息中心";
		String organizationName = "联想事业部";
		List<OrganizationLeaderBean> list = HrInfoUtil
				.getLeaderByOrganizationName(organizationName);

		OrganizationLeaderBean bean = null;
		for (int i = 0; i < list.size(); i++) {
			bean = list.get(i);

			System.out.println(bean);
		}
	}

	@Test
	public void getMailByUserName() throws Exception {
		String assignee = "李媛，马立亚，杨旭阳，王瑾";
		// String assignee = "王瑾";
		String email = "";
		if (!StringUtils.isEmpty(assignee)) {
			assignee = assignee.replaceAll("，", ",").replaceAll(" ", "");
			if (assignee.contains(",")) {
				String[] assignees = StringUtils.tokenizeToStringArray(
						assignee, ",");
				for (int i = 0, j = assignees.length; i < j; i++) {
					email += HrInfoUtil.getMailByUserName(assignees[i].trim())
							+ ",";
				}
			} else {
				email = HrInfoUtil.getMailByUserName(assignee);
			}
		}
		System.out.println(email);
	}

	// 分总
	@Test
	public void getFenZong() {
		String erpNum = "3450";
		String position = "分支机构行政人事组.行政人事专员";
		List<CuxHrSupervisorRec> list = HrInfoUtil.getFenZong(erpNum, position);
		System.out.println(list.size());
	}
}
