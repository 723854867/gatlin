package org.gatlin.core.bean.model;

import java.util.ArrayList;

public class Paginate<E> extends ArrayList<E> {

	private static final long serialVersionUID = -1680782454943785654L;

	private long total;
	private int pages;
	private int pageStart;
	
	public long getTotal() {
		return total;
	}
	
	public int getPages() {
		return pages;
	}
	
	public int getPageStart() {
		return pageStart;
	}
	
	public void calculate(int page, int pageSize, long total) {
		this.total = total;
		pageSize = Math.max(1, pageSize);    // 至少显示一条数据
		long mod = total % pageSize;
		this.pages = (int) (0 == mod ? total / pageSize : (total / pageSize) + 1);
		page = Math.max(1, page);
		page = Math.min(this.pages, page);
		this.pageStart = (page - 1) * pageSize;
	}
}
