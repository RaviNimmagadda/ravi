/**
 * 
 */
package com.shashi.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

public class QueueListener implements MessageListener {

	public void onMessage(Message message) {
		System.out.println(message);
	}

}
