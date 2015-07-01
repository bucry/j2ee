package com.oracle.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.oracle.mdb.remote.Student;


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
public class StudentMesageListener implements MessageListener{

	@EJB(name="StudentBean")
	private Student student;
	public void onMessage(Message msg) {
		try{
			if(msg instanceof MapMessage)
			{
				MapMessage map = (MapMessage)msg;
				String name = map.getString("name");
				String gender = map.getString("gender");
				int age = map.getInt("age");
				student.add(name, gender, age);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
