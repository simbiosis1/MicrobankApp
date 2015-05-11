package org.simbiosis.bprs.ui.config.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigDv implements IsSerializable {
	Long vault;
	String strVault;
	Long rab;
	String strRab;

	public ConfigDv() {
		vault = 0L;
		rab = 0L;
	}

	public Long getVault() {
		return vault;
	}

	public void setVault(Long vault) {
		this.vault = vault;
	}

	public String getStrVault() {
		return strVault;
	}

	public void setStrVault(String strVault) {
		this.strVault = strVault;
	}

	public Long getRab() {
		return rab;
	}

	public void setRab(Long rab) {
		this.rab = rab;
	}

	public String getStrRab() {
		return strRab;
	}

	public void setStrRab(String strRab) {
		this.strRab = strRab;
	}

}
