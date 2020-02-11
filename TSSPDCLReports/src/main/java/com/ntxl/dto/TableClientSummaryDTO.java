package com.ntxl.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TableClientSummaryDTO {
	
	private String accountName;
	
	private String inputDay;
	
	private String hour;
	
	private String inserted;
	
	private String submitted;
	
	private String delivered;
	
	private String submitFailed;
	
	private String deliveryFailed;
	
	private String violations;

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getInputDay() {
		return inputDay;
	}

	public void setInputDay(String inputDay) {
		this.inputDay = inputDay;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getInserted() {
		return inserted;
	}

	public void setInserted(String inserted) {
		this.inserted = inserted;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}

	public String getSubmitFailed() {
		return submitFailed;
	}

	public void setSubmitFailed(String submitFailed) {
		this.submitFailed = submitFailed;
	}

	public String getDeliveryFailed() {
		return deliveryFailed;
	}

	public void setDeliveryFailed(String deliveryFailed) {
		this.deliveryFailed = deliveryFailed;
	}

	public String getViolations() {
		return violations;
	}

	public void setViolations(String violations) {
		this.violations = violations;
	}
	
	
	 public static Comparator<TableClientSummaryDTO> dateComparator = new Comparator<TableClientSummaryDTO>() {         
		    @Override         
		    public int compare(TableClientSummaryDTO rsdto1, TableClientSummaryDTO rsdto2) {  
		    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    	 Date date1 = null;
		    	 Date date2 = null;
		    	 try {
					date1 = sdf.parse(rsdto1.getInputDay());
					date2 = sdf.parse(rsdto2.getInputDay());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		      if(date1.compareTo(date2) == -1)
		      return 1;
		      if(date1.compareTo(date2) == 1)
		       return -1;
		      else
		       return 0;         
		    }     
		  };  

	
}
