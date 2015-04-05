package org.simbiosis.ui.bprs.loan.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.loan.client.guarantee.GuaranteeListActivity;
import org.simbiosis.ui.bprs.loan.client.guarantee.GuaranteeListPlace;
import org.simbiosis.ui.bprs.loan.client.info.InfoListActivity;
import org.simbiosis.ui.bprs.loan.client.info.InfoListPlace;
import org.simbiosis.ui.bprs.loan.client.loan.LoanListActivity;
import org.simbiosis.ui.bprs.loan.client.loan.LoanListPlace;
import org.simbiosis.ui.bprs.loan.client.places.Reschedule;
import org.simbiosis.ui.bprs.loan.client.reschedule.RescheduleListActivity;
import org.simbiosis.ui.bprs.loan.client.simulation.SimulationActivity;
import org.simbiosis.ui.bprs.loan.client.simulation.SimulationPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class BprsLoanActivityMapper extends KembangActivityMapper {

	public BprsLoanActivityMapper(BprsLoanFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		BprsLoanFactory clientFactory = (BprsLoanFactory) getClientFactory();
		if (place instanceof LoanListPlace) {
			return new LoanListActivity((LoanListPlace) place,
					clientFactory);
		} else if (place instanceof InfoListPlace) {
			return new InfoListActivity((InfoListPlace) place, clientFactory);
		} else if (place instanceof Reschedule) {
			return new RescheduleListActivity((Reschedule) place, clientFactory);
		} else if (place instanceof GuaranteeListPlace) {
			return new GuaranteeListActivity((GuaranteeListPlace) place, clientFactory);
		} else if (place instanceof SimulationPlace) {
			return new SimulationActivity((SimulationPlace) place, clientFactory);
		}
		return null;
	}

}
