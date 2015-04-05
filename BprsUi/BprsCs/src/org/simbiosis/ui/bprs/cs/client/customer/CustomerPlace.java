package org.simbiosis.ui.bprs.cs.client.customer;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CustomerPlace extends Place {
	String token;

	public CustomerPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<CustomerPlace> {

		@Override
		public CustomerPlace getPlace(String token) {
			return new CustomerPlace(token);
		}

		@Override
		public String getToken(CustomerPlace place) {
			return place.getToken();
		}

	}
}
