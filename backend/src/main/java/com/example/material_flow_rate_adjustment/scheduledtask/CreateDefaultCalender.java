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
import com.example.material_flow_rate_adjustment.savedata.historydata.CalenderHistoryRepository;
import com.example.material_flow_rate_adjustment.savedata.historydata.CalenderHistorySQL;
import com.example.material_flow_rate_adjustment.savedata.historydata.HistoryEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateDefaultCalender {
	private static final Account DEFAULT_ACCOUNT = new Account(-1, "システム自動");
	private final CalendarRepository calendarRepository;
	private final CalenderHistoryRepository calenderHistoryRepository;
	private final UtilityService utility;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void createCalenderRollback(int materialId, YearMonth targetMonth) {
		MaterialSQL material = utility.getMaterialSQL(materialId);
		createCalender(material, targetMonth, null);
	}
	
	void createCalender(MaterialSQL material, YearMonth targetMonth, AccountSQL loginUser) {
		Account account = createAccount(loginUser);
		LocalDate targetDate = targetMonth.atDay(1);
		List<CalendarSQL> existingCalenders = calendarRepository.findByHolidayBetweenAndMaterial(targetDate, targetMonth.atEndOfMonth(), material);
		List<Calender> calenders = targetDate.datesUntil(targetDate.plusMonths(1))
												.filter(this::holidayFilter)
												.map(i -> calenderHandle(i, existingCalenders, material))
												.filter(Objects::nonNull)
												.toList();
		calendarRepository.saveAll(calenders.stream().map(Calender::calender).toList());
		List<CalenderHistorySQL> histories = createHistories(calenders, account);
		calenderHistoryRepository.saveAll(histories);
	}
	
	record Account(int id, String name) {}
	
	Account createAccount(AccountSQL loginUser) {
		return loginUser == null? DEFAULT_ACCOUNT: new Account(loginUser.getId(), loginUser.getDisplayedUser());
	}
	
	boolean holidayFilter(LocalDate date) {
		return date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}
	
	record Calender(CalendarSQL calender, boolean existsChange) {}
	
	Calender calenderHandle(LocalDate date, List<CalendarSQL> existingCalenders, MaterialSQL material) {
		Optional<CalendarSQL> calender = existingCalenders.stream().filter(i -> i.getHoliday().equals(date)).findFirst();
		return calender.isPresent()? changeCalender(calender.get()): newCalender(date, material);
	}
	
	Calender changeCalender(CalendarSQL changeCalender) {
		if(!changeCalender.getHasDeleted()) {
			return null;
		}
		changeCalender.setHasDeleted(false);
		return new Calender(changeCalender, true);
	}
	
	Calender newCalender(LocalDate date, MaterialSQL material) {
		CalendarSQL newCalender = createCalenderSQL(date, material);
		return new Calender(newCalender, false);
	}
	
	CalendarSQL createCalenderSQL(LocalDate date, MaterialSQL material) {
		return CalendarSQL.builder()
				.material(material)
				.holiday(date)
				.type(HolidayType.ALL_DAY)
				.hasDeleted(false)
				.build();
	}
	
	List<CalenderHistorySQL> createHistories(List<Calender> calenders, Account account) {
		return calenders.stream().map(i -> i.existsChange? createChangeCalenderHistory(i.calender, account): createNewCalenderHistory(i.calender, account)).toList();
	}
	
	CalenderHistorySQL createChangeCalenderHistory(CalendarSQL calender, Account account) {
		return CalenderHistorySQL.builder()
				.targetId(calender.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.oldMaterialId(calender.getMaterial().getId())
						.oldName(calender.getMaterial().getName())
						.oldDestination(calender.getMaterial().getDestination())
						.build())
				.oldHoliday(calender.getHoliday())
				.oldType(calender.getType().name())
				.hasDeletedOld(true)
				.hasDeletedNew(false)
				.action(HistoryEnum.CHANGE.name())
				.actionId(account.id)
				.actionUser(account.name)
				.build();
	}
	
	CalenderHistorySQL createNewCalenderHistory(CalendarSQL calender, Account account) {
		return CalenderHistorySQL.builder()
				.targetId(calender.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.newMaterialId(calender.getMaterial().getId())
						.newName(calender.getMaterial().getName())
						.newDestination(calender.getMaterial().getDestination())
						.build())
				.newHoliday(calender.getHoliday())
				.newType(calender.getType().name())
				.hasDeletedNew(false)
				.action(HistoryEnum.CREATE.name())
				.actionId(account.id)
				.actionUser(account.name)
				.build();
	}
}