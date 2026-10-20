package com.example.material_flow_rate_adjustment.authpage.managerpage.correctplan;

import java.math.BigDecimal;

import com.example.material_flow_rate_adjustment.customannotations.ValidFlowNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidIdNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonthNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidYearNullable;

record CorrectPlanRecord(
		@ValidIdNullable
		Integer materialId,
		@ValidYearNullable
		Integer year,
		@ValidMonthNullable
		Integer month,
		@ValidFlowNullable
		BigDecimal flow,
		boolean isDeleted) {}