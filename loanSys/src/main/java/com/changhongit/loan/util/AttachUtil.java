/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.util<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.changhongit.attach.client.AttachClient;
import com.changhongit.attach.module.AttachBean;
import com.changhongit.attach.module.AttachSaveResult;
import com.changhongit.attach.module.Utils;
import com.changhongit.loan.entity.AttachmentEntity;

/**
 * <p>
 * 描述：附件 上传下载 工具类
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月25日下午3:56:22
 */
public class AttachUtil {
	/**
	 * 附件上传WSDL地址
	 */
	static final String ATTACH_UPLOAD_WSDL = "http://attachservice.changhongit.com/attachservice/AttachService?wsdl";
	/**
	 * 附件下载 URL地址
	 */
	static final String DOWNLOAD_URL = "http://attachdownload.changhongit.com/attachDownload/Download/";

	/**
	 * 
	 * <p>
	 * 描述：附件上传
	 * </p>
	 * 
	 * @Date 2018年6月25日下午4:01:48 <br>
	 * @param fileName
	 *            上传附件名称
	 * @param fis
	 *            上传附件输入流
	 * @return 附件ID
	 * @throws Exception
	 */
	public static Long uploadAttach(String fileName, InputStream fis)
			throws Exception {
		// 初始化附件服务客户端
		AttachClient ac = AttachClient
				.getAttachClientInstance(ATTACH_UPLOAD_WSDL);
		byte[] con = new byte[fis.available()];
		fis.read(con);// 读文件内容

		// 保存附件
		AttachBean bean = new AttachBean();
		bean.setAttachName(new String(org.apache.commons.codec.binary.Base64
				.encodeBase64(fileName.getBytes("UTF-8"))));// 对附件文件名进行转换
		bean.setContent(Utils.byteToStr(con));// 将附件的内容按字节进行转换
		AttachSaveResult ret = ac.saveAttach(bean);

		if (null == ret) {
			throw new RuntimeException("附件服务器: 保存文件失败！");
		}
		// 取文件名
		Long id = ret.getId();

		return id;
	}

	/**
	 * 
	 * <p>
	 * 描述：单文件下载
	 * </p>
	 * 
	 * @Date 2018年6月26日上午10:49:08 <br>
	 * @param attachId
	 *            附件ID
	 * @return
	 * @throws RemoteException
	 * @throws UnsupportedEncodingException
	 */
	public static ResponseEntity<byte[]> download(Long attachId,
			HttpServletRequest request) throws IOException {
		AttachClient ac = AttachClient
				.getAttachClientInstance(ATTACH_UPLOAD_WSDL);// 初始化附件服务客户端
		String fileName = ac.getAttachNameById(attachId.toString());
		byte[] buffer = ac.getAttachContentById(attachId.toString());

		String codedfilename = null;
		String agent = request.getHeader("USER-AGENT");
		if (null != agent && -1 != agent.indexOf("MSIE") || null != agent
				&& -1 != agent.indexOf("Trident")) {
			// ie
			codedfilename = java.net.URLEncoder.encode(fileName, "UTF8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
			// 火狐,chrome等
			codedfilename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
				+ codedfilename);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(buffer, headers, HttpStatus.OK);
	}

	/**
	 * 
	 * 
	 * <p>
	 * 描述：多文件 ZIP下载
	 * </p>
	 * 
	 * @Date 2018年6月26日下午1:23:16 <br>
	 * @param attachIds
	 *            附件ID集合
	 * @return
	 * @throws Exception
	 */
	public static ResponseEntity<byte[]> downloadZip(List<Long> attachIds)
			throws Exception {
		// 1）创建临时文件夹
		File zipDirectory = new File(".");
		String uuid = UUID.randomUUID().toString();
		zipDirectory = new File(zipDirectory, uuid);
		FileUtils.forceMkdir(zipDirectory);

		// 2）循环生成服务器本地文件
		AttachClient ac = AttachClient
				.getAttachClientInstance(ATTACH_UPLOAD_WSDL);// 初始化附件服务客户端
		Long attachId = null;// 附件ID号
		String fileName = null;// 附件名称
		File temp = null;// 附件
		for (int i = 0, j = attachIds.size(); i < j; i++) {
			attachId = attachIds.get(i);
			fileName = ac.getAttachNameById(attachId.toString());
			temp = new File(zipDirectory, fileName);
			FileUtils.touch(temp);
			FileUtils.copyURLToFile(new URL(DOWNLOAD_URL + attachId), temp);
		}

		// 3）压缩临时文件夹，创建ZIP文件（该文件也存在临时文件夹下，好删除操作）
		File[] temps = zipDirectory.listFiles();

		File zipFile = new File(zipDirectory, uuid + ".zip");
		FileUtils.touch(zipFile);
		ZipOutputStream zipOS = new ZipOutputStream(new FileOutputStream(
				zipFile));
		zipOS.setEncoding("GBK");
		zipFile(temps, zipOS);

		// 4）生成输出对象
		HttpHeaders headers = new HttpHeaders();
		// headers.setContentDispositionFormData("attachment",
		// zipFile.getName());
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
				+ zipFile.getName() + "\"");
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(zipFile), headers, HttpStatus.OK);

		// 5）删除临时文件夹
		FileUtils.deleteQuietly(zipDirectory);
		return responseEntity;
	}

	/**
	 * 
	 * <p>
	 * 描述：压缩文件列表中的文件
	 * </p>
	 * 
	 * @param files
	 * @param zipOS
	 * @throws IOException
	 * @throws ServletException
	 */
	private static void zipFile(File[] files, ZipOutputStream zipOS)
			throws Exception {
		try {
			int size = files.length;
			for (int i = 0; i < size; i++) {
				File file = (File) files[i];
				zipFile(file, zipOS);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			zipOS.flush();
			zipOS.close();
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：压缩文件
	 * </p>
	 * 
	 * @param inputFile
	 * @param zipOS
	 * @throws IOException
	 * @throws ServletException
	 */
	private static void zipFile(File inputFile, ZipOutputStream zipOS)
			throws Exception {
		FileInputStream inStream = null;
		BufferedInputStream bInStream = null;

		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					inStream = new FileInputStream(inputFile);
					bInStream = new BufferedInputStream(inStream);

					ZipEntry entry = new ZipEntry(inputFile.getName());
					zipOS.putNextEntry(entry);

					final int MAX_BYTE = 3 * 1024 * 1024; // 最大的流为3M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							zipOS.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					zipOS.write(inOutbyte);
					// Closes the current ZIP entry
					zipOS.closeEntry();
				}
			} else {
				throw new Exception("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			// 关闭
			bInStream.close();
			inStream.close();
		}
	}

	/**
	 * 
	 * <p>
	 * 描述：附件上传（一个或多个）
	 * </p>
	 * 
	 * @Date 2018年8月17日下午3:50:55 <br>
	 * @param request
	 *            带附件信息的 HttpServletRequest
	 * @param remake
	 *            附件备注
	 * @param operatorName
	 *            操作人
	 * @return
	 * @throws Exception
	 */
	public static List<AttachmentEntity> uplaod(HttpServletRequest request,
			String remake, String operatorName) throws Exception {
		List<AttachmentEntity> list = new ArrayList<AttachmentEntity>();

		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件
			MultiValueMap<String, MultipartFile> multiFileMap = multiRequest
					.getMultiFileMap();
			List<MultipartFile> fileSet = new LinkedList<MultipartFile>();
			for (Entry<String, List<MultipartFile>> temp : multiFileMap
					.entrySet()) {
				fileSet = temp.getValue();
			}
			AttachmentEntity attachmentEntity = null;
			Long attachmentId = null;
			MultipartFile file = null;
			for (int i = 0, j = fileSet.size(); i < j; i++) {
				file = fileSet.get(i);
				if (file != null) {
					if (file.isEmpty()) {
						// 文件为空
						continue;
					}
					attachmentId = AttachUtil.uploadAttach(
							file.getOriginalFilename(), file.getInputStream());
					// 说明没有附件，或附件上传失败
					if (null == attachmentId || attachmentId == 0) {
						continue;
					}

					attachmentEntity = new AttachmentEntity();
					attachmentEntity.setAttachmentId(attachmentId);
					attachmentEntity.setAttachmentName(file
							.getOriginalFilename());
					attachmentEntity.setAttachmentSize(file.getInputStream()
							.available() / 1024 + "KB");
					attachmentEntity.setCreateBy(operatorName);
					attachmentEntity.setCreateDate(new Date());
					attachmentEntity.setRemake(remake);

					list.add(attachmentEntity);
				}
			}

		}

		return list;
	}

	/**
	 * 
	 * <p>
	 * 描述：附件上传（已作废，每次只能上传一个附件）
	 * </p>
	 * 
	 * @Date 2018年9月7日下午4:12:37 <br>
	 * @param request
	 * @param remake
	 * @param operatorName
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static List<AttachmentEntity> uplaod1(HttpServletRequest request,
			String remake, String operatorName) throws Exception {
		List<AttachmentEntity> list = new ArrayList<AttachmentEntity>();

		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 这种方式 只能获取第一个上传的文件
			Iterator<String> iter = multiRequest.getFileNames();

			AttachmentEntity attachmentEntity = null;
			Long attachmentId = null;
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next()
						.toString());
				if (file != null) {
					if (file.isEmpty()) {
						// 文件为空
						continue;
					}
					attachmentId = AttachUtil.uploadAttach(
							file.getOriginalFilename(), file.getInputStream());
					// 说明没有附件，或附件上传失败
					if (null == attachmentId || attachmentId == 0) {
						continue;
					}

					attachmentEntity = new AttachmentEntity();
					attachmentEntity.setAttachmentId(attachmentId);
					attachmentEntity.setAttachmentName(file
							.getOriginalFilename());
					attachmentEntity.setAttachmentSize(file.getInputStream()
							.available() / 1024 + "KB");
					attachmentEntity.setCreateBy(operatorName);
					attachmentEntity.setCreateDate(new Date());
					attachmentEntity.setRemake(remake);

					list.add(attachmentEntity);
				}

			}

		}

		return list;
	}
}
