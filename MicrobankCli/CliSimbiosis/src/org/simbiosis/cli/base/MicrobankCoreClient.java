package org.simbiosis.cli.base;

public class MicrobankCoreClient extends MicrobankClient {

	public MicrobankCoreClient(String host, int port) {
		super("http://" + host + ":" + port + "/MicrobankApiCore/");
	}

	public void login(String user, String password) {
		setKey(sendRawData("loginSystem", user + ";" + password));
	}

	public void logout() {
		sendRawData("logoutSystem", getKey());
	}

}
