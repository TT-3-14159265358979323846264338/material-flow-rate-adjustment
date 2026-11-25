package com.example.material_flow_rate_adjustment.commontest;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnitUtil;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseRepositoryIT<T extends JpaRepository<U, Integer>, U> {
	protected abstract T repository();
	protected abstract List<U> entity();
	protected abstract EntityManager entityManager();
	
	protected List<U> saveTestData() {
		List<U> saveData = repository().saveAll(entity());
		clear();
		return saveData;
	}
	
	protected void clear() {
		entityManager().flush();
		entityManager().clear();
	}
	
	protected PersistenceUnitUtil unitUtil() {
		return entityManager().getEntityManagerFactory().getPersistenceUnitUtil();
	}
}