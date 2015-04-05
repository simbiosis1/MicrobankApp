package org.simbiosis.ui.bprs.reporting.client.revenuesharing;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RevenueSharingPlace extends Place {
	String token;

	public RevenueSharingPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<RevenueSharingPlace> {

		@Override
		public RevenueSharingPlace getPlace(String token) {
			return new RevenueSharingPlace(token);
		}

		@Override
		public String getToken(RevenueSharingPlace place) {
			return place.getToken();
		}
	}
}
