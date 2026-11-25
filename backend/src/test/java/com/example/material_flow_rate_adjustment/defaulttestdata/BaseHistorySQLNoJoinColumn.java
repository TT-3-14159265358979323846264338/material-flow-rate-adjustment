package com.example.material_flow_rate_adjustment.defaulttestdata;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface BaseHistorySQLNoJoinColumn<T> extends BaseSQLNoJoinColumn<T>{
	public LocalDate getDate();
	public String tableName();
	
	public static <U extends BaseHistorySQLNoJoinColumn<T>, T> List<LocalDate> getTimeList(U[] target){
		return Stream.of(target).map(BaseHistorySQLNoJoinColumn::getDate).toList();
	}
}