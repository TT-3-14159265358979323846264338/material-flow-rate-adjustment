package com.example.material_flow_rate_adjustment.authpage.userpage.correctachievement;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.material_flow_rate_adjustment.customannotations.ValidBaseNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidDateInMySQLNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidLotNullable;
import com.example.material_flow_rate_adjustment.savedata.maindata.Inspection;

record CorrectAchievementRecord(
		@ValidDateInMySQLNullable
		LocalDate date,
		@ValidLotNullable
		String lot,
		@ValidBaseNullable
		BigDecimal flow,
		Inspection inspection,
		boolean isDeleted
		) {}