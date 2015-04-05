package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NominatifAo extends Place {
	String token;

	public NominatifAo(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<NominatifAo> {

		@Override
		public NominatifAo getPlace(String token) {
			return new NominatifAo(token);
		}

		@Override
		public String getToken(NominatifAo place) {
			return place.getToken();
		}
	}
}
