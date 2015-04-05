package org.simbiosis.ui.bprs.dashboard.server;

import javax.ejb.EJB;

import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.ui.bprs.dashboard.client.rpc.ReportSrv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ReportSrvImpl extends RemoteServiceServlet implements ReportSrv {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystemBp;

	public ReportSrvImpl() {
	}

}
