package com.ustc.box.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
@Repository
public class HDao {

	private static final Log logger = LogFactory.getLog(HDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void save(Object entity) {
		getCurrentSession().save(entity);
	}

	@Transactional
	public void update(Object entity) {
		getCurrentSession().update(entity);
	}
	
	@Transactional
	public void delete(Object entity) {
		getCurrentSession().delete(entity);
	}

	@Transactional
	public int bulkUpdate(String hql, Object... values) {
		Query query = getCurrentSession().createQuery(hql);
		setQueryParam(query, values);
		return query.executeUpdate();
	}

	@Transactional
	public int bulkUpdateBySQL(final String sql, final Object... values) {
		Query query = getCurrentSession().createSQLQuery(sql);
		setQueryParam(query, values);
		return query.executeUpdate();
	}

	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) getCurrentSession().get(clazz, id);
	}

	public <T> List<T> getAll(Class<T> clazz) {
		Criteria criteria = getCurrentSession().createCriteria(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();

	}

	public <T> T load(Class<T> clazz, Serializable id) {
		return (T) getCurrentSession().load(clazz, id);
	}

	public <T> List<T> find(String hql, Object... values) {
		Query query = getCurrentSession().createQuery(hql);
		setQueryParam(query, values);
		return query.list();
	}

	public <T> List<T> find(String hql, String[] paramNames, Object[] params) {
		Query query = getCurrentSession().createQuery(hql);
		setQueryParam(query, paramNames, params);
		return query.list();
	}

	public <T> List<T> find(String hql, String[] paramNames, Object[] params,
			String[] listParamNames, Collection<?>[] listParams) {
		Query query = getCurrentSession().createQuery(hql);
		setQueryParam(query, paramNames, params);
		setListQueryParam(query, listParamNames, listParams);
		return query.list();
	}

	public <T> T findUniqueResult(String hql, Object... values) {
		Query query = getCurrentSession().createQuery(hql);
		setQueryParam(query, values);
		return (T) query.uniqueResult();
	}

	public <T> List<T> findBySQL(String sql, Object... values) {
		Query query = getCurrentSession().createSQLQuery(sql);
		setQueryParam(query, values);
		return query.list();
	}

	public <T> List<T> findBySQL(String sql, String[] paramNames,
			Object[] params) {
		Query query = getCurrentSession().createSQLQuery(sql);
		setQueryParam(query, paramNames, params);
		return query.list();
	}

	public <T> List<T> findBySQL(String sql, String[] paramNames,
			Object[] params, String[] listParamNames, Collection<?>[] listParams) {
		Query query = getCurrentSession().createSQLQuery(sql);
		setQueryParam(query, paramNames, params);
		setListQueryParam(query, listParamNames, listParams);
		return query.list();
	}

	public <T> T findBySQLUniqueResult(String sql, Object... values) {
		Query query = getCurrentSession().createSQLQuery(sql);
		setQueryParam(query, values);
		return (T) query.uniqueResult();
	}

	public <T> PageBean<T> findByPage(PageBean<T> pageBean, String hql,
			Object... values) {
		Query query = getCurrentSession().createQuery(hql);
		Query countQuery = getCurrentSession().createQuery(toCountHQL(hql));
		setQueryParam(query, values);
		setQueryParam(countQuery, values);
		Object obj = countQuery.uniqueResult();
		pageBean.setCount(getCount(obj));
		query.setFirstResult(pageBean.getStart());
		query.setMaxResults(pageBean.getLimit());
		pageBean.setData(query.list());
		return pageBean;
	}

	public <T> PageBean<T> findByPage(PageBean<T> pageBean, String hql,
			String[] paramNames, Object[] params) {
		Query query = getCurrentSession().createQuery(hql);
		Query countQuery = getCurrentSession().createQuery(toCountHQL(hql));
		setQueryParam(query, paramNames, params);
		setQueryParam(countQuery, paramNames, params);
		Object obj = countQuery.uniqueResult();
		pageBean.setCount(getCount(obj));
		query.setFirstResult(pageBean.getStart());
		query.setMaxResults(pageBean.getLimit());
		pageBean.setData(query.list());
		return pageBean;
	}

	public <T> PageBean<T> findByPage(PageBean<T> pageBean, String hql,
			String[] paramNames, Object[] params, String[] listParamNames,
			Collection<?>[] listParams) {
		Query query = getCurrentSession().createQuery(hql);
		Query countQuery = getCurrentSession().createQuery(toCountHQL(hql));

		setQueryParam(query, paramNames, params);
		setListQueryParam(query, listParamNames, listParams);

		setQueryParam(countQuery, paramNames, params);
		setListQueryParam(countQuery, listParamNames, listParams);

		Object obj = countQuery.uniqueResult();
		pageBean.setCount(getCount(obj));
		query.setFirstResult(pageBean.getStart());
		query.setMaxResults(pageBean.getLimit());
		pageBean.setData(query.list());
		return pageBean;
	}

	public <T> PageBean<T> findBySQLPage(PageBean<T> pageBean, String sql,
			Object... values) {
		Query query = getCurrentSession().createSQLQuery(sql);
		Query countQuery = getCurrentSession().createSQLQuery(toCountSQL(sql));
		setQueryParam(query, values);
		setQueryParam(countQuery, values);
		Object obj = countQuery.uniqueResult();
		pageBean.setCount(getCount(obj));
		query.setFirstResult(pageBean.getStart());
		query.setMaxResults(pageBean.getLimit());
		pageBean.setData(query.list());
		return pageBean;
	}

	public <T> PageBean<T> findBySQLPage(PageBean<T> pageBean, String sql,
			String[] paramNames, Object[] params) {
		Query query = getCurrentSession().createSQLQuery(sql);
		Query countQuery = getCurrentSession().createSQLQuery(toCountSQL(sql));
		setQueryParam(query, paramNames, params);
		setQueryParam(countQuery, paramNames, params);
		Object obj = countQuery.uniqueResult();
		pageBean.setCount(getCount(obj));
		query.setFirstResult(pageBean.getStart());
		query.setMaxResults(pageBean.getLimit());
		pageBean.setData(query.list());
		return pageBean;
	}

	public <T> PageBean<T> findBySQLPage(PageBean<T> pageBean, String sql,
			String[] paramNames, Object[] params, String[] listParamNames,
			Collection<?>[] listParams) {
		Query query = getCurrentSession().createSQLQuery(sql);
		Query countQuery = getCurrentSession().createSQLQuery(toCountSQL(sql));

		setQueryParam(query, paramNames, params);
		setListQueryParam(query, listParamNames, listParams);

		setQueryParam(countQuery, paramNames, params);
		setListQueryParam(countQuery, listParamNames, listParams);

		Object obj = countQuery.uniqueResult();
		pageBean.setCount(getCount(obj));
		query.setFirstResult(pageBean.getStart());
		query.setMaxResults(pageBean.getLimit());
		pageBean.setData(query.list());
		return pageBean;
	}

	private void setQueryParam(Query query, Object... values) {
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
	}

	private void setQueryParam(Query query, String[] paramNames, Object[] params) {
		if (paramNames == null || params == null)
			return;
		for (int i = 0; i < paramNames.length; i++) {
			query.setParameter(paramNames[i], params[i]);
		}
	}

	private void setListQueryParam(Query query, String[] listParamNames,
			Collection<?>[] listParams) {
		if (listParamNames == null || listParams == null)
			return;
		for (int i = 0; i < listParamNames.length; i++) {
			query.setParameterList(listParamNames[i], listParams[i]);
		}
	}

	private long getCount(Object obj) {
		if (obj instanceof Object[]) {
			return getCount(((Object[]) obj)[0]);
		}
		if (obj instanceof List) {
			return getCount(((List<?>) obj).get(0));
		}
		if (obj instanceof Number) {
			Number count = (Number) obj;
			return count.longValue();
		}
		return 0;
	}

	private String toCountHQL(String hql) {
		if (null == hql || hql.trim().equals(""))
			return "";
		String formatQl = hql;
		String pStr = "^\\s*((s|S)(e|E)(l|L)(e|E)(c|C)(t|T))?(.*?)(f|F)(r|R)(o|O)(m|M)\\s";
		String pOrderStr = "\\s*(o|O)(r|R)(d|D)(e|E)(r|R)\\s+(b|B)(y|Y)\\s+\\S+(\\s+((a|A)(s|S)(c|C)|(d|D)(e|E)(s|S)(c|C)))?(\\s*,\\s*\\S+(\\s+((a|A)(s|S)(c|C)|(d|D)(e|E)(s|S)(c|C)))?)*";
		Pattern p = Pattern.compile(pStr, Pattern.DOTALL);
		Matcher m = p.matcher(hql);
		if (m.find()) {
			StringBuffer countHeader = new StringBuffer("SELECT COUNT(*)");
			if (m.group(8) != null && !m.group(8).trim().equals("")) {
				countHeader.append(", " + m.group(8).trim());
			}
			countHeader.append(" FROM ");
			formatQl = formatQl.replaceAll(pStr, countHeader.toString());
		} else {
			logger.warn("can't convert to countHQL, hql[" + hql + "]");
		}
		formatQl = formatQl.replaceFirst(pOrderStr, "");
		return formatQl;
	}

	private String toCountSQL(String sql) {
		if (null == sql || sql.trim().equals(""))
			return "";
		String formatQl = sql;
		String pOrderStr = "\\s*(o|O)(r|R)(d|D)(e|E)(r|R)\\s+(b|B)(y|Y)\\s+\\S+(\\s+((a|A)(s|S)(c|C)|(d|D)(e|E)(s|S)(c|C)))?(\\s*,\\s*\\S+(\\s+((a|A)(s|S)(c|C)|(d|D)(e|E)(s|S)(c|C)))?)*";
		formatQl = formatQl.replaceAll(pOrderStr, "");
		return "SELECT COUNT(*) FROM (" + formatQl + ") AS TOTAL";
	}

}