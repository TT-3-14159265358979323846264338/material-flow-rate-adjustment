package com.example.material_flow_rate_adjustment.defaulttestdata;

import java.util.List;
import java.util.stream.Stream;

public interface BaseSQLJoinColumn<T, U> {
	public T getSQL(U data);
	
	@SafeVarargs
	public static <V extends BaseSQLJoinColumn<T, U>, T, U> List<T> getSQLList(U data, V ...target){
		return Stream.of(target).map(i -> i.getSQL(data)).toList();
	}
}