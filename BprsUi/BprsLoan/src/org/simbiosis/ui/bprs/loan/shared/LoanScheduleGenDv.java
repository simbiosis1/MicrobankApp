package org.simbiosis.ui.bprs.loan.shared;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoanScheduleGenDv implements IsSerializable {
	String name;
	Double principal;
	Double rate;
	Integer tenor;
	List<LoanScheduleDv> schedules = new ArrayList<LoanScheduleDv>();
	int scheduleType;

	public LoanScheduleGenDv() {
		scheduleType = 1;
		principal = 0D;
		rate = 0D;
		tenor = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getTenor() {
		return tenor;
	}

	public void setTenor(Integer tenor) {
		this.tenor = tenor;
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
