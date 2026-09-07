package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

record GetPlanRecord(
		String minYear,
		String minMonth,
		String maxYear,
		String maxMonth,
		String material,
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		PlanSortEnum target) {}