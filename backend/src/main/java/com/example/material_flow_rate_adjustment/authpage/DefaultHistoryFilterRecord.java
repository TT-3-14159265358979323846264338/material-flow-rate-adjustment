package com.example.material_flow_rate_adjustment.authpage;

import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrderNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTargetNotNull;

public record DefaultHistoryFilterRecord(
		Integer minYear,
		Integer minMonth,
		Integer maxYear,
		Integer maxMonth,
		@ValidSortOrderNotNull
		OrderSortEnum order,
		@ValidSortTargetNotNull
		DefaultHistorySortEnum target) {}