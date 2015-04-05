package org.simbiosis.bprs.ui.config.client.loan;

import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoanProductViewer extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanProductViewer> {
	}

	LoanProductViewerMbh viewerMbh = new LoanProductViewerMbh();
	LoanProductViewerIjarah viewerIjarah = new LoanProductViewerIjarah();
	LoanProductViewerMdh viewerMdh = new LoanProductViewerMdh();

	@UiField
	HorizontalPanel viewerForm;

	public LoanProductViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setData(ProductDv loanProduct) {
		viewerForm.clear();
		if (loanProduct.getSchema() < 4 || loanProduct.getSchema() == 8) {
			viewerForm.add(viewerMbh);
			viewerMbh.setUser(loanProduct);
		} else if (loanProduct.getSchema() == 4) {
			viewerForm.add(viewerIjarah);
			viewerIjarah.setUser(loanProduct);
		} else {
			viewerForm.add(viewerMdh);
			viewerMdh.setUser(loanProduct);
		}
	}

}
