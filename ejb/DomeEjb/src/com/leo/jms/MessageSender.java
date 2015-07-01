package com.leo.jms;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class MessageSender {
	public void sendMessage() throws NamingException, JMSException {
		final String CONNECT_FACTORY_JNDI = "weblogic.jms.ConnectionFactory";
		Context ctx = getInitialContext();
		ConnectionFactory connFactory = (ConnectionFactory)ctx.lookup(CONNECT_FACTORY_JNDI);
		Destination desc = (Destination)ctx.lookup("JmsQueue");
		Connection conn = connFactory.createConnection();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer sender = session.createProducer(desc);
		sender.setDeliveryMode(DeliveryMode.PERSISTENT);
		sender.setTimeToLive(20000);
		TextMessage msg = session.createTextMessage();
		msg.setText("hello");
		sender.send(msg);
		session.close();
		conn.close();
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
	
	
	public static void main(String args[]) {
		try {
			new MessageSender().sendMessage();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
