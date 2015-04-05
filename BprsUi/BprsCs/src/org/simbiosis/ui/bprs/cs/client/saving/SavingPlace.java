package org.simbiosis.ui.bprs.cs.client.saving;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingPlace extends Place {
	String token;

	public SavingPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavingPlace> {

		@Override
		public SavingPlace getPlace(String token) {
			return new SavingPlace(token);
		}

		@Override
		public String getToken(SavingPlace place) {
			return place.getToken();
		}

	}
}
