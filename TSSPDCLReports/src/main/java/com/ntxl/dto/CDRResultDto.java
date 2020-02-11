package com.ntxl.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class CDRResultDto {
	
	private String msgId;
	
	private String mobile;
	
	private String message;
	
	private String sender;
	
	private String submitTime;
	
	private String deliverTime;
	
	private String deliverStatus;
	
	private String errorCode;
	
	private String systemId;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(String deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	 public static Comparator<CDRResultDto> dateComparator = new Comparator<CDRResultDto>() {         
		    @Override         
		    public int compare(CDRResultDto rsdto1, CDRResultDto rsdto2) {  
		    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		    	 Date date1 = null;
		    	 Date date2 = null;
		    	 try {
		    			 date1 = sdf.parse(rsdto1.getSubmitTime());
		    			 date2 = sdf.parse(rsdto2.getSubmitTime());
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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}  
	

}
