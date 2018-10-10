/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import jodd.datetime.JDateTime;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.springframework.core.io.ClassPathResource;

import com.changhongit.bean.OperatingUnitsDataBean;
import com.changhongit.loan.entity.LoanOrganizationEntity;
import com.changhongit.loan.enums.LoanTypeEnum;
import com.changhongit.loan.enums.OuMapEnum;

/**
 * <p>
 * 描述：初始化类
 * </p>
 * 
 * @author wanglongjie<br>
 *         LATFORM
 * @version v1.0 2018年5月23日上午9:38:54
 */
public class InitUtil {
	/**
	 * 借款类型对应Activiti流程图（classpath:diagrams/*.bpmn）的id
	 */
	public final static Map<String, String> loanTypeToBPMNMap;

	/**
	 * 固定审批人值（缓存）Map
	 */
	public final static Map<String, String> fixedApproverMap;

	/**
	 * 借款单对应发票类型（缓存）Map
	 */
	public final static Map<String, String> invoiceMap;

	/**
	 * 借款类型对应会计（缓存）Map
	 */
	public final static Map<String, String> fnAccountingMap;

	/**
	 * 分公司出纳配置（缓存）Map
	 */
	public final static Map<String, String> branchCashierMap;
	/**
	 * 分公司会计配置（缓存）Map
	 */
	public final static Map<String, String> branchAccountingMap;
	/**
	 * 平台部长配置（缓存）Map
	 */
	public final static Map<String, String> platformMinisterMap;

	/**
	 * OU映射Map
	 */
	public final static Map<String, String> ouMap;

	/**
	 * 所有的流程（BPMN）中的环节名称（查询下拉列表使用）
	 */
	public final static Set<String> bpmnHanderWorkNameSet;

	/**
	 * 驳回/补正 环节 标识 Map（流程中 驳回、补正 环节 体现）<br>
	 * 
	 * <pre>
	 * key：流程环节名称；value：哪个环节标识
	 * </pre>
	 */
	public final static Map<String, Integer> correctionsMap;

	static {
		ouListMap = new HashMap<String, List<OperatingUnitsDataBean>>(1);
		// 初始化OU映射（信产("信息产品"), 智能("佳华智能"), 数字("增值数字公司"), 哆啦("哆啦有货公司")）
		ouMap = new HashMap<String, String>(8);
		OuMapEnum[] enums = OuMapEnum.values();
		for (OuMapEnum mapEnum : enums) {
			ouMap.put(mapEnum.getMap(), mapEnum.name());
			ouMap.put(mapEnum.name(), mapEnum.getMap());
		}

		// 在 SysListener.java 里初始化（服务启动时）
		fixedApproverMap = new HashMap<String, String>(8);
		fnAccountingMap = new HashMap<String, String>(8);
		invoiceMap = new HashMap<String, String>(8);
		branchCashierMap = new HashMap<String, String>(8);
		branchAccountingMap = new HashMap<String, String>(8);
		platformMinisterMap = new HashMap<String, String>();

		// 初始化值 借款类型对应的审批流图
		loanTypeToBPMNMap = new LinkedHashMap<String, String>(8);
		// ① 周转备用金|押金、质保金类
		loanTypeToBPMNMap.put(LoanTypeEnum.Epicyclic.getType(), "loan_type_01");
		loanTypeToBPMNMap.put(LoanTypeEnum.DepositWarranty.getType(),
				"loan_type_01");
		// ② 诉讼费
		loanTypeToBPMNMap.put(LoanTypeEnum.LitigationFee.getType(),
				"loan_type_02");
		// ③ 小额工程采购|研发|其他
		loanTypeToBPMNMap.put(LoanTypeEnum.SmallProjectProcurement.getType(),
				"loan_type_03");
		loanTypeToBPMNMap.put(LoanTypeEnum.Development.getType(),
				"loan_type_03");
		loanTypeToBPMNMap.put(LoanTypeEnum.Other.getType(), "loan_type_03");
		// ④ 资产购置
		loanTypeToBPMNMap.put(LoanTypeEnum.AssetPurchase.getType(),
				"loan_type_04");
		// ⑤ 个人因公特殊
		loanTypeToBPMNMap
				.put(LoanTypeEnum.Individual.getType(), "loan_type_05");

		// 初始化 所有的流程（BPMN）中的环节名称值
		bpmnHanderWorkNameSet = new HashSet<String>();
		try {
			addBpmnHanderWorkNameSet("diagrams/loan_type_01.bpmn");
			addBpmnHanderWorkNameSet("diagrams/loan_type_02.bpmn");
			addBpmnHanderWorkNameSet("diagrams/loan_type_03.bpmn");
			addBpmnHanderWorkNameSet("diagrams/loan_type_04.bpmn");
			addBpmnHanderWorkNameSet("diagrams/loan_type_05.bpmn");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 初始化 驳回/补正 环节 标识值（每个BPMN流程图都遵守这个，且不能改动，有新环节可以在后面添加）
		// 编号唯一就可以，没有先后顺序
		correctionsMap = new HashMap<String, Integer>();
		correctionsMap.put("直接领导", 1);
		correctionsMap.put("分公司总经理", 2);
		correctionsMap.put("事业部总/平台部长", 3);
		correctionsMap.put("本部总", 4);
		correctionsMap.put("分管高管", 5);
		correctionsMap.put("财务会计", 6);
		correctionsMap.put("转分公司会计", 7);
		correctionsMap.put("财务总监", 8);
		correctionsMap.put("出纳", 9);
		correctionsMap.put("财务复核", 10);
		correctionsMap.put("平台高管", 11);
		correctionsMap.put("法务部部长", 12);
		correctionsMap.put("平台部长", 13);
		correctionsMap.put("财务部长", 14);
		correctionsMap.put("主责平台部长", 15);
		correctionsMap.put("总裁", 16);
		// add new handle
	}

	// 每天缓存OU列表
	private final static Map<String, List<OperatingUnitsDataBean>> ouListMap;

	/**
	 * 
	 * <p>
	 * 描述：缓存 OU列表（每天）
	 * </p>
	 * 
	 * @Date 2018年5月23日上午10:23:50 <br>
	 * @return
	 * @throws Exception
	 */
	public static List<OperatingUnitsDataBean> getOulist() throws Exception {
		JDateTime today = new JDateTime(new Date());
		today.setFormat("YYYY-MM-DD");
		String key = today.toString();
		if (!ouListMap.containsKey(key)) {
			ouListMap.clear();
			ouListMap.put(key, HrInfoUtil.getOUList());
		}

		return ouListMap.get(key);
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 事业部Map（key：事业部名称；value：事业部ID）
	 * </p>
	 * 
	 * @Date 2018年5月28日上午9:48:03 <br>
	 * @return
	 */
	public static Map<String, String> getShiYeBuMap() {
		List<LoanOrganizationEntity> list = HrInfoUtil
				.getLoanOrganizationEntities();
		Map<String, String> map = new LinkedHashMap<String, String>(20);
		LoanOrganizationEntity entity = null;
		for (int i = 0, j = list.size(); i < j; i++) {
			entity = list.get(i);
			map.put(entity.getOrganizationName(), entity.getOrganizationName());
		}
		return map;
	}

	/**
	 * 
	 * <p>
	 * 描述：获取 工作流中 任务节点名称集合
	 * </p>
	 * 
	 * @Date 2018年6月28日上午9:41:04 <br>
	 * @param bpmnResourcePath
	 * @throws Exception
	 */
	private static void addBpmnHanderWorkNameSet(String bpmnResourcePath)
			throws Exception {
		InputStream resouceStream = null;
		InputStreamReader in = null;
		XMLStreamReader xtr = null;
		XMLInputFactory xif = null;
		try {
			xif = XMLInputFactory.newInstance();
			resouceStream = new ClassPathResource(bpmnResourcePath)
					.getInputStream();
			in = new InputStreamReader(resouceStream, "UTF-8");
			xtr = xif.createXMLStreamReader(in);
			BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(xtr);
			Collection<FlowElement> flowElements = model.getMainProcess()
					.getFlowElements();
			for (FlowElement e : flowElements) {
				// 只取 任务节点 的名称
				if (e instanceof UserTask) {
					bpmnHanderWorkNameSet.add(e.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			xtr.close();
			in.close();
			resouceStream.close();
		}
	}
}
