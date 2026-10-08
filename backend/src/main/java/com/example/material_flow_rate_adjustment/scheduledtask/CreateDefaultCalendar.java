package com.example.material_flow_rate_adjustment.scheduledtask;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
public class CreateDefaultCalendar {
	private static final Account DEFAULT_ACCOUNT = new Account(-1, "システム自動");
	private final CalendarRepository calendarRepository;
	private final CalendarHistoryRepository calendarHistoryRepository;
	private final UtilityService utility;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createCalendarRollback(int materialId, YearMonth targetMonth) {
		MaterialSQL material = utility.getMaterialSQL(materialId);
		createCalendar(material, targetMonth, null);
	}
	
	void createCalendar(MaterialSQL material, YearMonth targetMonth, AccountSQL loginUser) {
		Account account = createAccount(loginUser);
		LocalDate targetDate = targetMonth.atDay(1);
		List<CalendarSQL> existingCalendars = calendarRepository.findByHolidayBetweenAndMaterial(targetDate, targetMonth.atEndOfMonth(), material);
		List<Calendar> calendars = targetDate.datesUntil(targetDate.plusMonths(1))
												.filter(this::holidayFilter)
												.map(i -> calendarHandle(i, existingCalendars, material))
												.filter(Objects::nonNull)
												.toList();
		calendarRepository.saveAll(calendars.stream().map(Calendar::calendar).toList());
		List<CalendarHistorySQL> histories = createHistories(calendars, account);
		calendarHistoryRepository.saveAll(histories);
	}
	
	record Account(int id, String name) {}
	
	Account createAccount(AccountSQL loginUser) {
		return loginUser == null? DEFAULT_ACCOUNT: new Account(loginUser.getId(), loginUser.getDisplayedUser());
	}
	
	boolean holidayFilter(LocalDate date) {
		return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}
	
	record Calendar(CalendarSQL calendar, boolean existsChange) {}
	
	Calendar calendarHandle(LocalDate date, List<CalendarSQL> existingCalendars, MaterialSQL material) {
		Optional<CalendarSQL> calender = existingCalendars.stream().filter(i -> i.getHoliday().equals(date)).findFirst();
		return calender.isPresent()? changeCalendar(calender.get()): newCalendar(date, material);
	}
	
	Calendar changeCalendar(CalendarSQL changeCalendar) {
		if(!changeCalendar.getHasDeleted()) {
			return null;
		}
		changeCalendar.setHasDeleted(false);
		return new Calendar(changeCalendar, true);
	}
	
	Calendar newCalendar(LocalDate date, MaterialSQL material) {
		CalendarSQL newCalendar = createCalendarSQL(date, material);
		return new Calendar(newCalendar, false);
	}
	
	CalendarSQL createCalendarSQL(LocalDate date, MaterialSQL material) {
		return CalendarSQL.builder()
				.material(material)
				.holiday(date)
				.type(HolidayType.ALL_DAY)
				.hasDeleted(false)
				.build();
	}
	
	List<CalendarHistorySQL> createHistories(List<Calendar> calenders, Account account) {
		return calenders.stream().map(i -> i.existsChange? createChangeCalendarHistory(i.calendar, account): createNewCalendarHistory(i.calendar, account)).toList();
	}
	
	CalendarHistorySQL createChangeCalendarHistory(CalendarSQL calendar, Account account) {
		return CalendarHistorySQL.builder()
				.targetId(calendar.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.oldMaterialId(calendar.getMaterial().getId())
						.oldName(calendar.getMaterial().getName())
						.oldDestination(calendar.getMaterial().getDestination())
						.build())
				.oldHoliday(calendar.getHoliday())
				.oldType(calendar.getType().name())
				.hasDeletedOld(true)
				.hasDeletedNew(false)
				.action(HistoryEnum.CHANGE.name())
				.actionId(account.id)
				.actionUser(account.name)
				.build();
	}
	
	CalendarHistorySQL createNewCalendarHistory(CalendarSQL calendar, Account account) {
		return CalendarHistorySQL.builder()
				.targetId(calendar.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.newMaterialId(calendar.getMaterial().getId())
						.newName(calendar.getMaterial().getName())
						.newDestination(calendar.getMaterial().getDestination())
						.build())
				.newHoliday(calendar.getHoliday())
				.newType(calendar.getType().name())
				.hasDeletedNew(false)
				.action(HistoryEnum.CREATE.name())
				.actionId(account.id)
				.actionUser(account.name)
				.build();
	}
}