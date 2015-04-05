package org.simbiosis.microbank;

import java.util.Date;
import java.util.List;

public interface IRevenue {
	//
	List<RevenueDto> listRevenueByProduct(long company, Date beginDate,
			Date endDate);

	List<RevenueDto> listRevenueByCoa(long company);

	//
	long saveRevenue(RevenueDto dto);

	RevenueDto getRevenue(long id);

	long saveRevenueSharing(RevenueSharingDto dto);

	RevenueSharingDto getRevenueSharing(long id);

	List<RevenueSharingDto> listRevenueSharing(long company,
			int month, int year, long id);

	//
	long saveDepositRevSharing(DepositRevSharingDto drs);

	DepositRevSharingDto getDepositRevSharing(long id);

	List<DepositRevSharingDto> listDepositRevSharing(long company, Date date);

	void saveDepositRevSharingStatus(long id, int status);
}
