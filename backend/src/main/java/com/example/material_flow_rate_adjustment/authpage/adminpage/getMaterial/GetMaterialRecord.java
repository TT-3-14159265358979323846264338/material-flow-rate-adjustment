package com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrderNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTargetNotNull;

record GetMaterialRecord(
		@ValidSortOrderNotNull
		OrderSortEnum order,
		@ValidSortTargetNotNull
		MaterialSortEnum target) {}