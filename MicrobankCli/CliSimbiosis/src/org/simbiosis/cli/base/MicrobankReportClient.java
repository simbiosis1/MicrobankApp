package org.simbiosis.cli.base;

public class MicrobankReportClient extends MicrobankClient {

	public MicrobankReportClient(String host, int port) {
		super("http://" + host + ":" + port + "/MicrobankApiReport/");
	}

}
