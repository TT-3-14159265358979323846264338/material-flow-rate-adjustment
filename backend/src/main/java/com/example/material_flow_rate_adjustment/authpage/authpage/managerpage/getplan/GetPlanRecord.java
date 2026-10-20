package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidIdNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonthNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonthNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrderNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTargetNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidYearNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidYearNullable;

record GetPlanRecord(
		@ValidYearNullable
		Integer minYear,
		@ValidMonthNullable
		Integer minMonth,
		@ValidYearNullable
		Integer maxYear,
		@ValidMonthNullable
		Integer maxMonth,
		@ValidIdNullable
		Integer material,
		@ValidSortOrderNotNull
		OrderSortEnum order,
		@ValidSortTargetNotNull
		PlanSortEnum target) {}

record GetDesignatedRecord(
		@ValidYearNotNull
		Integer year,
		@ValidMonthNotNull
		Integer month) {}