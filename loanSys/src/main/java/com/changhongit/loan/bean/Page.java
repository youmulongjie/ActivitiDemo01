package com.changhongit.loan.bean;

import java.util.ArrayList;
import java.util.List;

import com.changhongit.loan.constant.IConstant;

public class Page<T> {
	// 当前页码
	private int currentPage;
	// 全部页码
	private int totalPage;
	// 全部数据
	private int totalCount;
	// 每页多少数据
	private int pageSize = IConstant.INIT_PAGESIZE;
	// 查询返回结果
	private List<T> list = new ArrayList<T>();
	// 分页链接
	private String url;

	public Page() {
		super();
	}

	/**
	 * @param pageSize
	 */
	public Page(int pageSize) {
		super();
		if (pageSize <= 0) {
			pageSize = IConstant.INIT_PAGESIZE;
		}
		this.pageSize = pageSize;
	}

	public String getUrl() {
		return url;
	}

	public Page<T> setUrl(String url) {
		this.url = url;
		return this;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	// 先设置 totalCount
	public Page<T> setCurrentPage(int currentPage) {
		if (currentPage < 0) {
			currentPage = 0;
		}
		this.currentPage = currentPage;
		return this;
	}

	public int getTotalPage() {
		if (totalCount % pageSize == 0) {
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}
		return totalPage;
	}

	public Page<T> setTotalPage(int totalPage) {
		if (totalPage < 0) {
			totalPage = 0;
		}
		this.totalPage = totalPage;
		return this;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public Page<T> setTotalCount(int totalCount) {
		if (totalCount < 0) {
			totalCount = 0;
		}
		this.totalCount = totalCount;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Page<T> setPageSize(int pageSize) {
		if (pageSize <= 0) {
			pageSize = IConstant.INIT_PAGESIZE;
		}
		this.pageSize = pageSize;
		return this;
	}

	public List<T> getList() {
		return list;
	}

	public Page<T> setList(List<T> list) {
		this.list = list;
		return this;
	}

}
