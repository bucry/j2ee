package com.leo.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.leo.entity.TestEntity;

public class JpaQs {
	@PersistenceContext(unitName="qs")
	private EntityManager em;
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("itcast");
	
	public static void main(String args[]) {
		final EntityManager em = emf.createEntityManager();
		TestEntity ent = new TestEntity();
		ent.setNamr("sss");
		try {
			em.getTransaction().begin();
			em.persist(ent);
			em.getTransaction().commit();
		} catch (Exception e) {
			
		}
	}
}
