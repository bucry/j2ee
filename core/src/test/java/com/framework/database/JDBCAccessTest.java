package com.framework.database;

import javax.inject.Inject;

import org.junit.Test;

import com.framework.SpringTest;
import com.framework.core.database.HibernateAccess;
import com.framework.core.database.JDBCAccess;

/**
 * @author neo
 */
public class JDBCAccessTest extends SpringTest {
    @Inject
    JDBCAccess jdbcAccess;
    
    @Inject
    protected HibernateAccess hibernateAccess;

    @Test
    public void start() {
    	try {
    		System.out.println(jdbcAccess == null);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
