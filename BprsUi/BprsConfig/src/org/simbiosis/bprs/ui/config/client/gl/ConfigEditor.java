package org.simbiosis.bprs.ui.config.client.gl;

import java.util.List;

import org.simbiosis.bprs.ui.config.client.editor.CoaListEditor;
import org.simbiosis.bprs.ui.config.shared.CoaDv;
import org.simbiosis.bprs.ui.config.shared.ConfigDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ConfigEditor extends Composite implements Editor<ConfigDv> {

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, ConfigEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<ConfigDv, ConfigEditor> {
	}

	static Driver driver = GWT.create(Driver.class);

	@UiField
	CoaListEditor vault;
	@UiField
	CoaListEditor rab;

	public ConfigEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		// driver.edit(new ConfigDv());
	}

	void setData(ConfigDv config) {
		driver.edit(config);
	}

	public ConfigDv getData() {
		return driver.flush();
	}

	public void setCoaList(List<CoaDv> coaList) {
		vault.setList(coaList);
		rab.setList(coaList);
	}
}
