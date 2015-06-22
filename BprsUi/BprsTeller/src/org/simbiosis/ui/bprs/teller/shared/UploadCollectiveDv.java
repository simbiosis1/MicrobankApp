package org.simbiosis.ui.bprs.teller.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UploadCollectiveDv implements IsSerializable {
	private Integer nr;
	private Long savingId;
	private String refCode;
	private String code;
	private String name;
	private String systemName;
	private String description;
	private Double debit;
	private Double credit;
	private Double total;
	private Double value;
	private Integer direction;
	private Integer status;

	public UploadCollectiveDv() {
		nr = 0;
		savingId = 0L;
		value = 0D;
		direction = 1;
		status = 0;
	}

	public UploadCollectiveDv(Integer nr, String refCode, String code,
			String name, String systemName, String description, Double debit,
			Double credit, Double total) {
		this.nr = nr;
		this.code = code;
		this.refCode = refCode;
		this.name = name;
		savingId = 0L;
		this.systemName = systemName;
		this.description = description;
		this.debit = debit;
		this.credit = credit;
		this.total = total;
		direction = 1;
		value = 0D;
		status = 0;
	}

	public void processSavingTrans() {
		if ((credit == 0 && debit != 0) || (credit != 0 && debit == 0)) {
			if (credit != 0) {
				direction = 1;
				value = credit;
			} else {
				direction = 2;
				value = debit;
			}
		} else {
			status = 2;
		}
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
