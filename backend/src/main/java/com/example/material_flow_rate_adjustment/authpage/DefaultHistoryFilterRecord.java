package com.example.material_flow_rate_adjustment.authpage;

import com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

public record DefaultHistoryFilterRecord(
		String minYear,
		String minMonth,
		String maxYear,
		String maxMonth,
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		DefaultHistorySortEnum target) {}