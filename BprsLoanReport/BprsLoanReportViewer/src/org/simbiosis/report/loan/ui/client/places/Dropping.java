package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Dropping  extends Place {
	String token;

	public Dropping(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Dropping> {

		@Override
		public Dropping getPlace(String token) {
			return new Dropping(token);
		}

		@Override
		public String getToken(Dropping place) {
			return place.getToken();
		}
	}
}
