package com.oracle.sStateless;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class Session01
 */
@Stateless(mappedName = "Session01")
@LocalBean
@Local(Session01Local.class)
@Remote(Session01Remote.class)
public class Session01 implements Session01Remote, Session01Local {

    /**
     * Default constructor. 
     */
    public Session01() {
    	
    }

	@Override
	public void local() {
		System.out.println("this is lcoal");
	}

	@Override
	public void remote() {
		System.out.println("this is remote");
	}

}
