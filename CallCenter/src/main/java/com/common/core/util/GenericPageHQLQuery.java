package com.common.core.util;

import com.common.AppContext;
import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.security.entity.DataPrivilege;
import com.common.security.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.data.jpa.repository.query.QueryUtils.COUNT_QUERY_STRING;
import static org.springframework.data.jpa.repository.query.QueryUtils.getQueryString;

@SuppressWarnings("unchecked")
public class GenericPageHQLQuery<T> {
    @PersistenceContext(unitName="defaultPU")
    protected EntityManager em;
    protected final static String placeholder = "x";

    protected Class<T> entityClass;
    protected final static String where = " where 1=1 and ";
    protected String from;

    @Autowired
    private AppContext appContext;
    private Logger logger = Logger.getLogger(GenericPageHQLQuery.class);

    public GenericPageHQLQuery() {
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            entityClass = (Class<T>) params[0];
            from = " from " + entityClass.getName() + where;
        }
    }

    public FilterTranslator createFilter(String where) {
        return new FilterTranslator(where);
    }

    public Page<T> findAll(String where, Pageable page) {
        FilterTranslator translator = new FilterTranslator(where);
        return findAll(translator, page);
    }

    public Page<T> findAll(FilterTranslator translator, Pageable page) {
        if (SecurityUtil.getDataFilter()) {
            DataPrivilege dataPrivilege = appContext.getDataPrivilege(entityClass);
            if (dataPrivilege != null) {
                translator.addFilterGroup(Constant.FILTER_OP_AND, dataPrivilege.getPrivilegeRule());
            }
        }
        translator.translate();
        return findAll(translator.getCondition(), translator.getParams(), page);
    }

    protected Page<T> findAll(String condition, Map<String, Object> params, Pageable page) {
        TypedQuery<Long> countQuery = getCountQuery(condition);
        TypedQuery<T> resultQuery = getResultQuery(condition,page==null?null: page.getSort());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            countQuery.setParameter(param.getKey(), param.getValue());
            resultQuery.setParameter(param.getKey(), param.getValue());
        }
        return page == null ? new PageImpl<T>(resultQuery.getResultList()) : readPage(countQuery, resultQuery, page);
    }

    protected String getCountQueryString() {
        String countQuery = String.format(COUNT_QUERY_STRING, placeholder, "%s");
        return getQueryString(countQuery, entityClass.getName());
    }

    protected TypedQuery<Long> getCountQuery(String condition) {
        return em.createQuery(getCountQueryString() + where + condition, Long.class);
    }

    protected TypedQuery<T> getResultQuery(String condition, Sort sort) {
        StringBuffer hql = new StringBuffer(from + condition);
        if (sort != null) {
            Iterator<Sort.Order> orders = sort.iterator();
            while (orders.hasNext()) {
                Sort.Order o = orders.next();
                hql.append(" order by ").append(o.getProperty()).append(" ").append(o.getDirection());
            }
        }
        return em.createQuery(hql.toString(), entityClass);
    }

    protected Page<T> readPage(TypedQuery<Long> countQuery, TypedQuery<T> resultQuery, Pageable pageable) {
        resultQuery.setFirstResult(pageable.getOffset());
        resultQuery.setMaxResults(pageable.getPageSize());
        Long total = countQuery.getSingleResult();
        return new PageImpl<T>(resultQuery.getResultList(), pageable, total);
    }

    public void clear() {
        em.clear();
    }

    public Connection getConnection() {
        return em.unwrap(Connection.class);
    }
}
