package com.oracle.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;



@Stateless(mappedName="Bmt")
@TransactionManagement(TransactionManagementType.BEAN)
public class BeanTransaction implements Bmt{

	private DataSource ds = null;
	@Resource
	private UserTransaction tx;
	public BeanTransaction() throws NamingException{
		Context ctx = new InitialContext();
		ds=(DataSource)ctx.lookup("Oracle"); 
	}
	
	public void insert(){
		try{
			tx.begin();
			Connection conn=ds.getConnection();          
			Statement stmt=conn.createStatement();  
			stmt.executeUpdate("update  emp set name='nem',salary='bb' where id=2");
			stmt.close(); 
		}catch(SQLException e){
			e.printStackTrace();
			try {
				tx.setRollbackOnly();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SystemException e1) {
				e1.printStackTrace();
			}
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		try {
			new BeanTransaction().insert();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}
