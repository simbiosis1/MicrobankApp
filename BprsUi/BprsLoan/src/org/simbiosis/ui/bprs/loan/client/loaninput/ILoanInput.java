package org.simbiosis.ui.bprs.loan.client.loaninput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.common.shared.LoanDv;
import org.simbiosis.ui.bprs.common.shared.LoanScheduleDv;
import org.simbiosis.ui.bprs.loan.shared.LoanScheduleGenDv;
import org.simbiosis.ui.bprs.loan.shared.UserDv;

public interface ILoanInput {

	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void showData(LoanDv loanDv);

	LoanDv getSelectedData();

	LoanDv getEditedData();

	void setLoanProductList(List<DataDv> listLoanProduct);

	void setLoanAO(List<UserDv> listAo);

	void setBISektor(List<String> listBISektor);

	void setSchedule(List<LoanScheduleDv> newSchedule);

	public abstract class Activity extends FormActivity {
		public abstract void showData(DataDv dataDv);

		public abstract void newData();

		public abstract void loadCommonList();

		public abstract void generate(LoanScheduleGenDv data);

	}
}
