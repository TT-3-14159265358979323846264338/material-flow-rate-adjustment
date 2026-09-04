package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

record GetPlanRecord(
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		PlanSortEnum target,
		String maxSize) {}