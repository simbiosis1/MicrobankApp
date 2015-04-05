package org.simbiosis.ui.bprs.loan.client.simulation;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SimulationPlace extends Place {
	String token;

	public SimulationPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SimulationPlace> {

		@Override
		public SimulationPlace getPlace(String token) {
			return new SimulationPlace(token);
		}

		@Override
		public String getToken(SimulationPlace place) {
			return place.getToken();
		}

	}
}
