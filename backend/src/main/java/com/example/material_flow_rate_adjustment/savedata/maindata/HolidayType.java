package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HolidayType {
	WORKING(BigDecimal.ZERO),
	ALL_DAY(BigDecimal.ONE),
	MORNING(new BigDecimal("0.5")),
	AFTERNOON(new BigDecimal("0.5"));
	
	private final BigDecimal days;
}