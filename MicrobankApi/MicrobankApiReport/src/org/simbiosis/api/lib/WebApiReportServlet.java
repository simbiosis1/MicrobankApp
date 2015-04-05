package org.simbiosis.api.lib;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.IFinancialReport;
import org.simbiosis.gl.ILedger;
import org.simbiosis.microbank.IDepositReport;
import org.simbiosis.microbank.ILoan;
import org.simbiosis.microbank.ILoanReport;
import org.simbiosis.microbank.ISavingReport;

public class WebApiReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3968392161184640824L;

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	protected ISystemBp iSystem;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	protected ISavingBp iSaving;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	protected IDepositBp iDeposit;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/LoanImpl")
	protected ILoan iLoan;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	protected ILoanBp iLoanBp;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/DepositReport")
	protected IDepositReport iDepositReport;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/LoanReport")
	protected ILoanReport iLoanReport;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankReportEjb/SavingReport")
	protected ISavingReport iSavingReport;

	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	protected ICoa iCoa;

	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	protected ILedger iLedger;

	@EJB(lookup = "java:global/GlEar/GlReportEjb/FinancialReport")
	protected IFinancialReport iFinancialReport;

	public WebApiReportServlet() {

	}

}
