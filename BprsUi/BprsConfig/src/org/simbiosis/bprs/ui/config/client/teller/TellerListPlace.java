package org.simbiosis.bprs.ui.config.client.teller;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TellerListPlace extends Place {
	String token;

	public TellerListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<TellerListPlace> {

		@Override
		public TellerListPlace getPlace(String token) {
			return new TellerListPlace(token);
		}

		@Override
		public String getToken(TellerListPlace place) {
			return place.getToken();
		}

	}
}
