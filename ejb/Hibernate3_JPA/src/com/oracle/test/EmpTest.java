package com.oracle.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.oracle.bean.Emp;

public class EmpTest {

	/**
	 * @param args
	 */
	private static EntityManagerFactory emf=
			Persistence.createEntityManagerFactory("qs");
	public static void main(String[] args) {
		final EntityManager em=emf.createEntityManager();
		Emp emp=new Emp();
		emp.setId(2);
		emp.setName("sss");
		emp.setSalary("aa");
		emp.setAge(12);
		try{
			em.getTransaction().begin();
			//em.persist(emp);
			Emp e=em.find(Emp.class, 2);
			System.out.println(e.getName());
			//em.remove(e);
			em.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			em.close();
		}
	}

}
