package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Nominatif extends Place {
	String token;

	public Nominatif(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Nominatif> {

		@Override
		public Nominatif getPlace(String token) {
			return new Nominatif(token);
		}

		@Override
		public String getToken(Nominatif place) {
			return place.getToken();
		}
	}
}
