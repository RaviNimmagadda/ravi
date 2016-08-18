/**
 * 
 */
package com.shashi.jms;

import java.util.Properties;

import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSSubscriber {

	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.enterprise.naming.SerialInitContextFactory");
		props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
		props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
		InitialContext ctx = new InitialContext(props);
		TopicConnectionFactory factory = (TopicConnectionFactory) ctx
				.lookup("jms/TopicConnFactory");
		TopicConnection conn = null;
		TopicSession session = null;
		try {

			conn = factory.createTopicConnection();
			conn.start();
			System.out.println("Got the connection");
			Topic dest = (Topic) ctx.lookup("jms/MyTopic");
			session = conn.createTopicSession(false,
					JMSContext.AUTO_ACKNOWLEDGE);

			TopicSubscriber subscriber = session.createSubscriber(dest);
			subscriber.setMessageListener(new TopicListener());

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
