package com.example.material_flow_rate_adjustment.authpage.commonpage.historycalendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.DefaultHistoryFilterRecord;
import com.example.material_flow_rate_adjustment.authpage.HistoryService;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistorySQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryCalendarService {
	private final CalendarHistoryRepository repository;
	private final HistoryService historyService;
	
	@Transactional(readOnly = true)
	public List<History> getHistory(DefaultHistoryFilterRecord filter) {
		return historyService.getHistory(filter, repository).map(this::createHistory).toList();
	}
	
	History createHistory(CalendarHistorySQL history) {
		return new History(
				history.getId(),
				history.getOldName(),
				history.getNewName(),
				history.getOldDestination(),
				history.getNewDestination(),
				history.getOldHoliday(),
				history.getNewHoliday(),
				history.getOldType(),
				history.getNewType(),
				history.getHasDeletedOld(),
				history.getHasDeletedNew(),
				history.getAction(),
				history.getActionUser(),
				history.getDate());
	}
	
	record History(
			Integer id,
			String oldName,
			String newName,
			String oldDestination,
			String newDestination,
			LocalDate oldHoliday,
			LocalDate newHoliday,
			String oldType,
			String newType,
			Boolean hasDeletedOld,
			Boolean hasDeletedNew,
			String action, 
			String actionUser, 
			LocalDateTime date) {}
}