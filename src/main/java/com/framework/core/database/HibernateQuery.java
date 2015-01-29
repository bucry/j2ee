package com.framework.core.database;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author bfc
 */
public class HibernateQuery {
    private final String hql;
    private final Integer firstResult;
    private final Integer maxResults;

    public HibernateQuery(String hql, Integer firstResult, Integer maxResults) {
        this.hql = hql;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    Query createQuery(Session session, Object... params) {
        Query query = session.createQuery(hql);
        if (null != params) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
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
