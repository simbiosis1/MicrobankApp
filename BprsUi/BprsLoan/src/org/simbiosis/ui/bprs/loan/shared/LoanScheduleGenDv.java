package org.simbiosis.ui.bprs.loan.shared;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoanScheduleGenDv implements IsSerializable {
	String name;
	String strPrincipal;
	String strRate;
	String strTenor;
	List<LoanScheduleDv> schedules = new ArrayList<LoanScheduleDv>();
	int scheduleType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LoanScheduleGenDv() {
		scheduleType = 1;
	}

	public String getStrPrincipal() {
		return strPrincipal;
	}

	public void setStrPrincipal(String strPrincipal) {
		this.strPrincipal = strPrincipal;
	}

	public String getStrRate() {
		return strRate;
	}

	public void setStrRate(String strRate) {
		this.strRate = strRate;
	}

	public String getStrTenor() {
		return strTenor;
	}

	public void setStrTenor(String strTenor) {
		this.strTenor = strTenor;
	}

	public List<LoanScheduleDv> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<LoanScheduleDv> schedules) {
		this.schedules = schedules;
	}

	public int getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(int scheduleType) {
		this.scheduleType = scheduleType;
	}

}
