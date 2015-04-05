package org.simbiosis.cli.revsharing;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.cli.base.CliBase;
import org.simbiosis.gl.model.Coa;
import org.simbiosis.gl.model.GlTrans;
import org.simbiosis.gl.model.GlTransItem;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;

public class KoreksiBasilDeposito extends CliBase {

	public class BasilDeposit {
		String deposit;
		String saving;
		double revSharing;
		double tax;

		public BasilDeposit() {

		}

		public BasilDeposit(String deposit, String saving, double revSharing,
				double tax) {
			this.deposit = deposit;
			this.saving = saving;
			this.revSharing = revSharing;
			this.tax = tax;
		}

		public String getDeposit() {
			return deposit;
		}

		public String getSaving() {
			return saving;
		}

		public double getRevSharing() {
			return revSharing;
		}

		public double getTax() {
			return tax;
		}

	}

	public static void main(String[] args) {
		KoreksiBasilDeposito koreksiBasil = new KoreksiBasilDeposito();
		koreksiBasil.run();
	}

	List<BasilDeposit> basilList = new ArrayList<BasilDeposit>();
	String period = "0414";

	public KoreksiBasilDeposito() {
		super("cli.properties");
		createList();
	}

	public void run() {
		while (next()) {
			long coaTax = 307;
			Date currentDate = new Date();
			for (BasilDeposit basil : basilList) {
				// Cari deposito
				String strDepId = getCoreClient().sendRawData(
						"getDepositIdByCode", basil.getDeposit());
				String strDepInfo = getCoreClient().sendRawData(
						"getDepositInformation", strDepId);
				String strSavId = getCoreClient().sendRawData(
						"getSavingIdByCode", basil.getSaving());
				String strSavInfo = getCoreClient().sendRawData(
						"getSavingInformation", strSavId);
				ObjectMapper mapper = new ObjectMapper();
				try {
					DepositInformationDto depInfo = mapper.readValue(
							strDepInfo, DepositInformationDto.class);
					SavingInformationDto savInfo = mapper.readValue(strSavInfo,
							SavingInformationDto.class);
					long sumber = depInfo.getCoa2();
					long tujuan = savInfo.getCoa1();
					String codeBahas = "KORBAHASDEP(01-03/14)";
					String codeTax = "PPHBAHASDEP(01-03/14)";
					String descBahas = "KOREKSI BAHAS DEPOSITO 01-03/14 - "
							+ depInfo.getName() + " (" + depInfo.getCode()
							+ ")";
					String descTax = "PPH BAHAS DEPOSITO 01-03/14 - "
							+ depInfo.getName() + " (" + depInfo.getCode()
							+ ")";
					// System.out.println(description + " : " + sumber + "-"
					// + tujuan);

					if (sumber != 0 && tujuan != 0) {
						//
						SavingTransactionDto savTrans = new SavingTransactionDto();
						savTrans.setSaving(savInfo.getId());
						savTrans.setCode(codeBahas);
						savTrans.setRefCode(codeBahas);
						savTrans.setHasCode(true);
						savTrans.setBranch(savInfo.getBranch());
						savTrans.setDate(currentDate);
						savTrans.setDescription(descBahas);
						savTrans.setDirection(1);
						savTrans.setValue(basil.getRevSharing());
						savTrans.setType(3);
						//
						mapper = new ObjectMapper();
						StringWriter sw = new StringWriter();
						mapper.writeValue(sw, savTrans);
						System.out.println(sw.toString());
						getCoreClient().sendRawData("saveSavingTrans",
								sw.toString());
						//
						GlTrans trans = new GlTrans();
						trans.setCompany(depInfo.getCompany());
						trans.setBranch(depInfo.getBranch());
						trans.setDate(currentDate);
						trans.setCode(codeBahas);
						trans.setDescription(descBahas);
						//
						GlTransItem glItem = new GlTransItem();
						glItem.setCoa(getCoa(sumber));
						glItem.setDescription(trans.getDescription());
						glItem.setDirection(1);
						glItem.setValue(basil.getRevSharing());
						trans.getItems().add(glItem);
						//
						glItem = new GlTransItem();
						glItem.setCoa(getCoa(tujuan));
						glItem.setDescription(trans.getDescription());
						glItem.setDirection(2);
						glItem.setValue(basil.getRevSharing());
						trans.getItems().add(glItem);
						//
						//
						mapper = new ObjectMapper();
						sw = new StringWriter();
						mapper.writeValue(sw, trans);
						System.out.println(sw.toString());
						getCoreClient().sendRawData("saveGlTrans",
								sw.toString());
						//
						if (basil.getTax() > 0) {
							//
							savTrans = new SavingTransactionDto();
							savTrans.setSaving(savInfo.getId());
							savTrans.setCode(codeTax);
							savTrans.setRefCode(codeTax);
							savTrans.setHasCode(true);
							savTrans.setBranch(savInfo.getBranch());
							savTrans.setDate(currentDate);
							savTrans.setDescription(descTax);
							savTrans.setDirection(2);
							savTrans.setValue(basil.getTax());
							savTrans.setType(4);
							//
							mapper = new ObjectMapper();
							sw = new StringWriter();
							mapper.writeValue(sw, savTrans);
							System.out.println(sw.toString());
							getCoreClient().sendRawData("saveSavingTrans",
									sw.toString());
							//
							trans = new GlTrans();
							trans.setCompany(depInfo.getCompany());
							trans.setBranch(depInfo.getBranch());
							trans.setDate(currentDate);
							trans.setCode(codeTax);
							trans.setDescription(descTax);
							//
							glItem = new GlTransItem();
							glItem.setCoa(getCoa(tujuan));
							glItem.setDescription(trans.getDescription());
							glItem.setDirection(1);
							glItem.setValue(basil.getTax());
							trans.getItems().add(glItem);
							//
							glItem = new GlTransItem();
							glItem.setCoa(getCoa(coaTax));
							glItem.setDescription(trans.getDescription());
							glItem.setDirection(2);
							glItem.setValue(basil.getTax());
							trans.getItems().add(glItem);
							//
							mapper = new ObjectMapper();
							sw = new StringWriter();
							mapper.writeValue(sw, trans);
							System.out.println(sw.toString());
							getCoreClient().sendRawData("saveGlTrans",
									sw.toString());
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private Coa getCoa(Long id) {
		Coa coa = new Coa();
		coa.setId(id);
		return coa;
	}

	private void createList() {
		basilList.add(new BasilDeposit("0013300007", "0012300108", 82780.344,
				16556.0688));
		basilList.add(new BasilDeposit("0013300028", "0012300073",
				8278.0347272727, 1655.6069454546));
		basilList.add(new BasilDeposit("0013300163", "0012300073", 20695.086,
				4139.0172));
		basilList.add(new BasilDeposit("0013300197", "0011101489", 20695.086,
				4139.0172));
		basilList.add(new BasilDeposit("0013300256", "0011102626",
				8278.0347272727, 0));
		basilList.add(new BasilDeposit("0013300257", "0011100982",
				804808.902692308, 160961.780538462));
		basilList.add(new BasilDeposit("0013300262", "0012102338",
				4139.0173636364, 0));
		basilList.add(new BasilDeposit("0013300264", "0012200314", 82780.344,
				16556.0688));
		basilList.add(new BasilDeposit("0013300265", "0012300757", 41390.172,
				8278.0344));
		basilList.add(new BasilDeposit("0013300266", "0012200761",
				103475.430818182, 20695.0861636364));
		basilList.add(new BasilDeposit("0033300260", "0031102550", 41390.172,
				8278.0344));
		basilList.add(new BasilDeposit("0013600034", "0011100008",
				505879.881913044, 101175.976382609));
		basilList.add(new BasilDeposit("0013600101", "0011100112",
				116352.372782609, 23270.4745565217));
		basilList.add(new BasilDeposit("0013600176", "0012100092",
				10117.5972173913, 2023.5194434783));
		basilList.add(new BasilDeposit("0013600231", "0012302197",
				30352.7926086957, 6070.5585217391));
		basilList.add(new BasilDeposit("0013600232", "0011100008",
				505879.881913044, 101175.976382609));
		basilList.add(new BasilDeposit("0013600243", "0012200314",
				379409.911913044, 75881.9823826087));
		basilList.add(new BasilDeposit("0013600244", "0012302463",
				35411.592173913, 7082.3184347826));
		basilList.add(new BasilDeposit("0023600235", "0012200761",
				202351.952956522, 40470.3905913043));
		basilList.add(new BasilDeposit("0023600238", "0012200761",
				151763.964956522, 30352.7929913044));
		basilList.add(new BasilDeposit("0023600239", "0012200761",
				126469.970956522, 25293.9941913043));
		basilList.add(new BasilDeposit("0023600240", "0012200761",
				379409.911913044, 75881.9823826087));
		basilList.add(new BasilDeposit("0023600241", "0012200761",
				556467.869913044, 111293.573982609));
		basilList.add(new BasilDeposit("0023600242", "0012200761",
				455291.893913043, 91058.3787826087));
		basilList.add(new BasilDeposit("0043600235", "0042202390",
				505879.881913044, 101175.976382609));
		basilList.add(new BasilDeposit("0013800008", "0012300087",
				5978.5808333333, 0));
		basilList.add(new BasilDeposit("0013800068", "0011100008",
				1207213.35362069, 241442.670724138));
		basilList.add(new BasilDeposit("0013800083", "0011100012",
				298929.021083333, 59785.8042166667));
		basilList.add(new BasilDeposit("0013800088", "0012300584",
				482885.341448276, 96577.0682896552));
		basilList.add(new BasilDeposit("0013800090", "0012300108", 89678.706,
				17935.7412));
		basilList.add(new BasilDeposit("0013800091", "0012100729",
				23914.3211666667, 4782.8642333333));
		basilList.add(new BasilDeposit("0013800106", "0011100151",
				5978.5808333333, 0));
		basilList.add(new BasilDeposit("0013800114", "0012300584",
				149464.511083333, 29892.9022166667));
		basilList.add(new BasilDeposit("0013800122", "0011100008",
				482885.341448276, 96577.0682896552));
		basilList.add(new BasilDeposit("0013800132", "0011100008",
				724328.012172414, 144865.602434483));
		basilList.add(new BasilDeposit("0013800234", "0011100098",
				26903.6115833333, 0));
		basilList.add(new BasilDeposit("0013800244", "0011100084",
				239143.217083333, 47828.6434166667));
		basilList.add(new BasilDeposit("0013800247", "0012100729", 14946.451,
				2989.2902));
		basilList.add(new BasilDeposit("0013800250", "0012302367",
				5978.5808333333, 0));
		basilList.add(new BasilDeposit("0013800254", "0012300584",
				597858.042166667, 119571.608433333));
		basilList.add(new BasilDeposit("0013800261", "0012300584",
				597858.042166667, 119571.608433333));
		basilList.add(new BasilDeposit("0013800262", "0012300585",
				448393.532166667, 89678.7064333333));
		basilList.add(new BasilDeposit("0013800263", "0012300584",
				448393.532166667, 89678.7064333333));
		basilList.add(new BasilDeposit("0013800265", "0011100057",
				1195716.08433333, 239143.216866667));
		basilList.add(new BasilDeposit("0013800267", "0012300726",
				11957.1605833333, 0));
		basilList.add(new BasilDeposit("0013800268", "0012200522",
				298929.021083333, 59785.8042166667));
		basilList.add(new BasilDeposit("0013800270", "0012300584",
				597858.042166667, 119571.608433333));
		basilList
				.add(new BasilDeposit("0013800271", "0012302367", 29892.902, 0));
		basilList.add(new BasilDeposit("0013800272", "0012302460",
				5978.5808333333, 0));
		basilList.add(new BasilDeposit("0013800273", "0012302461",
				167400.251416667, 33480.0502833333));

	}
}
