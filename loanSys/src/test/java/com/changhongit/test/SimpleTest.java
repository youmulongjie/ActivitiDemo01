/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.test<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import jodd.datetime.JDateTime;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.changhongit.loan.enums.FixedApproverEnum;
import com.changhongit.loan.util.PingYinUtil;

/**
 * <p>
 * 描述：
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午4:53:49
 */
public class SimpleTest {
	public static final String DOWNLOAD_URL = "http://attachdownload.changhongit.com/attachDownload/Download/";
	
	@Test
	public void test1() {
		FixedApproverEnum[] approverEnums = FixedApproverEnum.values();
		for (FixedApproverEnum enum1 : approverEnums) {
			System.out.println(enum1.name());
		}
	}

	@Test
	public void pinyi() {
		String name = "王龙杰";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "马晓龙";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "李媛";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "李宁";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "王雪";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "孙媛媛";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "袁晓丽";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
		name = "李镇2";
		System.out.println(PingYinUtil.getPinYinHeadChar(name));
	}

	@Test
	public void testFile() throws Exception {
		// File file = new File("E://test/HR接口调用.docx");
		// FileUtils.copyURLToFile(new URL(DOWNLOAD_URL + "5253931"), file);

		// File file = new File(".");
		// String fileName = UUID.randomUUID().toString()+".doc";
		// file = new File(file, fileName);
		// FileUtils.touch(file);
		// System.out.println(file.getAbsolutePath());

		// List<Long> attachIds = new ArrayList<Long>();
		// attachIds.add(5253931L);
		// attachIds.add(5253927L);
		// AttachUtil.downloadZip(attachIds);

		System.out.println(FileUtils.getTempDirectoryPath());
	}

	// 测试 BPMN 所有环节名称
	@Test
	public void testBpmn() {
		InputStream resouceStream = this.getClass().getClassLoader()
				.getResourceAsStream("diagrams/loan_type_01.bpmn");
		XMLInputFactory xif = XMLInputFactory.newInstance();
		InputStreamReader in;
		XMLStreamReader xtr;
		try {
			in = new InputStreamReader(resouceStream, "UTF-8");
			xtr = xif.createXMLStreamReader(in);
			BpmnModel model = new BpmnXMLConverter().convertToBpmnModel(xtr);
			Collection<FlowElement> flowElements = model.getMainProcess()
					.getFlowElements();
			for (FlowElement e : flowElements) {
				if (e instanceof FlowNode) {
					System.out.println(e.getName());
				}
				// System.out.println("flowelement id:" + e.getId() + "  name:"
				// + e.getName() + "   class:" + e.getClass().toString());
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testJdateTime() {
		JDateTime dateTime = new JDateTime();
		dateTime.addDay(31);
		System.out.println(dateTime.toString("YYYY-MM-DD"));

		JDateTime dd = new JDateTime(getYearLast(Calendar.getInstance().get(
				Calendar.YEAR)));
		System.out.println(dd.toString("YYYY-MM-DD"));
		
		
		JDateTime dateTime2 = new JDateTime(1420041600000L);
		System.out.println(dateTime2);
	}

	// 获取年 最后一天
	public Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}
}
