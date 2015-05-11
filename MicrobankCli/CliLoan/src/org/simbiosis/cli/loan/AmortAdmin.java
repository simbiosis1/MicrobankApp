package org.simbiosis.cli.loan;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.LoanDto;
import org.simbiosis.microbank.LoanProductDto;

public class AmortAdmin extends CliBase {

	public static void main(String[] args) {
		AmortAdmin amortAdmin = new AmortAdmin();
		amortAdmin.execute();
	}

	Map<Long, LoanProductDto> loanProducts = new HashMap<Long, LoanProductDto>();
	List<LoanDto> loanAdmins = new ArrayList<LoanDto>();
	String period = "";
	DateTime today = new DateTime();
	DateTimeFormatter periodDtf = DateTimeFormat.forPattern("MMyyyy");

	public AmortAdmin() {
		super("cli.properties");
		//
		period = periodDtf.print(today);
	}

	public void execute() {
		while (next()) {
			//
			//
			loanProducts.clear();
			loanAdmins.clear();
			//
			//
			createLoanAdmins();
			//
			for (LoanDto loan : loanAdmins) {
				double amortAdmin = loan.getAdmin() / loan.getTenor();
				LoanProductDto product = loanProducts.get(loan.getProduct());
				if (product == null) {
					product = getLoanProduct(loan.getProduct());
					if (product != null) {
						loanProducts.put(loan.getProduct(), product);
					}
				}
				if (product != null && product.getProfitShared() != 0) {
					ObjectMapper mapper = new ObjectMapper();
					try {
						GlTrans trans = new GlTrans();
						trans.setCompany(loan.getCompany());
						trans.setBranch(loan.getBranch());
						trans.setDate(today.toDate());
						trans.setCode("AMOR-ADM-" + period);
						trans.setDescription("AMOR ADM LOAN " + loan.getCode()
								+ " " + period);
						//
						GlTransItem item1 = new GlTransItem();
						item1.setCoa(getCoa(product.getCoa5()));
						item1.setDescription(trans.getDescription());
						item1.setDirection(1);
						item1.setValue(amortAdmin);
						trans.getItems().add(item1);
						//
						item1 = new GlTransItem();
						item1.setCoa(getCoa(product.getCoa6()));
						item1.setDescription(trans.getDescription());
						item1.setDirection(2);
						item1.setValue(amortAdmin);
						trans.getItems().add(item1);
						//
						//
						mapper = new ObjectMapper();
						StringWriter sw = new StringWriter();
						mapper.writeValue(sw, trans);
						System.out.println(sw.toString());
						getCoreClient().sendRawData("saveGlTrans",
								sw.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private Coa getCoa(Long id) {
		Coa coa = new Coa();
		coa.setId(id);
		return coa;
	}

	private void createLoanAdmins() {
		String data = getCoreClient().sendRawData("listLoanByScheme", "1");

		ObjectMapper mapper = new ObjectMapper();
		try {
			List<LoanDto> loans = mapper.readValue(
					data,
					mapper.getTypeFactory().constructCollectionType(
							ArrayList.class, LoanDto.class));
			for (LoanDto loan : loans) {
				if (loan.getAdmin() > 0) {
					loanAdmins.add(loan);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private LoanProductDto getLoanProduct(Long id) {
		String data = getCoreClient().sendRawData("getLoanProduct",
				id.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			LoanProductDto product = mapper.readValue(data,
					LoanProductDto.class);
			return product;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
