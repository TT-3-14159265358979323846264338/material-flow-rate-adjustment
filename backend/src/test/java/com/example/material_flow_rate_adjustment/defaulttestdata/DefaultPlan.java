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
public enum DefaultPlan {
	DUMMY_0(2026, 11, new BigDecimal("10.5"), new BigDecimal("11.5"), new BigDecimal("12.5"), new BigDecimal("13.5"), new BigDecimal("14.5"), false),
	DUMMY_1(2026, 12, new BigDecimal("20.5"), new BigDecimal("21.5"), new BigDecimal("22.5"), new BigDecimal("23.5"), new BigDecimal("24.5"), false),
	DUMMY_2(2027, 2, new BigDecimal("30.5"), new BigDecimal("31.5"), new BigDecimal("32.5"), new BigDecimal("33.5"), new BigDecimal("34.5"), false),
	DUMMY_3(2027, 1, new BigDecimal("40.5"), new BigDecimal("41.5"), new BigDecimal("42.5"), new BigDecimal("43.5"), new BigDecimal("44.5"), true);
	
	private final int year;
	private final int month;
	private final BigDecimal flow;
	private final BigDecimal achievement;
	private final BigDecimal shipping;
	private final BigDecimal adjustment;
	private final BigDecimal remaining;
	private final boolean hasDeleted;
	
	public static List<MonthPlanSQL> getPlanList(MaterialSQL material){
		return Stream.of(DefaultPlan.values()).map(i -> i.getPlan(material)).toList();
	}
	
	public static List<MonthPlanSQL> getPlanList(MaterialSQL material, DefaultPlan ...part){
		return Stream.of(part).map(i -> i.getPlan(material)).toList();
	}
	
	public LocalDate getDate() {
		return LocalDate.of(year, month, 1);
	}
	
	public MonthPlanSQL getPlan(MaterialSQL material) {
		return MonthPlanSQL.builder()
				.material(material)
				.year(year)
				.month(month)
				.planDate(getDate())
				.flow(flow)
				.achievement(achievement)
				.shipping(shipping)
				.adjustment(adjustment)
				.remaining(remaining)
				.hasDeleted(hasDeleted)
				.build();
	}
}
