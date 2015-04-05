package org.simbiosis.ui.bprs.security.client.auth;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AuthListPlace extends Place {
	String token;

	public AuthListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<AuthListPlace> {

		@Override
		public AuthListPlace getPlace(String token) {
			return new AuthListPlace(token);
		}

		@Override
		public String getToken(AuthListPlace place) {
			return place.getToken();
		}

	}
}
