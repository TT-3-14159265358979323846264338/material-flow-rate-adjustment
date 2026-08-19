package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import com.example.material_flow_rate_adjustment.customannotations.ValidDisplayedUserName;
import com.example.material_flow_rate_adjustment.customannotations.ValidId;
import com.example.material_flow_rate_adjustment.customannotations.ValidRole;
import com.example.material_flow_rate_adjustment.customannotations.ValidLoginUserName;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

record AdminCorrectUserData(
		@ValidId
		int targetId, 
		@ValidLoginUserName
		String newLoginName, 
		@ValidDisplayedUserName
		String newDisplayedName,
		@ValidRole
		AccountRole newRole,
		boolean isDeleted
		) {}