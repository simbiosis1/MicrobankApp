package org.simbiosis.bp.micbank;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


import org.simbiosis.bp.dto.AnswerTransactionMsg;
import org.simbiosis.microbank.ITeller;

/**
 * Message-Driven Bean implementation class for: TellerTrans
 * 
 */
@MessageDriven(name = "TransTellerMdb", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/queue/transTellerIn"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class TellerTrans implements MessageListener {

	@EJB(lookup = "java:global/MicrobankEar/MicrobankEjb/TellerImpl")
	ITeller teller;

	/**
	 * Default constructor.
	 */
	public TellerTrans() {
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			AnswerTransactionMsg answerTransactionMsg = (AnswerTransactionMsg) objectMessage
					.getObject();
			teller.saveTellerTransactionRef(
					answerTransactionMsg.getIdDestinantion(),
					answerTransactionMsg.getIdSource());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
