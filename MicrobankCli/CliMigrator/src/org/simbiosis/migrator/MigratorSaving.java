package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.microbank.SavingDto;
import org.simbiosis.migrator.lib.Migrator;

public class MigratorSaving extends Migrator {

	public static void main(String[] args) {
		MigratorSaving saving = new MigratorSaving();
		saving.execute();
	}

	public MigratorSaving() {
		super();
	}

	@Override
	protected void process() {
		String strQuery = "select savingaccount_id,savingproduct_id,customer_id,savingaccount_code,"
				+ "savingaccount_createdate,savingaccount_closedate, savingaccount_zakat,"
				+ "savingaccount_active "
				+ "from tb_savingaccount "
				+ "where savingaccount_createdate between'"
				+ getStrDbBeginDate() + "' and '" + getStrDbEndDate() + "'";
		try {

			ResultSet rs = execSQL(strQuery);
			// int i = 1;
			while (rs.next()) {
				String strCif = getCoreClient().sendRawData("getCifIdByRef",
						rs.getString("customer_id"));
				String strSaving = getCoreClient().sendRawData(
						"getSavingIdByRef",
						getBranch() + ";" + rs.getString("savingaccount_id"));
				if (!strCif.equalsIgnoreCase("0")
						&& strSaving.equalsIgnoreCase("0")) {
					SavingDto dto = new SavingDto();
					dto.setBranch(getBranch());
					dto.setRefId(rs.getLong("savingaccount_id"));
					dto.setCustomer(Long.parseLong(strCif));
					dto.setProduct(getNewProduct(rs.getLong("savingproduct_id")));
					dto.setCode(rs.getString("savingaccount_code"));
					dto.setRegistration(rs.getDate("savingaccount_createdate"));
					dto.setZakat(rs.getInt("savingaccount_zakat"));
					dto.setActive(rs.getInt("savingaccount_active"));
					dto.setClosing(rs.getDate("savingaccount_closedate"));
					// kirim data
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper.writeValue(sw, dto);
					getCoreClient().sendRawData("saveSaving", sw.toString());
					System.out.print(".");
				} else {
					System.out.println("Data tabungan masalah "
							+ rs.getString("savingaccount_id") + " = " + strCif
							+ ";" + strSaving);
				}
			}
			closeDb();
		} catch (java.sql.SQLException | IOException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

	long getNewProduct(Long product) {
		int iProduct = product.intValue();
		switch (iProduct) {
		case 18:
			return 2;
		case 2:
			return 3;
		case 3:
			return 4;
		case 4:
			return 5;
		case 17:
			return 6;
		case 19:
			return 7;
		default:
			return 2;
		}
	}
}
