package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import jakarta.persistence.PersistenceUnitUtil;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.material_flow_rate_adjustment.savedata.maindata.BaseSQL;

public abstract class BaseMainSQLIT<T extends JpaRepository<U, Integer>, U extends BaseSQL> extends BaseSQLIT<T, U>{
	protected abstract Consumer<U> changeMethod();
	
	@Test
	@Transactional
	void updateDate() {
		U data = saveTestData();
		LocalDateTime updatedDate = data.getUpdatedDate();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			fail(e);
		}
		changeMethod().accept(data);
		repository().save(data);
		clear();
		Optional<U> result = repository().findById(data.getId());
		assertThat(result)
			.isPresent()
			.get()
			.extracting(U::getUpdatedDate)
			.isNotNull()
			.isNotEqualTo(updatedDate);
	}
	
	@Test
	@Transactional
	void notNullHasDeleted() {
		U entity = entity();
		entity.setHasDeleted(null);
		assertThrowError(entity);
	}
	
	protected abstract List<String> grantUniqueVariableName();
	
	@Test
	@Transactional
	@DisabledIf("existsUnique")
	void limitationUnique() {
		saveTestData();
		IntStream.range(0, grantUniqueVariableName().size()).forEach(i -> validUnique(i, grantUniqueVariableName().get(i)));
	}
	
	boolean existsUnique() {
		return CollectionUtils.isEmpty(grantUniqueVariableName());
	}
	
	void validUnique(int number, String variableName) {
		errorFail(variableName, () -> {
			U entity = entity();
			for(String i: grantUniqueVariableName()) {
				if(i.equals(variableName)) {
					continue;
				}
				setValue(entity, i, (value, _) -> (String) value + number);
			}
			assertThrowError(entity);
		});
	}

	protected abstract List<String> grantLazyFetch();
	
	@Test
	@Transactional
	@DisabledIf("existsLazyFetch")
	void lazyFetch() {
		int id = saveTestData().getId();
		clear();
		Optional<U> data = repository().findById(id);
		PersistenceUnitUtil unitUtil = entityManager().getEntityManagerFactory().getPersistenceUnitUtil();
		assertThat(data)
			.isPresent()
			.get()
			.satisfies(entity -> grantLazyFetch().forEach(name -> assertThat(unitUtil.isLoaded(entity, name)).isFalse()));
	}
	
	boolean existsLazyFetch() {
		return CollectionUtils.isEmpty(grantLazyFetch());
	}
	
	protected abstract List<DeleteData<?, ?>> grantOnDelete();
	
	public record DeleteData<V extends JpaRepository<W, Integer>, W extends BaseSQL> (V repository, W entity) {}
	
	@Test
	@Transactional
	@DisabledIf("existsOnDelete")
	void onDeleteAction() {
		grantOnDelete().forEach(this::validOnDelete);
	}
	
	boolean existsOnDelete() {
		return CollectionUtils.isEmpty(grantOnDelete());
	}
	
	<V extends JpaRepository<W, Integer>, W extends BaseSQL> void validOnDelete(DeleteData<V, W> deleteData) {
		saveTestData();
		clear();
		assertThatThrownBy(() -> {
			deleteData.repository.delete(deleteData.entity);
			entityManager().flush();
		}).isInstanceOf(ConstraintViolationException.class);
	}
}