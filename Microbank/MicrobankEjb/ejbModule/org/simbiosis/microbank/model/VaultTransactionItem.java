package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_VAULTTRANSITEM")
public class VaultTransactionItem {
	@Id
	@Column(name = "VAI_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_vaulttransitem")
	@TableGenerator(name = "gen_mib_vaulttransitem", allocationSize = 1, pkColumnValue = "gen_mib_vaulttransitem")
	long id;
	@Column(name = "VAI_VALUE")
	double value;
	@Column(name = "VAI_AMOUNT")
	double amount;
	@Column(name = "VAI_TYPE")
	int type;

	@ManyToOne
	@JoinColumn(name = "VAT_ID", referencedColumnName = "VAT_ID")
	VaultTransaction vaultTransaction;

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

	public VaultTransaction getVaultTransaction() {
		return vaultTransaction;
	}

	public void setVaultTransaction(VaultTransaction vaultTransaction) {
		this.vaultTransaction = vaultTransaction;
	}

}
