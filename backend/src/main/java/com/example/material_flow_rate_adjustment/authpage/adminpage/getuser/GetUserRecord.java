package com.example.material_flow_rate_adjustment.authpage.adminpage.getuser;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrderNotNull;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTargetNotNull;

record GetUserRecord(
		boolean isAdmin,
		boolean isUser,
		boolean isManager,
		@ValidSortOrderNotNull
		OrderSortEnum order,
		@ValidSortTargetNotNull
		UserSortEnum target) {}