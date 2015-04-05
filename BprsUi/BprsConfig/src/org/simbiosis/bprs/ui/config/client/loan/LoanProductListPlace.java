package org.simbiosis.bprs.ui.config.client.loan;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanProductListPlace extends Place {
	String token;

	public LoanProductListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanProductListPlace> {

		@Override
		public LoanProductListPlace getPlace(String token) {
			return new LoanProductListPlace(token);
		}

		@Override
		public String getToken(LoanProductListPlace place) {
			return place.getToken();
		}

	}
}
