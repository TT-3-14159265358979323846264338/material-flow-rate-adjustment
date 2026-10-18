package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidMaterialId;
import com.example.material_flow_rate_adjustment.customannotations.ValidMonth;
import com.example.material_flow_rate_adjustment.customannotations.ValidYear;

record NewPlan(
		@ValidMaterialId
		Integer materialId,
		@ValidYear
		Integer year,
		@ValidMonth
		Integer month,
		@NotNull(message = "予定数量が入力されていません")
		Integer flow) {}