package org.simbiosis.ui.bprs.teller.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UploadCollectiveDv implements IsSerializable {
	Integer nr;
	Long savingId;
	String code;
	String name;
	String systemName;
	Double debit;
	Double credit;
	Double total;

	public UploadCollectiveDv() {
		nr = 0;
		savingId = 0L;
	}

	public UploadCollectiveDv(Integer nr, String code, String name,
			String systemName, Double debit, Double credit, Double total) {
		this.nr = nr;
		this.code = code;
		this.name = name;
		savingId = 0L;
		this.systemName = systemName;
		this.debit = debit;
		this.credit = credit;
		this.total = total;
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

}
