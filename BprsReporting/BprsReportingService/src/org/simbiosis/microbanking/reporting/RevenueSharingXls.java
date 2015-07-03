package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.IRevenueBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbanking.reporting.model.RevenueSharingDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getRevenueSharingXls")
public class RevenueSharingXls extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/RevenueBp")
	IRevenueBp revenueBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	IDepositBp depositBp;
	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;

	long branch;
	Date date;

	public RevenueSharingXls() {
		super("RevenueSharing");
		setType(2);
	}

	List<RevenueSharingDv> prepareData(int month, int year) {
		List<RevenueSharingDv> result = new ArrayList<RevenueSharingDv>();
		List<RevenueSharingDto> listRevSharing = revenueBp.listRevenueSharing(
				getSession(), month, year, 0);
		for (RevenueSharingDto revSharing : listRevSharing) {
			RevenueSharingDv sn = new RevenueSharingDv();
			sn.setAvgBallance(revSharing.getAverageValue());
			sn.setRevValue(revSharing.getCustomerSharing());
			sn.setEquivalent(sn.getRevValue() * 1200 / sn.getAvgBallance());
			sn.setTax(revSharing.getTax());
			sn.setType(revSharing.getType());
			if (revSharing.getType() == 1) {
				SavingInformationDto info = savingBp.getInformation(revSharing
						.getAccount());
				sn.setCode(info.getCode());
				sn.setName(info.getName());
				sn.setProduct(info.getProductName());
			} else {
				DepositInformationDto info = depositBp
						.getDepositInformation(revSharing.getAccount());
				sn.setCode(info.getCode());
				sn.setName(info.getName());
				sn.setProduct(info.getProductName());
			}
			result.add(sn);
		}
		// Sortir
		Collections.sort(result, new Comparator<RevenueSharingDv>() {

			@Override
			public int compare(RevenueSharingDv arg0, RevenueSharingDv arg1) {
				if (arg0.getType() == arg1.getType()) {
					if (arg0.getProduct().equalsIgnoreCase(arg1.getProduct())) {
						return arg0.getCode().compareToIgnoreCase(
								arg1.getCode());
					}
					return arg0.getProduct().compareToIgnoreCase(
							arg1.getProduct());
				}
				return arg0.getType() - arg1.getType();
			}
		});
		//
		int i = 1;
		for (RevenueSharingDv rev : result) {
			rev.setNr(i++);
		}
		//
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		try {
			String month = request.getParameter("month");
			String year = request.getParameter("year");
			System.out.println("Bulan tahun " + month + "-" + year);
			prepare();
			setBeanCollection(prepareData(Integer.parseInt(month),
					Integer.parseInt(year)));
			//
			setParameter("RevenueSharing.company", getCompanyName());
			setParameter("RevenueSharing.branch", "KONSOLIDASI");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

	}

}
