package com.example.material_flow_rate_adjustment.authpage.commonpage.getAchievement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.material_flow_rate_adjustment.authpage.TransformService;
import com.example.material_flow_rate_adjustment.authpage.UtilityService;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarRepository;
import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;
import com.example.material_flow_rate_adjustment.savedata.maindata.Inspection;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAchievementService {
	private final AchievementRepository achievementRepository;
	private final CalendarRepository calendarRepository;
	private final UtilityService utility;
	private final TransformService transform;
	
	@Transactional(readOnly = true)
	public TotalAchievement getAchievement(int id){
		MonthPlanSQL plan = utility.getMonthPlanSQL(id);
		List<Achievement> allAchievement = achievementRepository.findByPlanAndHasDeletedFalse(plan)
				.stream()
				.map(this::createAchievement)
				.toList();
		return createTotalAchievement(allAchievement, plan);
	}
	
	record Achievement(
			Integer id,
			String name,
			LocalDate date,
			String lot,
			BigDecimal flow,
			Inspection inspection) {}
	
	Achievement createAchievement(AchievementSQL achievement) {
		return new Achievement(
				achievement.getId(),
				achievement.getUserName(),
				achievement.getDate(),
				achievement.getLot(),
				achievement.getFlow(),
				achievement.getInspection());
	}
	
	record TotalAchievement(
			BigDecimal passed,
			BigDecimal uninspection,
			List<Achievement> allAchievement,
			List<Schedule> schedule,
			String unit) {}
	
	TotalAchievement createTotalAchievement(List<Achievement> allAchievement, MonthPlanSQL plan) {
		return new TotalAchievement(
				getConfirmed(allAchievement),
				getUninspected(allAchievement),
				allAchievement,
				createSchedule(plan),
				createUnit(plan));
	}
	
	BigDecimal getConfirmed(List<Achievement> allAchievement) {
		return getTotal(allAchievement, Inspection.PASSED);
	}
	
	BigDecimal getUninspected(List<Achievement> allAchievement) {
		return getTotal(allAchievement, Inspection.UNINSPECTION);
	}
	
	BigDecimal getTotal(List<Achievement> allAchievement, Inspection targetInspection) {
		return allAchievement.stream().filter(i -> i.inspection.equals(targetInspection))
										.map(Achievement::flow)
										.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	record Schedule(LocalDate date, BigDecimal quantity) {}
	
	List<Schedule> createSchedule(MonthPlanSQL plan){
		List<CalendarSQL> calendar = calendarRepository.findByHolidayBetweenAndMaterialAndHasDeletedFalse(
															transform.minDate(plan.getYear(), plan.getMonth()), 
															transform.maxDate(plan.getYear(), plan.getMonth()), 
															plan.getMaterial());
		List<LocalDate> operatedDate = getOperatedDate(plan);
		List<Schedule> schedule = new ArrayList<>();
		BigDecimal restFlow = plan.getFlow().subtract(plan.getAchievement());
		for(int i = 0; i < operatedDate.size(); i++) {
			LocalDate targetDate = operatedDate.get(i);
			HolidayType targetType = calendar.stream().filter(j -> j.getHoliday().equals(targetDate))
												.findFirst()
												.map(CalendarSQL::getType)
												.orElse(HolidayType.WORKING);
			if(targetType.equals(HolidayType.ALL_DAY)) {
				continue;
			}
			BigDecimal flowOnTheDay = getFlowOnTheDay(calendar, targetDate, targetType, restFlow, new BigDecimal(operatedDate.size() - i));
			restFlow = correctData(schedule, restFlow, targetDate, flowOnTheDay, plan.getMaterial().getBase());
		}
		return schedule;
	}
	
	List<LocalDate> getOperatedDate(MonthPlanSQL plan){
		LocalDate startDate = YearMonth.of(plan.getYear(), plan.getMonth()).equals(YearMonth.now())? LocalDate.now(): plan.getPlanDate();
		return startDate.datesUntil(plan.getPlanDate().plusMonths(1)).toList();
	}
	
	BigDecimal getFlowOnTheDay(List<CalendarSQL> calendar, LocalDate targetDate, HolidayType targetType, BigDecimal restFlow, BigDecimal restDay) {
		BigDecimal totalHoliday = calendar.stream()
										.filter(j -> !j.getHoliday().isBefore(targetDate))
										.map(j -> j.getType().getDays())
										.reduce(BigDecimal.ZERO, BigDecimal::add);
		BigDecimal restOperationDay = restDay.subtract(totalHoliday);
		BigDecimal dailyFlow = restFlow.divide(restOperationDay, 3, RoundingMode.CEILING);
		return dailyFlow.multiply(BigDecimal.ONE.subtract(targetType.getDays()));
	}
	
	BigDecimal correctData(List<Schedule> schedule, BigDecimal restFlow, LocalDate targetDate, BigDecimal flowOnTheDay, BigDecimal base) {
		BigDecimal number = switch(base) {
								case null -> flowOnTheDay;
								default -> flowOnTheDay.divide(base, 0, RoundingMode.CEILING);
							};
		BigDecimal flow = switch(base) {
								case null -> flowOnTheDay;
								default -> number.multiply(base);
							};
		schedule.add(new Schedule(targetDate, number));
		return restFlow.subtract(flow);
	}
	
	String createUnit(MonthPlanSQL plan) {
		MaterialSQL material = plan.getMaterial();
		return material.getBase() == null? material.getUnit(): "Lot";
	}
}