package com.example.material_flow_rate_adjustment.commontest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.MonthPlanSQL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultMonthPlan {
	DUMMY_0(2026, 11, new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("4"), new BigDecimal("5"), false),
	DUMMY_1(2026, 12, new BigDecimal("11.05"), new BigDecimal("12.05"), new BigDecimal("13.05"), new BigDecimal("14.05"), new BigDecimal("15.05"), false),
	DUMMY_2(2027, 1, new BigDecimal("21.05"), new BigDecimal("22.05"), new BigDecimal("23.05"), new BigDecimal("24.05"), new BigDecimal("25.05"), true);
	
	private final Integer year;
	private final Integer month;
	private final BigDecimal flow;
	private final BigDecimal achievement;
	private final BigDecimal shipping;
	private final BigDecimal adjustment;
	private final BigDecimal remaining;
	private final boolean hasDeleted;
	
	public static List<MonthPlanSQL> getPlanList(MaterialSQL material){
		return Stream.of(DefaultMonthPlan.values()).map(i -> i.getPlan(material)).toList();
	}
	
	public static List<MonthPlanSQL> getPlanList(MaterialSQL material, DefaultMonthPlan ...part){
		return Stream.of(part).map(i -> i.getPlan(material)).toList();
	}
	
	public MonthPlanSQL getPlan(MaterialSQL material) {
		return MonthPlanSQL.builder()
						.material(material)
						.year(year)
						.month(month)
						.planDate(LocalDate.of(year, month, 1))
						.flow(flow)
						.achievement(achievement)
						.shipping(shipping)
						.adjustment(adjustment)
						.remaining(remaining)
						.hasDeleted(hasDeleted)
						.build();
	}
}