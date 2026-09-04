package com.example.material_flow_rate_adjustment.authpage.adminpage.getuser;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortOrder;
import com.example.material_flow_rate_adjustment.customannotations.ValidSortTarget;

public record GetUserRecord(
		boolean isAdmin,
		boolean isUser,
		boolean isManager,
		@ValidSortOrder
		OrderSortEnum order,
		@ValidSortTarget
		UserSortEnum target) {}