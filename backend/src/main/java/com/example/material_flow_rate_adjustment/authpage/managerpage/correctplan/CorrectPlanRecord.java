package com.example.material_flow_rate_adjustment.authpage.managerpage.correctplan;

record CorrectPlanRecord(
		Integer materialId,
		Integer year,
		Integer month,
		Integer flow,
		boolean isDeleted) {}