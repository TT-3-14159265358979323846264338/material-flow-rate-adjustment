package com.example.material_flow_rate_adjustment.authpage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

import org.springframework.stereotype.Service;

@Service
public class TransformService {
	private final int MIN_YEAR = 1000;
	private final int MIN_MONTH = 1;
	private final int MAX_YEAR = 9999;
	private final int MAX_MONTH = 12;
	
	public LocalDate minDate(String minYear, String minMonth) {
		return of(minYear(minYear), MIN_YEAR, minMonth(minMonth), MIN_MONTH).atDay(1);
	}
	
	public LocalDateTime minDateTime(String minYear, String minMonth) {
		return minDate(minYear, minMonth).atStartOfDay();
	}
	
	public LocalDate maxDate(String maxYear, String maxMonth) {
		return of(maxYear(maxYear), MAX_YEAR, maxMonth(maxMonth), MAX_MONTH).atEndOfMonth();
	}
	
	public LocalDateTime maxDateTime(String maxYear, String maxMonth) {
		return maxDate(maxYear, maxMonth).atTime(LocalTime.MAX);
	}
	
	int minYear(String minYear) {
		return minValue(minYear, MIN_YEAR, MAX_YEAR);
	}
	
	int minMonth(String minMonth) {
		return minValue(minMonth, MIN_MONTH, MAX_MONTH);
	}
	
	int maxYear(String maxYear) {
		return maxValue(maxYear, MIN_YEAR, MAX_YEAR);
	}
	
	int maxMonth(String maxMonth) {
		return maxValue(maxMonth, MIN_MONTH, MAX_MONTH);
	}
	
	YearMonth of(int year, int baseYear, int month, int baseMonth) {
		try {
			return YearMonth.of(year, month);
		}catch(Exception e) {
			return YearMonth.of(baseYear, baseMonth);
		}
	}
	
	int minValue(String minValue, int minBase, int maxBase) {
		return value(minValue, minBase, maxBase, false);
	}
	
	int maxValue(String maxValue, int minBase, int maxBase) {
		return value(maxValue, minBase, maxBase, true);
	}
	
	int value(String original, int minBase, int maxBase, boolean isUsedMaxBase) {
		int base = isUsedMaxBase? maxBase: minBase;
		int value = integerValue(original, base);
		return (value < minBase || maxBase < value)? base: value;
	}
	
	public Integer integerValue(String value, Integer base) {
		try {
			return Integer.parseInt(value);
		}catch(Exception e) {
			return base;
		}
	}
}