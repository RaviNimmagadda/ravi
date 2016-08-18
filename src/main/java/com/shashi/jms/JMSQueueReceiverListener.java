/**
 * 
 */
package com.shashi.jms;

import java.util.Properties;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSQueueReceiverListener {

	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext ctx = new InitialContext(props);
		QueueConnectionFactory factory = (QueueConnectionFactory) ctx
				.lookup("jms/QueueConnFactory");
		QueueConnection conn = null;
		QueueSession session = null;
		try {

			conn = factory.createQueueConnection();
			conn.start();
			System.out.println("Got the connection");
			Queue queue = (Queue) ctx.lookup("jms/MyQueue");
			session = conn.createQueueSession(false,
					JMSContext.AUTO_ACKNOWLEDGE);
			QueueReceiver receiver = session.createReceiver(queue);
			receiver.setMessageListener(new QueueListener());

			while (true) {
				Thread.sleep(10000);
			}

			// System.out.println("JMS Message sent to the queue");
			// conn.stop();
		} finally {
			if (session != null) {
				session.close();
			}

			if (conn != null) {
				conn.close();
			}
		}

	}

}
