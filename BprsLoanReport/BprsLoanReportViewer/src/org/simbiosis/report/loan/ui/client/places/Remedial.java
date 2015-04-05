package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Remedial extends Place {
	String token;

	public Remedial(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Remedial> {

		@Override
		public Remedial getPlace(String token) {
			return new Remedial(token);
		}

		@Override
		public String getToken(Remedial place) {
			return place.getToken();
		}
	}
}
