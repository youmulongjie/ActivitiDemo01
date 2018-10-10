/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.controller<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.changhongit.loan.bean.Message;
import com.changhongit.loan.bean.User;
import com.changhongit.loan.constant.IConstant;
import com.changhongit.loan.entity.AttachmentEntity;
import com.changhongit.loan.service.LoanMainService;
import com.changhongit.loan.util.AttachUtil;

/**
 * <p>
 * 描述：附件 Controller
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月25日上午10:59:59
 */
@Controller
public class AttachController {
	public static final Logger logger = LoggerFactory
			.getLogger(AttachController.class);
	@Autowired
	private LoanMainService loanMainService;

	/**
	 * 
	 * <p>
	 * 描述：调整到查看附件 页面
	 * </p>
	 * 
	 * @Date 2018年6月26日上午9:34:54 <br>
	 * @param mainId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "viewAttach", method = RequestMethod.GET)
	public String viewAttach(@RequestParam("mainId") Long mainId,
			ModelMap modelMap) {
		modelMap.put("mainId", mainId);
		List<AttachmentEntity> entities = loanMainService
				.getAttachmentEntitiesByMainId(mainId);
		modelMap.put("entities", entities);

		return "personalWork/viewAttach";
	}

	/**
	 * 
	 * <p>
	 * 描述：跳转到 上传页面
	 * </p>
	 * 
	 * @Date 2018年6月25日上午11:04:16 <br>
	 * @return
	 */
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String upload(
			@RequestParam(value = "mainId", required = false, defaultValue = "0") Long mainId,
			ModelMap modelMap) {
		modelMap.put("mainId", mainId);
		List<AttachmentEntity> entities = loanMainService
				.getAttachmentEntitiesByMainId(mainId);
		modelMap.put("entities", entities);

		return "personalWork/upload";
	}

	/**
	 * 
	 * <p>
	 * 描述：上传 附件
	 * </p>
	 * 
	 * @Date 2018年6月25日下午4:09:18 <br>
	 * @param request
	 * @param remake
	 *            备注
	 * @param mainId
	 *            借款基本信息表ID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String uplaod(HttpServletRequest request, String remake, Long mainId)
			throws Exception {
		logger.info("附件上传...");

		User sessionUser = (User) request.getSession().getAttribute(
				IConstant.SESSION_USER);

		List<AttachmentEntity> attachmentEntities = AttachUtil.uplaod(request,
				remake, sessionUser.getUsername());
		AttachmentEntity attachmentEntity = null;

		for (int i = 0, j = attachmentEntities.size(); i < j; i++) {
			attachmentEntity = attachmentEntities.get(i);
			attachmentEntity.setMainId(mainId);
			loanMainService.saveAttachmentEntity(attachmentEntity);
		}

		return "redirect:/upload.do?mainId=" + mainId;
	}

	/**
	 * 
	 * <p>
	 * 描述：删除所选
	 * </p>
	 * 
	 * @Date 2018年6月26日上午10:00:09 <br>
	 * @param ids
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAttach", method = RequestMethod.POST)
	public Message deleteAttach(String ids, HttpServletRequest request) {
		logger.info("要删除的附件 ids:{}", ids);
		Message message = null;
		if (StringUtils.isEmpty(ids)) {
			message = Message.failure("没有要删除的选项");
		} else {
			String[] temp = ids.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (int i = 0, j = temp.length; i < j; i++) {
				idList.add(Long.valueOf(temp[i].trim()));
			}
			try {
				User user = (User) request.getSession().getAttribute(
						IConstant.SESSION_USER);
				loanMainService.deleteEntityByIds(AttachmentEntity.class,
						idList, user.getUsername());
				message = Message.success("删除操作成功！");
			} catch (Exception e) {
				e.printStackTrace();
				message = Message.failure("删除操作失败", e);
			}
		}
		logger.info(message.getResult());
		return message;
	}

	/**
	 * 
	 * <p>
	 * 描述：下载 附件
	 * </p>
	 * 
	 * @Date 2018年6月26日上午10:16:34 <br>
	 * @param ids
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(String ids,
			HttpServletRequest request) throws Exception {
		logger.info("要下载的附件 ids:{}", ids);
		String[] temp = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0, j = temp.length; i < j; i++) {
			idList.add(Long.valueOf(temp[i].trim()));
		}

		if (idList.size() == 1) {
			// 单附件 下载
			return AttachUtil.download(idList.get(0), request);
		} else {
			// 多附件 下载
			return AttachUtil.downloadZip(idList);
		}

	}

}
