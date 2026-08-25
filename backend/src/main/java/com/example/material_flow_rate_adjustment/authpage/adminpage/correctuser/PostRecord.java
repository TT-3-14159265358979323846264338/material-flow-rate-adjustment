package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidDisplayedUserName;
import com.example.material_flow_rate_adjustment.customannotations.ValidRole;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;
import com.example.material_flow_rate_adjustment.customannotations.ValidLoginUserName;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountRole;

record UserSort(
		boolean isAdmin,
		boolean isUser,
		boolean isManager,
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		UserSortEnum target) {}

record AdminCorrectUserData(
		@ValidLoginUserName
		String newLoginName, 
		@ValidDisplayedUserName
		String newDisplayedName,
		@ValidRole
		AccountRole newRole,
		boolean isDeleted
		) {}