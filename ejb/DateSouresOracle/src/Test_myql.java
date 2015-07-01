import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class Test_myql {
	public static void main(String args[]){
		try{          
			Context initCtx=new InitialContext();   
			DataSource ds=(DataSource)initCtx.lookup("Mysql");          
			Connection conn=ds.getConnection();          
			Statement stmt=conn.createStatement();        //查询表test数据条数         
			ResultSet rs=stmt.executeQuery("select  name,salary,age from emp");   
			while(rs.next()){
				System.out.print(rs.getString("name")+"   ");  
				System.out.print(rs.getString("salary")+"   ");  
				System.out.println(rs.getString("age"));  
			}       
			rs.close();          
			stmt.close();      
			}catch(Exception e){          
				e.printStackTrace();
		}
	}
}
