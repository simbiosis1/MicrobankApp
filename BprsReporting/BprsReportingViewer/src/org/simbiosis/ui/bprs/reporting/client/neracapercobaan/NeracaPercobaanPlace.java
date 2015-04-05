package org.simbiosis.ui.bprs.reporting.client.neracapercobaan;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NeracaPercobaanPlace extends Place {
	String token;

	public NeracaPercobaanPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<NeracaPercobaanPlace> {

		@Override
		public NeracaPercobaanPlace getPlace(String token) {
			return new NeracaPercobaanPlace(token);
		}

		@Override
		public String getToken(NeracaPercobaanPlace place) {
			return place.getToken();
		}
	}
}
