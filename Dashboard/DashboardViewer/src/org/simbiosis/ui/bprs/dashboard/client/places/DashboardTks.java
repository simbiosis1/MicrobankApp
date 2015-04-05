package org.simbiosis.ui.bprs.dashboard.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DashboardTks extends Place {
	String token;

	public DashboardTks(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DashboardTks> {

		@Override
		public DashboardTks getPlace(String token) {
			return new DashboardTks(token);
		}

		@Override
		public String getToken(DashboardTks place) {
			return place.getToken();
		}
	}
}
