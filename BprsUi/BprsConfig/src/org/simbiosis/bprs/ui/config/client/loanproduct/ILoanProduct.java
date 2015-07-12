package org.simbiosis.bprs.ui.config.client.loanproduct;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

public interface ILoanProduct {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setCoa(List<CoaDv> branches);

	void setProducts(List<ProductDv> users);

	void editSelected();

	void newProduct();

	void viewSelected();

	void clearViewer();

	ProductDv getProduct();

	public abstract class Activity extends FormActivity {
	}

}
