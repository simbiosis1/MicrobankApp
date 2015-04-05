package org.simbiosis.ui.bprs.loan.client.loan;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanListPlace extends Place {
	String token;

	public LoanListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanListPlace> {

		@Override
		public LoanListPlace getPlace(String token) {
			return new LoanListPlace(token);
		}

		@Override
		public String getToken(LoanListPlace place) {
			return place.getToken();
		}

	}
}
