package org.simbiosis.ui.bprs.cs.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingBlock extends Place {
	String token;

	public SavingBlock(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavingBlock> {

		@Override
		public SavingBlock getPlace(String token) {
			return new SavingBlock(token);
		}

		@Override
		public String getToken(SavingBlock place) {
			return place.getToken();
		}

	}
}
