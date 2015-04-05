package org.simbiosis.ui.bprs.loan.client.editor;

import org.kembang.editor.client.ListBoxEnumEditor;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleTypeDv;

public class LoanScheduleType extends ListBoxEnumEditor<LoanScheduleTypeDv> {

	public LoanScheduleType() {
		super(LoanScheduleTypeDv.class);
	}

}
