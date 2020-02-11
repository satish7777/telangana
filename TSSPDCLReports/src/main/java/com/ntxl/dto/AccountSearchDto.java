package com.ntxl.dto;


public class AccountSearchDto {
	
	private String startDate;
	
	private String endDate;
	
	private String accountType;
	
	private String subAccountType;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSubAccountType() {
		return subAccountType;
	}

	public void setSubAccountType(String subAccountType) {
		this.subAccountType = subAccountType;
	}
	

}
