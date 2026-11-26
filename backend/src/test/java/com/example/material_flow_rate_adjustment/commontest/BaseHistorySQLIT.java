package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

import jakarta.persistence.Column;

import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;

public abstract class BaseHistorySQLIT <T extends JpaRepository<U, Integer>, U extends BaseHistorySQL> extends BaseSQLIT<T, U>{
	protected abstract List<String> grantLengthVariableNameInHistory();
	
	@Override
	protected List<String> grantLengthVariableName(){
		return targetVariableName(this::grantLengthVariableNameInHistory, "action");
	};
	
	protected abstract List<String> grantNotNullVariableNameInHistory();
	
	@Override
	protected List<String> grantNotNullVariableName(){
		return targetVariableName(this::grantNotNullVariableNameInHistory, "action", "actionId", "actionUser");
	}
	
	protected abstract List<String> grantNotUpdateVariableNameInHistory();
	
	@Test
	@Transactional
	void grantNotUpdateVariableName(){
		U entity = saveTestData();
		targetVariableName(this::grantNotUpdateVariableNameInHistory, "targetId", "hasDeletedOld", "hasDeletedNew", "action", "actionId", "actionUser")
			.forEach(i -> update(entity, i));
		repository().save(entity);
		clear();
		U result = repository().findById(entity.getId()).get();
		assertThat(result)
			.usingRecursiveComparison()
			.ignoringFields("id", "createdDate")
			.withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
			.isEqualTo(entity());
	}
	
	void update(U entity, String variableName) {
		errorFail(variableName, () -> {
			Class<?> type = ReflectionUtils.findField(entity.getClass(), variableName).getType();
			BiFunction<Object, Column, Object> valueMethod = switch (type) {
				case Class<?> t when t == Integer.class -> (value, _) -> value == null? 1: (Integer) value + 1;
				case Class<?> t when t == String.class -> (value, _) -> "d".equals(value)? "u": "d";
				case Class<?> t when t == Boolean.class -> (value, _) -> value == null? false: !(Boolean) value;
				case Class<?> t when t == BigDecimal.class -> (value, _) -> value == null? BigDecimal.ONE: BigDecimal.ONE.add((BigDecimal) value);
				case Class<?> t when t == LocalDate.class -> (value, _) -> value == null? LocalDate.of(2026, 11, 1): ((LocalDate) value).plusDays(1);
				default -> throw new Exception(variableName + "の型が設定されていません");
			};
			setValue(entity, variableName, valueMethod);
		});
	}
	
	List<String> targetVariableName(Supplier<List<String>> addVariableName, String ...variableName){
		return Stream.concat(
					CollectionUtils.isEmpty(addVariableName.get())? Stream.empty(): addVariableName.get().stream(), 
					Arrays.stream(variableName))
				.toList();
	}
}