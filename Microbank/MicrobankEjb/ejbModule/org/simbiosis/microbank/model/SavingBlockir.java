package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_SAVINGBLOCKIR")
@NamedQueries({
	@NamedQuery(name = "getTotalBlockExceptType", query = "select sum(x.value) from SavingBlockir x where x.saving.id=:savingId and x.type<>:type"),
	@NamedQuery(name = "getTotalBlock", query = "select sum(x.value) from SavingBlockir x where x.saving.id=:savingId"),
	@NamedQuery(name = "listSavingBlock", query = "select x from SavingBlockir x where x.saving.id=:savingId") })
public class SavingBlockir {
	@Id
	@Column(name = "SBL_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_savingblockir")
	@TableGenerator(name = "gen_mib_savingblockir", allocationSize = 1, pkColumnValue = "gen_mib_savingblockir")
	long id;
	@Column(name = "SBL_TYPE")
	int type;
	@Column(name = "SBL_DESCRIPTION", length = 200)
	String description;
	@Column(name = "SBL_VALUE")
	double value;
	@ManyToOne
	@JoinColumn(name = "SAV_ID", referencedColumnName = "SAV_ID")
	Saving saving;

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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Saving getSaving() {
		return saving;
	}

	public void setSaving(Saving saving) {
		this.saving = saving;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
