package org.simbiosis.ui.bprs.cs.client.savingclose;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingClosePlace extends Place {
	String token;

	public SavingClosePlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavingClosePlace> {

		@Override
		public SavingClosePlace getPlace(String token) {
			return new SavingClosePlace(token);
		}

		@Override
		public String getToken(SavingClosePlace place) {
			return place.getToken();
		}

	}
}
