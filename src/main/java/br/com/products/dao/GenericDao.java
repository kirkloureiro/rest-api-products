package br.com.products.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

	public T create(T t);

	public T read(PK id);

	public T update(T t);

	public void delete(T t);

	public T findEntityById(PK id);

	public List<T> findAll();

	public void flush();
}
