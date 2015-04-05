package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.migrator.lib.Migrator;

public class MigratorSavingTrans extends Migrator {
	public static void main(String[] args) {
		MigratorSavingTrans saving = new MigratorSavingTrans();
		saving.execute();
	}

	public MigratorSavingTrans() {
		super();
	}

	@Override
	protected void process() {
		// String strQuery =
		// "select custtransaction_id,savingaccount_id,custtransaction_code," +
		// "custtransaction_date,custtransaction_description, custtransaction_direction, " +
		// "custtransaction_value,custtransaction_timestamp,custtransaction_refcode "
		// + "from tb_custtransaction "
		// + "where custtransaction_date between '"
		// + getStrDbBeginDate()
		// + "' and '" + getStrDbEndDate() + "'";
		//
		// Bagi hasil
		//
		String strQuery = "select custtransaction_id,savingaccount_id,custtransaction_code,"
				+ "custtransaction_date,custtransaction_description, custtransaction_direction, "
				+ "custtransaction_value,custtransaction_timestamp,custtransaction_refcode "
				+ "from tb_custtransaction "
				+ "where custtransaction_date='31.05.2014' "
				+ "and savingaccount_id=2026 "
				+ "and (CUSTTRANSACTION_CODE='00106' or CUSTTRANSACTION_CODE='00109')";
		
//		+"where CUSTTRANSACTION_DESCRIPTION like '%BAGI HASIL DEPOSITO%'"
//		+"and CUSTTRANSACTION_DATE between '"+getStrDbBeginDate()+"' and '"+getStrDbEndDate()+"'";
		
//		String strQuery = "select custtransaction_id,savingaccount_id,custtransaction_code,"
//				+ "custtransaction_date,custtransaction_description, custtransaction_direction, "
//				+ "custtransaction_value,custtransaction_timestamp,custtransaction_refcode "
//				+ "from tb_custtransaction "
//				+ "where custtransaction_date='11.02.2014' and savingaccount_id=1785";
		try {

			ResultSet rs = execSQL(strQuery);
			// int i = 1;
			while (rs.next()) {
				String strSaving = getCoreClient().sendRawData(
						"getSavingIdByRef",
						getBranch() + ";" + rs.getString("savingaccount_id"));
				String strSavingTrans = getCoreClient().sendRawData(
						"getSavingTransIdByRef",
						getBranch() + ";" + rs.getString("custtransaction_id"));
				if (!strSaving.equalsIgnoreCase("0")
						&& strSavingTrans.equalsIgnoreCase("0")) {
					SavingTransactionDto dto = new SavingTransactionDto();
					dto.setBranch(getBranch());
					dto.setRefId(rs.getLong("custtransaction_id"));
					dto.setSaving(Long.parseLong(strSaving));
					dto.setDate(rs.getDate("custtransaction_date"));
					dto.setCode(rs.getString("custtransaction_code"));
					dto.setRefCode(rs.getString("custtransaction_refcode"));
					dto.setRefCode(dto.getCode());
					dto.setDescription(rs
							.getString("custtransaction_description"));
					dto.setDirection(rs.getInt("custtransaction_direction"));
					dto.setValue(rs.getDouble("custtransaction_value"));
					dto.setTimestamp(rs
							.getTimestamp("custtransaction_timestamp"));
					dto.setHasCode(true);
					dto.setType(dto.getDirection());
					// kirim data
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper.writeValue(sw, dto);
					// System.out.println(data);
					System.out.print(".");
					getCoreClient().sendRawData("saveSavingTrans",
							sw.toString());
				} else {
					System.out.println("Transaksi masalah "
							+ rs.getString("custtransaction_id") + " = "
							+ strSaving + ";" + strSavingTrans);
				}
			}
			closeDb();
		} catch (java.sql.SQLException | IOException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

}
