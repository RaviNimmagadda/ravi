/**
 * 
 */
package com.shashi.jms;

import java.util.Properties;

import javax.jms.JMSContext;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JMSPublisher {

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
			Topic destination = (Topic) ctx.lookup("jms/MyTopic");
			session = conn.createTopicSession(false,
					JMSContext.AUTO_ACKNOWLEDGE);
			TopicPublisher publisher = session.createPublisher(destination);

			TextMessage message = session.createTextMessage();
			message.setText("Topic Message Hello Topic");

			publisher.publish(message);

			System.out.println("JMS Message sent to the topic");

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
