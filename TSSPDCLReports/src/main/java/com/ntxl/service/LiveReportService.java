package com.ntxl.service;

import java.util.List;

import com.ntxl.dto.CDRResultDto;
import com.ntxl.dto.LiveSearchDto;

public interface LiveReportService {

	
	List<CDRResultDto> searchAllAccountsBy(LiveSearchDto liveSearchDto);

}
