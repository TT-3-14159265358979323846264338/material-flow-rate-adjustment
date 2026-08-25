package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import java.util.Comparator;

public enum OrderSortEnum{
	ASCENDING,
	DESCENDING;
	
	public static <T extends Comparable<? super T>> Comparator<T> orderComparator(OrderSortEnum order){
		return switch(order) {
			case ASCENDING -> Comparator.naturalOrder();
			case DESCENDING -> Comparator.reverseOrder();
		};
	}
	
	public static <T> Comparator<T> orderComparator(Comparator<T> baseComparator, OrderSortEnum order){
		return switch(order) {
			case ASCENDING -> baseComparator;
			case DESCENDING -> baseComparator.reversed();
		};
	}
}