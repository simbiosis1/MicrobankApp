package org.simbiosis.ui.bprs.dashboard.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanMonitor extends Place {
	String token;

	public LoanMonitor(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanMonitor> {

		@Override
		public LoanMonitor getPlace(String token) {
			return new LoanMonitor(token);
		}

		@Override
		public String getToken(LoanMonitor place) {
			return place.getToken();
		}
	}
}
