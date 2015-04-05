package org.simbiosis.bp.savingMsg;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;


@Stateless
@LocalBean
public class SavingTransMessaging {
	@Resource(mappedName = "java:/ConnectionFactory")
	ConnectionFactory connectionFactory;
	@Resource(mappedName = "java:jboss/queue/transSavingIn")
	Queue transSavingIn;

	/*
	 * Function : sendSavingTrans Description : Mengirim savingtransaction
	 * melalui messaging
	 */
	public void sendSavingTrans(SavingTransMsg savingTransactionMsg) {
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(transSavingIn);
			ObjectMessage message = session.createObjectMessage();
			message.setObject(savingTransactionMsg);
			connection.start();
			producer.send(message);
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}


}
