package org.simbiosis.bp.savingMsg;

import java.io.Serializable;

import org.simbiosis.microbank.SavingTransactionDto;

public class SavingTransMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 46588402591518085L;
	long idSource;
	String queueName;
	SavingTransactionDto savingTransactionDto;

	public long getIdSource() {
		return idSource;
	}

	public void setIdSource(long idSource) {
		this.idSource = idSource;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public SavingTransactionDto getSavingTransactionDto() {
		return savingTransactionDto;
	}

	public void setSavingTransactionDto(
			SavingTransactionDto savingTransactionDto) {
		this.savingTransactionDto = savingTransactionDto;
	}
}
