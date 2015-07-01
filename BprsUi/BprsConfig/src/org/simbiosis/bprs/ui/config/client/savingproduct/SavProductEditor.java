package org.simbiosis.bprs.ui.config.client.savingproduct;

import java.util.List;

import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class SavProductEditor extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SavProductEditor> {
	}

	SavProductEditorWdh editorWadiah;
	SavProductEditorMdh editorMudharabah;
	Boolean isEditor = false;

	ProductDv productDv;
	@UiField
	ListBox schema;
	@UiField
	HorizontalPanel editorForm;

	@UiHandler("schema")
	void onChange(ChangeEvent event) {
		editorForm.clear();
		if (schema.getSelectedIndex() == 0) {
			productDv.setSchema(1);
			editorForm.add(editorWadiah);
			editorWadiah.setUser(productDv);
		} else {
			productDv.setSchema(2);
			editorForm.add(editorMudharabah);
			editorMudharabah.setUser(productDv);
		}
	}

	public SavProductEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		schema.addItem("WADIAH");
		schema.addItem("MUDHARABAH");
	}

	public void setCoa(List<CoaDv> listCoa) {
		editorWadiah = new SavProductEditorWdh(listCoa);
		editorMudharabah = new SavProductEditorMdh(listCoa);
	}

	public void setData(ProductDv user) {
		this.productDv = user;
		editorForm.clear();
		schema.setSelectedIndex(user.getSchema() - 1);
		if (schema.getSelectedIndex() == 0) {
			editorForm.add(editorWadiah);
			editorWadiah.setUser(user);
		} else {
			editorForm.add(editorMudharabah);
			editorMudharabah.setUser(user);
		}
	}

	public ProductDv getUser() {
		return (schema.getSelectedIndex() == 0) ? editorWadiah.getUser()
				: editorMudharabah.getUser();
	}

}
