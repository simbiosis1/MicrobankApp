package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.migrator.lib.Migrator;
import org.simbiosis.migrator.lib.SIMbprsGlTrans;

public class MigratorGl extends Migrator {

	public static void main(String[] args) {
		MigratorGl gl = new MigratorGl();
		gl.execute();
	}

	List<SIMbprsGlTrans> headerMap = new ArrayList<SIMbprsGlTrans>();
	Map<Long, List<SIMbprsGlTrans>> itemMap = new HashMap<Long, List<SIMbprsGlTrans>>();
	// String strSql =
	// "select * from tb_accounttrans where accounttrans_date between '"
	// + getStrDbBeginDate() + "' and '" + getStrDbEndDate() + "'";
	// String strSql =
	// "select * from tb_accounttrans where (accounttrans_description like 'SETOR TUNAI%' or accounttrans_description like 'TARIK TUNAI%' or accounttrans_description like 'SETORAN AWAL%') "
	// + "and accounttrans_date between '"
	// + getStrDbBeginDate() + "' and '" + getStrDbEndDate() + "'";
	//
	// String strSql =
	// "select * from tb_accounttrans where (accounttrans_code='00106' or accounttrans_code='00109') "
	// + "and accounttrans_date between '"
	// + getStrDbBeginDate() + "' and '" + getStrDbEndDate() + "'";
	// String strSql =
	// "select * from tb_accounttrans where accounttrans_description like '%BAGI HASIL DEPOSITO%' "
	// + "and accounttrans_date between '"
	// + getStrDbBeginDate() + "' and '" + getStrDbEndDate() + "'";
	String strSql = "select * from tb_accounttrans where accounttrans_description like 'ANGSURAN%' AND accounttrans_description not containing 'NURIL FAHIM' and accounttrans_description not containing 'BUDIANTO'"
			+ "and accounttrans_date between '"
			+ getStrDbBeginDate()
			+ "' and '" + getStrDbEndDate() + "'";

	public MigratorGl() {
		super();
	}

	protected void process() {
		// testCoa();
		migrateTrans();
	}

	void testCoa() {
		try {
			ResultSet rs = execSQL(strSql);
			int i = 1;
			while (rs.next()) {
				ObjectMapper mapper = new ObjectMapper();
				String data = getCoreClient().sendRawData("getCoaByRefId",
						rs.getString("accountlist_id"));
				try {
					Coa coa = mapper.readValue(data, Coa.class);
					// System.out.println(data);
					if (coa == null) {
						System.out.println(i++ + " Butuh : "
								+ rs.getString("accountlist_id"));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			closeDb();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

	SIMbprsGlTrans convertTrans(ResultSet rs) {
		SIMbprsGlTrans trans = new SIMbprsGlTrans();
		try {
			trans.setId(rs.getLong("accounttrans_id"));
			if (trans.getId() != 0) {
				trans.setDate(rs.getDate("accounttrans_date"));
				trans.setCode(rs.getString("accounttrans_code"));
				trans.setDescription(rs.getString("accounttrans_description"));
				trans.setDirection(rs.getInt("accounttrans_direct"));
				trans.setValue(rs.getDouble("accounttrans_value"));
				ObjectMapper mapper = new ObjectMapper();
				String data = getCoreClient().sendRawData("getCoaByRefId",
						rs.getString("accountlist_id"));
				try {
					Coa coa = mapper.readValue(data, Coa.class);
					trans.setCoa(coa.getId());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return trans;
	}

	void migrateTrans() {
		try {
			// Bentuk struktur transaksi dan masukkan ke dalam map
			System.out.println("Buat list struktur transaksi");
			ResultSet rs = execSQL(strSql);
			while (rs.next()) {
				SIMbprsGlTrans trans = convertTrans(rs);
				System.out.println(trans.getCode() + "-"
						+ trans.getDescription());
				if (trans.getId() != 0) {
					long refId = rs.getLong("accounttrans_ref");
					if (refId == 0) {
						headerMap.add(trans);
					} else {
						List<SIMbprsGlTrans> list = itemMap.get(refId);
						if (list == null) {
							list = new ArrayList<SIMbprsGlTrans>();
						}
						list.add(trans);
						itemMap.put(refId, list);
					}
				}
			}
			// Buat struktur untuk GlTrans
			System.out.println("Masukkan ke dalam transaksi sistem baru");
			for (SIMbprsGlTrans oldTrans : headerMap) {
				// Buat GLTrans
				GlTrans glTrans = new GlTrans();
				glTrans.setDate(oldTrans.getDate());
				glTrans.setCode(oldTrans.getCode());
				glTrans.setBranch(getBranch());
				glTrans.setDescription(oldTrans.getDescription());
				glTrans.setRefId(oldTrans.getId());
				// Buat GLTransItem pertama
				GlTransItem item = new GlTransItem();
				item.setDescription(oldTrans.getDescription());
				item.setDirection(oldTrans.getDirection());
				item.setCoa(getCoa(oldTrans.getCoa()));
				item.setValue(oldTrans.getValue());
				glTrans.getItems().add(item);
				// Buat GLTransItem berikutnya
				List<SIMbprsGlTrans> items = itemMap.get(oldTrans.getId());
				if (items != null) {
					for (SIMbprsGlTrans oldItem : items) {
						item = new GlTransItem();
						item.setDescription(oldItem.getDescription());
						item.setDirection(oldItem.getDirection());
						item.setCoa(getCoa(oldItem.getCoa()));
						item.setValue(oldItem.getValue());
						glTrans.getItems().add(item);
					}
					try {
						ObjectMapper mapper = new ObjectMapper();
						StringWriter sw = new StringWriter();
						mapper.writeValue(sw, glTrans);
						System.out.println(sw.toString());
						getCoreClient().sendRawData("saveGlTrans",
								sw.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}

				} else {
					System.out.println("Transaksi masalah");
				}
			}
			//
			closeDb();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to clode a connection.");
		}
	}

	private Coa getCoa(Long id) {
		Coa coa = new Coa();
		coa.setId(id);
		return coa;
	}
}
