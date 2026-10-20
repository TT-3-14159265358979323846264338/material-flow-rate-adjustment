package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

import java.math.BigDecimal;

import com.example.material_flow_rate_adjustment.customannotations.ValidFlowNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialIdNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonthNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidYearNotNull;

record NewPlan(
		@ValidMaterialIdNotNull
		Integer materialId,
		@ValidYearNotNull
		Integer year,
		@ValidMonthNotNull
		Integer month,
		@ValidFlowNotNull
		BigDecimal flow) {}