package com.example.material_flow_rate_adjustment.authpage.commonpage.correctpassword;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidPassword;

record NewPass(
		@NotNull(message="以前のパスワードは必須入力です")
		@ValidPassword
		String oldPass, 
		@NotNull(message="新規のパスワードは必須入力です")
		@ValidPassword
		String newPass
		) {}