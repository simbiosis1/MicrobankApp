package org.simbiosis.cli.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.joda.time.DateTime;

public class CliBase {

	private String host = "localhost";
	private int port = 8080;
	private String userName = "testadmin";
	private String password = "testadmin";
	private int count = 1;
	private int index = 0;
	private String[] arrUserName;
	private String[] arrPassword;
	private boolean isOpen = false;

	private MicrobankCoreClient coreClient = null;
	private MicrobankReportClient reportClient = null;

	private String strBulkBegin;
	private String strBulkEnd;

	private boolean bulkSaving;
	private boolean bulkDeposit;
	private boolean bulkLoan;

	private String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04",
			"31-05", "30-06", "31-07", "31-08", "30-09", "31-10", "30-11",
			"31-12" };

	public CliBase() {
		String users = "testadmin;gb";
		String passwords = "testadmin;gb";
		arrUserName = users.split(";");
		arrPassword = passwords.split(";");
		count = arrUserName.length;
	}

	public CliBase(String properties) {
		Properties prop = new Properties();

		try {
			// load a properties file
			prop.load(new FileInputStream(properties));
			host = prop.getProperty("host");
			String strPort = prop.getProperty("port");
			port = Integer.parseInt(strPort);
			String users = prop.getProperty("users");
			String passwords = prop.getProperty("passwords");
			arrUserName = users.split(";");
			arrPassword = passwords.split(";");
			count = arrUserName.length;
			strBulkBegin = prop.getProperty("bulkBegin");
			strBulkEnd = prop.getProperty("bulkEnd");
			bulkSaving = prop.getProperty("bulkSaving").equalsIgnoreCase("1");
			bulkDeposit = prop.getProperty("bulkDeposit").equalsIgnoreCase("1");
			bulkLoan = prop.getProperty("bulkLoan").equalsIgnoreCase("1");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public boolean next() {
		if (isOpen) {
			close();
		}
		if (index < count) {
			open();
		}
		index++;
		return index <= count;
	}

	public void open() {
		userName = arrUserName[index];
		password = arrPassword[index];
		//
		coreClient = new MicrobankCoreClient(host, port);
		coreClient.login(userName, password);
		reportClient = new MicrobankReportClient(host, port);
		reportClient.setKey(coreClient.getKey());
		//
		isOpen = true;
	}

	public void close() {
		coreClient.logout();
	}

	public MicrobankCoreClient getCoreClient() {
		return coreClient;
	}

	public MicrobankReportClient getReportClient() {
		return reportClient;
	}

	public String getEndMonths(int month) {
		return endMonths[month];
	}

	public DateTime getEndMonths(DateTime dateTime) {
		return dateTime.dayOfMonth().withMaximumValue();
	}

	public String getStrBulkBegin() {
		return strBulkBegin;
	}

	public String getStrBulkEnd() {
		return strBulkEnd;
	}

	public boolean isBulkSaving() {
		return bulkSaving;
	}

	public boolean isBulkDeposit() {
		return bulkDeposit;
	}

	public boolean isBulkLoan() {
		return bulkLoan;
	}

	public String getHost() {
		return host;
	}
}
