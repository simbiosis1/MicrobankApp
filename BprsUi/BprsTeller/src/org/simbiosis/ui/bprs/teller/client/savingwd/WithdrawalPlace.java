package org.simbiosis.ui.bprs.teller.client.savingwd;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class WithdrawalPlace extends Place {
	String token;

	public WithdrawalPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<WithdrawalPlace> {

		@Override
		public WithdrawalPlace getPlace(String token) {
			return new WithdrawalPlace(token);
		}

		@Override
		public String getToken(WithdrawalPlace place) {
			return place.getToken();
		}

	}
}
