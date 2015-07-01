package com.leo.jms;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AsyncConnsumer implements MessageListener {
	
	public AsyncConnsumer() throws NamingException, JMSException, InterruptedException {
		final String CONNECTION_FACTORY_JNDI = "weblogic.jms.ConnectionFactory";
		Context ctx = getInitialContext();
		ConnectionFactory connFactory = (ConnectionFactory) ctx
				.lookup(CONNECTION_FACTORY_JNDI);
		Destination desc = (Destination) ctx.lookup("JmsQueue");
		Connection conn = connFactory.createConnection();
		conn.start();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer receiver = session.createConsumer(desc);
		receiver.setMessageListener(this);
		Thread.sleep(20000);
		session.close();
		conn.close();
	}

	public static void main(String[] args) {
		try {
			new AsyncConnsumer();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
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

	@Override
	public void onMessage(Message arg0) {
		TextMessage msg = (TextMessage)arg0;
		System.out.println(msg);
		try {
			System.out.println("收到异步消息:" + msg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
