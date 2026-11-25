package com.example.material_flow_rate_adjustment.defaulttestdata;

import java.util.List;
import java.util.stream.Stream;

public interface BaseSQLNoJoinColumn<T> {
	public T getSQL();
	
	@SafeVarargs
	public static <U extends BaseSQLNoJoinColumn<T>, T> List<T> getSQLList(U ...target){
		return Stream.of(target).map(BaseSQLNoJoinColumn::getSQL).toList();
	}
}