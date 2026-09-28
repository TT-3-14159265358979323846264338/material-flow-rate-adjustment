package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

record GetPlanRecord(
		Integer minYear,
		Integer minMonth,
		Integer maxYear,
		Integer maxMonth,
		Integer material,
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		PlanSortEnum target) {}