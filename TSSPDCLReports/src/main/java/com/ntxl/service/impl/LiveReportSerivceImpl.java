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
import com.ntxl.dto.LiveSearchDto;
import com.ntxl.service.LiveReportService;

@Service
public class LiveReportSerivceImpl implements LiveReportService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<CDRResultDto> searchAllAccountsBy(LiveSearchDto liveSearchDto) {
		List<CDRResultDto> resultList = new ArrayList<CDRResultDto>();
		List<CDRResultDto> nullList = new ArrayList<CDRResultDto>();
		try{
		StringBuffer query = new StringBuffer("SELECT a.msgId,a.destination,a.msgtext AS Message,a.senderId,b.submittime,c.delivertime,"
				+ "c.status,c.receipt,a.msgtype FROM inserted_history a LEFT JOIN submitted_history b "
				+ "ON b.msgId = a.msgId LEFT JOIN delivered_history c  ON a.msgId = c.msgId ");
		
				if( liveSearchDto.getMobile()!=null|| !liveSearchDto.getMobile().isEmpty()){
					query.append(" where a.destination='91"+liveSearchDto.getMobile()+"'");
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
							if(cdrResultDTo.getSubmitTime().equalsIgnoreCase("null")){
								nullList.add(cdrResultDTo);
							}else{
								resultList.add(cdrResultDTo);
								
							}
							
						}
					}
		}catch(Exception e){
			e.printStackTrace();
		}
		Collections.sort(resultList, CDRResultDto.dateComparator); 
		resultList.addAll(nullList);
		return resultList;
	}
	

}
