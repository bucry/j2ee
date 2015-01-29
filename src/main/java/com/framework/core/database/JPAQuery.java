package com.framework.core.database;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * @author bfc
 *
 */
public class JPAQuery {
    
    private final String hql;
    private final Integer firstResult;
    private final Integer maxResults;

    public JPAQuery(String hql, Integer firstResult, Integer maxResults) {
        this.hql = hql;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    Query createQuery(EntityManager entityManager, Map<String, Object> params) {
        Query query = entityManager.createQuery(hql);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            } 
        }
        if (firstResult != null) query.setFirstResult(firstResult);
        if (maxResults != null) query.setMaxResults(maxResults);
        return query;
    }

    @Override
    public String toString() {
        return String.format("Query[hql=%s, firstResult=%d, maxResults=%d]", hql, firstResult, maxResults);
    }
    
}