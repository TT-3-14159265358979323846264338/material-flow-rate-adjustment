package com.example.material_flow_rate_adjustment.authpage.newuser;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidRole;
import com.example.material_flow_rate_adjustment.customannotations.ValidUserName;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

record NewUser(
		@NotNull(message = "ユーザー名は必須入力です")
		@ValidUserName
		String name, 
		@ValidRole
		AccountRole role
		) {}