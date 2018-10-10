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
 * 
 * <p>
 * 描述：固定审批人 Entity
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年5月17日下午1:36:59
 */
@Entity
@Table(name = "loan_Approver_config")
public class LoanApproverConfigEntity extends BaseEntity implements
		Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_Approver_config_s")
	@SequenceGenerator(name = "loan_Approver_config_s", initialValue = 1, allocationSize = 1, sequenceName = "loan_Approver_config_s")
	private Long id;

	/**
	 * 姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 职位
	 */
	@Column(name = "position")
	private String position;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

}
