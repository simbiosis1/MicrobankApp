package org.simbiosis.microbank;

import java.io.Serializable;

public class SavingPrintCodeRefDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1077095523215378105L;
	Long company;
	Integer type;
	String code;

	public SavingPrintCodeRefDto() {
		company = 0L;
		type = 0;
	}

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
