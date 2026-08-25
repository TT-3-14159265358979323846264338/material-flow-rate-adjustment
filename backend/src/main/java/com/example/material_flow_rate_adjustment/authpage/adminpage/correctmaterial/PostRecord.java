package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialBase;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialDestination;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialName;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialUnit;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

record MaterialSort(
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		MaterialSortEnum target) {}

record CorrectMaterial(
		@ValidMaterialName
		String newName,
		@ValidMaterialDestination
		String newDestination,
		@ValidMaterialBase
		String newBase,
		@ValidMaterialUnit
		String newUnit,
		boolean isDeleted
		) {}