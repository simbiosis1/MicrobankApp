package org.simbiosis.ui.bprs.dashboard.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Dashboard extends Place {
	String token;

	public Dashboard(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Dashboard> {

		@Override
		public Dashboard getPlace(String token) {
			return new Dashboard(token);
		}

		@Override
		public String getToken(Dashboard place) {
			return place.getToken();
		}
	}
}
