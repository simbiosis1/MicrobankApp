package org.simbiosis.ui.bprs.loan.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.loan.client.guarantee.IGuaranteeList;
import org.simbiosis.ui.bprs.loan.client.guaranteeinput.IGuaranteeInput;
import org.simbiosis.ui.bprs.loan.client.info.IInfoList;
import org.simbiosis.ui.bprs.loan.client.infoviewer.IInfoViewer;
import org.simbiosis.ui.bprs.loan.client.loan.ILoanList;
import org.simbiosis.ui.bprs.loan.client.loaninput.ILoanInput;
import org.simbiosis.ui.bprs.loan.client.reschedule.IRescheduleList;
import org.simbiosis.ui.bprs.loan.client.rescheduleinput.IRescheduleInput;
import org.simbiosis.ui.bprs.loan.client.simulation.ISimulation;

public interface BprsLoanFactory extends KembangClientFactory {

	ILoanList getLoanList();

	ILoanInput getLoanViewer();

	ILoanInput getLoanEditor();

	IInfoList getInfoList();

	IInfoViewer getInfoViewer();

	IGuaranteeList getGuaranteeList();

	IGuaranteeInput getGuaranteeViewer();

	IGuaranteeInput getGuaranteeEditor();

	ISimulation getSimulation();

	IRescheduleList getRescheduleList();

	IRescheduleInput getRescheduleViewer();

	IRescheduleInput getRescheduleEditor();
}
