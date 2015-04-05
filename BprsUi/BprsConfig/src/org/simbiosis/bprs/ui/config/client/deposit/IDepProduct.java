package org.simbiosis.bprs.ui.config.client.deposit;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

public interface IDepProduct {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setCoa(List<CoaDv> branches);

	void setProduct(List<ProductDv> users);

	void editSelected();

	void newProduct();

	void viewSelected();

	void clearViewer();

	void setTerm(List<SimpleBranchDv> terms);

	ProductDv getProduct();

	public abstract class Activity extends FormActivity {
	}

}
