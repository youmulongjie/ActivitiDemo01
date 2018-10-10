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
 * 描述：借款系统管理员
 * </p>
 * 
 * @author wanglongjie<br>
 * @version v1.0 2018年7月5日下午4:43:11
 */
@Entity
@Table(name = "loan_sys_admin")
public class LoanSysAdminEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOAN_SYS_ADMIN_S")
	@SequenceGenerator(name = "LOAN_SYS_ADMIN_S", initialValue = 1, allocationSize = 1, sequenceName = "LOAN_SYS_ADMIN_S")
	private Long id;

	/**
	 * 管理员名称
	 */
	@Column(name = "username")
	private String username;

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
