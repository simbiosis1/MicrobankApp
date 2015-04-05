package org.simbiosis.ui.bprs.admin.client.loan;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanPlace extends Place {
	String token;

	public LoanPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanPlace> {

		@Override
		public LoanPlace getPlace(String token) {
			return new LoanPlace(token);
		}

		@Override
		public String getToken(LoanPlace place) {
			return place.getToken();
		}

	}
}
