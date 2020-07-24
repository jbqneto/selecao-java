package io.jbqneto.desafioindra.database.dao;

import java.util.List;

public interface BasicRepositoryCrud <T> {

	public T save(T entity);
	public List<T> findAll();
	public T findById(long id);
	public void deleteById(long id);
	
}
