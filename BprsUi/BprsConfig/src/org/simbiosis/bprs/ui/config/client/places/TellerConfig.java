package org.simbiosis.bprs.ui.config.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class TellerConfig extends Place {
	String token;

	public TellerConfig(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<TellerConfig> {

		@Override
		public TellerConfig getPlace(String token) {
			return new TellerConfig(token);
		}

		@Override
		public String getToken(TellerConfig place) {
			return place.getToken();
		}

	}
}
