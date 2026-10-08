package com.example.material_flow_rate_adjustment.authpage.commonpage.correctcalendar;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
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
public class CorrectCalenderService {
	private final CalendarRepository calendarRepository;
	private final CalenderHistoryRepository calenderHistoryRepository;
	private final UtilityService utility;
	
	@Transactional
	public String correctCalender(CorrectCalenderRecord data, String loginUser) {
		AccountSQL account = utility.getAccountSQL(loginUser);
		MaterialSQL material = utility.getMaterialSQL(data.material());
		YearMonth targetMonth = YearMonth.of(data.year(), data.month());
		List<CalendarSQL> existingCalenders = calendarRepository.findByHolidayBetweenAndMaterial(targetMonth.atDay(1), targetMonth.atEndOfMonth(), material);
		List<Calender> calenders = data.calenders().stream()
												.map(i -> calenderHandle(i, existingCalenders, material))
												.filter(Objects::nonNull)
												.toList();
		calendarRepository.saveAll(calenders.stream().map(Calender::calender).toList());
		List<CalenderHistorySQL> histories = createHistories(calenders, account);
		calenderHistoryRepository.saveAll(histories);
		return "日程を修正しました";
	}
	
	record Calender(CalendarSQL calender, HolidayType oldHolidayType, Boolean oldHasDeleted, HistoryEnum handleType) {}
	
	Calender calenderHandle(CalenderRecord newCalender, List<CalendarSQL> existingCalenders, MaterialSQL material) {
		Optional<CalendarSQL> target = existingCalenders.stream().filter(i -> i.getHoliday().equals(newCalender.holiday())).findFirst();
		return target.map(i -> createCalender(newCalender, i)).orElse(createCalender(newCalender, material));
	}
	
	Calender createCalender(CalenderRecord newCalender, CalendarSQL targetCalender) {
		HolidayType oldHolidayType = targetCalender.getType();
		boolean oldHasDeleted = targetCalender.getHasDeleted();
		if(newCalender.code().equals(oldHolidayType) && !oldHasDeleted) {
			return null;
		}
		HistoryEnum handleType = changeHandle(newCalender, targetCalender);
		return new Calender(targetCalender, oldHolidayType, oldHasDeleted, handleType);
	}
	
	HistoryEnum changeHandle(CalenderRecord newCalender, CalendarSQL targetCalender) {
		if(newCalender.code().equals(HolidayType.WORKING)) {
			targetCalender.setHasDeleted(true);
			return HistoryEnum.DELETE;
		}
		targetCalender.setType(newCalender.code());
		targetCalender.setHasDeleted(false);
		return HistoryEnum.CHANGE;
	}
	
	Calender createCalender(CalenderRecord newCalender, MaterialSQL material) {
		return new Calender(createCalendarSQL(newCalender, material), null, null, HistoryEnum.CREATE);
	}
	
	CalendarSQL createCalendarSQL(CalenderRecord newCalender, MaterialSQL material) {
		return  CalendarSQL.builder()
				.material(material)
				.holiday(newCalender.holiday())
				.type(newCalender.code())
				.hasDeleted(false)
				.build();
	}
	
	List<CalenderHistorySQL> createHistories(List<Calender> calenders, AccountSQL account){
		return calenders.stream().map(i -> i.handleType.equals(HistoryEnum.CREATE)? createCreateCalenderHistory(i, account): changeCalenderHistoryHandle(i, account)).toList();
	}
	
	CalenderHistorySQL createCreateCalenderHistory(Calender calender, AccountSQL account) {
		return CalenderHistorySQL.builder()
				.targetId(calender.calender.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.newMaterialId(calender.calender.getMaterial().getId())
						.newName(calender.calender.getMaterial().getName())
						.newDestination(calender.calender.getMaterial().getDestination())
						.build())
				.newHoliday(calender.calender.getHoliday())
				.newType(calender.calender.getType().name())
				.hasDeletedNew(false)
				.build();
	}
	
	CalenderHistorySQL changeCalenderHistoryHandle(Calender calender, AccountSQL account) {
		CalenderHistorySQL history = createChangeCalenderHistory(calender, account);
		if(calender.handleType.equals(HistoryEnum.DELETE)) {
			history.setHasDeletedNew(true);
		}else {
			if(!calender.calender.getType().equals(calender.oldHolidayType)) {
				history.setNewType(calender.calender.getType().name());
			}
			if(!calender.calender.getHasDeleted().equals(calender.oldHasDeleted)) {
				history.setHasDeletedNew(false);
			}
		}
		return history;
	}
	
	CalenderHistorySQL createChangeCalenderHistory(Calender calender, AccountSQL account) {
		return CalenderHistorySQL.builder()
				.targetId(calender.calender.getId())
				.baseMaterialHistory(BaseMaterialHistory.builder()
						.oldMaterialId(calender.calender.getMaterial().getId())
						.oldName(calender.calender.getMaterial().getName())
						.oldDestination(calender.calender.getMaterial().getDestination())
						.build())
				.oldHoliday(calender.calender.getHoliday())
				.oldType(calender.oldHolidayType.name())
				.hasDeletedOld(calender.oldHasDeleted)
				.action(calender.handleType.name())
				.actionId(account.getId())
				.actionUser(account.getDisplayedUser())
				.build();
	}
}
