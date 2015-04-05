package org.simbiosis.ui.bprs.loan.client.info;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class InfoListPlace extends Place {
	String token;

	public InfoListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<InfoListPlace> {

		@Override
		public InfoListPlace getPlace(String token) {
			return new InfoListPlace(token);
		}

		@Override
		public String getToken(InfoListPlace place) {
			return place.getToken();
		}

	}
}
