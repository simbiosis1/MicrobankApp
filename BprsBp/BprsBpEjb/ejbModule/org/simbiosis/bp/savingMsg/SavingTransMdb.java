package org.simbiosis.bp.savingMsg;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;


import org.simbiosis.bp.dto.AnswerTransactionMsg;
import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.microbank.SavingTransactionDto;

/**
 * Message-Driven Bean implementation class for: SavingTrans
 * 
 */
@MessageDriven(name = "SavingTransMdb", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/queue/transSavingIn"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class SavingTransMdb implements MessageListener {

	@EJB(lookup = "java:module/SavingBp")
	ISavingBp saving;

	@Resource(mappedName = "java:/ConnectionFactory")
	ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:jboss/queue/transTellerIn")
	Queue transTellerIn;

	/**
	 * Default constructor.
	 */
	public SavingTransMdb() {
	}

	void sendTransTellerBack(AnswerTransactionMsg answerTransactionMsg){
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(transTellerIn);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(answerTransactionMsg);
			connection.start();
			producer.send(message);
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			ObjectMessage objectMessage = (ObjectMessage) message;
			SavingTransMsg savingTransactionMsg = (SavingTransMsg) objectMessage.getObject();
			SavingTransactionDto savingTransactionDto = savingTransactionMsg.getSavingTransactionDto();
			long id= saving.saveSavingTransaction(savingTransactionDto);
			//
			AnswerTransactionMsg answerTransactionMsg = new AnswerTransactionMsg();
			answerTransactionMsg.setIdDestinantion(savingTransactionMsg.getIdSource());
			answerTransactionMsg.setIdSource(id);
			sendTransTellerBack(answerTransactionMsg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
