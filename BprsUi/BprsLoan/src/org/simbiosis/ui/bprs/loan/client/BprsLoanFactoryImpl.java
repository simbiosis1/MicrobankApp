package org.simbiosis.ui.bprs.loan.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.loan.client.guarantee.GuaranteeList;
import org.simbiosis.ui.bprs.loan.client.guarantee.IGuaranteeList;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.GuaranteeEditor;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.GuaranteeViewer;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.IGuaranteeInput;
import org.simbiosis.ui.bprs.loan.client.info.IInfoList;
import org.simbiosis.ui.bprs.loan.client.info.InfoList;
import org.simbiosis.ui.bprs.loan.client.infoviewer.IInfoViewer;
import org.simbiosis.ui.bprs.loan.client.infoviewer.InfoViewer;
import org.simbiosis.ui.bprs.loan.client.loan.ILoanList;
import org.simbiosis.ui.bprs.loan.client.loan.LoanList;
import org.simbiosis.ui.bprs.loan.client.loaninput.ILoanInput;
import org.simbiosis.ui.bprs.loan.client.loaninput.LoanEditor;
import org.simbiosis.ui.bprs.loan.client.loaninput.LoanViewer;
import org.simbiosis.ui.bprs.loan.client.reschedule.IRescheduleList;
import org.simbiosis.ui.bprs.loan.client.reschedule.RescheduleList;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.IRescheduleInput;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.RescheduleEditor;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.RescheduleViewer;
import org.simbiosis.ui.bprs.loan.client.simulation.ISimulation;
import org.simbiosis.ui.bprs.loan.client.simulation.Simulation;

public class BprsLoanFactoryImpl extends KembangClientFactoryImpl implements
		BprsLoanFactory {

	static final LoanList LIST_PINJAMAN = new LoanList();
	static final LoanViewer PINJAMAN_VIEWER = new LoanViewer();
	static final LoanEditor PINJAMAN_EDITOR = new LoanEditor();
	static final InfoList INFO_LIST = new InfoList();
	static final InfoViewer INFO_VIEWER = new InfoViewer();
	static final GuaranteeList GUARANTEE_LIST = new GuaranteeList();
	static final GuaranteeViewer GUARANTEE_VIEWER = new GuaranteeViewer();
	static final GuaranteeEditor GUARANTEE_EDITOR = new GuaranteeEditor();
	static final RescheduleList RESCHEDULE_LIST = new RescheduleList();
	static final RescheduleViewer RESCHEDULE_VIEWER = new RescheduleViewer();
	static final RescheduleEditor RESCHEDULE_EDITOR = new RescheduleEditor();
	static final Simulation SIMULATION = new Simulation();

	@Override
	public ILoanList getLoanList() {
		return LIST_PINJAMAN;
	}

	@Override
	public ILoanInput getLoanViewer() {
		return PINJAMAN_VIEWER;
	}

	@Override
	public ILoanInput getLoanEditor() {
		return PINJAMAN_EDITOR;
	}

	@Override
	public IInfoList getInfoList() {
		return INFO_LIST;
	}

	@Override
	public IInfoViewer getInfoViewer() {
		return INFO_VIEWER;
	}

	@Override
	public IGuaranteeList getGuaranteeList() {
		return GUARANTEE_LIST;
	}

	@Override
	public IGuaranteeInput getGuaranteeViewer() {
		return GUARANTEE_VIEWER;
	}

	@Override
	public IGuaranteeInput getGuaranteeEditor() {
		return GUARANTEE_EDITOR;
	}

	@Override
	public ISimulation getSimulation() {
		return SIMULATION;
	}

	@Override
	public IRescheduleList getRescheduleList() {
		return RESCHEDULE_LIST;
	}

	@Override
	public IRescheduleInput getRescheduleViewer() {
		return RESCHEDULE_VIEWER;
	}

	@Override
	public IRescheduleInput getRescheduleEditor() {
		return RESCHEDULE_EDITOR;
	}

}
