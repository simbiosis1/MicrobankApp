package org.simbiosis.ui.bprs.cs.shared;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.ui.bprs.common.shared.SavingDv;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SavingBlockirDataDv implements IsSerializable {
	private SavingDv saving;
	private List<SavingBlockirDv> blockir = new ArrayList<SavingBlockirDv>();

	public SavingDv getSaving() {
		return saving;
	}

	public void setSaving(SavingDv saving) {
		this.saving = saving;
	}

	public List<SavingBlockirDv> getBlockir() {
		return blockir;
	}

	public void setBlockir(List<SavingBlockirDv> blockir) {
		this.blockir = blockir;
	}

}
