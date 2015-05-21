package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.bprs.ui.config.client.deposit.DepProductList;
import org.simbiosis.bprs.ui.config.client.deposit.IDepProduct;
import org.simbiosis.bprs.ui.config.client.gl.GlConfig;
import org.simbiosis.bprs.ui.config.client.gl.IGlConfig;
import org.simbiosis.bprs.ui.config.client.loan.ILoanProduct;
import org.simbiosis.bprs.ui.config.client.loan.LoanProductList;
import org.simbiosis.bprs.ui.config.client.saving.ISavProduct;
import org.simbiosis.bprs.ui.config.client.saving.SavProductList;
import org.simbiosis.bprs.ui.config.client.teller.ITeller;
import org.simbiosis.bprs.ui.config.client.teller.TellerList;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final TellerList TELLER = new TellerList();
	static final SavProductList SAVPRODUCT = new SavProductList();
	static final DepProductList DEPPRODUCT = new DepProductList();
	static final LoanProductList LOANPRODUCT = new LoanProductList();
	static final GlConfig GL_CONFIG = new GlConfig();

	@Override
	public ITeller getTeller() {
		return TELLER;
	}

	@Override
	public ISavProduct getSavProduct() {
		return SAVPRODUCT;
	}

	@Override
	public IDepProduct getDepProduct() {
		return DEPPRODUCT;
	}

	@Override
	public ILoanProduct getLoanProduct() {
		return LOANPRODUCT;
	}

	@Override
	public IGlConfig getGlConfig() {
		return GL_CONFIG;
	}

}
