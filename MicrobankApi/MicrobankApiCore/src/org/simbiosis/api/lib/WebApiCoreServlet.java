package org.simbiosis.api.lib;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import org.simbiosis.bp.gl.IPublicReport;
import org.simbiosis.bp.micbank.IDepositBp;
import org.simbiosis.bp.micbank.ILoanBp;
import org.simbiosis.bp.micbank.IRevenueBp;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.micbank.ITellerBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.gl.ICoa;
import org.simbiosis.gl.ILedger;
import org.simbiosis.microbank.ICustomer;
import org.simbiosis.microbank.ILoan;

public class WebApiCoreServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3968392161184640824L;

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	protected ISystemBp iSystem;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/CustomerImpl")
	protected ICustomer iCustomer;

	@EJB(lookup = "java:global/GlEar/GlEjb/CoaImpl")
	protected ICoa iCoa;

	@EJB(lookup = "java:global/GlEar/GlEjb/LedgerImpl")
	protected ILedger iLedger;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/PublicReportBp")
	protected IPublicReport iPublicReport;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/DepositBp")
	protected IDepositBp iDepositBp;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	protected ISavingBp iSavingBp;

	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/LoanImpl")
	protected ILoan iLoan;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/LoanBp")
	protected ILoanBp iLoanBp;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/RevenueBp")
	protected IRevenueBp iRevenueBp;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/TellerBp")
	protected ITellerBp iTellerBp;

	public WebApiCoreServlet() {

	}

}
