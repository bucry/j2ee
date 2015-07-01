package com.oracle.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * 消息驱动bean
 * @author Administrator
 * 消息驱动Bean接收者
 * 间接实现MesageListener接口
 *
 */
//指定MDB所监听消息目的的类型
@MessageDriven(activationConfig=
{
		@ActivationConfigProperty(propertyName="destinationType"
				,propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="acknowledgeMode"
				,propertyValue="Auto-acknowledge"),
				//指定MDB所监听的消息目的的JNDI绑定名
		@ActivationConfigProperty(propertyName="destination"
				,propertyValue="MessageQueue")
		//让MDB的Bean实现类间接的实现MessageListener接口
},messageListenerInterface=javax.jms.MessageListener.class
		//指定MDB所监听的消息目的的JNDI名
		,mappedName="MessageQueue"
		)
public class Consumer {
	//实现onMessage（）方法--当JMS消息被送达消息目的时，该方法被触发
	public void onMessage(Message msg){
		try{
			if(msg instanceof TextMessage)
			{
				TextMessage txt=(TextMessage)msg;
				String content = txt.getText();
				System.out.println("JMS 消息为:"+content);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
