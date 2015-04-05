package org.simbiosis.migrator.lib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FirebirdDb {

	String host = "";
	String database = "/home/shares/database/SIMbprs.fdb";
	String user = "sysdba";
	String password = "masterkey";
	String driverName = "org.firebirdsql.jdbc.FBDriver";

	Connection c = null;

	public FirebirdDb(String host) {
		this.host = host;
		try {
			Class.forName(driverName);
		} catch (java.lang.ClassNotFoundException e) {
			// A call to Class.forName() forces us to consider this exception
			// :-)...
			System.out
					.println("Firebird JCA-JDBC driver not found in class path");
			System.out.println(e.getMessage());
		}
	}

	public void connect() {
		try {
			String url = "jdbc:firebirdsql:" + host + "/3050:" + database;
			c = java.sql.DriverManager.getConnection(url, user, password);
			System.out.println("Connection established.");
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out
					.println("Unable to establish a connection through the driver manager.");
		}
	}

	public void close() {
		try {
			c.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to clode a connection.");
		}
	}

	public ResultSet executeQuery(String query) {
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			System.out.println("Unable to clode a connection.");
		}
		return null;
	}
}
