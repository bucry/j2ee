import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TetsUnb {
	public static void main(String args[]){
		 try {
			Context ctx = new InitialContext();
			ctx.unbind("bfc/bfc/name1");
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		 
	}

}
