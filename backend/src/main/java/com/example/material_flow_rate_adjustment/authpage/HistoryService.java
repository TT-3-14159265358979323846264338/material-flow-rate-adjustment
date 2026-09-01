package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial.OrderSortEnum;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;

@Service
public class HistoryService {
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
		return getYearMonth(minYear, 1000, minMonth, 1).atDay(1).atStartOfDay();
	}
	
	LocalDateTime maxTerm(String maxYear, String maxMonth) {
		return getYearMonth(maxYear, 9999, maxMonth, 12).atEndOfMonth().atTime(LocalTime.MAX);
	}
	
	YearMonth getYearMonth(String year, int baseYear, String month, int baseMonth) {
		try {
			return YearMonth.of(getIntValue(year, baseYear), getIntValue(month, baseMonth));
		}catch(Exception e) {
			return YearMonth.of(baseYear, baseMonth);
		}
	}
	
	int getIntValue(String value, int base) {
		try {
			return Integer.parseInt(value);
		}catch(Exception e) {
			return base;
		}
	}
	
	Sort historySort(OrderSortEnum order, DefaultHistorySortEnum target) {
		return target.getHistorySort(order);
	}
}