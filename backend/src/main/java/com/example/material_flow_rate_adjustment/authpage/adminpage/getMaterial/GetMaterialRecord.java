package com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

record GetMaterialRecord(
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		MaterialSortEnum target) {}