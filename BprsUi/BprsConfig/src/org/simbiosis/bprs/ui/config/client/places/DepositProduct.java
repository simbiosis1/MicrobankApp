package org.simbiosis.bprs.ui.config.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DepositProduct extends Place {
	String token;

	public DepositProduct(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DepositProduct> {

		@Override
		public DepositProduct getPlace(String token) {
			return new DepositProduct(token);
		}

		@Override
		public String getToken(DepositProduct place) {
			return place.getToken();
		}

	}
}
