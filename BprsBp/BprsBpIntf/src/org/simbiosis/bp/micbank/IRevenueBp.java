package org.simbiosis.bp.micbank;

import java.util.Date;
import java.util.List;

import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.microbank.RevenueSharingDto;

public interface IRevenueBp {

	void saveDepositRevSharing(DepositRevSharingDto revenue);

	List<RevenueDto> listRevenueByProduct(String key, Date beginDate,
			Date endDate);

	List<RevenueDto> listRevenueByCoa(String key, Date beginDate,
			Date endDate);

	List<DepositRevSharingDto> listDepositRevSharing(String key, Date strToDate);

	List<RevenueSharingDto> listRevenueSharing(String key, int month, int year,
			long id);

	void saveRevenueSharing(RevenueSharingDto revenue);

	void saveRevenue(RevenueDto revenue);

	void saveDepositRevSharingStatus(long id, int status);

}
