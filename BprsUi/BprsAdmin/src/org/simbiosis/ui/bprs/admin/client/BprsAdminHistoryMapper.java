package org.simbiosis.ui.bprs.admin.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.admin.client.deposit.DepositPlace;
import org.simbiosis.ui.bprs.admin.client.loan.LoanPlace;
import org.simbiosis.ui.bprs.admin.client.places.UploadCollective;
import org.simbiosis.ui.bprs.admin.client.savingjournal.SavingJournalPlace;
import org.simbiosis.ui.bprs.admin.client.transfer.TransferPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ LoanPlace.Tokenizer.class, DepositPlace.Tokenizer.class,
		SavingJournalPlace.Tokenizer.class, TransferPlace.Tokenizer.class,
		UploadCollective.Tokenizer.class })
public interface BprsAdminHistoryMapper extends KembangHistoryMapper {

}
