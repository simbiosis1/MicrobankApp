package org.simbiosis.ui.bprs.admin.client.transfer;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TransferPlace extends Place {
	String token;

	public TransferPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<TransferPlace> {

		@Override
		public TransferPlace getPlace(String token) {
			return new TransferPlace(token);
		}

		@Override
		public String getToken(TransferPlace place) {
			return place.getToken();
		}

	}
}
