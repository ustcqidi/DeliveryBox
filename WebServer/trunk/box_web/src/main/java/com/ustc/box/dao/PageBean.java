package com.ustc.box.dao;

import java.util.ArrayList;
import java.util.List;


public class PageBean<T> {

	private int start;
	private int limit;
	private long count;
	private List<T> data = new ArrayList<T>();

	public PageBean() {

	}

	public PageBean(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	

}
