package org.simbiosis.microbank;

import java.io.Serializable;

public class VaultTransactionItemDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5097978181221623362L;
	long id;
	double value;
	double amount;
	int type;
	long vaultTransaction;

	public VaultTransactionItemDto() {
		super();
		id = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getVaultTransaction() {
		return vaultTransaction;
	}

	public void setVaultTransaction(long vaultTransaction) {
		this.vaultTransaction = vaultTransaction;
	}

}
