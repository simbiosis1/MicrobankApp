package org.simbiosis.ui.bprs.common.client.handler;

import org.simbiosis.ui.bprs.common.shared.LoanDv;


public interface GetLoanHandler {
	public abstract void showLoan(LoanDv loanDv);

	public abstract void showLoading();

	public abstract void hideLoading();
}
