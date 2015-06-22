package org.simbiosis.ui.bprs.common.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DataDv implements IsSerializable {
	Integer nr;
	Long id;
	String kode;
	String nama;
	String alamat;
	Long produk;
	String strProduk;
	Double rate;

	public DataDv() {
		nr = 0;
		id = 0L;
		produk = 0L;
		rate = 0D;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public Long getProduk() {
		return produk;
	}

	public void setProduk(Long produk) {
		this.produk = produk;
	}

	public String getStrProduk() {
		return strProduk;
	}

	public void setStrProduk(String strProduk) {
		this.strProduk = strProduk;
	}

	@Override
	public String toString() {
		return nama;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
