package org.simbiosis.ui.bprs.dashboard.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DashboardLoan extends Place {
	String token;

	public DashboardLoan(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DashboardLoan> {

		@Override
		public DashboardLoan getPlace(String token) {
			return new DashboardLoan(token);
		}

		@Override
		public String getToken(DashboardLoan place) {
			return place.getToken();
		}
	}
}
