package org.simbiosis.bprs.ui.config.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.bprs.ui.config.client.deposit.IDepProduct;
import org.simbiosis.bprs.ui.config.client.loan.ILoanProduct;
import org.simbiosis.bprs.ui.config.client.saving.ISavProduct;
import org.simbiosis.bprs.ui.config.client.teller.ITeller;

public interface AppFactory extends KembangClientFactory {

	ITeller getTeller();

	ISavProduct getSavProduct();

	IDepProduct getDepProduct();

	ILoanProduct getLoanProduct();

}
