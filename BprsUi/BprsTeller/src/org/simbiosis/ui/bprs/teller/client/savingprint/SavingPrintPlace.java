package org.simbiosis.ui.bprs.teller.client.savingprint;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingPrintPlace extends Place {
	String token;

	public SavingPrintPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements
			PlaceTokenizer<SavingPrintPlace> {

		@Override
		public SavingPrintPlace getPlace(String token) {
			return new SavingPrintPlace(token);
		}

		@Override
		public String getToken(SavingPrintPlace place) {
			return place.getToken();
		}

	}
}
