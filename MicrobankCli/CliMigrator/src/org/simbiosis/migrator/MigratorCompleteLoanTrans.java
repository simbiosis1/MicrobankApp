package org.simbiosis.migrator;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.migrator.lib.Migrator;

public class MigratorCompleteLoanTrans extends Migrator {

	public static void main(String[] args) {
		MigratorCompleteLoanTrans loanTrans = new MigratorCompleteLoanTrans();
		loanTrans.execute();
	}

	public MigratorCompleteLoanTrans() {
		super();
	}

	@Override
	protected void process() {
		Map<String, LoanTransactionDto> map = new HashMap<String, LoanTransactionDto>();
		Map<Long, Long> mapSaving = new HashMap<Long, Long>();
		String strQuery = "select tb_financingaccount.financingaccount_id,financingaccount_code,"
				+ "financingtrans_id,"
				+ "financingtrans_refcode,financingtrans_code,financingtrans_date,"
				+ "financingtrans_type,financingtrans_description,financingtrans_direction,"
				+ "financingtrans_pokok,financingtrans_return,financingtrans_value "
				+ "from tb_financingtrans,tb_financingaccount "
				+ "where tb_financingtrans.financingaccount_id=tb_financingaccount.financingaccount_id "
				+ "and financingtrans_direction=2 "
				+ "and financingtrans_date between '"
				+ getStrDbBeginDate()
				+ "' and  '"
				+ getStrDbEndDate()
				+ "' order by financingtrans_code";

		// +
		// "where financingaccount_id=1829 and financingtrans_date='31.07.2012'"
		// + " order by financingtrans_code";
		try {
			ResultSet rs = execSQL(strQuery);
			// int i = 1;
			while (rs.next()) {
				String strLoan = getCoreClient().sendRawData("getLoanIdByCode",
						rs.getString("financingaccount_code"));
				String strLoanTrans = getCoreClient().sendRawData(
						"getLoanTransIdByRef",
						rs.getString("financingtrans_id"));
				if (!strLoan.equalsIgnoreCase("0")
						&& strLoanTrans.equalsIgnoreCase("0")) {
					int type = rs.getInt("financingtrans_type");
					LoanTransactionDto dto = null;
					if (type == 4) {
						dto = map.get(rs.getString("financingtrans_code"));
						if (dto != null) {
							System.out.println("+");
							dto.setType(type);
							dto.setDiscount(rs
									.getDouble("financingtrans_return"));
							map.put(rs.getString("financingtrans_code"), dto);
						}
					} else {
						dto = new LoanTransactionDto();
						dto.setRefId(rs.getLong("financingtrans_id"));
						dto.setLoan(Long.parseLong(strLoan));
						dto.setDate(rs.getDate("financingtrans_date"));
						dto.setType(type);
						dto.setHasCode(true);
						dto.setCode(rs.getString("financingtrans_code"));
						dto.setRefCode(rs.getString("financingtrans_refcode"));
						dto.setDescription(rs
								.getString("financingtrans_description"));
						dto.setDirection(rs.getInt("financingtrans_direction"));
						dto.setPrincipal(rs.getDouble("financingtrans_pokok"));
						dto.setMargin(rs.getDouble("financingtrans_return"));
						dto.setDiscount(0);
						map.put(dto.getCode(), dto);
						//
						LoanDto loanDto = getLoan(strLoan);
						mapSaving.put(loanDto.getId(), loanDto.getSaving());
						//
						System.out.print("-");
					}
				} else {
					System.out.println("Data problem "
							+ rs.getString("financingaccount_id"));
				}
			}
			//
			System.out.println();
			for (LoanTransactionDto trans : map.values()) {
				// kirim data
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, trans);
				// System.out.println(sw.toString());
				System.out.print(".");
				//
				Long saving = mapSaving.get(trans.getLoan());
				getCoreClient().sendRawData("payBilling", saving.toString(),
						"", sw.toString());
				// System.out.println(saving+"="+sw.toString());
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			System.out.println("Unable to close a connection.");
		}
	}

	LoanDto getLoan(String strLoanId) {
		LoanDto result = null;
		ObjectMapper mapper = new ObjectMapper();
		String strLoan = getCoreClient().sendRawData("getLoan", strLoanId);
		try {
			result = mapper.readValue(strLoan, LoanDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
