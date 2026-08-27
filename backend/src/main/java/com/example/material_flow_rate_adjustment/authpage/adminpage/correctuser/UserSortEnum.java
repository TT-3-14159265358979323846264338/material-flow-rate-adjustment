package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import org.springframework.data.domain.Sort;

import com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial.OrderSortEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserSortEnum {
	ID("id"),
	NAME("loginUser"),
	AUTHORITY("role");
	
	private final String target;
	
	public Sort getUserSort(OrderSortEnum order){
		return Sort.by(order.getOrderSort(), target);
	}
}
