package org.simbiosis.ui.bprs.common.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TransactionDv implements IsSerializable {

	public enum TransactionType {
		Masuk, Keluar;

		public static String valueToString(int type) {
			switch (type) {
			case 1:
				return "Masuk";
			case 2:
				return "Keluar";
			}
			return "";
		}
	}

	public enum VaultTransactionType {
		Pengambilan, Pengembalian;

		public static String valueToString(int type) {
			switch (type) {
			case 1:
				return "Pengambilan";
			case 2:
				return "Pengembalian";
			}
			return "";
		}
	}

	public enum SavingTransType {
		Setoran, Tarikan;

		public static String valueToString(int type) {
			switch (type) {
			case 1:
				return "Setoran";
			case 2:
				return "Tarikan";
			}
			return "";
		}
	}

	public enum DepositTransType {
		Setoran, Pencairan;

		public static String valueToString(int type) {
			switch (type) {
			case 1:
				return "Setoran";
			case 2:
				return "Pencairan";
			}
			return "";
		}
	}

	public enum LoanTransType {
		Pencairan, Angsuran, Pelunasan;

		public static String valueToString(int type) {
			switch (type) {
			case 1:
				return "Pencairan";
			case 2:
				return "Angsuran";
			}
			return "";
		}
	}

	Long id;
	Integer nr;
	String code;
	String refCode;
	Date date;
	String strDate;
	int type;
	String strType;
	String description;
	Double value;
	String strValue;
	Double principal;
	String strPrincipal;
	Double margin;
	String strMargin;
	Double discount;
	String strDiscount;
	Double debit;
	String strDebit;
	Double credit;
	String strCredit;
	Double total;
	String strTotal;
	Integer direction;
	String validationText;
	String maker;
	Long teller;
	String strTeller;
	Long coa;
	String strCoa;

	SavingDv saving = new SavingDv();
	SavingDv savingDest = new SavingDv();
	LoanDv loan = new LoanDv();
	DepositDv deposit = new DepositDv();

	public TransactionDv() {
		id = 0L;
		direction = 0;
		teller = 0L;
		coa = 0L;
		principal = 0D;
		margin = 0D;
		discount = 0D;
		total = 0D;
		debit = 0D;
		credit = 0D;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public SavingDv getSaving() {
		return saving;
	}

	public void setSaving(SavingDv savingDv) {
		this.saving = savingDv;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public String getStrDebit() {
		return strDebit;
	}

	public void setStrDebit(String strDebit) {
		this.strDebit = strDebit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public String getStrCredit() {
		return strCredit;
	}

	public void setStrCredit(String strCredit) {
		this.strCredit = strCredit;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getStrTotal() {
		return strTotal;
	}

	public void setStrTotal(String strTotal) {
		this.strTotal = strTotal;
	}

	public String getValidationText() {
		return validationText;
	}

	public void setValidationText(String validationText) {
		this.validationText = validationText;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public long getTeller() {
		return teller;
	}

	public void setTeller(long teller) {
		this.teller = teller;
	}

	public String getStrTeller() {
		return strTeller;
	}

	public void setStrTeller(String strTeller) {
		this.strTeller = strTeller;
	}

	public LoanDv getLoan() {
		return loan;
	}

	public void setLoan(LoanDv loanDv) {
		this.loan = loanDv;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public String getStrPrincipal() {
		return strPrincipal;
	}

	public void setStrPrincipal(String strPrincipal) {
		this.strPrincipal = strPrincipal;
	}

	public Double getMargin() {
		return margin;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public String getStrMargin() {
		return strMargin;
	}

	public void setStrMargin(String strMargin) {
		this.strMargin = strMargin;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getStrDiscount() {
		return strDiscount;
	}

	public void setStrDiscount(String strDiscount) {
		this.strDiscount = strDiscount;
	}

	public DepositDv getDeposit() {
		return deposit;
	}

	public void setDeposit(DepositDv deposit) {
		this.deposit = deposit;
	}

	public Long getCoa() {
		return coa;
	}

	public void setCoa(Long coa) {
		this.coa = coa;
	}

	public String getStrCoa() {
		return strCoa;
	}

	public void setStrCoa(String strCoa) {
		this.strCoa = strCoa;
	}

	public void setTeller(Long teller) {
		this.teller = teller;
	}

	public SavingDv getSavingDest() {
		return savingDest;
	}

	public void setSavingDest(SavingDv savingDest) {
		this.savingDest = savingDest;
	}

}
