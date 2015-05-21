package org.simbiosis.bprs.ui.config.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GlConfig extends Place {
	String token;

	public GlConfig(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<GlConfig> {

		@Override
		public GlConfig getPlace(String token) {
			return new GlConfig(token);
		}

		@Override
		public String getToken(GlConfig place) {
			return place.getToken();
		}

	}
}
