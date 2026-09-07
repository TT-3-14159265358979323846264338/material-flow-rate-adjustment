package com.example.material_flow_rate_adjustment.authpage;

import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {
	private final TransformService transform;
	
	public <T extends BaseHistorySQL, U extends BaseHistoryRepository<T, Integer>> Stream<T> getHistory(DefaultHistoryFilterRecord filter, U repository) {
		return repository.findByDateBetween(transform.minDateTime(filter.minYear(), filter.minMonth()), 
											transform.maxDateTime(filter.maxYear(), filter.maxMonth()), 
											historySort(filter.order(), filter.target())).stream();
	}
	
	<T extends BaseHistorySQL> boolean targetFilter(T account, int targetId) {
		if(0 < targetId) {
			return account.getTargetId() == targetId;
		}
		return true;
	}
	
	Sort historySort(OrderSortEnum order, DefaultHistorySortEnum target) {
		return target.getHistorySort(order);
	}
}