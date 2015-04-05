package org.simbiosis.bprs.ui.config.client.deposit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DepProductListPlace extends Place {
	String token;

	public DepProductListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DepProductListPlace> {

		@Override
		public DepProductListPlace getPlace(String token) {
			return new DepProductListPlace(token);
		}

		@Override
		public String getToken(DepProductListPlace place) {
			return place.getToken();
		}

	}
}
