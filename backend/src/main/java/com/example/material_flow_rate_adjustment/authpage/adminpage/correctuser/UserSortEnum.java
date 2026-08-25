package com.example.material_flow_rate_adjustment.authpage.adminpage.correctuser;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.example.material_flow_rate_adjustment.authpage.adminpage.correctmaterial.OrderSortEnum;
import com.example.material_flow_rate_adjustment.savedata.maindata.AccountSQL;

public enum UserSortEnum {
	ID,
	NAME,
	AUTHORITY;
	
	public static Comparator<AccountSQL> userComparator(UserSortEnum target){
		return switch(target) {
			case ID -> Comparator.comparingInt(AccountSQL::getId);
			case NAME -> Comparator.comparing(AccountSQL::getLoginUser, Collator.getInstance(Locale.JAPANESE));
			case AUTHORITY -> Comparator.comparing(AccountSQL::getRole, Collator.getInstance(Locale.JAPANESE));
		};
	}
	
	public static Comparator<AccountSQL> userComparator(OrderSortEnum order, UserSortEnum target){
		return OrderSortEnum.orderComparator(userComparator(target), order);
	}
}
