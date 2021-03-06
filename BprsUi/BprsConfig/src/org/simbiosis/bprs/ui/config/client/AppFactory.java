package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.bprs.ui.config.client.depositproduct.IDepProduct;
import org.simbiosis.bprs.ui.config.client.gl.IGlConfig;
import org.simbiosis.bprs.ui.config.client.loanproduct.ILoanProduct;
import org.simbiosis.bprs.ui.config.client.savingproduct.ISavProduct;
import org.simbiosis.bprs.ui.config.client.teller.ITeller;

public interface AppFactory extends KembangClientFactory {

	ITeller getTeller();

	ISavProduct getSavProduct();

	IDepProduct getDepProduct();

	ILoanProduct getLoanProduct();

	IGlConfig getGlConfig();

}
