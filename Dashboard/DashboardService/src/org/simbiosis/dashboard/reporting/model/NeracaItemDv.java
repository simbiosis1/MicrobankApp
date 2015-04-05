package org.simbiosis.dashboard.reporting.model;

import java.util.ArrayList;
import java.util.List;

public class NeracaItemDv {
	List<NeracaItemDv> children = new ArrayList<NeracaItemDv>();
	String ledger;
	String subLedger;
	String subSubLedger;
	String ledgerWoCode;
	String subLedgerWoCode;
	String subSubLedgerWoCode;
	String tipeNeraca;
	double valueBefore;
	double valueNow;
	double valueNowNo3;
	double valueNowNo4;
	double debet;
	double kredit;
	int factor;

	
	public double getDebet() {
		return debet;
	}

	public void setDebet(double debet) {
		this.debet = debet;
	}

	public double getKredit() {
		return kredit;
	}

	public void setKredit(double kredit) {
		this.kredit = kredit;
	}

	public double getValueNowNo3() {
		return valueNowNo3;
	}

	public void setValueNowNo3(double valueNowNo3) {
		this.valueNowNo3 = valueNowNo3;
	}

	public double getValueNowNo4() {
		return valueNowNo4;
	}

	public void setValueNowNo4(double valueNowNo4) {
		this.valueNowNo4 = valueNowNo4;
	}

	public List<NeracaItemDv> getChildren() {
		return children;
	}

	public void setChildren(List<NeracaItemDv> children) {
		this.children = children;
	}

	public String getLedger() {
		return ledger;
	}

	public void setLedger(String ledger) {
		this.ledger = ledger;
	}

	public String getSubLedger() {
		return subLedger;
	}

	public void setSubLedger(String subLedger) {
		this.subLedger = subLedger;
	}

	public String getSubSubLedger() {
		return subSubLedger;
	}

	public void setSubSubLedger(String subSubLedger) {
		this.subSubLedger = subSubLedger;
	}

	public String getTipeNeraca() {
		return tipeNeraca;
	}

	public void setTipeNeraca(String tipeNeraca) {
		this.tipeNeraca = tipeNeraca;
	}

	public double getValueBefore() {
		return valueBefore;
	}

	public void setValueBefore(double valueBefore) {
		this.valueBefore = valueBefore;
	}

	public double getValueNow() {
		return valueNow;
	}

	public void setValueNow(double valueNow) {
		this.valueNow = valueNow;
	}

	public String getLedgerWoCode() {
		return ledgerWoCode;
	}

	public void setLedgerWoCode(String ledgerWoCode) {
		this.ledgerWoCode = ledgerWoCode;
	}

	public String getSubLedgerWoCode() {
		return subLedgerWoCode;
	}

	public void setSubLedgerWoCode(String subLedgerWoCode) {
		this.subLedgerWoCode = subLedgerWoCode;
	}

	public String getSubSubLedgerWoCode() {
		return subSubLedgerWoCode;
	}

	public void setSubSubLedgerWoCode(String subSubLedgerWoCode) {
		this.subSubLedgerWoCode = subSubLedgerWoCode;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

}
