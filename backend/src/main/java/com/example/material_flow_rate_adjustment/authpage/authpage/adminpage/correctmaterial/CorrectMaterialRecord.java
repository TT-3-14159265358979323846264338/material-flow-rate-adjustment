package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import java.math.BigDecimal;

import com.example.material_flow_rate_adjustment.customannotations.ValidBaseNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialDestinationNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialNameNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialUnitNullable;

record CorrectMaterialRecord(
		@ValidMaterialNameNullable
		String newName,
		@ValidMaterialDestinationNullable
		String newDestination,
		@ValidBaseNullable
		BigDecimal newBase,
		@ValidMaterialUnitNullable
		String newUnit,
		boolean isDeleted
		) {}