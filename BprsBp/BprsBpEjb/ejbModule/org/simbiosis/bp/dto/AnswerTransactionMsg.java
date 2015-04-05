package org.simbiosis.bp.dto;

import java.io.Serializable;

public class AnswerTransactionMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7128064410887703511L;
	long idSource;
	long idDestinantion;

	public long getIdSource() {
		return idSource;
	}

	public void setIdSource(long idSource) {
		this.idSource = idSource;
	}

	public long getIdDestinantion() {
		return idDestinantion;
	}

	public void setIdDestinantion(long idDestinantion) {
		this.idDestinantion = idDestinantion;
	}

}
