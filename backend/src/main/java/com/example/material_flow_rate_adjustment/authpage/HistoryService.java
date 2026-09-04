package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
	private final UtilityService utility;
	private final int MIN_YEAR = 1000;
	private final int MIN_MONTH = 1;
	private final int MAX_YEAR = 9999;
	private final int MAX_MONTH = 12;
	
	public <T extends BaseHistorySQL, U extends BaseHistoryRepository<T, Integer>> Stream<T> getHistory(DefaultHistoryFilterRecord filter, U repository) {
		return repository.findByDateBetween(minTerm(filter.minYear(), filter.minMonth()), 
											maxTerm(filter.maxYear(), filter.maxMonth()), 
											historySort(filter.order(), filter.target())).stream();
	}
	
	<T extends BaseHistorySQL> boolean targetFilter(T account, int targetId) {
		if(0 < targetId) {
			return account.getTargetId() == targetId;
		}
		return true;
	}
	
	LocalDateTime minTerm(String minYear, String minMonth) {
		return getYearMonth(minYear, MIN_YEAR, minMonth, MIN_MONTH).atDay(1).atStartOfDay();
	}
	
	LocalDateTime maxTerm(String maxYear, String maxMonth) {
		return getYearMonth(maxYear, MAX_YEAR, maxMonth, MAX_MONTH).atEndOfMonth().atTime(LocalTime.MAX);
	}
	
	YearMonth getYearMonth(String year, int baseYear, String month, int baseMonth) {
		try {
			return YearMonth.of(utility.getIntValue(year, baseYear), utility.getIntValue(month, baseMonth));
		}catch(Exception e) {
			return YearMonth.of(baseYear, baseMonth);
		}
	}
	
	Sort historySort(OrderSortEnum order, DefaultHistorySortEnum target) {
		return target.getHistorySort(order);
	}
}