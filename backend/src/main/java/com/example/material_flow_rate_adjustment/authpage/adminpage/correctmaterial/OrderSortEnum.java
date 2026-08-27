package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderSortEnum{
	ASCENDING(Sort.Direction.ASC),
	DESCENDING(Sort.Direction.DESC);
	
	private final Sort.Direction orderSort;
}