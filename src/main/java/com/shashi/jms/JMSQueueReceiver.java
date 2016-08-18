/**
 * 
 */
package com.shashi.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSQueueReceiver {

	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPrt", "3700");
		InitialContext ctx = new InitialContext(props);
		QueueConnectionFactory factory = (QueueConnectionFactory) ctx
				.lookup("jms/QueueConnFactory");
		Connection conn = null;
		Session session = null;
		try {

			conn = factory.createConnection();
			conn.start();
			System.out.println("connection is established");
			Queue queue = (Queue) ctx.lookup("jms/MyQueue");
			session = conn.createSession(false, JMSContext.AUTO_ACKNOWLEDGE);
			MessageConsumer consumer = session.createConsumer(queue);
			Message message = consumer.receive();
			System.out.println(message);
			System.out.println("JMS Message sent to the queue");
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
