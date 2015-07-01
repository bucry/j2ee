package com.leo.db;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class NameServiceTest {

	/**
	 * @param args
	 * @throws NamingException 
	 */
	public static void main(String[] args) throws NamingException {
		final String fileName = "Battle.java";
		final String dirName = "jndi";
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
		env.put(Context.PROVIDER_URL, "file:/E:");
		Context ctx = new InitialContext(env);
		Object file = ctx.lookup(fileName);
		System.out.println(file.getClass());
	}

}
