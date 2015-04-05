package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "MIB_SAVINGRESUME")
public class SavingResume {
	@Id
	@Column(name = "SVR_ID")
	@GeneratedValue(strategy = TABLE, generator = "gen_mib_savingresume")
	@TableGenerator(name = "gen_mib_savingresume", allocationSize = 1, pkColumnValue = "gen_mib_savingresume")
	long id;
}
