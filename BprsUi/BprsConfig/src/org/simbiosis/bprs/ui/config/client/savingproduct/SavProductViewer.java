package org.simbiosis.bprs.ui.config.client.savingproduct;

import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SavProductViewer extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SavProductViewer> {
	}

	SavProductViewerMdh viewerMudharabah = new SavProductViewerMdh();
	SavProductViewerWdh viewerWadiah = new SavProductViewerWdh();

	@UiField
	HorizontalPanel viewerForm;

	public SavProductViewer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setData(ProductDv user) {
		viewerForm.clear();
		if (user.getSchema() == 1) {
			viewerForm.add(viewerWadiah);
			viewerWadiah.setUser(user);
		} else {
			viewerForm.add(viewerMudharabah);
			viewerMudharabah.setUser(user);
		}
	}

}
