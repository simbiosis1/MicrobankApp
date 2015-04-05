package org.simbiosis.ui.bprs.loan.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TypeDv implements IsSerializable {
	Integer type;
	String description;
	Long id;

	public TypeDv() {
		type = 0;
		id = 0L;
	}

	@Override
	public String toString() {
		return description;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
