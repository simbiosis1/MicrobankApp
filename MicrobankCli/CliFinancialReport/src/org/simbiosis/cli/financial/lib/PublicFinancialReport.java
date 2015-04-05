package org.simbiosis.cli.financial.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.bp.gl.model.PublicReportDto;
import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.cli.base.MicrobankReportClient;
import org.simbiosis.gl.model.FinancialRpt;

public class PublicFinancialReport {

	Date date = null;
	String strDate = "";

	MicrobankCoreClient coreClient;
	MicrobankReportClient reportClient;

	Map<Long, LapKeuDv> level1 = new HashMap<Long, LapKeuDv>();
	Map<Long, LapKeuDv> level2 = new HashMap<Long, LapKeuDv>();
	Map<Long, LapKeuDv> level3 = new HashMap<Long, LapKeuDv>();

	List<FinancialRpt> finReportList = new ArrayList<FinancialRpt>();

	List<Long> branches = new ArrayList<Long>();
	List<PublicReportDto> coaRef = new ArrayList<PublicReportDto>();
	Map<String, PublicReportDto> publicMap = new HashMap<String, PublicReportDto>();
	Long branch = 0L;

	public PublicFinancialReport(String strDate,
			MicrobankCoreClient coreClient, MicrobankReportClient reportClient) {
		this.coreClient = coreClient;
		this.reportClient = reportClient;
		//
		this.strDate = strDate;
		DateTimeFormatter sdf = DateTimeFormat.forPattern("dd-MM-yyyy");
		date = sdf.parseDateTime(strDate).toDate();
		//
		createBranches();
		loadFinancialReportRef();
	}

	private void savePublicReport() {
		List<PublicReportDto> list = new ArrayList<PublicReportDto>();
		list.addAll(publicMap.values());
		// Sortir
		Collections.sort(list, new Comparator<PublicReportDto>() {

			@Override
			public int compare(PublicReportDto o1, PublicReportDto o2) {
				String d1 = o1.getGroup() + "-" + o1.getCode();
				String d2 = o2.getGroup() + "-" + o2.getCode();
				return d1.compareTo(d2);
			}
		});
		// Koreksi laba rugi
		Iterator<PublicReportDto> iter = list.iterator();
		while (iter.hasNext()) {
			PublicReportDto dto = iter.next();
			if (dto.getGroup() == 2) {
				if (((dto.getCode().equalsIgnoreCase("461") || dto.getCode()
						.equalsIgnoreCase("465")) && dto.getValue() < 0)
						|| ((dto.getCode().equalsIgnoreCase("462") || dto
								.getCode().equalsIgnoreCase("466")) && dto
								.getValue() > 0)) {
					iter.remove();
				}
			}
		}
		//
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, list);
			// System.out.println(sw.toString());
			reportClient
					.sendRawData("savePublicFinancialReport", sw.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		// Hapus yang lama
		//
		reportClient.sendRawData("deletePublicFinancialReport", strDate);
		//
		for (Long currentBranch : branches) {
			branch = currentBranch;
			System.out.println("Cabang : " + branch);
			//
			listFinReport(branch);
			createInternalList();
			//
			createValue(branch);
			savePublicReport();
		}
	}

	void createBranches() {
		branches.clear();
		branches.add(0L);
		String strBranchData = coreClient.sendRawData("listBranchId", "");
		System.out.println(strBranchData);
		String[] strBranches = strBranchData.split(";");
		for (String strBranch : strBranches) {
			branches.add(Long.parseLong(strBranch));
		}
	}

	private void loadFinancialReportRef() {
		coaRef.clear();
		ObjectMapper mapper = new ObjectMapper();
		try {
			// Schema syariah;aktiva
			String data = coreClient.sendRawData("listFinancialReportRef",
					"1;1");
			List<PublicReportDto> objects = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
			// Schema syariah;pasiva
			data = coreClient.sendRawData("listFinancialReportRef", "1;2");
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
			// Schema syariah;pendapatan
			data = coreClient.sendRawData("listFinancialReportRef", "1;3");
			System.out.println(data);
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
			// Schema syariah;bagi hasil
			data = coreClient.sendRawData("listFinancialReportRef", "1;4");
			System.out.println(data);
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
			// Schema syariah;beban
			data = coreClient.sendRawData("listFinancialReportRef", "1;5");
			System.out.println(data);
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
			// Schema syariah;pendapatan non ops
			data = coreClient.sendRawData("listFinancialReportRef", "1;6");
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
			// Schema syariah;beban non ops
			data = coreClient.sendRawData("listFinancialReportRef", "1;7");
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, PublicReportDto.class));
			for (PublicReportDto rs : objects) {
				coaRef.add(rs);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createValue(long branch) {
		publicMap.clear();
		for (PublicReportDto rpt : coaRef) {
			double value = getValue(rpt.getCoa(), rpt.getCoaLevel());
			PublicReportDto pubRpt = publicMap.get(rpt.getGroup() + "-"
					+ rpt.getCode());
			if (pubRpt == null) {
				pubRpt = new PublicReportDto();
				pubRpt.setDate(date);
				pubRpt.setBranch(branch);
				pubRpt.setGroup(rpt.getGroup());
				pubRpt.setCode(rpt.getCode());
				pubRpt.setDescription(rpt.getDescription());
				pubRpt.setValue(value);
			} else {
				pubRpt.setValue(pubRpt.getValue() + value);
			}
			publicMap.put(rpt.getGroup() + "-" + rpt.getCode(), pubRpt);
		}
	}

	private double getValue(long coa, int level) {
		LapKeuDv lap = null;
		switch (level) {
		case 1:
			lap = level1.get(coa);
			break;
		case 2:
			lap = level2.get(coa);
			break;
		case 3:
			lap = level3.get(coa);
			break;
		}
		return lap != null ? lap.getOriValue() : 0;
	}

	private void createInternalList() {
		//
		level1.clear();
		level2.clear();
		level3.clear();
		//
		for (FinancialRpt dto : finReportList) {
			//
			LapKeuDv lap1 = level1.get(dto.getCoaGrandParent());
			if (lap1 == null) {
				lap1 = new LapKeuDv();
				// if (dto.getGroup() == 1 || dto.getGroup() == 2) {
				// lap1.setType(1);
				// } else {
				// lap1.setType(2);
				// }
				lap1.setCoa(dto.getCoaGrandParent());
				lap1.setStrCoa(dto.getCoaGrandParentCode());
				lap1.setOriValue(dto.getEndValue());
			} else {
				lap1.setOriValue(lap1.getOriValue() + dto.getEndValue());
			}
			level1.put(dto.getCoaGrandParent(), lap1);
			//
			LapKeuDv lap2 = level2.get(dto.getCoaParent());
			if (lap2 == null) {
				lap2 = new LapKeuDv();
				// if (dto.getGroup() == 1 || dto.getGroup() == 2) {
				// lap2.setType(1);
				// } else {
				// lap2.setType(2);
				// }
				lap2.setCoa(dto.getCoaParent());
				lap2.setStrCoa(dto.getCoaParentCode());
				lap2.setOriValue(dto.getEndValue());
			} else {
				lap2.setOriValue(lap2.getOriValue() + dto.getEndValue());
			}
			level2.put(dto.getCoaParent(), lap2);
			//
			LapKeuDv lap3 = level3.get(dto.getId().getCoa());
			if (lap3 == null) {
				lap3 = new LapKeuDv();
				// if (dto.getGroup() == 1 || dto.getGroup() == 2) {
				// lap3.setType(1);
				// } else {
				// lap3.setType(2);
				// }
				lap3.setCoa(dto.getId().getCoa());
				lap3.setStrCoa(dto.getCoaCode());
				lap3.setOriValue(dto.getEndValue());
			} else {
				lap3.setOriValue(lap3.getOriValue() + dto.getEndValue());
			}
			level3.put(dto.getId().getCoa(), lap3);
		}
	}

	private void listFinReport(Long branch) {
		String param = branch.toString() + ";" + strDate;
		String data = reportClient.sendRawData("listFinancialReport", param);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<FinancialRpt> result = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, FinancialRpt.class));
			finReportList.clear();
			finReportList.addAll(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
