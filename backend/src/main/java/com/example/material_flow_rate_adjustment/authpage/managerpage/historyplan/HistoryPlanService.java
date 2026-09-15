package com.example.material_flow_rate_adjustment.authpage.managerpage.historyplan;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;
import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.PlanHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryPlanService {
	private final PlanHistoryRepository repository;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<History> getHistory(DefaultHistoryFilterRecord filter) {
		return historyService.getHistory(filter, repository).map(this::createHistory).toList();
	}
	
	History createHistory(PlanHistorySQL history) {
		return new History(
				history.getId(),
				history.getOldName(),
				history.getNewName(),
				history.getOldDestination(),
				history.getNewDestination(),
				history.getOldYear(),
				history.getNewYear(),
				history.getOldMonth(),
				history.getNewMonth(),
				history.getOldFlow(),
				history.getNewFlow(),
				history.getOldAchievement(),
				history.getNewAchievement(),
				history.getOldShipping(),
				history.getNewShipping(),
				history.getOldAdjustment(),
				history.getNewAdjustment(),
				history.getOldRemaining(),
				history.getNewRemaining(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(Integer id,
			String oldName,
			String newName,
			String oldDestination,
			String newDestination,
			Integer oldYear,
			Integer newYear,
			Integer oldMonth,
			Integer newMonth,
			Integer oldFlow,
			Integer newFlow,
			Integer oldAchievement,
			Integer newAchievement,
			Integer oldShipping,
			Integer newShipping,
			Integer oldAdjustment,
			Integer newAdjustment,
			Integer oldRemaining,
			Integer newRemaining,
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}