package com.oracle.mdb;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleSendMessage {
	public void sendMessage() throws JMSException, NamingException{
		//定义Weblogic默认连接工厂的JNDI
		final String CONNECTION_FACTORY_JNDI = "ConnectionFactory" ; 
		//获取JNDI服务所需的Cotext
		Context ctx = getInitialContext();
		//通过JNDI查找获取连接工厂
		ConnectionFactory connFactory = (ConnectionFactory)ctx.lookup(CONNECTION_FACTORY_JNDI);
		//连接工厂创建连接
		Connection conn = connFactory.createConnection();
		//通过JNDI查找获取消息目的
		Destination dest = (Destination)ctx.lookup("MessageQueue");
		//JMS连接创建JMS会话
		Session session = conn.createSession(false /*不是事物性会话*/
				, Session.AUTO_ACKNOWLEDGE);
		//JMS会话创建消息生产者
		MessageProducer sender = session.createProducer(dest);
		//设置消息生产者生产出来的消息的传递模式,有效时间
		sender.setDeliveryMode(20000);
		//通过JMS会话创建一个文本消息
		TextMessage msg = session.createTextMessage();
		//设置消息内容
		msg.setText("Hello");
		//发送消息
		sender.send(msg);
		//再次发送
		msg.setText("Wellocome to JMS");
		//关闭资源
		session.close();
		conn.close();
	}
	
	private Context getInitialContext(){
		Context ctx = null;
		Properties props = new Properties();
        props.setProperty( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        props.setProperty( Context.PROVIDER_URL, "t3://localhost:7001");    
        try {
			 ctx = new InitialContext(props);
		} catch (NamingException e) {
			e.printStackTrace();
		}
        
        return ctx;
	}
	
	
	public static void main(String args[]){
		SimpleSendMessage mp = new SimpleSendMessage();
		try {
			mp.sendMessage();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
