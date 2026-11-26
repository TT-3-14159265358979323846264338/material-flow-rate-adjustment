package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;

import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.example.material_flow_rate_adjustment.savedata.BaseId;

public abstract class BaseSQLIT<T extends JpaRepository<U, Integer>, U extends BaseId> {
	protected abstract T repository();
	protected abstract U entity();
	protected abstract EntityManager entityManager();
	
	protected U saveTestData() {
		return repository().save(entity());
	}
	
	protected void clear() {
		entityManager().flush();
		entityManager().clear();
	}
	
	protected abstract ThrowingConsumer<U> assertLoad();
	
	@Test
	@Transactional
	void saveAndLoad() {
		int id = saveTestData().getId();
		clear();
		Optional<U> result = repository().findById(id);
		assertThat(result)
			.isPresent()
			.get()
			.satisfies(assertLoad());
	}
	
	@Test
	@Transactional
	void createdDate() {
		int id = saveTestData().getId();
		clear();
		Optional<U> result = repository().findById(id);
		assertThat(result)
			.isPresent()
			.get()
			.extracting(U::getCreatedDate)
			.isNotNull();
	}
	
	protected abstract List<String> grantLengthVariableName();
	
	@Test
	@Transactional
	@DisabledIf("existsLength")
	void limitationLength() {
		grantLengthVariableName().forEach(this::validLength);
	}
	
	boolean existsLength() {
		return CollectionUtils.isEmpty(grantLengthVariableName());
	}
	
	void validLength(String variableName) {
		errorFail(variableName, () -> {
			U entity = entity();
			setValue(entity, variableName, (_, column) -> "i".repeat(column.length() + 1));
			assertThrowError(entity);
		});
	}
	
	protected abstract List<String> grantNotNullVariableName();
	
	@Test
	@Transactional
	@DisabledIf("existsNotNull")
	void limitationNotNull() {
		grantNotNullVariableName().forEach(this::validNotNull);
	}
	
	boolean existsNotNull() {
		return CollectionUtils.isEmpty(grantNotNullVariableName());
	}
	
	void validNotNull(String variableName) {
		errorFail(variableName, () -> {
			U entity = entity();
			setValue(entity, variableName, (_, _) -> null);
			assertThrowError(entity);
		});
	}
	
	protected abstract List<String> grantBigDecimalParams();
	
	@Test
	@Transactional
	@DisabledIf("existsBigDecimalParams")
	void limitationBigDecimalParams() {
		grantBigDecimalParams().forEach(this::validBigDecimalParams);
	}
	
	boolean existsBigDecimalParams() {
		return CollectionUtils.isEmpty(grantBigDecimalParams());
	}
	
	void validBigDecimalParams(String variableName) {
		errorFail(variableName, () -> {
			Field field = ReflectionUtils.findField(entity().getClass(), variableName);
			Column column = AnnotatedElementUtils.findMergedAnnotation(field, Column.class);
			assertThat(column.precision()).isGreaterThan(0);
			assertThat(column.scale())
				.isGreaterThan(0)
				.isLessThan(column.precision());
		});
	}
	
	@FunctionalInterface
	interface ThrowErrorMethod {
	    void run() throws Exception;
	}
	
	void errorFail(String variableName, ThrowErrorMethod method) {
		try{
			method.run();
		}catch(Exception e) {
			fail(variableName + "フィールド操作ができませんでした", e);
		}
	}
	
	void setValue(U entity, String variableName, BiFunction<Object, Column, Object> valueMethod) throws Exception {
		Field field = ReflectionUtils.findField(entity.getClass(), variableName);
		field.setAccessible(true);
		Column column = AnnotatedElementUtils.findMergedAnnotation(field, Column.class);
		BeanUtils.getPropertyDescriptor(entity.getClass(), variableName)
				.getWriteMethod()
				.invoke(entity, valueMethod.apply(field.get(entity), column));
	}
	
	void assertThrowError(U entity) {
		assertThatThrownBy(() -> {
			repository().save(entity);
		}).isInstanceOf(DataIntegrityViolationException.class);
	}
}