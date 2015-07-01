package com.leo.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.leo.dao.ISessionStatus;

public class SessionStatusClient {

	public void test() throws NamingException {
		Context ctx = getInitialContext();
		ISessionStatus status = (ISessionStatus)ctx.lookup("SessionStatus#com.leo.dao.ISessionStatus");
		status.addData();
	}
	public static void main(String[] args) {
		try {
			new SessionStatusClient().test();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private Context getInitialContext() {
		final String INIT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
		final String SERVER_URL = "t3://localhost:7001";
		Context ctx = null;
		try {
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY, INIT_FACTORY);
			props.put(Context.PROVIDER_URL, SERVER_URL);
			ctx = new InitialContext(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctx;
	}
}
