package org.simbiosis.ui.bprs.teller.client.htvault;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HtVaultPlace extends Place {
	String token;

	public HtVaultPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<HtVaultPlace> {

		@Override
		public HtVaultPlace getPlace(String token) {
			return new HtVaultPlace(token);
		}

		@Override
		public String getToken(HtVaultPlace place) {
			return place.getToken();
		}

	}
}
