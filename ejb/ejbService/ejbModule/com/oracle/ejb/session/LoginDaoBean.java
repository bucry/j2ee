package com.oracle.ejb.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.oracle.entity.User;


@Stateless
@WebService(endpointInterface = "com.oracle.webservice.bean.LoginDao")
public class LoginDaoBean {
	@PersistenceContext
	protected EntityManager em;
	public boolean isLogin(String name , String password){
		StringBuffer hql = new StringBuffer();
		//boolean resturt 
		Query query = em.createQuery(hql.toString());
		@SuppressWarnings("unchecked")
		List<User> list = query.getResultList();
		
		if(list.isEmpty()){
			return false;
		}else{
			return true;
		}
		
	}

}
