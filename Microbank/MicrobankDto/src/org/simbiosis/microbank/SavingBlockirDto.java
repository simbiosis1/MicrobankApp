package org.simbiosis.microbank;

import java.io.Serializable;

/*
 * Class SavingBlockirDto
 * */
public class SavingBlockirDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4040516607990269167L;
	long id;
	int type;
	String description;
	double value;
	long saving;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}
}
