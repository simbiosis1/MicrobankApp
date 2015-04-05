package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Remedial1 extends Place {
	String token;

	public Remedial1(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Remedial1> {

		@Override
		public Remedial1 getPlace(String token) {
			return new Remedial1(token);
		}

		@Override
		public String getToken(Remedial1 place) {
			return place.getToken();
		}
	}
}
