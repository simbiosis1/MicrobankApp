package org.simbiosis.ui.bprs.common.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FindTransactionDv implements IsSerializable {
	List<TransactionDv> resultTable = new ArrayList<TransactionDv>();

	public List<TransactionDv> getResultTable() {
		return resultTable;
	}

	public void setResultTable(List<TransactionDv> resultTable) {
		this.resultTable = resultTable;
	}

}
