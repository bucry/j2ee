package com.leo.topic;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SyncConnsumer {

	public void reciveMessage() throws NamingException, JMSException {
		final String CONNECTION_FACTORY_JNDI = "weblogic.jms.ConnectionFactory";
		Context ctx = getInitialContext();
		ConnectionFactory connFactory = (ConnectionFactory)ctx.lookup(CONNECTION_FACTORY_JNDI);
		Topic desc = (Topic)ctx.lookup("JmsTopic");
		Connection conn = connFactory.createConnection();
		conn.setClientID("bucry.com");
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer receiver = session.createDurableSubscriber(desc, "bucry.com");
		TextMessage msg = (TextMessage)receiver.receiveNoWait();
		System.out.println(msg);
		if (msg != null) {
			System.out.println("收到同步消息:" + msg.getText());
		}
		session.close();
		conn.close();
	}
	
	public static void main(String[] args) {
		try {
			new SyncConnsumer().reciveMessage();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private Context getInitialContext() {
		final String INIT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
		final String SERVER_URL = "t3://localhost:7001";
		Context ctx = null;
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, INIT_FACTORY);
			props.put(Context.PROVIDER_URL, SERVER_URL);
			ctx = new InitialContext(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctx;
	}

}
