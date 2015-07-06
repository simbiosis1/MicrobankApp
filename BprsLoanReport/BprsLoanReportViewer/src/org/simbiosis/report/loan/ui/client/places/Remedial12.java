package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Remedial12 extends Place {
	String token;

	public Remedial12(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Remedial12> {

		@Override
		public Remedial12 getPlace(String token) {
			return new Remedial12(token);
		}

		@Override
		public String getToken(Remedial12 place) {
			return place.getToken();
		}
	}
}
