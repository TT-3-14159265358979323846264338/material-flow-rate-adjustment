package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

import org.springframework.stereotype.Service;

@Service
public class TransformService {
	private static final int MIN_YEAR = 1000;
	private static final int MIN_MONTH = 1;
	private static final int MAX_YEAR = 9999;
	private static final int MAX_MONTH = 12;
	
	public Integer integerValue(String value, Integer base) {
		try {
			return Integer.parseInt(value);
		}catch(Exception e) {
			return base;
		}
	}
	
	public LocalDate minDate(String minYear, String minMonth) {
		return YearMonth.of(minYear(integerValue(minYear, null)), minMonth(integerValue(minMonth, null))).atDay(1);
	}
	
	public LocalDate minDate(Integer minYear, Integer minMonth) {
		return YearMonth.of(minYear(minYear), minMonth(minMonth)).atDay(1);
	}
	
	public LocalDateTime minDateTime(String minYear, String minMonth) {
		return minDate(minYear, minMonth).atStartOfDay();
	}
	
	public LocalDateTime minDateTime(Integer minYear, Integer minMonth) {
		return minDate(minYear, minMonth).atStartOfDay();
	}
	
	public LocalDate maxDate(String maxYear, String maxMonth) {
		return YearMonth.of(maxYear(integerValue(maxYear, null)), maxMonth(integerValue(maxMonth, null))).atEndOfMonth();
	}
	
	public LocalDate maxDate(Integer maxYear, Integer maxMonth) {
		return YearMonth.of(maxYear(maxYear), maxMonth(maxMonth)).atEndOfMonth();
	}
	
	public LocalDateTime maxDateTime(String maxYear, String maxMonth) {
		return maxDate(maxYear, maxMonth).atTime(LocalTime.MAX);
	}
	
	public LocalDateTime maxDateTime(Integer maxYear, Integer maxMonth) {
		return maxDate(maxYear, maxMonth).atTime(LocalTime.MAX);
	}
	
	int minYear(Integer minYear) {
		return minValue(minYear, MIN_YEAR, MAX_YEAR);
	}
	
	int minMonth(Integer minMonth) {
		return minValue(minMonth, MIN_MONTH, MAX_MONTH);
	}
	
	int maxYear(Integer maxYear) {
		return maxValue(maxYear, MIN_YEAR, MAX_YEAR);
	}
	
	int maxMonth(Integer maxMonth) {
		return maxValue(maxMonth, MIN_MONTH, MAX_MONTH);
	}
	
	int minValue(Integer minValue, int minBase, int maxBase) {
		return value(minValue, minBase, maxBase, false);
	}
	
	int maxValue(Integer maxValue, int minBase, int maxBase) {
		return value(maxValue, minBase, maxBase, true);
	}
	
	int value(Integer original, int minBase, int maxBase, boolean isUsedMaxBase) {
		int base = isUsedMaxBase? maxBase: minBase;
		if(original == null) {
			return base;
		}
		return (original < minBase || maxBase < original)? base: original;
	}
}