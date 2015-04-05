package org.simbiosis.ui.bprs.cs.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SavingBlockirDv implements IsSerializable {
	Long id;
	Integer type;
	String description;
	Double value;

	public SavingBlockirDv() {
		id = 0L;
		type = 0;
		value = 0D;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

}
