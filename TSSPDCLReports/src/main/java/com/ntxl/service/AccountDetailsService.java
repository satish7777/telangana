package com.ntxl.service;

import java.util.List;

import com.ntxl.dto.AccountSearchDto;
import com.ntxl.dto.TableClientSummaryDTO;

public interface AccountDetailsService {

	public List<String> findByAccountName(String accountType);

	
	List<TableClientSummaryDTO> searchAllAccountsBy(AccountSearchDto accountSearchDto);

}
