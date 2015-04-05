package org.simbiosis.ui.bprs.admin.client.savingjournal;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingJournalPlace extends Place {
	String token;

	public SavingJournalPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavingJournalPlace> {

		@Override
		public SavingJournalPlace getPlace(String token) {
			return new SavingJournalPlace(token);
		}

		@Override
		public String getToken(SavingJournalPlace place) {
			return place.getToken();
		}

	}
}
