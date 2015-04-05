package org.simbiosis.cli.nominatif.lib;

import java.util.ArrayList;
import java.util.List;

import org.simbiosis.cli.base.MicrobankCoreClient;
import org.simbiosis.cli.base.MicrobankReportClient;

public class SavingNominatif {

	String strDate;
	MicrobankCoreClient jsonMain = null;
	MicrobankReportClient jsonReport = null;
	int queueNumber = 0;
	int runningWriter = 20;
	int waitingTime = 2500;

	private synchronized void decrementRunningWriter() {
		runningWriter--;
	}

	public class WriterThread implements Runnable {

		List<String> ids = new ArrayList<String>();
		Integer tag = -1;
		String strThreadDate;
		MicrobankReportClient jsonReport = null;

		public WriterThread(MicrobankReportClient jsonReport,
				String strThreadDate, int i, List<String> ids) {
			this.ids.addAll(ids);
			this.tag = i;
			this.strThreadDate = strThreadDate;
			this.jsonReport = jsonReport;
		}

		public void addId(String id) {
			ids.add(id);
		}

		@Override
		public void run() {
			for (String sid : ids) {
				jsonReport.sendRawData("createDailySaving", strThreadDate, sid);
				// System.out.print(tag + "*" + strDate);
				// System.out.print(tag);
			}
			decrementRunningWriter();
		}
	}

	public SavingNominatif(MicrobankCoreClient jsonMain,
			MicrobankReportClient jsonReport, String strDate) {
		super();
		this.jsonMain = jsonMain;
		this.jsonReport = jsonReport;
		this.strDate = strDate;
	}

	public void execute() {
		System.out.println("Delete saving nominatif...");
		jsonReport.sendRawData("deleteDailySaving", strDate);
		System.out.println("Generate saving nominatif...");
		String data = jsonMain.sendRawData("listSavingId", strDate);
		if (!data.isEmpty()) {
			String[] sids = data.split(";");
			writeReportPar(sids);
		}
		// System.out.println();
	}

	private void writeReportPar(String[] sids) {
		if (sids.length >= runningWriter) {
			List<List<String>> idQueues = new ArrayList<List<String>>();
			for (int i = 0; i < runningWriter; i++) {
				idQueues.add(new ArrayList<String>());
			}
			for (String sid : sids) {
				idQueues.get(queueNumber++).add(sid);
				if (queueNumber > runningWriter - 1) {
					queueNumber = 0;
				}
			}
			//
			List<Thread> wtList = new ArrayList<Thread>();
			for (int i = 0; i < runningWriter; i++) {
				wtList.add(new Thread(new WriterThread(jsonReport, strDate, i,
						idQueues.get(i))));
			}
			for (Thread wt : wtList) {
				wt.start();
			}
			while (runningWriter > 0) {
				try {
					Thread.sleep(waitingTime);
					// System.out.print(".");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			for (String sid : sids) {
				jsonReport.sendRawData("createDailySaving", strDate, sid);
				// System.out.println(".");
			}
		}
	}

}
