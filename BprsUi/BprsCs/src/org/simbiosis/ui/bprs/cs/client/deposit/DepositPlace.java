package org.simbiosis.ui.bprs.cs.client.deposit;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DepositPlace extends Place {
	String token;

	public DepositPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DepositPlace> {

		@Override
		public DepositPlace getPlace(String token) {
			return new DepositPlace(token);
		}

		@Override
		public String getToken(DepositPlace place) {
			return place.getToken();
		}

	}
}
