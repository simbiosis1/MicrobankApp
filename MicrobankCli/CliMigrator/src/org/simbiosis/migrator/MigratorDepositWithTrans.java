package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositTransactionDto;
import org.simbiosis.migrator.lib.Migrator;

public class MigratorDepositWithTrans extends Migrator {

	public static void main(String[] args) {
		MigratorDepositWithTrans gl = new MigratorDepositWithTrans();
		gl.execute();
	}

	public MigratorDepositWithTrans() {
		super();
	}

	@Override
	protected void process() {
		String strQuery = "select depositaccount_id,savingproduct_id,customer_id,depositaccount_code,"
				+ "depositaccount_createdate,depositaccount_closedate, depositaccount_zakat,"
				+ "depositaccount_active,depositaccount_begindate,depositaccount_enddate, "
				+ "depositaccount_nominal,depositaccount_aro,depositaccount_returnextra, "
				+ "depositaccount_returnaccount,depositaccount_bilyet "
				+ "from tb_depositaccount "
				+ "where depositaccount_createdate between '"
				+ getStrDbBeginDate() + "' and  '" + getStrDbEndDate() + "'";
		try {
			ResultSet rs = execSQL(strQuery);
			// int i = 1;
			while (rs.next()) {
				String strCif = getCoreClient().sendRawData("getCifIdByRef",
						rs.getString("customer_id"));
				String strSaving = getCoreClient().sendRawData(
						"getSavingIdByRef",
						getBranch() + ";"
								+ rs.getString("depositaccount_returnaccount"));
				String strDeposit = getCoreClient().sendRawData(
						"getDepositIdByRef",
						getBranch() + ";" + rs.getString("depositaccount_id"));
				if (!strCif.equalsIgnoreCase("0")
						&& !strSaving.equalsIgnoreCase("0")
						&& strDeposit.equalsIgnoreCase("0")) {
					DepositDto dto = new DepositDto();
					dto.setRefId(rs.getLong("depositaccount_id"));
					dto.setBranch(getBranch());
					dto.setCustomer(Long.parseLong(strCif));
					dto.setProduct(getNewProduct(rs.getLong("savingproduct_id")));
					dto.setHasCode(true);
					dto.setCode(rs.getString("depositaccount_code"));
					dto.setBilyet(rs.getString("depositaccount_bilyet"));
					dto.setRegistration(rs.getDate("depositaccount_createdate"));
					dto.setBegin(rs.getDate("depositaccount_begindate"));
					dto.setEnd(rs.getDate("depositaccount_enddate"));
					dto.setClosing(rs.getDate("depositaccount_closedate"));
					dto.setValue(rs.getDouble("depositaccount_nominal"));
					dto.setZakat(rs.getInt("depositaccount_zakat"));
					dto.setActive(rs.getInt("depositaccount_active"));
					dto.setAro(rs.getInt("depositaccount_aro"));
					dto.setSpecialRate(rs
							.getDouble("depositaccount_returnextra"));
					dto.setSaving(Long.parseLong(strSaving));
					// // kirim data
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper.writeValue(sw, dto);
					// System.out.println(sw.toString());
					System.out.print(".");
					String strDepId = getCoreClient().sendRawData(
							"saveDeposit", sw.toString());
					//
					saveDepositTransByDepRefId(strDepId, dto);
				} else {
					System.out.println("Data masalah "
							+ rs.getLong("depositaccount_id") + " : " + strCif
							+ ";" + strSaving + ";" + strDeposit);
				}
			}
			closeDb();
		} catch (java.sql.SQLException | IOException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
		System.out.println();
	}

	void saveDepositTransByDepRefId(String strDepId, DepositDto dto) {
		try {
			Long id = Long.parseLong(strDepId);
			DepositTransactionDto dep = new DepositTransactionDto();
			dep.setBranch(getBranch());
			dep.setDate(dto.getRegistration());
			dep.setDeposit(id);
			dep.setDescription("SETORAN DEPOSITO " + dto.getCode());
			dep.setDirection(1);
			dep.setValue(dto.getValue());
			// // kirim data
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, dep);
			// System.out.println(sw.toString());
			System.out.print("+");
			getCoreClient().sendRawData("saveDepositTrans", sw.toString());
			if (dto.getActive() == 0) {
				dep = new DepositTransactionDto();
				dep.setBranch(getBranch());
				dep.setDate(dto.getClosing());
				dep.setDeposit(id);
				dep.setDescription("PENCAIRAN DEPOSITO " + dto.getCode());
				dep.setDirection(2);
				dep.setValue(dto.getValue());
				// // kirim data
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, dep);
				System.out.print("-");
				getCoreClient().sendRawData("saveDepositTrans", sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("Unable to close a connection.");
		}
	}

	long getNewProduct(Long product) {
		switch (product.intValue()) {
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 4;
		case 8:
			return 5;
		default:
			return 2;
		}
	}
}
