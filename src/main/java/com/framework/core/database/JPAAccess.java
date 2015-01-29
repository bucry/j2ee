package com.framework.core.database;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.utils.StopWatch;

/**
 * @author bfc
 */
public class JPAAccess {
    
    private final Logger logger = LoggerFactory.getLogger(JPAAccess.class);

    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public <T> List<T> find(JPAQuery jpaQuery, Map<String, Object> params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = jpaQuery.createQuery(entityManager, params);
            return (List<T>) query.getResultList();
        } finally {
            logger.debug("find, hibernateQuery={}, params={}, elapsedTime={}", new Object[]{jpaQuery, params, watch.elapsedTime()});
        }
    }

    public <T> List<T> find(String jpaql, Map<String, Object> params) {
        return find(new JPAQuery(jpaql, null, null), params);
    }

    public <T> List<T> find(String jpaql, Integer firstResult, Integer maxResults, Map<String, Object> params) {
        return find(new JPAQuery(jpaql, firstResult, maxResults), params);
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(String jpaql, Map<String, Object> params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = entityManager.createQuery(jpaql);
            
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
            return (T) query.getSingleResult();
        } finally {
            logger.debug("findUniqueResult, hql={}, params={}, elapsedTime={}", new Object[]{jpaql, params, watch.elapsedTime()});
        }
    }
    
    public <T> List<T> find(CriteriaQuery<T> query, Integer firstResult, Integer maxResults) {
        StopWatch watch = new StopWatch();
        try {
            TypedQuery<T> typedQuery = entityManager.createQuery(query);
            if (firstResult != null) typedQuery.setFirstResult(firstResult);
            if (maxResults != null) typedQuery.setMaxResults(maxResults);
            return typedQuery.getResultList();
        } finally {
            logger.debug("find by CriteriaQuery<T>, elapsedTime={}", watch.elapsedTime());
        }
    }
    
    public <T> List<T> find(CriteriaQuery<T> query) {
        return find(query, null, null);
    }
    
    public <T> T findUniqueResult(CriteriaQuery<T> query) {
        StopWatch watch = new StopWatch();
        try {
            return entityManager.createQuery(query).getSingleResult();
        } finally {
            logger.debug("find by CriteriaQuery<T>, elapsedTime={}", watch.elapsedTime());
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(DetachedCriteria detachedCriteria, Integer firstResult, Integer maxResults) {
        StopWatch watch = new StopWatch();
        try {
            Criteria criteria = detachedCriteria.getExecutableCriteria(entityManager.unwrap(Session.class));
            if (firstResult != null) criteria.setFirstResult(firstResult);
            if (maxResults != null) criteria.setMaxResults(maxResults);
            return criteria.list();
        } finally {
            logger.debug("find, criteria={}, firstResult={}, maxResults={}, elapsedTime={}", new Object[]{String.valueOf(detachedCriteria), firstResult, maxResults, watch.elapsedTime()});
        }
    }

    public <T> List<T> find(DetachedCriteria detachedCriteria) {
        return find(detachedCriteria, null, null);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(DetachedCriteria detachedCriteria) {
        StopWatch watch = new StopWatch();
        try {
            Criteria criteria = detachedCriteria.getExecutableCriteria(entityManager.unwrap(Session.class));
            return (T) criteria.uniqueResult();
        } finally {
            logger.debug("findUniqueResult, criteria={}, elapsedTime={}", String.valueOf(detachedCriteria), watch.elapsedTime());
        }
    }

    public <T> T get(Class<T> entityClass, Object id) {
        StopWatch watch = new StopWatch();
        try {
            return entityManager.find(entityClass, id);
        } finally {
            logger.debug("get, entityClass={}, id={}, elapsedTime={}", new Object[]{entityClass.getName(), id, watch.elapsedTime()});
        }
    }

    public void persist(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            entityManager.persist(entity);
        } finally {
            logger.debug("get, entityClass={}, elapsedTime={}", entity.getClass().getName(), watch.elapsedTime());
        }
    }

    public void update(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            entityManager.merge(entity);
        } finally {
            logger.debug("update, entityClass={}, elapsedTime={}", entity.getClass().getName(), watch.elapsedTime());
        }
    }

    public void delete(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            entityManager.remove(entity);
        } finally {
            logger.debug("delete, entityClass={}, elapsedTime={}", entity.getClass().getName(), watch.elapsedTime());
        }
    }
    
    public int execute(String hql, Map<String, Object> params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = new JPAQuery(hql, null, null).createQuery(entityManager, params);
            return query.executeUpdate();
        } finally {
            logger.debug("execute, hql={}, params={}, elapsedTime={}", new Object[]{hql, params, watch.elapsedTime()});
        }
    }

    public CriteriaBuilder criteriaBuilder() {
        return entityManager.getCriteriaBuilder();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
