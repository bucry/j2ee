package com.oracle.mdb.remote;

import javax.ejb.Remote;

/**
 * 消息驱动bean接口
 * @author Administrator
 *
 */
@Remote
public interface Student {
	void add(String name,String gender,int age)throws Exception;
}
