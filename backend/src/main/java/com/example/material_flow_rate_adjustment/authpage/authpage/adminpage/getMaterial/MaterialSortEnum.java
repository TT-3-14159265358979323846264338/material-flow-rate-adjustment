package com.example.material_flow_rate_adjustment.authpage.adminpage.getMaterial;

import org.springframework.data.domain.Sort;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MaterialSortEnum{
	ID("id"),
	MATERIAL("name"),
	DESTINATION("destination");
	
	private final String target;
	
	public Sort getMaterialSort(OrderSortEnum order){
		return Sort.by(order.getOrderSort(), target);
	}
}