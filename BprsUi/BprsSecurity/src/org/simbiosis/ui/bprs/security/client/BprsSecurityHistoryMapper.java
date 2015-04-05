package org.simbiosis.ui.bprs.security.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.security.client.auth.AuthListPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ AuthListPlace.Tokenizer.class })
public interface BprsSecurityHistoryMapper extends KembangHistoryMapper {

}
