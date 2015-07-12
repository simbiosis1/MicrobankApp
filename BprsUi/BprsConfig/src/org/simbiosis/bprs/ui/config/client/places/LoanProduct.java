package org.simbiosis.bprs.ui.config.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanProduct extends Place {
	String token;

	public LoanProduct(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanProduct> {

		@Override
		public LoanProduct getPlace(String token) {
			return new LoanProduct(token);
		}

		@Override
		public String getToken(LoanProduct place) {
			return place.getToken();
		}

	}
}
