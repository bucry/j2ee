package com.oracle.sStateful;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class Session02
 */
@Stateful(mappedName = "Session02")
@LocalBean
@Local(Session02Local.class)
@Remote(Session02Remote.class)
public class Session02 implements Session02Remote, Session02Local {

    public Session02() {
    }

	@Override
	public void local() {
		System.out.println("Session02local");
	}

	@Override
	public void remote() {
		System.out.println("Session02remote");
	}

}
