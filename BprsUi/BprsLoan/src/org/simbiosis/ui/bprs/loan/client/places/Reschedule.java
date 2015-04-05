package org.simbiosis.ui.bprs.loan.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Reschedule extends Place {
	String token;

	public Reschedule(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Reschedule> {

		@Override
		public Reschedule getPlace(String token) {
			return new Reschedule(token);
		}

		@Override
		public String getToken(Reschedule place) {
			return place.getToken();
		}

	}
}
