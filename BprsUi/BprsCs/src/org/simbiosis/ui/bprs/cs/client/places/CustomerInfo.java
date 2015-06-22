package org.simbiosis.ui.bprs.cs.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CustomerInfo extends Place {
	String token;

	public CustomerInfo(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<CustomerInfo> {

		@Override
		public CustomerInfo getPlace(String token) {
			return new CustomerInfo(token);
		}

		@Override
		public String getToken(CustomerInfo place) {
			return place.getToken();
		}

	}
}
