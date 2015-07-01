package com.leo.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.TextMessage;

@MessageDriven(activationConfig={
		@ActivationConfigProperty(propertyName="destinationType", propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="acknowledgeMode", propertyValue="Auto-acknowledge"),
		@ActivationConfigProperty(propertyName="destination", propertyValue="JmsQueue")
}, messageListenerInterface=javax.jms.MessageListener.class, mappedName="JmsQueue")
public class SimpleMDB {
	public void onMessage(Message msg) {
		try {
			if (msg instanceof TextMessage) {
				TextMessage txt = (TextMessage)msg;
				String content = txt.getText();
				System.out.println("MDB收到消息：" + content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
