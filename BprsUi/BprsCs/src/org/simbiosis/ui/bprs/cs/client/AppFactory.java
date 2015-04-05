package org.simbiosis.ui.bprs.cs.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.cs.client.customer.ICustomer;
import org.simbiosis.ui.bprs.cs.client.customerinput.ICustomerInput;
import org.simbiosis.ui.bprs.cs.client.deposit.IDeposit;
import org.simbiosis.ui.bprs.cs.client.depositinput.IDepositInput;
import org.simbiosis.ui.bprs.cs.client.saving.ISaving;
import org.simbiosis.ui.bprs.cs.client.savingblock.ISavingBlockEditor;
import org.simbiosis.ui.bprs.cs.client.savingclose.ISavingClose;
import org.simbiosis.ui.bprs.cs.client.savinginput.ISavingInput;

public interface AppFactory extends KembangClientFactory {

	ICustomer getListCustomer();

	ICustomerInput getCustomerViewer();

	ICustomerInput getCustomerEditor();

	ISaving getListSaving();

	ISavingInput getSavingViewer();

	ISavingInput getSavingEditor();

	IDeposit getListDeposit();

	IDepositInput getDepositViewer();

	IDepositInput getDepositEditor();

	ISavingClose getSavingCloseViewer();

	ISavingClose getSavingCloseEditor();

	ISavingBlockEditor getSavingBlockEditor();

}
