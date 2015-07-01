import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TestDel {
	public static void main(String args[]){
		 try {
			Context ctx = new InitialContext();
			ctx.destroySubcontext("bfc");
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		 
	}

}
