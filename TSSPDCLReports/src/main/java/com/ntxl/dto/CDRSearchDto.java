package com.ntxl.dto;


public class CDRSearchDto {
	
	private String startDate;
	
	private String endDate;
	
	private String accountType;
	
	private String pageNo;
	
	private String itemsPerPage;
	
	private String selSubAccount;
	
	
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

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(String itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public String getSelSubAccount() {
		return selSubAccount;
	}

	public void setSelSubAccount(String selSubAccount) {
		this.selSubAccount = selSubAccount;
	}

}
