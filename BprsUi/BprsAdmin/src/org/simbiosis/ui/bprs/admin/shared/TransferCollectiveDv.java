package org.simbiosis.ui.bprs.admin.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransferCollectiveDv implements IsSerializable {
	Integer nr;
	Long savingId;
	String code;
	String name;
	String systemName;
	Double value;
	String status;

	public TransferCollectiveDv() {
		nr = 0;
		savingId = 0L;
	}

	public TransferCollectiveDv(Integer nr, String code, String name,
			Double value) {
		this.nr = nr;
		this.code = code;
		this.name = name;
		savingId = 0L;
		systemName = "";
		this.value = value;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
