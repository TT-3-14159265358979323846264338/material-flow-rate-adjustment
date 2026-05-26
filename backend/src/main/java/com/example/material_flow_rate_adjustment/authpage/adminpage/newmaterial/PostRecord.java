package com.example.material_flow_rate_adjustment.authpage.adminpage.newmaterial;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialDestination;
import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialName;

record NewMaterial(
		@NotNull(message = "製品名は必須入力です")
		@ValidMaterialName
		String name, 
		@NotNull(message = "向け先は必須入力です")
		@ValidMaterialDestination
		String destination
		) {}