package com.ntxl.dto;

import java.util.List;

public class Page<T> {
	
	private List<String> pageStates;
	private T pageObject;
	
	public List<String> getPageStates() {
		return pageStates;
	}
	public void setPageStates(List<String> pageStates) {
		this.pageStates = pageStates;
	}
	public T getPageObject() {
		return pageObject;
	}
	public void setPageObject(T pageObject) {
		this.pageObject = pageObject;
	}
	
}
