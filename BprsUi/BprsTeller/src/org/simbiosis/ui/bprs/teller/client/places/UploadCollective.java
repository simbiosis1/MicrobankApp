package org.simbiosis.ui.bprs.teller.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UploadCollective extends Place {
	String token;

	public UploadCollective(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<UploadCollective> {

		@Override
		public UploadCollective getPlace(String token) {
			return new UploadCollective(token);
		}

		@Override
		public String getToken(UploadCollective place) {
			return place.getToken();
		}

	}
}
