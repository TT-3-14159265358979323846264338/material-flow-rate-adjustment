package com.example.material_flow_rate_adjustment.authpage.commonpage.correctcalendar;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidDateInMySQLNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidHolidayTypeNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialIdNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonthNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidYearNotNull;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;

record CorrectCalendarRecord(
		@NotNull(message="カレンダーは必須入力です")
		List<CalendarRecord> calendars,
		@ValidYearNotNull
		Integer year,
		@ValidMonthNotNull
		Integer month,
		@ValidMaterialIdNotNull
		Integer material) {}

record CalendarRecord(
		@ValidDateInMySQLNotNull
		LocalDate holiday,
		@ValidHolidayTypeNotNull
		HolidayType code) {}