package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.microbank.SavingPrintCodeRefDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.microbanking.reporting.model.SavingTransDv;
import org.simbiosis.printing.lib.ValidationServlet;
import org.simbiosis.system.ConfigDto;

@WebServlet("/getSavingBook")
public class SavingBook extends ValidationServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;
	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp systemBp;

	// String[] strType = { "00", "01", "02", "03", "04", "05", "06", "07",
	// "08",
	// "09" };
	// String[] strType = { "01", "02", "03", "04", "05", "06", "07", "08",
	// "09",
	// "10" };
	List<SavingPrintCodeRefDto> printCodes = new ArrayList<SavingPrintCodeRefDto>();
	DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
	int space = 2;
	int maxLine = 38;
	int middleLine = 17;

	public SavingBook() {
		super("SavingBook");
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		String data = request.getParameter("data");
		String[] datas = data.split("<>");
		long id = Long.parseLong(datas[0]);
		Date date = dtf.parseDateTime(datas[1]).toDate();
		int nuc = Integer.parseInt(datas[2]);
		//
		prepare();
		// Ambil konfigurasi baris tengah cetak dan jumlah baris dalam 1
		// buku
		ConfigDto config = systemBp.getConfig(getSession(),
				"saving.printMiddleLine");
		middleLine = config.getIntValue();
		config = systemBp.getConfig(getSession(), "saving.printMaxLine");
		maxLine = config.getIntValue();
		config = systemBp.getConfig(getSession(), "saving.printSpace");
		space = config.getIntValue();
		printCodes.addAll(savingBp.listPrintCode(getSession()));
		//
		setBeanCollection(listSavingTransForPrint(id, date, nuc));
		//
	}

	private String getPrintCode(int type) {
		String result = "00";
		for (SavingPrintCodeRefDto printCode : printCodes) {
			if (printCode.getType() == type) {
				result = printCode.getCode();
				break;
			}
		}
		return result;
	}

	private String formatNumber(double value) {
		DecimalFormatSymbols fs = DecimalFormatSymbols.getInstance();
		fs.setDecimalSeparator(',');
		fs.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setDecimalFormatSymbols(fs);
		return df.format(value);
	}

	private List<SavingTransDv> listSavingTransForPrint(long id, Date date,
			int nuc) {
		int myNuc = nuc;
		List<SavingTransactionDto> dtoList = savingBp.listTransForPrint(id,
				date, myNuc);
		List<SavingTransDv> dvList = new ArrayList<SavingTransDv>();
		//
		if (myNuc == 0) {
			myNuc = savingBp.getLastNuc(id) + 1;
			if (myNuc >= (maxLine - space - 1))
				myNuc = 1;
		}
		//
		int i = 1;
		//
		List<SavingTransDv> endResultList = new ArrayList<SavingTransDv>();
		// Tambah baris kosong sampai sebanyak myNuc
		while (i < myNuc) {
			endResultList.add(new SavingTransDv());
			i++;
		}
		//
		double ballance = 0;
		if (nuc == 0) {
			ballance = savingBp.getBallanceBeforeNuc(id);
		} else {
			ballance = savingBp.getBallanceBefore(id, date);
		}
		// Format yang mau dicetak dan set nuc ke database
		for (SavingTransactionDto dto : dtoList) {
			SavingTransDv transDv = new SavingTransDv();
			transDv.setId(dto.getId());
			transDv.setNr("" + i);
			transDv.setDate(dtf.print(new DateTime(dto.getDate())));
			transDv.setType(getPrintCode(dto.getType()));
			if (dto.getDirection() == 1) {
				ballance += dto.getValue();
				transDv.setDebet("");
				transDv.setCredit(formatNumber(dto.getValue()));
				transDv.setBallance(formatNumber(ballance));
			} else {
				ballance -= dto.getValue();
				transDv.setDebet(formatNumber(dto.getValue()));
				transDv.setCredit("");
				transDv.setBallance(formatNumber(ballance));
			}
			// Teller tellerDto = teller.getTeller(dto.get);
			transDv.setOperator("T001");
			transDv.setCode(dto.getCode());
			//
			dvList.add(transDv);
			savingBp.saveNUC(dto.getId(), i);
			i++;
			if (i >= (maxLine - space))
				break;
		}
		// Tambah middle line
		int line = myNuc;
		if (myNuc < middleLine) {
			for (SavingTransDv trans : dvList) {
				endResultList.add(trans);
				line++;
				if (line == middleLine) {
					for (int spacing = 0; spacing < space; spacing++) {
						endResultList.add(new SavingTransDv());
						line++;
					}
				}
				if (line >= maxLine)
					break;
			}
		} else {
			for (int spacing = 0; spacing < space; spacing++) {
				endResultList.add(new SavingTransDv());
				line++;
			}
			for (SavingTransDv trans : dvList) {
				endResultList.add(trans);
				line++;
				if (line >= maxLine)
					break;
			}
		}
		//
		return endResultList;
	}

}
