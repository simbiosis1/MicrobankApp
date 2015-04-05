package org.simbiosis.bprs.ui.config.client.deposit;

import java.util.ArrayList;
import java.util.List;

import org.kembang.grid.client.GridSelectionHandler;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.SimpleBranchDv;
import org.simbiosis.bprs.ui.config.client.editor.ProductTable;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ProductDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DepProductList extends FormWidget implements IDepProduct {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, DepProductList> {
	}

	List<CoaDv> listCoa = new ArrayList<CoaDv>();
	List<ProductDv> listProduct = new ArrayList<ProductDv>();
	List<SimpleBranchDv> listTerm = new ArrayList<SimpleBranchDv>();

	DepProductViewer userViewer = new DepProductViewer();
	DepProductEditor userEditor = null;
	Boolean isEditor = false;

	@UiField
	ProductTable users;
	@UiField
	HorizontalPanel userForm;

	public DepProductList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasEdit(true);
		setHasSave(false);
		setHasReload(true);
		setHasBack(false);
		//
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
	public void setCoa(List<CoaDv> coas) {
		listCoa.clear();
		listCoa.addAll(coas);
	}

	@Override
	public void setTerm(List<SimpleBranchDv> terms) {
		listTerm.clear();
		listTerm.addAll(terms);
		userEditor = new DepProductEditor(listCoa, listTerm);
	}

	@Override
	public void setProduct(List<ProductDv> products) {
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

	private void setViewerData(ProductDv user) {
		userForm.clear();
		userForm.add(userViewer);
		userViewer.setUser(user);
		showBack(false);
		showSave(false);
		isEditor = false;
	}

	private void setEditorData(ProductDv product) {
		userForm.clear();
		userForm.add(userEditor);
		userEditor.setData(product);
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
			return userEditor.getData();
		} else {
			return users.getSelectedData();
		}
	}

}
