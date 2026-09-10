package com.example.material_flow_rate_adjustment.authpage.managerpage.newplan;

import jakarta.validation.constraints.NotNull;

record NewPlan(
		@NotNull(message = "製品が選択されていません")
		Integer materialId,
		@NotNull(message = "登録する年がありません")
		Integer year,
		@NotNull(message = "登録する月がありません")
		Integer month,
		@NotNull(message = "予定数量が入力されていません")
		Integer flow) {}