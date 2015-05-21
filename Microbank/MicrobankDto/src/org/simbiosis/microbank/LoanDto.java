package org.simbiosis.microbank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2534374197384602763L;
	long id;
	long refId;
	boolean hasCode;
	String code;
	String refCode;
	Date registration;
	double principal;
	double rate;
	double margin;
	int tenor;
	String contract;
	Date contractDate;
	int active;
	Date closing;
	long saving;
	long customer;
	long company;
	long branch;
	long product;
	boolean dropped;
	double guarantee;
	String purpose;
	String biSektor;
	long ao;
	String aoHistory;

	int reschedule;
	double admin;
	double fine;

	List<LoanScheduleDto> schedules = new ArrayList<LoanScheduleDto>();
	List<GuaranteeDto> guarantees = new ArrayList<GuaranteeDto>();

	public LoanDto() {
		id = 0;
		refId = 0;
		saving = 0;
		guarantee = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getTenor() {
		return tenor;
	}

	public void setTenor(int tenor) {
		this.tenor = tenor;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}

	public List<LoanScheduleDto> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<LoanScheduleDto> schedules) {
		this.schedules = schedules;
	}

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getClosing() {
		return closing;
	}

	public void setClosing(Date closing) {
		this.closing = closing;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}

	public boolean isHasCode() {
		return hasCode;
	}

	public void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public boolean isDropped() {
		return dropped;
	}

	public void setDropped(boolean dropped) {
		this.dropped = dropped;
	}

	public double getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(double guarantee) {
		this.guarantee = guarantee;
	}

	public List<GuaranteeDto> getGuarantees() {
		return guarantees;
	}

	public void setGuarantees(List<GuaranteeDto> guarantees) {
		this.guarantees = guarantees;
	}

	public long getAo() {
		return ao;
	}

	public void setAo(long ao) {
		this.ao = ao;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getBiSektor() {
		return biSektor;
	}

	public void setBiSektor(String biSektor) {
		this.biSektor = biSektor;
	}

	public String getAoHistory() {
		return aoHistory;
	}

	public void setAoHistory(String aoHistory) {
		this.aoHistory = aoHistory;
	}

	public int getReschedule() {
		return reschedule;
	}

	public void setReschedule(int reschedule) {
		this.reschedule = reschedule;
	}

	public double getAdmin() {
		return admin;
	}

	public void setAdmin(double admin) {
		this.admin = admin;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

}
