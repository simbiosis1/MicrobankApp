package org.simbiosis.ui.bprs.teller.client.vault;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class VaultPlace extends Place {
	String token;

	public VaultPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<VaultPlace> {

		@Override
		public VaultPlace getPlace(String token) {
			return new VaultPlace(token);
		}

		@Override
		public String getToken(VaultPlace place) {
			return place.getToken();
		}

	}
}
