package com.example.material_flow_rate_adjustment.authpage.correctuser;

import com.example.material_flow_rate_adjustment.customannotations.ValidId;
import com.example.material_flow_rate_adjustment.customannotations.ValidPassword;
import com.example.material_flow_rate_adjustment.customannotations.ValidRole;
import com.example.material_flow_rate_adjustment.customannotations.ValidUserName;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

record AdminCorrectOwnData(
		@ValidUserName
		String newName, 
		@ValidPassword
		String newPass, 
		@ValidPassword
		String oldPass
		) {}

record AdminCorrectUserData(
		@ValidId
		int targetId, 
		@ValidUserName
		String newName, 
		@ValidRole
		AccountRole newRole,
		boolean isDeleted
		) {}