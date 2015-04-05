package org.simbiosis.cli.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.message.BasicNameValuePair;

public class MicrobankClient {
	private String url = "";
	private String key = "akunpus_akunpus";
	private HttpClient httpClient;

	public MicrobankClient(String url) {
		this.url = url;
		PoolingClientConnectionManager conMan = new PoolingClientConnectionManager(
				SchemeRegistryFactory.createDefault());
		conMan.setMaxTotal(200);
		conMan.setDefaultMaxPerRoute(200);
		httpClient = new DefaultHttpClient(conMan);
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String sendRawData(String action, String id, String date, String data) {
		return sendRawData(action, key, id, date, data);
	}

	public String sendRawData(String action, String data) {
		return sendRawData(action, key, "", "", data);
	}

	public String sendRawData(String action, String date, String data) {
		return sendRawData(action, key, "", date, data);
	}

	private String sendRawData(String action, String key, String id,
			String date, String data) {
		HttpPost post = new HttpPost(url + action);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("key", key));
			nameValuePairs.add(new BasicNameValuePair("id", id));
			nameValuePairs.add(new BasicNameValuePair("date", date));
			nameValuePairs.add(new BasicNameValuePair("data", data));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			HttpResponse response = httpClient.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			String result = "";
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return "";
	}

}
