package org.simbiosis.microbank;

import java.util.List;

public interface IReference {

	public List<FinancialRefDto> listFinancialReportRef(int scheme, int type);
}
