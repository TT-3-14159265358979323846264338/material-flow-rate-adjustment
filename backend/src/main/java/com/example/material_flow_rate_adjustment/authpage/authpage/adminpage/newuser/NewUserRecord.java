package com.example.material_flow_rate_adjustment.authpage.adminpage.newuser;

import com.example.material_flow_rate_adjustment.customannotations.ValidRoleNotNull;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;
import com.example.material_flow_rate_adjustment.customannotations.ValidDisplayedUserNameNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidLoginUserNameNotNull;

record NewUser(
		@ValidLoginUserNameNotNull
		String loginName, 
		@ValidDisplayedUserNameNotNull
		String displayedName,
		@ValidRoleNotNull
		AccountRole role
		) {}

record Password(String password) {}