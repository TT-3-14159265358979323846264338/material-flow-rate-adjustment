package com.example.material_flow_rate_adjustment.authpage.commonpage.correctcalendar;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.savedata.maindata.HolidayType;

record CorrectCalenderRecord(
		@NotNull(message="カレンダーは必須入力です")
		List<CalenderRecord> calenders,
		@NotNull(message="対象年は必須入力です")
		Integer year,
		@NotNull(message="対象月は必須入力です")
		Integer month,
		@NotNull(message="対象製品は必須入力です")
		Integer material) {}

record CalenderRecord(
		@NotNull(message="日付は必須入力です")
		LocalDate holiday,
		@NotNull(message="休日タイプは必須入力です")
		HolidayType code) {}
