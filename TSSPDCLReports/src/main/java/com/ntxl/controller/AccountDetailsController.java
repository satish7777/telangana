package com.ntxl.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ntxl.dto.AccountSearchDto;
import com.ntxl.dto.TableClientSummaryDTO;
import com.ntxl.service.AccountDetailsService;
import com.ntxl.utils.Response;

@RestController
@RequestMapping("api/accoutdetails")
public class AccountDetailsController {
	
	
	@Autowired
	private Response response;
	
	
	@Autowired
	private AccountDetailsService accountDetailsService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailsController.class);
	
	@RequestMapping(value = "/loadSubAccounts", method = RequestMethod.GET)
	public Response loadSubAccounts(@RequestParam("selAccType") String selAccountType) {
		System.out.println(selAccountType);
		LOGGER.info("START: Loading Sub Accounts for selected Account Type: selected Account Type {}", selAccountType);
		List<String> accountNamesList = accountDetailsService.findByAccountName(selAccountType);
		LOGGER.info("START: Loaded Sub Accounts for selected Account Type: Number of sub accounts loaded{}", accountNamesList.size());
		response.setData(accountNamesList);
		LOGGER.info("END: Loading Sub Accounts for selected Account Type: selected Account Type {}", selAccountType);
		return response;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Response accountwiseSearch(@RequestBody AccountSearchDto accountSearchDto) {
		LOGGER.info("START: Loading Accounts Report for selected search Parameters: selected Account Type,Sub Account,Start Date,End Date {}", accountSearchDto.getAccountType(),accountSearchDto.getSubAccountType(),accountSearchDto.getStartDate(),accountSearchDto.getEndDate());
		try {
			List<TableClientSummaryDTO> pageData = accountDetailsService.searchAllAccountsBy(accountSearchDto);
			LOGGER.info("START: Loaded Accounts Report for selected search Parameters: Number of accounts loaded{}", pageData.size());
				response.setData(pageData);
				response.setStatusCode(100);
				response.setStatusMessage("Account Report details fetched.");
		} catch (Exception e) {
			response.setStatusCode(-1);
			response.setStatusMessage(e.getMessage());
		}
		LOGGER.info("END: Loading Loading Accounts Report for selected search Parameters: selected Account Type,Sub Account,Start Date,End Date {}", accountSearchDto.getAccountType(),accountSearchDto.getSubAccountType(),accountSearchDto.getStartDate(),accountSearchDto.getEndDate());
		return response;
	}
	
	
}
