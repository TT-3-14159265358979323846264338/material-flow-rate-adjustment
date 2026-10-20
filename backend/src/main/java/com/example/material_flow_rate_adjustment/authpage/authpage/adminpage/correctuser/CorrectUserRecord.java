package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import com.example.material_flow_rate_adjustment.customannotations.ValidDisplayedUserNameNullable;
import com.example.material_flow_rate_adjustment.customannotations.ValidRoleNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidLoginUserNameNullable;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

record CorrectUserRecord(
		@ValidLoginUserNameNullable
		String newLoginName, 
		@ValidDisplayedUserNameNullable
		String newDisplayedName,
		@ValidRoleNotNull
		AccountRole newRole,
		boolean isDeleted
		) {}