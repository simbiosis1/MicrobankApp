package org.simbiosis.ui.bprs.security.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.system.AuthDto;
import org.simbiosis.ui.bprs.security.client.rpc.SecService;
import org.simbiosis.ui.bprs.security.shared.AuthDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SecServiceImpl extends RemoteServiceServlet implements SecService {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp system;

	AuthDv createAuthDvFromDto(AuthDto dto) {
		AuthDv dv = new AuthDv();
		dv.setId(dto.getId());
		dv.setDescription(dto.getDescription());
		dv.setUser(dto.getUser());
		dv.setStrUser(dto.getUserName());
		dv.setStrBranch(dto.getBranchName());
		return dv;
	}

	@Override
	public List<AuthDv> listAuth(String key) {
		List<AuthDv> result = new ArrayList<AuthDv>();
		List<AuthDto> auths = system.listAuth(key);
		int nr = 1;
		for (AuthDto auth : auths) {
			AuthDv dv = createAuthDvFromDto(auth);
			dv.setNr(nr++);
			result.add(dv);
		}
		return result;
	}

	@Override
	public void authorize(String key, Long id) {
		AuthDto dto = new AuthDto();
		dto.setId(id);
		system.authorize(key, dto);
	}

}
