package com.example.material_flow_rate_adjustment.authpage.commonpage.getcalendar;

import com.example.material_flow_rate_adjustment.customannotations.ValidIdNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonthNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidYearNullable;

record GetCalendarRecord(
		@ValidYearNullable
		Integer year,
		@ValidMonthNullable
		Integer month,
		@ValidIdNullable
		Integer material) {}