package com.ntxl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ntxl.dto.TableClientSummaryDTO;
import com.ntxl.entity.TableClientSummary;

@Repository
public interface TableClientSummaryRepository extends PagingAndSortingRepository<TableClientSummary, Long>{

	
	@Query(nativeQuery=true, value="Select distinct accountname from tbl_client_hourly_summary where accountname like ?")
	List<String> findUniqueAccountNames(String accName);
	
	
	@Query(nativeQuery=true, value="Select distinct accountname from tbl_client_hourly_summary where accountname like ? or accountname='tspdcl'")
	List<String> findUniqueAccountNamesForSmpp(String accName);

	@Query(nativeQuery=true, value="Select * from tbl_client_hourly_summary where accountname=?1 and inputday between ")
	List<TableClientSummaryDTO> findAllAccountsBySearch(String subAccountType, String startDate, String endDate);
	
}
