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
@Table(name = "REF_OCCTYPE")
@NamedQueries({
		@NamedQuery(name = "listJenisPekerjaan", query = "select x from RefOccupationType x order by x.name"), })
public class RefOccupationType {

	@Id
	@Column(name = "ROT_ID")
	@GeneratedValue(strategy = TABLE, generator = "occtypeseq")
	@TableGenerator(name = "occtypeseq", pkColumnValue = "occtypeseq", allocationSize = 1)
	long id;

	@Column(name = "ROT_NAME")
	String name;

	@Column(name = "ROT_CODE", length = 30)
	String code;
	
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
