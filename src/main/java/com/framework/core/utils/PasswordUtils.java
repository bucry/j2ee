package com.framework.core.utils;


public class PasswordUtils {
	
	
	public  static String getPassHash(String password, String salt, int iteration){
		return calculatePasswordHash(password, salt, iteration);
	}
	
	private static String calculatePasswordHash(String password, String salt, int iteration) {
	      String passwordHash = password;
	      for (int i = 0; i < iteration; i++) {
	          passwordHash = DigestUtils.sha512(salt + passwordHash);
	      }
	      return passwordHash;
	  }

}
