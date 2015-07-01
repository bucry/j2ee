import javax.naming.Context;
import javax.naming.InitialContext;

import com.oracle.model.IHelloWorld;
import com.oracle.sStateless.Session01Remote;

public class ejbClient {
	public static void main(String[] args) throws Exception {
		//测试无状态bean
		//Properties props = new Properties();
        //props.setProperty( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
       // props.setProperty( Context.PROVIDER_URL, "t3://localhost:7001");    
       // Context ctx = new InitialContext(props);
       Context ctx = new InitialContext();//使用配置文件
		/*IHelloWorld a = (IHelloWorld) ctx.lookup("HelloWorldBean#"
				+ IHelloWorld.class.getName());
		String s = a.sayHello("nemesis ");
		System.out.println(s);
		System.out.println(IHelloWorld.class.getName());*/
		
		Session01Remote ssession=(Session01Remote) ctx.lookup("Session01#"
				+ Session01Remote.class.getName());
		ssession.remote();
		
		
	}
}