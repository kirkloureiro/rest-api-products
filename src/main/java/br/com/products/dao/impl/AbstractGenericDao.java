package br.com.products.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import br.com.products.dao.GenericDao;

public abstract class AbstractGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected Class<T> entityClass;

	@PersistenceContext
	protected EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public AbstractGenericDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public T create(final T t) {
		this.entityManager.persist(t);
		return t;
	}

	@Override
	public T read(final PK id) {
		return this.entityManager.find(entityClass, id);
	}

	@Override
	public T update(final T t) {
		return this.entityManager.merge(t);
	}

	@Override
	public void delete(T t) {
		t = this.entityManager.merge(t);
		this.entityManager.remove(t);
	}

	@Override
	public T findEntityById(final PK id) {
		return entityManager.find(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		return entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t", entityClass).getResultList();
	}

	@Override
	public void flush() {
		entityManager.flush();
	}
}
