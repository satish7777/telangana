package com.ntxl.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_client_hourly_summary")
public class TableClientSummary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AccountName")
	private String accountName;
	
	@Column(name = "InputDay")
	private Date inputDay;
	
	@Column(name = "Hour")
	private int hour;
	
	@Column(name = "Inserted")
	private int inserted;
	
	@Column(name = "Submitted")
	private int submitted;
	
	@Column(name = "Delivered")
	private int delivered;
	
	@Column(name = "SubmitFailed")
	private int submitFailed;
	
	@Column(name = "DeliveryFailed")
	private int deliveryFailed;
	
	@Column(name = "Violations")
	private int violations;
	
	
	private String startDate;
	private String endDate;
	private String selAccType;
	private String selSubAccType;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getInputDay() {
		return inputDay;
	}

	public void setInputDay(Date inputDay) {
		this.inputDay = inputDay;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getInserted() {
		return inserted;
	}

	public void setInserted(int inserted) {
		this.inserted = inserted;
	}

	public int getSubmitted() {
		return submitted;
	}

	public void setSubmitted(int submitted) {
		this.submitted = submitted;
	}

	public int getDelivered() {
		return delivered;
	}

	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}

	public int getSubmitFailed() {
		return submitFailed;
	}

	public void setSubmitFailed(int submitFailed) {
		this.submitFailed = submitFailed;
	}

	public int getDeliveryFailed() {
		return deliveryFailed;
	}

	public void setDeliveryFailed(int deliveryFailed) {
		this.deliveryFailed = deliveryFailed;
	}

	public int getViolations() {
		return violations;
	}

	public void setViolations(int violations) {
		this.violations = violations;
	}

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

	public String getSelAccType() {
		return selAccType;
	}

	public void setSelAccType(String selAccType) {
		this.selAccType = selAccType;
	}

	public String getSelSubAccType() {
		return selSubAccType;
	}

	public void setSelSubAccType(String selSubAccType) {
		this.selSubAccType = selSubAccType;
	}

}
