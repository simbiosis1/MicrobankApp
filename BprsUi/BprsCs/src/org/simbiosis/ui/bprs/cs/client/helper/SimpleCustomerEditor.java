package org.simbiosis.ui.bprs.cs.client.helper;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class SimpleCustomerEditor extends Composite implements
		Editor<CustomerDv> {

	private static AnggotaEditorSingkatUiBinder uiBinder = GWT
			.create(AnggotaEditorSingkatUiBinder.class);

	interface AnggotaEditorSingkatUiBinder extends
			UiBinder<Widget, SimpleCustomerEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<CustomerDv, SimpleCustomerEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	TextBox name;
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
	TextBox income;
	@UiField
	TextBox taxNr;
	@UiField
	TextBox descendant;
	@UiField
	TextBox descAddress;
	@UiField
	CheckBox bankRel;
	
	List<DataDv> provinceList = new ArrayList<DataDv>();
	List<DataDv> cityList = new ArrayList<DataDv>();
	List<DataDv> occupationList = new ArrayList<DataDv>();
	
	public SimpleCustomerEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		dob.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		//
		driver.initialize(this);
		province.setList(provinceList);
		city.setList(cityList);
		occupation.setList(occupationList);
	}

	public void showData(CustomerDv anggotaDv) {
		driver.edit(anggotaDv);
	}

	public CustomerDv getData() {
		return driver.flush();
	}
	
	public void setSavingProvinsiList(List<DataDv> savingProvinsiList){
		this.provinceList.clear();
		this.provinceList.addAll(savingProvinsiList);
		province.setList(this.provinceList);
	}
	
	public void setSavingCityList(List<DataDv> savingCityList){
		this.cityList.clear();
		this.cityList.addAll(savingCityList);
		city.setList(this.cityList);
	}
	
	public void setSavingPekerjaanList(List<DataDv> jenisPekerjaanList){
		this.occupationList.clear();
		this.occupationList.addAll(jenisPekerjaanList);
		occupation.setList(this.occupationList);
	}
}
