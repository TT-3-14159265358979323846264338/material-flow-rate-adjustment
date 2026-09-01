package com.example.material_flow_rate_adjustment.authpage;

import org.springframework.data.domain.Sort;

import com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial.OrderSortEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DefaultHistorySortEnum{
	DATE("date");
	
	private final String target;
	
	public Sort getHistorySort(OrderSortEnum order){
		return Sort.by(order.getOrderSort(), target);
	}
}