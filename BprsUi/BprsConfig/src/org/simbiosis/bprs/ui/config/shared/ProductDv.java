/**
 * 
 */
package org.simbiosis.bprs.ui.config.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author nanang
 * 
 */
public class ProductDv implements IsSerializable {

	public enum SchemaLoanTypeEnum {
		MURABAHAH, SALAM, ISHTISHNA, IJARAH, MUDHARABAH, MUSYARAKAH, QARDH;
		public static String valueToString(int value) {
			switch (value) {
			case 1:
				return "MURABAHAH";
			case 2:
				return "SALAM";
			case 3:
				return "ISHTISHNA";
			case 4:
				return "IJARAH";
			case 5:
				return "MUDHARABAH";
			case 6:
				return "MUSYARAKAH";
			case 7:
				return "QARDH";
			default:
				return "";
			}
		}
	};

	public enum SchemaTypeEnum {
		WADIAH, MUDHARABAH;
		public static String valueToString(int value) {
			switch (value) {
			case 1:
				return "WADIAH";
			case 2:
				return "MUDHARABAH";
			default:
				return "";
			}
		}
	};

	Integer nr;
	Long id;
	Long refId;
	String code;
	String name;
	Long company;
	Integer schema;
	Double sharing;
	Double minSharable;
	Long coa1;
	Long coa2;
	Long coa3;
	Long coa4;
	Long coa5;
	Long coa6;
	String strCoa1;
	String strCoa2;
	String strCoa3;
	String strCoa4;
	String strCoa5;
	String strCoa6;
	Double minValue;
	String strSchema;
	Boolean hasShare;
	String strHasShare;
	String strTerm;
	Long term;
	Double closeAdmin;

	public ProductDv() {
		id = 0L;
		refId = 0L;
		coa1 = 0L;
		coa2 = 0L;
		coa3 = 0L;
		coa4 = 0L;
		coa5 = 0L;
		coa6 = 0L;
		hasShare = true;
		sharing = 0D;
		minValue = 0D;
		closeAdmin = 0D;
		schema = 1;
		term = 0L;
	}

	public String getStrHasShare() {
		return strHasShare;
	}

	public void setStrHasShare(String strHasShare) {
		this.strHasShare = strHasShare;
	}

	public Boolean getHasShare() {
		return hasShare;
	}

	public void setHasShare(Boolean hasShare) {
		this.hasShare = hasShare;
	}

	public String getStrCoa1() {
		return strCoa1;
	}

	public void setStrCoa1(String strCoa1) {
		this.strCoa1 = strCoa1;
	}

	public String getStrCoa2() {
		return strCoa2;
	}

	public void setStrCoa2(String strCoa2) {
		this.strCoa2 = strCoa2;
	}

	public String getStrCoa3() {
		return strCoa3;
	}

	public void setStrCoa3(String strCoa3) {
		this.strCoa3 = strCoa3;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
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

	public Long getCompany() {
		return company;
	}

	public void setCompany(Long company) {
		this.company = company;
	}

	public Integer getSchema() {
		return schema;
	}

	public void setSchema(Integer schema) {
		this.schema = schema;
	}

	public Double getSharing() {
		return sharing;
	}

	public void setSharing(Double sharing) {
		this.sharing = sharing;
	}

	public Long getCoa1() {
		return coa1;
	}

	public void setCoa1(Long coa1) {
		this.coa1 = coa1;
	}

	public Long getCoa2() {
		return coa2;
	}

	public void setCoa2(Long coa2) {
		this.coa2 = coa2;
	}

	public Long getCoa3() {
		return coa3;
	}

	public void setCoa3(Long coa3) {
		this.coa3 = coa3;
	}

	public String getStrSchema() {
		return strSchema;
	}

	public void setStrSchema(String strSchema) {
		this.strSchema = strSchema;
	}

	public String getStrTerm() {
		return strTerm;
	}

	public void setStrTerm(String strTerm) {
		this.strTerm = strTerm;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public Long getCoa4() {
		return coa4;
	}

	public void setCoa4(Long coa4) {
		this.coa4 = coa4;
	}

	public Long getCoa5() {
		return coa5;
	}

	public void setCoa5(Long coa5) {
		this.coa5 = coa5;
	}

	public String getStrCoa4() {
		return strCoa4;
	}

	public void setStrCoa4(String strCoa4) {
		this.strCoa4 = strCoa4;
	}

	public String getStrCoa5() {
		return strCoa5;
	}

	public void setStrCoa5(String strCoa5) {
		this.strCoa5 = strCoa5;
	}

	public Long getCoa6() {
		return coa6;
	}

	public void setCoa6(Long coa6) {
		this.coa6 = coa6;
	}

	public String getStrCoa6() {
		return strCoa6;
	}

	public void setStrCoa6(String strCoa6) {
		this.strCoa6 = strCoa6;
	}

	public Double getCloseAdmin() {
		return closeAdmin;
	}

	public void setCloseAdmin(Double closeAdmin) {
		this.closeAdmin = closeAdmin;
	}

	public Double getMinSharable() {
		return minSharable;
	}

	public void setMinSharable(Double minSharable) {
		this.minSharable = minSharable;
	}

}
