package com.example.material_flow_rate_adjustment.savedata.maindata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HolidayType {
	ALL_DAY(1),
	MORNING(0.5),
	AFTERNOON(0.5);
	
	private final double days;
}