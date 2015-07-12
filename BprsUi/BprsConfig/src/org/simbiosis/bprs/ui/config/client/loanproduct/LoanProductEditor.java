package org.simbiosis.bprs.ui.config.client.loanproduct;

import java.util.ArrayList;
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

public class LoanProductEditor extends Composite {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanProductEditor> {
	}

	LoanProductEditorMbh editorMbh;
	LoanProductEditorMdh editorMdh;
	LoanProductEditorIjarah editorIjarah;
	Boolean isEditor = false;

	List<CoaDv> listCoa = new ArrayList<CoaDv>();

	ProductDv productDv;
	@UiField
	ListBox schema;
	@UiField
	HorizontalPanel editorForm;

	public LoanProductEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		// FIXME: Hardcoded jenis scheme
		schema.addItem("MURABAHAH");
		schema.addItem("SALAM");
		schema.addItem("ISTISHNA");
		schema.addItem("IJARAH");
		schema.addItem("MUDHARABAH");
		schema.addItem("MUSYARAKAH");
		schema.addItem("QARDH");
		schema.addItem("MULTIJASA");
		//
	}

	@UiHandler("schema")
	void onChange(ChangeEvent event) {
		editorForm.clear();
		productDv.setSchema(schema.getSelectedIndex() + 1);
		if (schema.getSelectedIndex() < 3 || schema.getSelectedIndex() == 7) {
			editorForm.add(editorMbh);
			editorMbh.setProduct(productDv);
		} else if (schema.getSelectedIndex() == 3) {
			editorForm.add(editorIjarah);
			editorIjarah.setProduct(productDv);
		} else {
			editorForm.add(editorMdh);
			editorMdh.setProduct(productDv);
		}
	}

	public void setCoa(List<CoaDv> coas) {
		listCoa.clear();
		listCoa.addAll(coas);
		editorMbh = new LoanProductEditorMbh(listCoa);
		editorMdh = new LoanProductEditorMdh(listCoa);
		editorIjarah = new LoanProductEditorIjarah(listCoa);
	}

	public void setData(ProductDv user) {
		this.productDv = user;
		editorForm.clear();
		schema.setSelectedIndex(user.getSchema() - 1);
		if (schema.getSelectedIndex() < 3 || schema.getSelectedIndex() == 7) {
			editorForm.add(editorMbh);
			editorMbh.setProduct(user);
		} else if (schema.getSelectedIndex() == 3) {
			editorForm.add(editorIjarah);
			editorIjarah.setProduct(user);
		} else {
			editorForm.add(editorMdh);
			editorMdh.setProduct(user);
		}
	}

	public ProductDv getUser() {
		if (schema.getSelectedIndex() < 3 || schema.getSelectedIndex() == 7) {
			return editorMbh.getProduct();
		} else if (schema.getSelectedIndex() == 3) {
			return editorIjarah.getProduct();
		} else {
			return editorMdh.getProduct();
		}
	}

}
