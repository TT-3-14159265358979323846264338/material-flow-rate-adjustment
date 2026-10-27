package com.example.material_flow_rate_adjustment.authpage.commonpage.historyachievement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;
import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.AchievementHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryAchievementService {
	private final AchievementHistoryRepository repository;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<History> getAchievementHistory(DefaultHistoryFilterRecord filter) {
		return historyService.getHistory(filter, repository).map(this::createHistory).toList();
	}
	
	History createHistory(AchievementHistorySQL history) {
		return new History(
				history.getId(),
				history.getOldUserName(),
				history.getNewUserName(),
				history.getBaseMaterialHistory().getOldName(),
				history.getBaseMaterialHistory().getNewName(),
				history.getBaseMaterialHistory().getOldDestination(),
				history.getBaseMaterialHistory().getNewDestination(),
				history.getBasePlanHistory().getOldYear(),
				history.getBasePlanHistory().getNewYear(),
				history.getBasePlanHistory().getOldMonth(),
				history.getBasePlanHistory().getNewMonth(),
				history.getOldDate(),
				history.getNewDate(),
				history.getOldLot(),
				history.getNewLot(),
				history.getOldFlow(),
				history.getNewFlow(),
				history.getOldInspection(),
				history.getNewInspection(),
				history.getHasDeletedOld(),
				history.getHasDeletedNew(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(
			Integer id,
			String oldUserName,
			String newUserName,
			String oldMaterialName,
			String newMaterialName,
			String oldDestination,
			String newDestination,
			Integer oldYear,
			Integer newYear,
			Integer oldMonth,
			Integer newMonth,
			LocalDate oldDate,
			LocalDate newDate,
			String oldLot,
			String newLot,
			BigDecimal oldFlow,
			BigDecimal newFlow,
			String oldInspection,
			String newInspection,
			Boolean hasDeletedOld,
			Boolean hasDeletedNew,
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}