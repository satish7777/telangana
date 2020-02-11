package com.ntxl.utils;

public class SearchCriteria {
	private String key;
    private String operation;
    private Object value;
    private String joinStr;
    
    public SearchCriteria(String key, String operation, Object value) {
    	this.key = key;
    	this.operation = operation;
    	this.value = value;
    }
    
    public SearchCriteria(String key, String operation, Object value, String joinStr) {
    	this.key = key;
    	this.operation = operation;
    	this.value = value;
    	this.joinStr = joinStr;
    }
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public String getJoinStr() {
		return joinStr;
	}

	public void setJoinStr(String joinStr) {
		this.joinStr = joinStr;
	}


}
