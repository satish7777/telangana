package com.ntxl.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ntxl.dto.AccountSearchDto;
import com.ntxl.dto.TableClientSummaryDTO;
import com.ntxl.repository.TableClientSummaryRepository;
import com.ntxl.service.AccountDetailsService;
import com.ntxl.utils.MapperUtils;

@Service
public class AccountDetailsSerivceImpl implements AccountDetailsService{

	
	@Autowired
	 private TableClientSummaryRepository tableClientSummaryRepo;
	@Autowired
	private MapperUtils mapperUtils;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<String> findByAccountName(String accountType) {
		String accountName=null;
		List<String> resultList = new ArrayList<String>();
		if(accountType.equalsIgnoreCase("smpp")){
			accountName="LS%";
		}else if(accountType.equalsIgnoreCase("http")){
			accountName="LH%";
		}else if(accountType.equalsIgnoreCase("web")){
			accountName="LW%";
		}
		if(accountType.equalsIgnoreCase("smpp")){
			resultList = tableClientSummaryRepo.findUniqueAccountNamesForSmpp(accountName);
		}else{
			resultList = tableClientSummaryRepo.findUniqueAccountNames(accountName);
		}
		 return resultList;
	}

	
	
	
	public List<TableClientSummaryDTO> searchAllAccountsBy(AccountSearchDto accountSearchDto){
		List<TableClientSummaryDTO> resultList = new ArrayList<TableClientSummaryDTO>();
		boolean subAccFlag = false;
		StringBuffer query = new StringBuffer("select * from tbl_client_hourly_summary ");
		Map<String,List<TableClientSummaryDTO>> summaryMap = new HashMap<String,List<TableClientSummaryDTO>>();	
				if(accountSearchDto.getAccountType()!=null && accountSearchDto.getSubAccountType()!=null && 
						accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					String endDate=accountSearchDto.getEndDate();
					query.append("where AccountName='"+accountSearchDto.getSubAccountType()+"' and concat(inputday) between '"+fromDate+"' and '"+endDate+"'");
				}else if(accountSearchDto.getAccountType()!=null && accountSearchDto.getSubAccountType()!=null && 
						(accountSearchDto.getStartDate()==null || accountSearchDto.getStartDate().isEmpty())&& (accountSearchDto.getEndDate()==null || accountSearchDto.getEndDate().isEmpty())){
					query.append("where AccountName='"+accountSearchDto.getSubAccountType()+"'");
				}else if(accountSearchDto.getAccountType()!=null && accountSearchDto.getSubAccountType()==null && 
						accountSearchDto.getStartDate()!=null && accountSearchDto.getEndDate()!=null  && !accountSearchDto.getStartDate().isEmpty() && !accountSearchDto.getEndDate().isEmpty()){
					String fromDate=accountSearchDto.getStartDate();
					String endDate=accountSearchDto.getEndDate();
					String accountName=null;
					if(accountSearchDto.getAccountType().equalsIgnoreCase("smpp")){
						accountName="LS%";
					}else if(accountSearchDto.getAccountType().equalsIgnoreCase("http")){
						accountName="LH%";
					}else if(accountSearchDto.getAccountType().equalsIgnoreCase("web")){
						accountName="LW%";
					}
					if(accountName!=null){
						if(accountName.equalsIgnoreCase("LS%")){
							query.append("where (AccountName like '"+accountName+"' or AccountName='tspdcl') and concat(inputday) between '"+fromDate+"' and '"+endDate+"'");
						}else{
							query.append("where AccountName like '"+accountName+"' and concat(inputday) between '"+fromDate+"' and '"+endDate+"'");
						}
						
					}else{
						query.append("where concat(inputday) between '"+fromDate+"' and '"+endDate+"'");
					}
					subAccFlag = true;
				}else if(accountSearchDto.getAccountType()!=null && accountSearchDto.getSubAccountType()==null && 
						(accountSearchDto.getStartDate()==null || accountSearchDto.getStartDate().isEmpty()) && (accountSearchDto.getEndDate()==null || accountSearchDto.getEndDate().isEmpty())){
					String accountName=null;
					if(accountSearchDto.getAccountType().equalsIgnoreCase("smpp")){
						accountName="LS%";
					}else if(accountSearchDto.getAccountType().equalsIgnoreCase("http")){
						accountName="LH%";
					}else if(accountSearchDto.getAccountType().equalsIgnoreCase("web")){
						accountName="LW%";
					}
					if(accountName!=null){
						if(accountName.equalsIgnoreCase("LS%")){
							query.append("where (AccountName like '"+accountName+"' or AccountName='tspdcl')");
						}else{
							query.append("where AccountName like '"+accountName+"'");
						}
					}
					subAccFlag = true;
				}
				
				List<Map<String, Object>> resultMap = jdbcTemplate.queryForList(query.toString());
				if(!subAccFlag){
					if(resultMap != null && resultMap.size() > 0){
						for(Map<String,Object> rowMap : resultMap){
							List<TableClientSummaryDTO> clientSummaryList = new ArrayList<TableClientSummaryDTO>();
							TableClientSummaryDTO tcs = new TableClientSummaryDTO();
							String insertTime = String.valueOf(rowMap.get("InputDay"));
							tcs.setAccountName(String.valueOf(rowMap.get("AccountName")));
							tcs.setInputDay(String.valueOf(rowMap.get("InputDay")));
							tcs.setSubmitted(String.valueOf(rowMap.get("Submitted")));
							tcs.setDelivered(String.valueOf(rowMap.get("Delivered")));
							tcs.setSubmitFailed(String.valueOf(rowMap.get("DeliveryFailed")));
							if(summaryMap.size()==0){
								clientSummaryList.add(tcs);
								summaryMap.put(insertTime, clientSummaryList);
							}else if(!summaryMap.containsKey(insertTime)){
								clientSummaryList.add(tcs);
								summaryMap.put(insertTime, clientSummaryList);
							}else{
								List<TableClientSummaryDTO> mapList = summaryMap.get(insertTime);
								mapList.add(tcs);
								summaryMap.put(insertTime, mapList);
							}
						//	clientSummaryList.add(tcs);
							
						}
					}
				}else{
					if(resultMap != null && resultMap.size() > 0){
						for(Map<String,Object> rowMap : resultMap){
							List<TableClientSummaryDTO> clientSummaryList = new ArrayList<TableClientSummaryDTO>();
							TableClientSummaryDTO tcs = new TableClientSummaryDTO();
							String insertTime = String.valueOf(rowMap.get("InputDay"));
							String accountName = String.valueOf(rowMap.get("AccountName"));
							tcs.setAccountName(String.valueOf(rowMap.get("AccountName")));
							tcs.setInputDay(String.valueOf(rowMap.get("InputDay")));
							tcs.setSubmitted(String.valueOf(rowMap.get("Submitted")));
							tcs.setDelivered(String.valueOf(rowMap.get("Delivered")));
							tcs.setSubmitFailed(String.valueOf(rowMap.get("DeliveryFailed")));
							if(summaryMap.size()==0){
								clientSummaryList.add(tcs);
								summaryMap.put(insertTime+"/"+accountName, clientSummaryList);
							}else if(!summaryMap.containsKey(insertTime+"/"+accountName)){
								clientSummaryList.add(tcs);
								summaryMap.put(insertTime+"/"+accountName, clientSummaryList);
							}else{
								List<TableClientSummaryDTO> mapList = summaryMap.get(insertTime+"/"+accountName);
								mapList.add(tcs);
								summaryMap.put(insertTime+"/"+accountName, mapList);
							}
							//clientSummaryList.add(tcs);
						}
					}
				}
				
				for (Map.Entry<String, List<TableClientSummaryDTO>> entry : summaryMap.entrySet())
				{
				    List<TableClientSummaryDTO> repoList = entry.getValue();
				    String insertTime = null;
				    String accountName=null;
				    if(!subAccFlag){
				    	insertTime = entry.getKey();
				    	accountName= accountSearchDto.getSubAccountType();
				    }else{
				    	String[] keys = entry.getKey().split("/");
				    	insertTime = keys[0];
				    	accountName=keys[1];
				    }
				    
					int submitted = 0;
					int delivered = 0;
					int failed =0;
				    for(int i=0 ;i<repoList.size();i++){
				    	submitted += Integer.valueOf(repoList.get(i).getSubmitted());
				    	delivered +=Integer.valueOf(repoList.get(i).getDelivered());
				    	failed+=Integer.valueOf(repoList.get(i).getSubmitFailed());
				    }
				    TableClientSummaryDTO clientsumrydto = new TableClientSummaryDTO();
				    clientsumrydto.setInputDay(insertTime);
					clientsumrydto.setAccountName(accountName);
					clientsumrydto.setSubmitted(String.valueOf(submitted));
					clientsumrydto.setDelivered(String.valueOf(delivered));
					clientsumrydto.setSubmitFailed(String.valueOf(failed));
					
					resultList.add(clientsumrydto);
				}
				 Collections.sort(resultList, TableClientSummaryDTO.dateComparator); 
		return resultList;
	}
	
	
	
	
	

}
