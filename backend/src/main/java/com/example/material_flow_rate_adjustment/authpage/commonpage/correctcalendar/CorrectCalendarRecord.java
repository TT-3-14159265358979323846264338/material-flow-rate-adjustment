package com.example.material_flow_rate_adjustment.authpage.commonpage.correctcalendar;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialId;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonth;
import com.example.material_flow_rate_adjustment.customannotations.ValidYear;
import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;

record CorrectCalendarRecord(
		@NotNull(message="カレンダーは必須入力です")
		List<CalendarRecord> calendars,
		@ValidYear
		Integer year,
		@ValidMonth
		Integer month,
		@ValidMaterialId
		Integer material) {}

record CalendarRecord(
		@NotNull(message="日付は必須入力です")
		LocalDate holiday,
		@NotNull(message="休日タイプは必須入力です")
		HolidayType code) {}