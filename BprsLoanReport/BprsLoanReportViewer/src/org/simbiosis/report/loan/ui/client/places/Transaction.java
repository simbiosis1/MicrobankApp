package org.simbiosis.report.loan.ui.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Transaction  extends Place {
	String token;

	public Transaction(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<Transaction> {

		@Override
		public Transaction getPlace(String token) {
			return new Transaction(token);
		}

		@Override
		public String getToken(Transaction place) {
			return place.getToken();
		}
	}
}
