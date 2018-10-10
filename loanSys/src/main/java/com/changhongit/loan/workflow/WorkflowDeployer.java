/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.workflow<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.workflow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 描述：结合Spring自动部署Activit流程定义文件BPMN（避免重复部署）
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年6月15日上午10:35:41
 */
public class WorkflowDeployer implements InitializingBean,
		ApplicationContextAware {
	private static final Logger logger = LoggerFactory
			.getLogger(WorkflowDeployer.class);
	/**
	 * bpmn 资源文件地址（classpath 下）
	 */
	private Resource[] deploymentResources;
	/**
	 * 工作流类别（随便设置，以项目名命名即可）
	 */
	private String category;
	private ApplicationContext context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		// TODO Auto-generated method stub
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(category)) {
			category = "loanSys";// 默认为项目名
		}

		if (null != deploymentResources && deploymentResources.length > 0) {
			RepositoryService repositoryService = context
					.getBean(RepositoryService.class);
			logger.info("部署资源个数：{}", deploymentResources.length);
			// 部署的流程名称
			String deploymentName = null;
			// BPMN流程文件资源名称
			String resourceName = null;
			for (Resource r : deploymentResources) {
				resourceName = r.getFilename();
				deploymentName = category + "_" + resourceName;
				logger.info("资源名称：{}", deploymentName);

				boolean doDeploy = true;
				List<Deployment> deployments = repositoryService
						.createDeploymentQuery().deploymentName(deploymentName)
						.orderByDeploymenTime().desc().list();
				if (!deployments.isEmpty()) {
					// 不为空，即已发布过版本
					Deployment history = deployments.get(0);
					InputStream in = null;
					OutputStream out = null;
					try {
						in = repositoryService.getResourceAsStream(
								history.getId(), resourceName);
						if (null != in) {
							File f = File.createTempFile(
									"deployment",
									"xml",
									new File(System
											.getProperty("java.io.tmpdir")));
							f.deleteOnExit();
							out = new FileOutputStream(f);
							IOUtils.copy(in, out);

							doDeploy = (FileUtils.checksumCRC32(f) != FileUtils
									.checksumCRC32(r.getFile()));
						} else {
							throw new ActivitiException("不能读取资源 "
									+ resourceName + ", 输入流为空");
						}
					} catch (Exception e) {
						// TODO: handle exception
						logger.error("Unable to read " + resourceName
								+ " of deployment " + history.getName()
								+ ", id: " + history.getId()
								+ ", will re-deploy");
					} finally {
						in.close();
						out.close();
					}

				} 

				if (doDeploy) {
					repositoryService.createDeployment().name(deploymentName)
							.addInputStream(resourceName, r.getInputStream())
							.deploy();
					logger.info("文件部署成功 : " + r.getFilename());
				} else {
					logger.info("流程文件BPMN没有改动，不需要更新部署.");
				}
			}
		}
	}

	/**
	 * @param deploymentResources
	 *            the deploymentResources to set
	 */
	public void setDeploymentResources(Resource[] deploymentResources) {
		this.deploymentResources = deploymentResources;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(ApplicationContext context) {
		this.context = context;
	}

}
