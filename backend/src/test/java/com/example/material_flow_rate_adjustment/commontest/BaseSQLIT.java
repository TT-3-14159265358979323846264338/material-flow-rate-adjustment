package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.IntStream;

import jakarta.persistence.Column;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.material_flow_rate_adjustment.savedata.maindata.BaseSQL;

public abstract class BaseSQLIT<T extends JpaRepository<U, Integer>, U extends BaseSQL> {
	protected abstract T repository();
	protected abstract U entity();
	protected abstract Consumer<U> changeMethod();
	
	protected U saveTestData() {
		return repository().save(entity());
	}
	
	@Test
	@Transactional
	void updateDate() {
		U data = saveTestData();
		LocalDateTime updatedDate = data.getUpdatedDate();
		LocalDateTime createdDate = data.getCreatedDate();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			fail(e);
		}
		changeMethod().accept(data);
		repository().saveAndFlush(data);
		assertThat(data.getUpdatedDate()).isNotEqualTo(updatedDate);
		assertThat(data.getCreatedDate()).isEqualTo(createdDate);
	}
	
	@Test
	@Transactional
	void notNullHasDeleted() {
		U entity = entity();
		entity.setHasDeleted(null);
		assertThrowError(entity);
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
			setValue(entity, variableName, column -> "i".repeat(column.length() + 1));
			assertThrowError(entity);
		});
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
				setValue(entity, i, _ -> "change" + number);
			}
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
			setValue(entity, variableName, _ -> null);
			assertThrowError(entity);
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
			fail(variableName + "でエラー", e);
		}
	}
	
	void setValue(U entity, String variableName, Function<Column, ?> setValue) throws Exception {
		Field field = entity.getClass().getDeclaredField(variableName);
		Column column = AnnotatedElementUtils.findMergedAnnotation(field, Column.class);
		String setterName = "set" + Character.toUpperCase(variableName.charAt(0)) + variableName.substring(1);
		entity.getClass().getMethod(setterName, field.getType()).invoke(entity, setValue.apply(column));
	}
	
	void assertThrowError(U entity) {
		assertThatThrownBy(() -> {
			repository().save(entity);
		}).isInstanceOf(DataIntegrityViolationException.class);
	}
}