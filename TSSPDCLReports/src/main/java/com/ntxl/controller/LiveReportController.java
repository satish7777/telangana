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
import com.ntxl.dto.LiveSearchDto;
import com.ntxl.service.LiveReportService;
import com.ntxl.utils.Response;

@RestController
@RequestMapping("api/liverrpt")
public class LiveReportController {
	
	
	@Autowired
	private Response response;
	
	
	@Autowired
	private LiveReportService liveReportService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LiveReportController.class);
	
	@RequestMapping(value = "/searchMsisdnDetails", method = RequestMethod.POST)
	public Response searchMsisdnDetails(@RequestBody LiveSearchDto liveSearchDto) {
		LOGGER.info("START: Loading Live Report for selected search Parameters: Mobile Number {}", liveSearchDto.getMobile());
		try {
			List<CDRResultDto> pageData = liveReportService.searchAllAccountsBy(liveSearchDto);
			LOGGER.info("START: Loaded Live Report for selected search Parameters: Number of accounts loaded{}", pageData.size());
				response.setData(pageData);
				response.setStatusCode(100);
				response.setStatusMessage("CDR Report details fetched.");
		} catch (Exception e) {
			response.setStatusCode(-1);
			response.setStatusMessage(e.getMessage());
		}
		LOGGER.info("END: Loading Live Report for selected search Parameters: Mobile Number {}", liveSearchDto.getMobile());
		return response;
	}
	
	
	
}
