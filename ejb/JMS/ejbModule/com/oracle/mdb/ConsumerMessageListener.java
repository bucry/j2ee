package com.oracle.mdb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息驱动bean
 * @author Administrator
 * 消息驱动Bean接收者
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
}
		//指定MDB所监听的消息目的的JNDI名
		,mappedName="MessageQueue"
		)
//直接实现MessageListener接口
public class ConsumerMessageListener implements MessageListener{
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
	
	@PostConstruct
	public void myInit(){
		System.out.println("--初始化方法--");
	}
	
	@PreDestroy
	//可以在该方法中关系在@@PostConstruct中申请的系统资源
	public void myDestory(){
		System.out.println("--销毁之前的方法--");
	}

}
