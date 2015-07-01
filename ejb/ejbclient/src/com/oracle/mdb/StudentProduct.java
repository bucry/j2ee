package com.oracle.mdb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class StudentProduct {
	public void sendMessage()throws NamingException ,JMSException 
	{
		//定义Weblogic默认连接工厂的JNDI
				final String CONNECTION_FACTORY_JNDI
					  ="ConnectionFactory";
				//获取JNDI服务所需的Context
				Context ctx=new InitialContext();
				//通过jndi查找获取连接的工厂
				ConnectionFactory connFactory = (ConnectionFactory)
						ctx.lookup(CONNECTION_FACTORY_JNDI);
				//通过jndi查找获取消息目的
				Destination dest = (Destination)ctx.lookup("MessageQueue");
				//连接工厂
				Connection conn = connFactory.createConnection();
				//JMS连接创建JMS会话
				Session session = conn.createSession(false/*不是事物性会话*/, Session.AUTO_ACKNOWLEDGE);
				//JMS会话创建消息生产者
				MessageProducer sender = session.createProducer(dest);
				//设置消息生产者生产出来的消息传递模式，有效时间
				sender.setDeliveryMode(DeliveryMode.PERSISTENT);
				sender.setTimeToLive(200000);
				
				//通过JMS会话创建一个文本消息
				MapMessage msg = session.createMapMessage();
				//设置消息内容
				msg.setString("name","孙悟空");
				msg.setString("gender","男");
				msg.setInt("age", 500);
				sender.send(msg);
				session.close();
				conn.close();
			}
			//省略获取命名服务的Context对象的工具方法
			@SuppressWarnings("unused")
			private Context getInitialContext()
			{
				return null;
				
			}
			public  static void main(String args[]){
				try {
					new Product().sendMessage();
				} catch (NamingException e) {
					e.printStackTrace();
				} catch (JMSException e) {
					e.printStackTrace();
				}
	}

}
