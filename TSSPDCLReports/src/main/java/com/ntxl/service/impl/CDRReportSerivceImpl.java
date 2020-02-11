package com.ntxl.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ntxl.dto.CDRResultDto;
import com.ntxl.dto.CDRSearchDto;
import com.ntxl.service.CDRReportService;

@Service
public class CDRReportSerivceImpl implements CDRReportService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<CDRResultDto> searchAllAccountsBy(CDRSearchDto accountSearchDto) {
		List<CDRResultDto> resultList = new ArrayList<CDRResultDto>();
		try{
		StringBuffer query = new StringBuffer("SELECT a.msgId,a.destination,a.msgtext AS Message,a.senderId,b.submittime,c.delivertime,"
				+ "c.status,c.receipt,a.msgtype FROM inserted_history a LEFT JOIN submitted_history b "
				+ "ON b.msgId = a.msgId LEFT JOIN delivered_history c  ON a.msgId = c.msgId ");
		
				String accountName=null;
				if(accountSearchDto.getAccountType().equalsIgnoreCase("smpp")){
					accountName="LS%";
				}else if(accountSearchDto.getAccountType().equalsIgnoreCase("http")){
					accountName="LH%";
				}else if(accountSearchDto.getAccountType().equalsIgnoreCase("web")){
					accountName="LW%";
				}
				Integer pageNo =  Integer.valueOf(accountSearchDto.getPageNo())-1;
				Integer count = pageNo*Integer.valueOf(accountSearchDto.getItemsPerPage());
				String countStr = String.valueOf(count);
		
				if(accountName !=null && accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					fromDate = fromDate+" 00:00:00";
					String endDate=accountSearchDto.getEndDate();
					endDate = endDate+" 23:59:59";
					if(accountName!=null){
						if(accountName.equalsIgnoreCase("LS%")){
							query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' "
									+ " and (a.systemId LIKE '"+accountName+"' or a.systemId = 'tspdcl') LIMIT "+countStr+","+accountSearchDto.getItemsPerPage());
					
						}else{
							query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' "
									+ " and a.systemId LIKE '"+accountName+"' LIMIT "+countStr+","+accountSearchDto.getItemsPerPage());
					
						}
					}
					
					
				}else if(accountSearchDto.getAccountType()!=null && 
						(accountSearchDto.getStartDate()==null || accountSearchDto.getStartDate().isEmpty())&& (accountSearchDto.getEndDate()==null || accountSearchDto.getEndDate().isEmpty())){
					if(accountName!=null){
						if(accountName.equalsIgnoreCase("LS%")){
							query.append("where (a.systemId LIKE '"+accountName+"' or a.systemId ='tspdcl') LIMIT "+countStr+","+accountSearchDto.getItemsPerPage());
						}else{
							query.append("where a.systemId LIKE '"+accountName+"' LIMIT "+countStr+","+accountSearchDto.getItemsPerPage());
						}
					}
					
					
					
				}else if(accountName ==null &&  
						accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					String endDate=accountSearchDto.getEndDate();
					fromDate = fromDate+" 00:00:00";
					endDate = endDate+" 23:59:59";
					query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' LIMIT "+countStr+","+accountSearchDto.getItemsPerPage());
				}
				
				List<Map<String, Object>> resultMap = jdbcTemplate.queryForList(query.toString());
					if(resultMap != null && resultMap.size() > 0){
						for(Map<String,Object> rowMap : resultMap){
							CDRResultDto cdrResultDTo = new CDRResultDto();
							String msgId = String.valueOf(rowMap.get("msgId"));
							String mobile = String.valueOf(rowMap.get("destination"));
							String hexmessage = String.valueOf(rowMap.get("Message"));
							String msgType = String.valueOf(rowMap.get("msgtype"));
							
							if(msgType.equalsIgnoreCase("LONG TEXT")){
								hexmessage = hexmessage.substring(12,hexmessage.length());
							}
							
							byte[] bytes = Hex.decodeHex(hexmessage.toCharArray());
							String message = new String(bytes, "UTF-8");
							String sender = String.valueOf(rowMap.get("senderId"));
							String submitTime = String.valueOf(rowMap.get("submittime"));
							submitTime = submitTime.replace(".0","");
							String deliverTime = String.valueOf(rowMap.get("delivertime"));
							deliverTime = deliverTime.replace(".0","");
							
							
							
							
							String deliverStatus = String.valueOf(rowMap.get("status"));
							byte[] b = (byte[]) rowMap.get("receipt");
							String receipt="";
							if(b!=null){
								receipt = new String(b);
								int errIndex = receipt.indexOf("err:");
								receipt = receipt.substring(errIndex+4,errIndex+7);
							}
							cdrResultDTo.setMsgId(msgId);
							cdrResultDTo.setMobile(mobile);
							cdrResultDTo.setMessage(message);
							cdrResultDTo.setSender(sender);
							cdrResultDTo.setSubmitTime(submitTime);
							cdrResultDTo.setDeliverTime(deliverTime);
							cdrResultDTo.setDeliverStatus(deliverStatus);
							cdrResultDTo.setErrorCode(receipt);
							resultList.add(cdrResultDTo);
							
						}
					}
		}catch(Exception e){
			e.printStackTrace();
		}
		Collections.sort(resultList, CDRResultDto.dateComparator); 
		return resultList;
	}


	@Override
	public List<CDRResultDto> searchTotalCountAccounts(CDRSearchDto accountSearchDto) {
		List<CDRResultDto> resultList = new ArrayList<CDRResultDto>();
		try{
			StringBuffer query = new StringBuffer("SELECT a.msgId,a.systemId,a.destination,a.msgtext AS Message,a.senderId,b.submittime,c.delivertime,"
					+ "c.status,c.receipt,a.msgtype FROM inserted_history a FORCE INDEX(intime) LEFT JOIN submitted_history b "
					+ "ON b.msgId = a.msgId LEFT JOIN delivered_history c  ON a.msgId = c.msgId ");
			
					
			if(accountSearchDto.getSelSubAccount()!=null){
				if(accountSearchDto.getSelSubAccount() !=null && accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					fromDate = fromDate+" 00:00:00";
					String endDate=accountSearchDto.getEndDate();
					endDate = endDate+" 23:59:59";
							query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' "
									+ " and a.systemId = '"+accountSearchDto.getSelSubAccount()+"'");
				}
				
				
			}else{
				String accountName=null;
				if(accountSearchDto.getAccountType().equalsIgnoreCase("smpp")){
					accountName="LS%";
				}else if(accountSearchDto.getAccountType().equalsIgnoreCase("http")){
					accountName="LH%";
				}else if(accountSearchDto.getAccountType().equalsIgnoreCase("web")){
					accountName="LW%";
				}
				
		
				if(accountName !=null && accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					fromDate = fromDate+" 00:00:00";
					String endDate=accountSearchDto.getEndDate();
					endDate = endDate+" 23:59:59";
					if(accountName!=null){
						if(accountName.equalsIgnoreCase("LS%")){
							query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' "
									+ " and (a.systemId LIKE '"+accountName+"' or a.systemId = 'tspdcl')");
					
						}else{
							query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' "
									+ " and a.systemId LIKE '"+accountName+"'");
					
						}
					}
					
					
				}else if(accountSearchDto.getAccountType()!=null && 
						(accountSearchDto.getStartDate()==null || accountSearchDto.getStartDate().isEmpty())&& (accountSearchDto.getEndDate()==null || accountSearchDto.getEndDate().isEmpty())){
					if(accountName!=null){
						if(accountName.equalsIgnoreCase("LS%")){
							query.append("where (a.systemId LIKE '"+accountName+"' or a.systemId ='tspdcl')");
						}else{
							query.append("where a.systemId LIKE '"+accountName+"'");
						}
					}
				}else if(accountName ==null &&  
						accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					String endDate=accountSearchDto.getEndDate();
					fromDate = fromDate+" 00:00:00";
					endDate = endDate+" 23:59:59";
					query.append("where a.inTime between '"+fromDate+"' and '"+endDate+"' ");
				}
			}
			
			
			
					
					List<Map<String, Object>> resultMap = jdbcTemplate.queryForList(query.toString());
					if(resultMap != null && resultMap.size() > 0){
						for(Map<String,Object> rowMap : resultMap){
							CDRResultDto cdrResultDTo = new CDRResultDto();
							String msgId = String.valueOf(rowMap.get("msgId"));
							String mobile = String.valueOf(rowMap.get("destination"));
							String systemId = String.valueOf(rowMap.get("systemId"));
							String hexmessage = String.valueOf(rowMap.get("Message"));
							String msgType = String.valueOf(rowMap.get("msgtype"));
							
							if(msgType.equalsIgnoreCase("LONG TEXT")){
								hexmessage = hexmessage.substring(12,hexmessage.length());
							}
							byte[] bytes = Hex.decodeHex(hexmessage.toCharArray());
							String message = new String(bytes, "UTF-8");
							String sender = String.valueOf(rowMap.get("senderId"));
							String submitTime = String.valueOf(rowMap.get("submittime"));
							submitTime = submitTime.replace(".0","");
							String deliverTime = String.valueOf(rowMap.get("delivertime"));
							deliverTime = deliverTime.replace(".0","");
							String deliverStatus = String.valueOf(rowMap.get("status"));
							byte[] b = (byte[]) rowMap.get("receipt");
							String receipt="";
							if(b!=null){
								receipt = new String(b);
								int errIndex = receipt.indexOf("err:");
								receipt = receipt.substring(errIndex+4,errIndex+7);
							}
							cdrResultDTo.setMsgId(msgId);
							cdrResultDTo.setMobile(mobile);
							cdrResultDTo.setMessage(message);
							cdrResultDTo.setSender(sender);
							cdrResultDTo.setSubmitTime(submitTime);
							cdrResultDTo.setDeliverTime(deliverTime);
							cdrResultDTo.setDeliverStatus(deliverStatus);
							cdrResultDTo.setErrorCode(receipt);
							cdrResultDTo.setSystemId(systemId);
							resultList.add(cdrResultDTo);
							
						}
					}
			}catch(Exception e){
				e.printStackTrace();
			}
			return resultList;
	}
	
	
	
	
	

}
