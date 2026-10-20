package com.example.material_flow_rate_adjustment.authpage.adminpage.newmaterial;

import java.math.BigDecimal;

import com.example.material_flow_rate_adjustment.customannotations.ValidBaseNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialDestinationNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialNameNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialUnitNotNull;

record NewMaterial(
		@ValidMaterialNameNotNull
		String name, 
		@ValidMaterialDestinationNotNull
		String destination,
		@ValidBaseNullable
		BigDecimal base,
		@ValidMaterialUnitNotNull
		String unit
		) {}