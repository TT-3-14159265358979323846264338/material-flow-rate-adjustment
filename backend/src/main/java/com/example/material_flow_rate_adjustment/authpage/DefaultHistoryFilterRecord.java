package com.example.material_flow_rate_adjustment.authpage;

import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

public record DefaultHistoryFilterRecord(
		Integer minYear,
		Integer minMonth,
		Integer maxYear,
		Integer maxMonth,
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		DefaultHistorySortEnum target) {}