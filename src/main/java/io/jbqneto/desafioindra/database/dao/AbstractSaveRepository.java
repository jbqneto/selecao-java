package io.jbqneto.desafioindra.database.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractSaveRepository<T> implements BasicRepositoryCrud<T> {
	
	protected JpaRepository<T, Long> repository;
	
	public AbstractSaveRepository(JpaRepository<T, Long> repository) {
		this.repository = repository;
	}
	
	@Override
	public abstract T save(T entity);
	
	public abstract JpaRepository<T, Long> getRepository();
	
	@Override
	public void deleteById(long id) {
		this.getRepository().deleteById(id);
	}
	
	@Override
	public List<T> findAll() {
		return this.getRepository().findAll();
	}
	
	public T findById(long id) {
		return this.getRepository().getOne(id);
	}
	
}
