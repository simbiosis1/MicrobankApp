package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Remedial34 extends Place {
	String token;

	public Remedial34(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Remedial34> {

		@Override
		public Remedial34 getPlace(String token) {
			return new Remedial34(token);
		}

		@Override
		public String getToken(Remedial34 place) {
			return place.getToken();
		}
	}
}
