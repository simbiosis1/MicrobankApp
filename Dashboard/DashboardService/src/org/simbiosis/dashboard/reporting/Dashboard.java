package org.simbiosis.dashboard.reporting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simbiosis.dashboard.reporting.model.DashboardDv;
import org.simbiosis.microbank.model.DepositRpt;
import org.simbiosis.microbank.model.LoanRpt;
import org.simbiosis.microbank.model.SavingRpt;

public class Dashboard {

	public Dashboard(){
		
	}
	
	public List<DashboardDv> generate(List<SavingRpt> listSaving,
			List<DepositRpt> listDeposit, List<LoanRpt> listLoan) {
		Double[] posts = { 0.00, 25000000.00, 50000000.00, 100000000.00,
				200000000.00, 500000000.00, 1000000000.00 };
		String[] groups = { "", "Tabungan", "Deposito", "Pembiayaan" };
		double pembagi = 1000;
		List<DashboardDv> result = new ArrayList<DashboardDv>();
		Map<Long, DashboardDv> mapSaving = new HashMap<Long, DashboardDv>();
		Map<Long, DashboardDv> mapDeposit = new HashMap<Long, DashboardDv>();
		Map<Long, DashboardDv> mapLoan = new HashMap<Long, DashboardDv>();
		for (SavingRpt savingRpt : listSaving) {
			int pos = 7;
			if (savingRpt.getValAfter() < posts[1]) {
				pos = 1;
			} else if (savingRpt.getValAfter() < posts[2]) {
				pos = 2;
			} else if (savingRpt.getValAfter() < posts[3]) {
				pos = 3;
			} else if (savingRpt.getValAfter() < posts[4]) {
				pos = 4;
			} else if (savingRpt.getValAfter() < posts[5]) {
				pos = 5;
			} else if (savingRpt.getValAfter() < posts[6]) {
				pos = 6;
			}

			DashboardDv sn = mapSaving.get(savingRpt.getProduct());
			if (sn == null) {
				sn = new DashboardDv();
				sn.setGroup(groups[1]);
				sn.setCategory(savingRpt.getProductName());
				switch (pos) {
				case 1:
					sn.setCount1(1);
					sn.setValue1(savingRpt.getValAfter());
					break;
				case 2:
					sn.setCount2(1);
					sn.setValue2(savingRpt.getValAfter());
					break;
				case 3:
					sn.setCount3(1);
					sn.setValue3(savingRpt.getValAfter());
					break;
				case 4:
					sn.setCount4(1);
					sn.setValue4(savingRpt.getValAfter());
					break;
				case 5:
					sn.setCount5(1);
					sn.setValue5(savingRpt.getValAfter());
					break;
				case 6:
					sn.setCount6(1);
					sn.setValue6(savingRpt.getValAfter());
					break;
				case 7:
					sn.setCount7(1);
					sn.setValue7(savingRpt.getValAfter());
					break;
				default:
					break;
				}
				mapSaving.put(savingRpt.getProduct(), sn);
			} else {
				switch (pos) {
				case 1:
					sn.setCount1(sn.getCount1() + 1);
					sn.setValue1(sn.getValue1() + savingRpt.getValAfter());
					break;
				case 2:
					sn.setCount2(sn.getCount2() + 1);
					sn.setValue2(sn.getValue2() + savingRpt.getValAfter());
					break;
				case 3:
					sn.setCount3(sn.getCount3() + 1);
					sn.setValue3(sn.getValue3() + savingRpt.getValAfter());
					break;
				case 4:
					sn.setCount4(sn.getCount4() + 1);
					sn.setValue4(sn.getValue4() + savingRpt.getValAfter());
					break;
				case 5:
					sn.setCount5(sn.getCount5() + 1);
					sn.setValue5(sn.getValue5() + savingRpt.getValAfter());
					break;
				case 6:
					sn.setCount6(sn.getCount6() + 1);
					sn.setValue6(sn.getValue6() + savingRpt.getValAfter());
					break;
				case 7:
					sn.setCount7(sn.getCount7() + 1);
					sn.setValue7(sn.getValue7() + savingRpt.getValAfter());
					break;
				default:
					break;
				}
				mapSaving.put(savingRpt.getProduct(), sn);
			}
		}
		for (DashboardDv dv : mapSaving.values()) {
			dv.setValue1(dv.getValue1() / pembagi);
			dv.setValue2(dv.getValue2() / pembagi);
			dv.setValue3(dv.getValue3() / pembagi);
			dv.setValue4(dv.getValue4() / pembagi);
			dv.setValue5(dv.getValue5() / pembagi);
			dv.setValue6(dv.getValue6() / pembagi);
			dv.setValue7(dv.getValue7() / pembagi);
			dv.setSubTotal(dv.getValue1() + dv.getValue2() + dv.getValue3()
					+ dv.getValue4() + dv.getValue5() + dv.getValue6()
					+ dv.getValue7());
			result.add(dv);
		}

		for (DepositRpt depositRpt : listDeposit) {
			int pos = 7;
			if (depositRpt.getValue() < posts[1]) {
				pos = 1;
			} else if (depositRpt.getValue() < posts[2]) {
				pos = 2;
			} else if (depositRpt.getValue() < posts[3]) {
				pos = 3;
			} else if (depositRpt.getValue() < posts[4]) {
				pos = 4;
			} else if (depositRpt.getValue() < posts[5]) {
				pos = 5;
			} else if (depositRpt.getValue() < posts[6]) {
				pos = 6;
			}

			DashboardDv sn = mapDeposit.get(depositRpt.getProduct());
			if (sn == null) {
				sn = new DashboardDv();
				sn.setGroup(groups[2]);
				sn.setCategory(depositRpt.getProductName());
				switch (pos) {
				case 1:
					sn.setCount1(1);
					sn.setValue1(depositRpt.getValue());
					break;
				case 2:
					sn.setCount2(1);
					sn.setValue2(depositRpt.getValue());
					break;
				case 3:
					sn.setCount3(1);
					sn.setValue3(depositRpt.getValue());
					break;
				case 4:
					sn.setCount4(1);
					sn.setValue4(depositRpt.getValue());
					break;
				case 5:
					sn.setCount5(1);
					sn.setValue5(depositRpt.getValue());
					break;
				case 6:
					sn.setCount6(1);
					sn.setValue6(depositRpt.getValue());
					break;
				case 7:
					sn.setCount7(1);
					sn.setValue7(depositRpt.getValue());
					break;
				default:
					break;
				}
				mapDeposit.put(depositRpt.getProduct(), sn);
			} else {
				switch (pos) {
				case 1:
					sn.setCount1(sn.getCount1() + 1);
					sn.setValue1(sn.getValue1() + depositRpt.getValue());
					break;
				case 2:
					sn.setCount2(sn.getCount2() + 1);
					sn.setValue2(sn.getValue2() + depositRpt.getValue());
					break;
				case 3:
					sn.setCount3(sn.getCount3() + 1);
					sn.setValue3(sn.getValue3() + depositRpt.getValue());
					break;
				case 4:
					sn.setCount4(sn.getCount4() + 1);
					sn.setValue4(sn.getValue4() + depositRpt.getValue());
					break;
				case 5:
					sn.setCount5(sn.getCount5() + 1);
					sn.setValue5(sn.getValue5() + depositRpt.getValue());
					break;
				case 6:
					sn.setCount6(sn.getCount6() + 1);
					sn.setValue6(sn.getValue6() + depositRpt.getValue());
					break;
				case 7:
					sn.setCount7(sn.getCount7() + 1);
					sn.setValue7(sn.getValue7() + depositRpt.getValue());
					break;
				default:
					break;
				}
				mapDeposit.put(depositRpt.getProduct(), sn);
			}
		}
		for (DashboardDv dv : mapDeposit.values()) {
			dv.setValue1(dv.getValue1() / pembagi);
			dv.setValue2(dv.getValue2() / pembagi);
			dv.setValue3(dv.getValue3() / pembagi);
			dv.setValue4(dv.getValue4() / pembagi);
			dv.setValue5(dv.getValue5() / pembagi);
			dv.setValue6(dv.getValue6() / pembagi);
			dv.setValue7(dv.getValue7() / pembagi);
			dv.setSubTotal(dv.getValue1() + dv.getValue2() + dv.getValue3()
					+ dv.getValue4() + dv.getValue5() + dv.getValue6()
					+ dv.getValue7());
			result.add(dv);
		}

		for (LoanRpt loanRpt : listLoan) {
			int pos = 7;
			if (loanRpt.getOsPrincipal() < posts[1]) {
				pos = 1;
			} else if (loanRpt.getOsPrincipal() < posts[2]) {
				pos = 2;
			} else if (loanRpt.getOsPrincipal() < posts[3]) {
				pos = 3;
			} else if (loanRpt.getOsPrincipal() < posts[4]) {
				pos = 4;
			} else if (loanRpt.getOsPrincipal() < posts[5]) {
				pos = 5;
			} else if (loanRpt.getOsPrincipal() < posts[6]) {
				pos = 6;
			}

			DashboardDv sn = mapLoan.get(loanRpt.getProduct());
			if (sn == null) {
				sn = new DashboardDv();
				sn.setGroup(groups[3]);
				sn.setCategory(loanRpt.getProductName());
				switch (pos) {
				case 1:
					sn.setCount1(1);
					sn.setValue1(loanRpt.getOsPrincipal());
					break;
				case 2:
					sn.setCount2(1);
					sn.setValue2(loanRpt.getOsPrincipal());
					break;
				case 3:
					sn.setCount3(1);
					sn.setValue3(loanRpt.getOsPrincipal());
					break;
				case 4:
					sn.setCount4(1);
					sn.setValue4(loanRpt.getOsPrincipal());
					break;
				case 5:
					sn.setCount5(1);
					sn.setValue5(loanRpt.getOsPrincipal());
					break;
				case 6:
					sn.setCount6(1);
					sn.setValue6(loanRpt.getOsPrincipal());
					break;
				case 7:
					sn.setCount7(1);
					sn.setValue7(loanRpt.getOsPrincipal());
					break;
				default:
					break;
				}
				mapLoan.put(loanRpt.getProduct(), sn);
			} else {
				switch (pos) {
				case 1:
					sn.setCount1(sn.getCount1() + 1);
					sn.setValue1(sn.getValue1() + loanRpt.getOsPrincipal());
					break;
				case 2:
					sn.setCount2(sn.getCount2() + 1);
					sn.setValue2(sn.getValue2() + loanRpt.getOsPrincipal());
					break;
				case 3:
					sn.setCount3(sn.getCount3() + 1);
					sn.setValue3(sn.getValue3() + loanRpt.getOsPrincipal());
					break;
				case 4:
					sn.setCount4(sn.getCount4() + 1);
					sn.setValue4(sn.getValue4() + loanRpt.getOsPrincipal());
					break;
				case 5:
					sn.setCount5(sn.getCount5() + 1);
					sn.setValue5(sn.getValue5() + loanRpt.getOsPrincipal());
					break;
				case 6:
					sn.setCount6(sn.getCount6() + 1);
					sn.setValue6(sn.getValue6() + loanRpt.getOsPrincipal());
					break;
				case 7:
					sn.setCount7(sn.getCount7() + 1);
					sn.setValue7(sn.getValue7() + loanRpt.getOsPrincipal());
					break;
				default:
					break;
				}
				mapLoan.put(loanRpt.getProduct(), sn);
			}
		}
		for (DashboardDv dv : mapLoan.values()) {
			dv.setValue1(dv.getValue1() / pembagi);
			dv.setValue2(dv.getValue2() / pembagi);
			dv.setValue3(dv.getValue3() / pembagi);
			dv.setValue4(dv.getValue4() / pembagi);
			dv.setValue5(dv.getValue5() / pembagi);
			dv.setValue6(dv.getValue6() / pembagi);
			dv.setValue7(dv.getValue7() / pembagi);
			dv.setSubTotal(dv.getValue1() + dv.getValue2() + dv.getValue3()
					+ dv.getValue4() + dv.getValue5() + dv.getValue6()
					+ dv.getValue7());
			result.add(dv);
		}
		return result;
	}
}
