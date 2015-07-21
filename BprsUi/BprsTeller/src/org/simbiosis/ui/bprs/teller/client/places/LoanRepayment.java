package org.simbiosis.ui.bprs.teller.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanRepayment extends Place {
	String token;

	public LoanRepayment(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanRepayment> {

		@Override
		public LoanRepayment getPlace(String token) {
			return new LoanRepayment(token);
		}

		@Override
		public String getToken(LoanRepayment place) {
			return place.getToken();
		}

	}
}
