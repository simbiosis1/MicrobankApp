package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.migrator.lib.Migrator;

public class MigratorLoan extends Migrator {
	public static void main(String[] args) {
		MigratorLoan loan = new MigratorLoan();
		loan.execute();
	}

	public MigratorLoan() {
		super();
	}

	@Override
	protected void process() {
		Map<Long, LoanDto> map = new HashMap<Long, LoanDto>();
		try {
			createLoan(map);
			//
			System.out.println();
			//
			createSchedule(map);
			//
			closeDb();
			//
			for (LoanDto dto : map.values()) {
				// kirim data
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, dto);
				//System.out.println(sw.toString());
				System.out.print(".");
				getCoreClient().sendRawData("saveLoan", sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

	private void createLoan(Map<Long, LoanDto> map) {
		String strQuery = "select financingaccount_id,financingproduct_id,customer_id,financingaccount_code,"
				+ "financingaccount_createdate,financingaccount_closedate, financingaccount_active "
				+ "financingaccount_akadcode,financingaccount_akaddate, financingaccount_active, "
				+ "financingaccount_plafond,financingaccount_return,financingaccount_schedamount, "
				+ "financingaccount_rate1, "
				+ "financingaccount_savingaccount from tb_financingaccount "
				+ "where financingaccount_createdate between '"
				+ getStrDbBeginDate() + "' and  '" + getStrDbEndDate() + "' order by financingaccount_id";
		try {
			ResultSet rs = execSQL(strQuery);
			// int i = 1;
			while (rs.next()) {
				String strCif = getCoreClient().sendRawData("getCifIdByRef",
						rs.getString("customer_id"));
				String strSaving = getCoreClient()
						.sendRawData(
								"getSavingIdByRef",
								getBranch()
										+ ";"
										+ rs.getString("financingaccount_savingaccount"));
				if (!strCif.equalsIgnoreCase("0")
						&& !strSaving.equalsIgnoreCase("0")) {
					LoanDto dto = new LoanDto();
					dto.setBranch(getBranch());
					dto.setRefId(rs.getLong("financingaccount_id"));
					dto.setCustomer(Long.parseLong(strCif));
					dto.setProduct(getNewProduct(rs
							.getLong("financingproduct_id")));
					dto.setHasCode(true);
					dto.setCode(rs.getString("financingaccount_code"));
					dto.setRegistration(rs
							.getDate("financingaccount_createdate"));
					dto.setContract(rs.getString("financingaccount_akadcode"));
					dto.setContractDate(rs.getDate("financingaccount_akaddate"));
					dto.setActive(rs.getInt("financingaccount_active"));
					dto.setClosing(rs.getDate("financingaccount_closedate"));
					dto.setPrincipal(rs.getDouble("financingaccount_plafond"));
					dto.setTenor(rs.getDouble("financingaccount_schedamount"));
					dto.setRate(rs.getDouble("financingaccount_rate1"));
					dto.setMargin(rs.getDouble("financingaccount_return"));
					if (dto.getRate() < 0.01 && dto.getPrincipal() > 0.1) {
						dto.setRate(dto.getMargin() * 1200
								/ (dto.getPrincipal() * dto.getTenor()));
					}
					dto.setDropped(true);
					//
					dto.setSaving(Long.parseLong(strSaving));
					if (dto.getProduct() == 0) {
						System.out.println("Data problem");
					}
					//
					System.out.print("-");
					map.put(dto.getRefId(), dto);
				} else {
					System.out.println("\nData masalah : "
							+ rs.getLong("financingaccount_id") + ";" + strCif
							+ ";" + strSaving);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

	private void createSchedule(Map<Long, LoanDto> map) {
		String strQuery = "select financingaccount_id,financingschedule_date,"
				+ "financingschedule_pokok,financingschedule_return,financingschedule_value  "
				+ "from tb_financingschedule order by financingaccount_id, financingschedule_date";
		ResultSet rs = execSQL(strQuery);
		try {
			while (rs.next()) {
				LoanScheduleDto sched = new LoanScheduleDto();
				Long refId = rs.getLong("financingaccount_id");
				LoanDto dto = map.get(refId);
				if (dto != null) {
					// sched.setLoan(loanDto.getId());
					sched.setDate(rs.getDate("financingschedule_date"));
					Double value = rs.getDouble("financingschedule_pokok");
					sched.setPrincipal(value == null ? 0 : value);
					value = rs.getDouble("financingschedule_return");
					sched.setMargin(value == null ? 0 : value);
					sched.setTotal(sched.getPrincipal() + sched.getMargin());
					dto.getSchedules().add(sched);
					System.out.print("+");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

	private long getNewProduct(Long product) {
		int iProduct = product.intValue();
		switch (iProduct) {
		case 21:
			return 8;
		case 14:
			return 2;
		case 15:
			return 5;
		case 16:
			return 6;
		case 13:
			return 9;
		case 17:
			return 7;
		case 22:
			return 11;
		case 23:
			return 10;
		case 19:
			return 4;
		case 18:
			return 3;
		case 20:
			return 12;
		case 24:
			return 13;
		case 25:
			return 14;
		case 26:
			return 15;
		default:
			return 0;
		}
	}
}
