package com.example.material_flow_rate_adjustment.authpage.adminpage.newuser;

import jakarta.validation.constraints.NotNull;

import com.example.material_flow_rate_adjustment.customannotations.ValidRole;
import com.example.material_flow_rate_adjustment.customannotations.ValidDisplayedUserName;
import com.example.material_flow_rate_adjustment.customannotations.ValidLoginUserName;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

record NewUser(
		@NotNull(message = "ログイン用ユーザー名は必須入力です")
		@ValidLoginUserName
		String loginName, 
		@NotNull(message = "表示用ユーザー名は必須入力です")
		@ValidDisplayedUserName
		String displayedName,
		@ValidRole
		AccountRole role
		) {}

record Password(String password) {}