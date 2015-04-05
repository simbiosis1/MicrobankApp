package org.simbiosis.ui.bprs.teller.client.savingtrans;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingTransListPlace extends Place {
	String token;

	public SavingTransListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements
			PlaceTokenizer<SavingTransListPlace> {

		@Override
		public SavingTransListPlace getPlace(String token) {
			return new SavingTransListPlace(token);
		}

		@Override
		public String getToken(SavingTransListPlace place) {
			return place.getToken();
		}

	}
}
