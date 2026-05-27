package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

@Service
public class HistoryService {
	public <T extends BaseHistorySQL, U extends JpaRepository<T, Integer>> Stream<T> getHistory(DefaultHistoryFilterRecord filter, U repository) {
		int limitSize = filter.getNumber() <= 0? Integer.MAX_VALUE : filter.getNumber();
		return repository.findAll().stream()
				.filter(i -> targetFilter(i, filter.getTargetId()))
				.filter(i -> minTermFilter(i, filter.getMinTerm()))
				.filter(i -> maxTermFilter(i, filter.getMaxTerm()))
				.sorted(Comparator.comparing(T::getId).reversed())
				.limit(limitSize);
	}
	
	<T extends BaseHistorySQL> boolean targetFilter(T account, int targetId) {
		if(0 < targetId) {
			return account.getTargetId() == targetId;
		}
		return true;
	}
	
	<T extends BaseHistorySQL> boolean minTermFilter(T account, YearMonth minTerm) {
		if(minTerm != null) {
			LocalDateTime startTime = minTerm.atDay(1).atStartOfDay();
			return !startTime.isAfter(account.getDate());
		}
		return true;
	}
	
	<T extends BaseHistorySQL> boolean maxTermFilter(T account, YearMonth maxTerm) {
		if(maxTerm != null) {
			LocalDateTime endTime = maxTerm.atEndOfMonth().atTime(LocalTime.MAX);
			return !endTime.isBefore(account.getDate());
		}
		return true;
	}
	
	public <T extends BaseHistorySQL, U extends JpaRepository<T, Integer>> void saveHistory(AccountSQL loginAccount, T newHistory, U historyRepository, HistoryEnum code) {
		if(newHistory.getTargetId() != null) {
			newHistory.setAction(code.name());
			newHistory.setActionId(loginAccount.getId());
			newHistory.setActionUser(loginAccount.getDisplayedUser());
			historyRepository.save(newHistory);
		}
	}
}