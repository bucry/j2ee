package com.leo.session;

import java.sql.Connection;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.leo.interceptor.ApplicationInterceptor;

@Stateful(mappedName = "SessionStatus")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(ApplicationInterceptor.class)
public class SessionStatus implements ISessionStatus {
	
	@Resource
	private SessionContext sessCtx;
	private DataSource ds;
	
	@Override
	public void addData() {
		try {
			Connection conn = ds.getConnection();
			Statement stat = conn.createStatement();
			try {
				stat.executeUpdate(" insert into test(id,namr)values(3,'gh')");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			sessCtx.setRollbackOnly();
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void createEjbBean() {
		try {
			Context cxt = new InitialContext();
			ds = (DataSource)cxt.lookup("OracleDatasource");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void beanDestory() {
		
	}
	
	@PostActivate
	public void postActivity() {
		
	}
	
	@PrePassivate
	public void passivate() {
		
	}

}
