/**
 * 
 */
package com.shashi.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSContext;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSQueueSender {

	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext ctx = new InitialContext(props);
		QueueConnectionFactory factory = (QueueConnectionFactory) ctx
				.lookup("jms/QueueConnFactory");
		Connection conn = null;
		Session session = null;
		try {

			conn = factory.createConnection();
			conn.start();
			System.out.println("Got the connection");
			Queue queue = (Queue) ctx.lookup("jms/MyQueue");
			session = conn.createSession(false, JMSContext.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage();
			message.setText("Hello welcome to JMS SP");

			producer.send(message);

			System.out.println("JMS Message sent to the queue 2");

			conn.stop();

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
