package org.simbiosis.microbank.model;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "REF_PROVCITY")
@NamedQueries({
		@NamedQuery(name = "listCityRestore", query = "select x from RefProvCity x"),
		@NamedQuery(name = "listCity", query = "select x from RefProvCity x  where x.level=2 order by x.name"),
		@NamedQuery(name = "listProvinsi", query = "select x from RefProvCity x  where x.level=1  order by x.name"),
		@NamedQuery(name = "listCity1", query = "select x from RefProvCity x where x.level=1 and x.name=:name order by x.name"), })
public class RefProvCity {

	@Id
	@Column(name = "RPC_ID")
	@GeneratedValue(strategy = TABLE, generator = "cityseq")
	@TableGenerator(name = "cityseq", pkColumnValue = "cityseq", allocationSize = 1)
	long id;

	@Column(name = "RPC_PROV")
	String provinsi;

	@Column(name = "RPC_KOT")
	String kota;

	@Column(name = "RPC_NAME")
	String name;

	@Column(name = "RPC_CODE")
	String code;

	@Column(name = "RPC_LEVEL")
	long level;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvinsi() {
		return provinsi;
	}

	public void setProvinsi(String provinsi) {
		this.provinsi = provinsi;
	}

	public String getKota() {
		return kota;
	}

	public void setKota(String kota) {
		this.kota = kota;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

}
