import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestSub {
	public static void main(String args[]){
		 try {
			Context ctx = new InitialContext();
			String name=(String) ctx.lookup("bfc/name");
			System.out.println(name);
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		 
	}

}
