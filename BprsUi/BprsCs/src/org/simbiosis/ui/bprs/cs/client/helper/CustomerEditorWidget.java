package org.simbiosis.ui.bprs.cs.client.helper;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.bprs.common.client.editor.CityListEditor;
import org.simbiosis.ui.bprs.common.client.editor.OccupationListEditor;
import org.simbiosis.ui.bprs.common.client.editor.ProvinceListEditor;
import org.simbiosis.ui.bprs.common.shared.CustomerDv;
import org.simbiosis.ui.bprs.common.shared.DataDv;
import org.simbiosis.ui.bprs.cs.client.editor.IdTypeEditor;
import org.simbiosis.ui.bprs.cs.client.editor.SexTypeEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DateLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class CustomerEditorWidget extends FormWidget implements
		Editor<CustomerDv> {

	private static AnggotaEditorUiBinder uiBinder = GWT
			.create(AnggotaEditorUiBinder.class);

	interface AnggotaEditorUiBinder extends
			UiBinder<Widget, CustomerEditorWidget> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<CustomerDv, CustomerEditorWidget> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	DateLabel registration;
	@UiField
	TextBox name;
	@UiField
	TextBox title;
	@UiField
	SexTypeEditor sex;
	@UiField
	TextBox pob;
	@UiField
	DateBox dob;
	@UiField
	IdTypeEditor idType;
	@UiField
	TextBox idCode;
	@UiField
	TextBox motherName;
	@UiField
	TextBox spouseName;
	@UiField
	TextBox address;
	@UiField
	TextBox village;
	@UiField
	TextBox district;
	@UiField
	CityListEditor city;
	@UiField
	TextBox postCode;
	@UiField
	ProvinceListEditor province;
	@UiField
	TextBox phone;
	@UiField
	TextBox handphone;
	@UiField
	OccupationListEditor occupation;
	@UiField
	TextBox officeName;
	@UiField
	TextBox officeAddress;
	@UiField
	CityListEditor officeCity;
	@UiField
	TextBox income;
	@UiField
	CheckBox taxable;
	@UiField
	TextBox taxNr;
	@UiField
	TextBox descendant;
	@UiField
	TextBox descAddress;
	@UiField
	CheckBox bankRel;

	CustomerDv selectedData;

	List<DataDv> provinceList = new ArrayList<DataDv>();
	List<DataDv> cityList = new ArrayList<DataDv>();
	List<DataDv> occupationList = new ArrayList<DataDv>();

	public CustomerEditorWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		dob.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		//
		setHasSave(true);
		setHasBack(true);
		//
		driver.initialize(this);
		province.setList(provinceList);
		city.setList(cityList);
		occupation.setList(occupationList);
		officeCity.setList(cityList);
	}

	public void showData(CustomerDv customerDv) {
		selectedData = customerDv;
		driver.edit(customerDv);
	}

	public CustomerDv getSelectedData() {
		return selectedData;
	}

	public CustomerDv getEditedData() {
		return driver.flush();
	}

	public void setProvinceList(List<DataDv> provinceList) {
		this.provinceList.clear();
		this.provinceList.addAll(provinceList);
		province.setList(this.provinceList);
	}

	public void setCityList(List<DataDv> cityList) {
		this.cityList.clear();
		this.cityList.addAll(cityList);
		city.setList(this.cityList);
		officeCity.setList(this.cityList);
	}

	public void setOccupationList(List<DataDv> occupationList) {
		this.occupationList.clear();
		this.occupationList.addAll(occupationList);
		occupation.setList(this.occupationList);
	}

}
