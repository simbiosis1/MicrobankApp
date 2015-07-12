package org.simbiosis.bprs.ui.config.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingProduct extends Place {
	String token;

	public SavingProduct(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavingProduct> {

		@Override
		public SavingProduct getPlace(String token) {
			return new SavingProduct(token);
		}

		@Override
		public String getToken(SavingProduct place) {
			return place.getToken();
		}

	}
}
