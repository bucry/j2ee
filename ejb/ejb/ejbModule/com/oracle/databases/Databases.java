package com.oracle.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Stateless(mappedName="Cmt")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
/**
 * 容器管理事务
 * 修改人:bfc
 * @author Administrator
 *
 */
public class Databases {
	@Resource
	private SessionContext sessCtx;
	public void database(){
		Properties props = new Properties();
        props.setProperty( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        props.setProperty( Context.PROVIDER_URL, "t3://localhost:7001");  
		
		try{
			Context initCtx=new InitialContext(props);   
			DataSource ds=(DataSource)initCtx.lookup("Oracle");          
			Connection conn=ds.getConnection();          
			Statement stmt=conn.createStatement();  
			stmt.executeUpdate("update  emp set name='nem',salary='bb' where id=2");
			stmt.close(); 
		}catch(SQLException e){
			e.printStackTrace();
			sessCtx.setRollbackOnly();
		}catch(NamingException ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new Databases().database();
	}
}
