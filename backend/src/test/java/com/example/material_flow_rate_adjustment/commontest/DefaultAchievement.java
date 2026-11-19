package com.example.material_flow_rate_adjustment.commontest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.example.material_flow_rate_adjustment.savedata.maindata.AchievementSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.Inspection;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultAchievement {
	DUMMY_0("dummy_0", LocalDate.of(2026, 11, 2), "dummy_lot_0", BigDecimal.ONE, Inspection.PASSED, false),
	DUMMY_1("dummy_1", LocalDate.of(2026, 11, 3), "dummy_lot_1", BigDecimal.TWO, Inspection.PASSED, false),
	DUMMY_2("dummy_2", LocalDate.of(2026, 11, 4), "dummy_lot_2", BigDecimal.TEN, Inspection.FAILED, false),
	DUMMY_3("dummy_3", LocalDate.of(2026, 11, 5), "dummy_lot_3", new BigDecimal("10.05"), Inspection.FAILED, false),
	DUMMY_4("dummy_4", LocalDate.of(2026, 11, 6), "dummy_lot_4", new BigDecimal("100.005"), Inspection.UNINSPECTION, false),
	DUMMY_5("dummy_5", LocalDate.of(2026, 11, 9), "dummy_lot_5", new BigDecimal("1000.5"), Inspection.UNINSPECTION, false),
	DUMMY_6("dummy_6", LocalDate.of(2026, 11, 2), "dummy_lot_6", BigDecimal.ONE, Inspection.PASSED, true),
	DUMMY_7("dummy_7", LocalDate.of(2026, 11, 3), "dummy_lot_7", BigDecimal.TWO, Inspection.FAILED, true),
	DUMMY_8("dummy_8", LocalDate.of(2026, 11, 4), "dummy_lot_8", BigDecimal.TEN, Inspection.UNINSPECTION, true);
	
	private final String userName;
	private final LocalDate date;
	private final String lot;
	private final BigDecimal flow;
	private final Inspection inspection;
	private final boolean hasDeleted;
	
	public static List<AchievementSQL> getAchievementList(MonthPlanSQL plan){
		return Stream.of(DefaultAchievement.values()).map(i ->i.getAchievement(plan)).toList();
	}
	
	public static List<AchievementSQL> getAchievementList(MonthPlanSQL plan, DefaultAchievement ...part){
		return Stream.of(part).map(i -> i.getAchievement(plan)).toList();
	}
	
	public AchievementSQL getAchievement(MonthPlanSQL plan) {
		return AchievementSQL.builder()
						.userName(userName)
						.plan(plan)
						.date(date)
						.lot(lot)
						.flow(flow)
						.inspection(inspection)
						.hasDeleted(hasDeleted)
						.build();
	}
}