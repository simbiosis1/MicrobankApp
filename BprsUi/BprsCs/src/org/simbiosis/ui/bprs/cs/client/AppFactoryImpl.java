package org.simbiosis.ui.bprs.cs.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.cs.client.customer.Customer;
import org.simbiosis.ui.bprs.cs.client.customer.ICustomer;
import org.simbiosis.ui.bprs.cs.client.customerinfo.CustomerInfoData;
import org.simbiosis.ui.bprs.cs.client.customerinfo.CustomerInfoForm;
import org.simbiosis.ui.bprs.cs.client.customerinfo.ICustomerInfo;
import org.simbiosis.ui.bprs.cs.client.customerinfo.ICustomerInfoData;
import org.simbiosis.ui.bprs.cs.client.customerinput.CustomerEditor;
import org.simbiosis.ui.bprs.cs.client.customerinput.CustomerViewer;
import org.simbiosis.ui.bprs.cs.client.customerinput.ICustomerInput;
import org.simbiosis.ui.bprs.cs.client.deposit.Deposit;
import org.simbiosis.ui.bprs.cs.client.deposit.IDeposit;
import org.simbiosis.ui.bprs.cs.client.depositinput.DepositEditor;
import org.simbiosis.ui.bprs.cs.client.depositinput.DepositViewer;
import org.simbiosis.ui.bprs.cs.client.depositinput.IDepositInput;
import org.simbiosis.ui.bprs.cs.client.saving.ISaving;
import org.simbiosis.ui.bprs.cs.client.saving.Saving;
import org.simbiosis.ui.bprs.cs.client.savingblock.ISavingBlockEditor;
import org.simbiosis.ui.bprs.cs.client.savingblock.SavingBlockEditor;
import org.simbiosis.ui.bprs.cs.client.savingclose.ISavingClose;
import org.simbiosis.ui.bprs.cs.client.savingclose.SavingCloseEditor;
import org.simbiosis.ui.bprs.cs.client.savingclose.SavingCloseViewer;
import org.simbiosis.ui.bprs.cs.client.savinginput.ISavingInput;
import org.simbiosis.ui.bprs.cs.client.savinginput.SavingEditor;
import org.simbiosis.ui.bprs.cs.client.savinginput.SavingViewer;

public class AppFactoryImpl extends KembangClientFactoryImpl implements
		AppFactory {

	static final Customer LIST_CUSTOMER = new Customer();
	static final CustomerViewer CUSTOMER_VIEWER = new CustomerViewer();
	static final CustomerEditor CUSTOMER_EDITOR = new CustomerEditor();
	static final Saving LIST_SAVING = new Saving();
	static final SavingViewer SAVING_VIEWER = new SavingViewer();
	static final SavingEditor SAVING_EDITOR = new SavingEditor();
	static final SavingCloseViewer SAVING_CLOSE_VIEWER = new SavingCloseViewer();
	static final SavingCloseEditor SAVING_CLOSE_EDITOR = new SavingCloseEditor();
	static final Deposit LIST_DEPOSIT = new Deposit();
	static final DepositViewer DEPOSIT_VIEWER = new DepositViewer();
	static final DepositEditor DEPOSIT_EDITOR = new DepositEditor();
	static final SavingBlockEditor SAVING_BLOCK_EDITOR = new SavingBlockEditor();
	static final CustomerInfoForm CUSTOMER_INFO_FORM = new CustomerInfoForm();
	static final CustomerInfoData CUSTOMER_INFO_DATA = new CustomerInfoData();

	@Override
	public ICustomer getListCustomer() {
		return LIST_CUSTOMER;
	}

	@Override
	public ISaving getListSaving() {
		return LIST_SAVING;
	}

	@Override
	public ISavingInput getSavingViewer() {
		return SAVING_VIEWER;
	}

	@Override
	public ISavingInput getSavingEditor() {
		return SAVING_EDITOR;
	}

	@Override
	public ICustomerInput getCustomerViewer() {
		return CUSTOMER_VIEWER;
	}

	@Override
	public ICustomerInput getCustomerEditor() {
		return CUSTOMER_EDITOR;
	}

	@Override
	public IDeposit getListDeposit() {
		return LIST_DEPOSIT;
	}

	@Override
	public IDepositInput getDepositViewer() {
		return DEPOSIT_VIEWER;
	}

	@Override
	public IDepositInput getDepositEditor() {
		return DEPOSIT_EDITOR;
	}

	@Override
	public ISavingClose getSavingCloseViewer() {
		return SAVING_CLOSE_VIEWER;
	}

	@Override
	public ISavingClose getSavingCloseEditor() {
		return SAVING_CLOSE_EDITOR;
	}

	@Override
	public ISavingBlockEditor getSavingBlockEditor() {
		return SAVING_BLOCK_EDITOR;
	}

	@Override
	public ICustomerInfo getCustomerInfo() {
		return CUSTOMER_INFO_FORM;
	}

	@Override
	public ICustomerInfoData getCustomerInfoData() {
		return CUSTOMER_INFO_DATA;
	}

}
