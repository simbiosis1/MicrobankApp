package org.simbiosis.ui.bprs.teller.client.tellertrans;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TellerTransListPlace extends Place {
	String token;

	public TellerTransListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements
			PlaceTokenizer<TellerTransListPlace> {

		@Override
		public TellerTransListPlace getPlace(String token) {
			return new TellerTransListPlace(token);
		}

		@Override
		public String getToken(TellerTransListPlace place) {
			return place.getToken();
		}

	}
}
