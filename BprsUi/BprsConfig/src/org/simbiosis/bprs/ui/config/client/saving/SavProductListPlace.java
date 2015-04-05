package org.simbiosis.bprs.ui.config.client.saving;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavProductListPlace extends Place {
	String token;

	public SavProductListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavProductListPlace> {

		@Override
		public SavProductListPlace getPlace(String token) {
			return new SavProductListPlace(token);
		}

		@Override
		public String getToken(SavProductListPlace place) {
			return place.getToken();
		}

	}
}
