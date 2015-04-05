package org.simbiosis.ui.bprs.common.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CariDataDv implements IsSerializable {
	List<DataDv> resultTable = new ArrayList<DataDv>();

	public List<DataDv> getResultTable() {
		return resultTable;
	}

	public void setResultTable(List<DataDv> resultTable) {
		this.resultTable = resultTable;
	}
}
