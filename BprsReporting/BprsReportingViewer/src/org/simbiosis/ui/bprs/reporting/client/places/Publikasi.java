package org.simbiosis.ui.bprs.reporting.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Publikasi extends Place {
	String token;

	public Publikasi(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Publikasi> {

		@Override
		public Publikasi getPlace(String token) {
			return new Publikasi(token);
		}

		@Override
		public String getToken(Publikasi place) {
			return place.getToken();
		}
	}
}
