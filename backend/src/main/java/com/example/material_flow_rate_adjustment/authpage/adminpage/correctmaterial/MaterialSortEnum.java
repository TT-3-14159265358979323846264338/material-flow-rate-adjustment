package com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

public enum MaterialSortEnum{
	ID,
	MATERIAL,
	DESTINATION;
	
	public static Comparator<MaterialSQL> materialComparator(MaterialSortEnum target){
		return switch(target) {
			case ID -> Comparator.comparingInt(MaterialSQL::getId);
			case MATERIAL -> Comparator.comparing(MaterialSQL::getName, Collator.getInstance(Locale.JAPANESE));
			case DESTINATION -> Comparator.comparing(MaterialSQL::getDestination, Collator.getInstance(Locale.JAPANESE));
		};
	}
	
	public static Comparator<MaterialSQL> materialComparator(OrderSortEnum order, MaterialSortEnum target){
		return OrderSortEnum.orderComparator(materialComparator(target), order);
	}
}