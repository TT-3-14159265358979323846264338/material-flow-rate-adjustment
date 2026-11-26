package com.example.material_flow_rate_adjustment.commontest;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.defaulttestdata.BaseHistorySQLNoJoinColumn;
import com.example.material_flow_rate_adjustment.defaulttestdata.BaseSQLNoJoinColumn;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;

public abstract class BaseHistoryRepositoryIT<T extends BaseHistoryRepository<U, Integer>, U extends BaseHistorySQL, V extends BaseHistorySQLNoJoinColumn<U>> 
extends BaseRepositoryIT<T, U>{
	protected abstract V[] targetEnum();
	protected abstract JdbcTemplate jdbcTemplate();
	
	@Override
	protected List<U> entity() {
		return BaseSQLNoJoinColumn.getSQLList(targetEnum());
	}
	
	@Test
	@Transactional
	void findByyCreatedDateBetween() {
		List<Integer> expected = savedIdBetween(saveTestData()).toList();
		clear();
		List<U> result = repository().findByCreatedDateBetween(minTime(), maxTime(), Sort.by(OrderSortEnum.DESCENDING.getOrderSort(), "id"));
		assertThat(result)
			.extracting(U::getId)
			.containsExactlyElementsOf(expected);
	}
	
	protected Stream<Integer> savedIdBetween(List<U> saveData){
		List<LocalDateTime> createDateList = BaseHistorySQLNoJoinColumn.getTimeList(targetEnum()).stream().map(i -> i.atStartOfDay()).toList();
		String sql = "UPDATE " + targetEnum()[0].tableName() + " SET created_date = ? WHERE id = ?";
		LocalDateTime minTime = minTime();
		LocalDateTime maxTime = maxTime();
		return IntStream.range(0, saveData.size())
				.mapToObj(i -> {
					jdbcTemplate().update(sql, Timestamp.valueOf(createDateList.get(i)), saveData.get(i).getId());
					return new TimeData(saveData.get(i).getId(), createDateList.get(i));
				})
				.filter(i -> !i.time().isBefore(minTime) && !i.time().isAfter(maxTime))
				.sorted(Comparator.comparing(TimeData::id).reversed())
				.map(TimeData::id);
	}
	
	record TimeData(Integer id, LocalDateTime time) {}
	
	protected LocalDateTime minTime() {
		return targetEnum()[0].getDate().atStartOfDay();
	}
	
	protected LocalDateTime maxTime() {
		return targetEnum()[0].getDate().atTime(LocalTime.MAX);
	}
}