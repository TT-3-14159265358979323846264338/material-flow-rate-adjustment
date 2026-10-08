package com.example.material_flow_rate_adjustment.authpage.commonpage.correctcalendar;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.historydata.BaseMaterialHistory;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalendarHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CorrectCalendarService {
	private final CalendarRepository calendarRepository;
	private final CalendarHistoryRepository calendarHistoryRepository;
	private final UtilityService utility;
	
	@Transactional
	public String correctCalendar(CorrectCalendarRecord data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		MaterialSQL material = utility.getMaterialSQL(data.material());
		YearMonth targetMonth = YearMonth.of(data.year(), data.month());
		List<CalendarSQL> existingCalendars = calendarRepository.findByHolidayBetweenAndMaterial(targetMonth.atDay(1), targetMonth.atEndOfMonth(), material);
		List<Calendar> calendars = data.calendars().stream()
												.map(i -> calendarHandle(i, existingCalendars, material))
												.filter(Objects::nonNull)
												.toList();
		calendarRepository.saveAll(calendars.stream().map(Calendar::calendar).toList());
		List<CalendarHistorySQL> histories = createHistories(calendars, account);
		calendarHistoryRepository.saveAll(histories);
		return "日程を修正しました";
	}
	
	record Calendar(CalendarSQL calendar, HolidayType oldHolidayType, Boolean oldHasDeleted, HistoryEnum handleType) {}
	
	Calendar calendarHandle(CalendarRecord newCalendar, List<CalendarSQL> existingCalendars, MaterialSQL material) {
		Optional<CalendarSQL> target = existingCalendars.stream().filter(i -> i.getHoliday().equals(newCalendar.holiday())).findFirst();
		return target.isPresent()? createCalendar(newCalendar, target.get()): createCalendar(newCalendar, material);
	}
	
	Calendar createCalendar(CalendarRecord newCalendar, CalendarSQL targetCalendar) {
		HolidayType oldHolidayType = targetCalendar.getType();
		boolean oldHasDeleted = targetCalendar.getHasDeleted();
		if(newCalendar.code().equals(oldHolidayType) && !oldHasDeleted) {
			return null;
		}
		HistoryEnum handleType = changeHandle(newCalendar, targetCalendar);
		return new Calendar(targetCalendar, oldHolidayType, oldHasDeleted, handleType);
	}
	
	boolean isWorking(CalendarRecord newCalendar) {
		return newCalendar.code().equals(HolidayType.WORKING);
	}
	
	HistoryEnum changeHandle(CalendarRecord newCalendar, CalendarSQL targetCalendar) {
		if(isWorking(newCalendar)) {
			targetCalendar.setHasDeleted(true);
			return HistoryEnum.DELETE;
		}
		targetCalendar.setType(newCalendar.code());
		targetCalendar.setHasDeleted(false);
		return HistoryEnum.CHANGE;
	}
	
	Calendar createCalendar(CalendarRecord newCalendar, MaterialSQL material) {
		if(isWorking(newCalendar)) {
			return null;
		}
		return new Calendar(createCalendarSQL(newCalendar, material), null, null, HistoryEnum.CREATE);
	}
	
	CalendarSQL createCalendarSQL(CalendarRecord newCalendar, MaterialSQL material) {
		return  CalendarSQL.builder()
				.material(material)
				.holiday(newCalendar.holiday())
				.type(newCalendar.code())
				.hasDeleted(false)
				.build();
	}
	
	List<CalendarHistorySQL> createHistories(List<Calendar> calendars, AccountSQL account){
		return calendars.stream().map(i -> i.handleType.equals(HistoryEnum.CREATE)? createCreateCalendarHistory(i, account): changeCalendarHistoryHandle(i, account)).toList();
	}
	
	CalendarHistorySQL createCreateCalendarHistory(Calendar calendar, AccountSQL account) {
		return CalendarHistorySQL.builder()
				.targetId(calendar.calendar.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.newMaterialId(calendar.calendar.getMaterial().getId())
						.newName(calendar.calendar.getMaterial().getName())
						.newDestination(calendar.calendar.getMaterial().getDestination())
						.build())
				.newHoliday(calendar.calendar.getHoliday())
				.newType(calendar.calendar.getType().name())
				.hasDeletedNew(false)
				.action(calendar.handleType.name())
				.actionId(account.getId())
				.actionUser(account.getDisplayedUser())
				.build();
	}
	
	CalendarHistorySQL changeCalendarHistoryHandle(Calendar calendar, AccountSQL account) {
		CalendarHistorySQL history = createChangeCalendarHistory(calendar, account);
		if(calendar.handleType.equals(HistoryEnum.DELETE)) {
			history.setHasDeletedNew(true);
		}else {
			if(!calendar.calendar.getType().equals(calendar.oldHolidayType)) {
				history.setNewType(calendar.calendar.getType().name());
			}
			if(!calendar.calendar.getHasDeleted().equals(calendar.oldHasDeleted)) {
				history.setHasDeletedNew(false);
			}
		}
		return history;
	}
	
	CalendarHistorySQL createChangeCalendarHistory(Calendar calendar, AccountSQL account) {
		return CalendarHistorySQL.builder()
				.targetId(calendar.calendar.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.oldMaterialId(calendar.calendar.getMaterial().getId())
						.oldName(calendar.calendar.getMaterial().getName())
						.oldDestination(calendar.calendar.getMaterial().getDestination())
						.build())
				.oldHoliday(calendar.calendar.getHoliday())
				.oldType(calendar.oldHolidayType.name())
				.hasDeletedOld(calendar.oldHasDeleted)
				.action(calendar.handleType.name())
				.actionId(account.getId())
				.actionUser(account.getDisplayedUser())
				.build();
	}
}