package org.simbiosis.bprs.ui.config.client.savingproduct;

import java.util.ArrayList;
import java.util.List;

import org.kembang.grid.client.GridSelectionHandler;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.bprs.ui.config.client.editor.ProductTable;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SavProductList extends FormWidget implements ISavProduct {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, SavProductList> {
	}

	List<CoaDv> listCoa = new ArrayList<CoaDv>();
	List<ProductDv> listProduct = new ArrayList<ProductDv>();

	SavProductViewer productViewer = new SavProductViewer();
	SavProductEditor productEditor = new SavProductEditor();
	Boolean isEditor = false;

	@UiField
	ProductTable users;
	@UiField
	HorizontalPanel userForm;

	public SavProductList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasEdit(true);
		setHasSave(false);
		setHasReload(true);
		setHasBack(false);

		users.setSelectionHandler(new GridSelectionHandler() {

			@Override
			public void onSelection() {
				onSelectionHandler();
			}
		});

	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void setCoa(List<CoaDv> listCoa) {
		productEditor.setCoa(listCoa);
	}

	@Override
	public void setProducts(List<ProductDv> products) {
		listProduct.clear();
		listProduct.addAll(products);
		this.users.clear();
		for (ProductDv user : products) {
			this.users.addRow(user);
		}
	}

	void onSelectionHandler() {
		setViewerData(users.getSelectedData());
	}

	void onChangeHandler(ProductDv dv) {
		setEditorData(dv);
	}

	private void setViewerData(ProductDv user) {
		//
		userForm.clear();
		userForm.add(productViewer);
		productViewer.setData(user);

		showSave(false);
		isEditor = false;
	}

	public void setEditorData(ProductDv user) {
		userForm.clear();
		userForm.add(productEditor);
		productEditor.setData(user);
		showBack(true);
		showSave(true);
		isEditor = true;
	}

	@Override
	public void editSelected() {
		setEditorData(users.getSelectedData());
	}

	@Override
	public void newProduct() {
		setEditorData(new ProductDv());
	}

	@Override
	public void viewSelected() {
		setViewerData(users.getSelectedData());
	}

	@Override
	public void clearViewer() {
		setViewerData(new ProductDv());
	}

	@Override
	public ProductDv getProduct() {
		if (isEditor) {
			return productEditor.getUser();
		} else {
			return users.getSelectedData();
		}
	}

}
