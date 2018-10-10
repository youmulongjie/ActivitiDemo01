/**
 * <p>
 * 描述：
 * </p>

 * @package ：com.changhongit.loan.entity<br>
 * @author ：wanglongjie<br>
 */
package com.changhongit.loan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <p>
 * 描述：附件表 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月24日下午3:29:14
 */
@Entity
@Table(name = "loan_attachment")
public class AttachmentEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_attachment_s")
	@SequenceGenerator(name = "loan_attachment_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_attachment_s")
	private Long id;

	/**
	 * 借款基本信息ID
	 */
	@Column(name = "MAIN_ID")
	private Long mainId;
	/**
	 * 附件ID
	 */
	@Column(name = "attachment_id")
	private Long attachmentId;
	/**
	 * 附件名称
	 */
	@Column(name = "attachment_name")
	private String attachmentName;
	/**
	 * 附件大小（单位kb）
	 */
	@Column(name = "attachment_size")
	private String attachmentSize;
	/**
	 * 备注
	 */
	@Column(name = "remake")
	private String remake;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the mainId
	 */
	public Long getMainId() {
		return mainId;
	}

	/**
	 * @param mainId
	 *            the mainId to set
	 */
	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	/**
	 * @return the attachmentId
	 */
	public Long getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId
	 *            the attachmentId to set
	 */
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName
	 *            the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the attachmentSize
	 */
	public String getAttachmentSize() {
		return attachmentSize;
	}

	/**
	 * @param attachmentSize
	 *            the attachmentSize to set
	 */
	public void setAttachmentSize(String attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	/**
	 * @return the remake
	 */
	public String getRemake() {
		return remake;
	}

	/**
	 * @param remake
	 *            the remake to set
	 */
	public void setRemake(String remake) {
		this.remake = remake;
	}

}
