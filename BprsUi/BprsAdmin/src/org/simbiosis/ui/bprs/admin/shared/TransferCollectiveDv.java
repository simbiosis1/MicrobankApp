package org.simbiosis.ui.bprs.admin.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransferCollectiveDv implements IsSerializable {
	Integer nr;
	Long savingId;
	Long destSavingId;
	String code;
	String name;
	String systemName;
	String destCode;
	String destName;
	String destSystemName;
	Double value;
	Integer srcType;
	String status;

	public TransferCollectiveDv() {
		nr = 0;
		srcType = 0;
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

	public TransferCollectiveDv(Integer nr, String code, String name,
			String destCode, String destName, Double value) {
		this.nr = nr;
		this.code = code;
		this.name = name;
		this.destCode = destCode;
		this.destName = destName;
		savingId = 0L;
		destSavingId = 0L;
		systemName = "";
		destSystemName = "";
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

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

	public String getDestSystemName() {
		return destSystemName;
	}

	public void setDestSystemName(String destSystemName) {
		this.destSystemName = destSystemName;
	}

	public Integer getSrcType() {
		return srcType;
	}

	public void setSrcType(Integer srcType) {
		this.srcType = srcType;
	}

	public Long getDestSavingId() {
		return destSavingId;
	}

	public void setDestSavingId(Long destSavingId) {
		this.destSavingId = destSavingId;
	}

}
