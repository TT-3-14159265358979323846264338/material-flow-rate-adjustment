package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;

@Service
public class HistoryService {
	public <T extends BaseHistorySQL, U extends BaseHistoryRepository<T, Integer>> Stream<T> getHistory(DefaultHistoryFilterRecord filter, U repository) {
		return repository.findByDateBetween(minTerm(filter.getMinTerm()), maxTerm(filter.getMaxTerm()), historySort())
				.stream()
				.filter(i -> targetFilter(i, filter.getTargetId()));
	}
	
	<T extends BaseHistorySQL> boolean targetFilter(T account, int targetId) {
		if(0 < targetId) {
			return account.getTargetId() == targetId;
		}
		return true;
	}
	
	LocalDateTime minTerm(YearMonth minTerm) {
		return minTerm != null? minTerm.atDay(1).atStartOfDay(): LocalDateTime.of(1000, 1, 1, 0, 0, 0);
	}
	
	LocalDateTime maxTerm(YearMonth maxTerm) {
		return maxTerm != null? maxTerm.atEndOfMonth().atTime(LocalTime.MAX): LocalDateTime.of(9999, 12, 31, 23, 59, 59);
	}
	
	Sort historySort() {
		return Sort.by(Sort.Direction.ASC, "id");
	}
}