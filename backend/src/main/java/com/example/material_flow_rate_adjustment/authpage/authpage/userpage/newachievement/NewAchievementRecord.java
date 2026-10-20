package com.example.material_flow_rate_adjustment.authpage.userpage.newachievement;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.material_flow_rate_adjustment.customannotations.ValidBaseNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidDateInMySQLNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidInspectionNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidLotNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidPlanIdNotNull;
import com.example.material_flow_rate_adjustment.savedata.maindata.Inspection;

record NewAchievementRecord(
		@ValidPlanIdNotNull
		Integer plan,
		@ValidDateInMySQLNotNull
		LocalDate date,
		@ValidLotNotNull
		String lot,
		@ValidBaseNotNull
		BigDecimal flow,
		@ValidInspectionNotNull
		Inspection inspection) {}