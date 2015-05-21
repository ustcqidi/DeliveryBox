package com.ustc.box.dao;

import java.io.Serializable;
import java.util.List;


public interface IDao {

	public void save(Object entity);

	public void update(Object entity);

	public void delete(Object entity);

	public int bulkUpdate(String hql, Object... values);

	public int bulkUpdateBySQL(final String sql, final Object... values);

	public <T> T get(Class<T> clazz, Serializable id);

	public <T> List<T> find(String hql, Object... values);

	public <T> T findSingle(String hql, Object... values);

	public <T> PageBean<T> find(PageBean<T> pageBean, String hql,
			Object... values);

	public <T> List<T> find(String hql, String[] paramNames, Object[] params);

	public <T> PageBean<T> find(PageBean<T> pageBean, String hql,
			String[] paramNames, Object[] params);

	public <T> List<T> findBySQL(String sql, Object... values);

	public <T> T findSingleBySQL(String sql, Object... values);

	public <T> PageBean<T> findBySQL(PageBean<T> pageBean, String sql,
			Object... values);

	public <T> List<T> findBySQL(String sql, String[] paramNames,
			Object[] params);

	public <T> PageBean<T> findBySQL(PageBean<T> pageBean, String sql,
			String[] paramNames, Object[] params);
	
	public <T> T doInTx(Atom atom);
	
	public static interface Atom {
		Object exec();
	}

}
