import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class CreateSub {
	public static void main(String args[]){
		 try {
			Context ctx = new InitialContext();
			Context cxt1=ctx.createSubcontext("bfc/bfc");
			cxt1.bind("name1", new String("bfc1"));
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		 
	}

}
