package org.simbiosis.ui.bprs.teller.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TellerDv implements IsSerializable {
	Long id;
	String code;
	String name;

	public TellerDv() {
		id = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return code + " - " + name;
	}

}
