package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import com.example.material_flow_rate_adjustment.customannotations.ValidId;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialDestination;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialName;

record AdminCorrectMaterial(
		@ValidId
		int targetId,
		@ValidMaterialName
		String newName,
		@ValidMaterialDestination
		String newDestination,
		boolean isDeleted
		) {}