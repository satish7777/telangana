package com.ntxl.service;

import java.util.List;

import com.ntxl.dto.CDRResultDto;
import com.ntxl.dto.CDRSearchDto;

public interface CDRReportService {

	
	List<CDRResultDto> searchAllAccountsBy(CDRSearchDto accountSearchDto);

	List<CDRResultDto> searchTotalCountAccounts(CDRSearchDto cdrSearchDto);

}
