package org.simbiosis.ui.bprs.teller.client.cashtrans;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CashTransPlace extends Place {
	String token;

	public CashTransPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<CashTransPlace> {

		@Override
		public CashTransPlace getPlace(String token) {
			return new CashTransPlace(token);
		}

		@Override
		public String getToken(CashTransPlace place) {
			return place.getToken();
		}

	}
}
