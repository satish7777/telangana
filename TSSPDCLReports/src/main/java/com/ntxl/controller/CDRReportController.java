package com.ntxl.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ntxl.dto.CDRResultDto;
import com.ntxl.dto.CDRSearchDto;
import com.ntxl.service.CDRReportService;
import com.ntxl.utils.Response;

@RestController
@RequestMapping("api/cdrrpt")
public class CDRReportController {
	
	
	@Autowired
	private Response response;
	
	
	@Autowired
	private CDRReportService cdrReportService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CDRReportController.class);
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response accountwiseSearch(@RequestBody CDRSearchDto cdrSearchDto) {
		LOGGER.info("START: Loading CDR Report for selected search Parameters: selected Account Type,Start Date,End Date,Page No {}", cdrSearchDto.getAccountType(),cdrSearchDto.getStartDate(),cdrSearchDto.getEndDate(),cdrSearchDto.getPageNo());
		try {
			List<CDRResultDto> pageData = cdrReportService.searchAllAccountsBy(cdrSearchDto);
			LOGGER.info("START: Loaded CDR Report for selected search Parameters: Number of accounts loaded{}", pageData.size());
				response.setData(pageData);
				response.setStatusCode(100);
				response.setStatusMessage("CDR Report details fetched.");
		} catch (Exception e) {
			response.setStatusCode(-1);
			response.setStatusMessage(e.getMessage());
		}
		LOGGER.info("END: Loading CDR Report for selected search Parameters: selected Account Type,Start Date,End Date,Page No {},{},{},{}", cdrSearchDto.getAccountType(),cdrSearchDto.getStartDate(),cdrSearchDto.getEndDate(),cdrSearchDto.getPageNo());
		return response;
	}
	
	@RequestMapping(value = "/searchTotalCount", method = RequestMethod.POST)
	public Response accountwiseSearchTotalCount(@RequestBody CDRSearchDto cdrSearchDto) {
		LOGGER.info("START: Loading Total Number of CDR's for selected search Parameters: selected Account Type,selected Sub Account,Start Date,End Date {},{},{},{}", cdrSearchDto.getAccountType(),cdrSearchDto.getSelSubAccount(),cdrSearchDto.getStartDate(),cdrSearchDto.getEndDate());
		try {
			List<CDRResultDto> resultList = cdrReportService.searchTotalCountAccounts(cdrSearchDto);
			LOGGER.info("START: Loaded CDR Report Total Count for selected search Parameters: Number of accounts loaded{}", resultList);
				response.setData(resultList);
				response.setStatusCode(100);
				response.setStatusMessage("CDR Report details fetched.");
				
		} catch (Exception e) {
			response.setStatusCode(-1);
			response.setStatusMessage(e.getMessage());
		}
		LOGGER.info("END: Loading Total Number of CDR's for selected search Parameters: selected Account Type,selected Sub Account,Start Date,End Date {},{},{},{},{}", cdrSearchDto.getAccountType(),cdrSearchDto.getStartDate(),cdrSearchDto.getEndDate());
		return response;
	}
	
}
