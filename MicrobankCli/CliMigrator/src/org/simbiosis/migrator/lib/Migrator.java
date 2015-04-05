package org.simbiosis.migrator.lib;

import java.sql.ResultSet;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simbiosis.cli.base.CliBase;

public abstract class Migrator extends CliBase {

	long branch = 3;
	DateTime beginDate = null;
	DateTime endDate = null;
	FirebirdDb db = null;
	DateTimeFormatter sdfSystem = DateTimeFormat.forPattern("dd-MM-yyyy");
	DateTimeFormatter sdfDb = DateTimeFormat.forPattern("dd.MM.yyyy");

	public Migrator() {
		super("cli.properties");
		//
		beginDate = sdfSystem.parseDateTime(getStrBulkBegin());
		endDate = sdfSystem.parseDateTime(getStrBulkEnd());
		//
		db = new FirebirdDb(getHost());
		db.connect();
	}

	public void execute() {
		open();
		process();
		close();
	}

	protected ResultSet execSQL(String sql) {
		return db.executeQuery(sql);
	}

	protected void closeDb() {
		db.close();
	}

	protected long getBranch() {
		return branch;
	}

	public Date getBeginDate() {
		return beginDate.toDate();
	}

	public Date getEndDate() {
		return endDate.toDate();
	}

	public String getStrDbBeginDate() {
		return sdfDb.print(beginDate);
	}

	public String getStrDbEndDate() {
		return sdfDb.print(endDate);
	}

	protected abstract void process();
}
