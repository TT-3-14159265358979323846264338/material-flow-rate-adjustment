package com.example.material_flow_rate_adjustment.authpage.managerpage.getplan;

import java.util.function.Function;

import org.springframework.data.domain.Sort;

import com.example.material_flow_rate_adjustment.authpage.OrderSortEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PlanSortEnum {
	DATE((order) -> Sort.by(order.getOrderSort(), "year", "month")),
	MATERIAL((order) -> Sort.by(order.getOrderSort(), "material").and(Sort.by(OrderSortEnum.DESCENDING.getOrderSort(), "year", "month"))),
	UPDATE_DATE((order) -> Sort.by(order.getOrderSort(), "updated_date"));
	
	private final Function<OrderSortEnum, Sort> sortFunction;
	
	public Sort getPlanSort(OrderSortEnum order){
		return sortFunction.apply(order);
	}
}