package com.example.material_flow_rate_adjustment.commontest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

import com.example.material_flow_rate_adjustment.savedata.maindata.CalendarSQL;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;
import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultCalendar {
	//休日系
	DUMMY_0(1, HolidayType.ALL_DAY, false),
	DUMMY_1(7, HolidayType.MORNING, false),
	DUMMY_2(8, HolidayType.AFTERNOON, false),
	DUMMY_3(14, HolidayType.WORKING, false),
	DUMMY_4(15, HolidayType.ALL_DAY, true),
	DUMMY_5(21, HolidayType.MORNING, true),
	DUMMY_6(22, HolidayType.WORKING, true),
	//平日系
	DUMMY_7(2, HolidayType.ALL_DAY, false),
	DUMMY_8(3, HolidayType.MORNING, false),
	DUMMY_9(4, HolidayType.AFTERNOON, false),
	DUMMY_10(5, HolidayType.WORKING, false),
	DUMMY_11(6, HolidayType.ALL_DAY, true),
	DUMMY_12(9, HolidayType.MORNING, true),
	DUMMY_13(10, HolidayType.WORKING, true);
	
	private final int day;
	private final HolidayType type;
	private final boolean hasDeleted;
	
	public static final YearMonth MONTH = YearMonth.of(2026, 11);
	
	public static LocalDate getDate(int day) {
		return MONTH.atDay(day);
	}
	
	public static List<CalendarSQL> getCalendarList(MaterialSQL material){
		return Stream.of(DefaultCalendar.values()).map(i -> i.getCalendar(material)).toList();
	}
	
	public static List<CalendarSQL> getCalendarList(MaterialSQL material, DefaultCalendar ...part){
		return Stream.of(part).map(i -> i.getCalendar(material)).toList();
	}
	
	public CalendarSQL getCalendar(MaterialSQL material) {
		return CalendarSQL.builder()
				.material(material)
				.holiday(MONTH.atDay(day))
				.type(type)
				.hasDeleted(hasDeleted)
				.build();
	}
}
