package org.simbiosis.ui.bprs.loan.client.guarantee;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GuaranteeListPlace extends Place {
	String token;

	public GuaranteeListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<GuaranteeListPlace> {

		@Override
		public GuaranteeListPlace getPlace(String token) {
			return new GuaranteeListPlace(token);
		}

		@Override
		public String getToken(GuaranteeListPlace place) {
			return place.getToken();
		}

	}
}
