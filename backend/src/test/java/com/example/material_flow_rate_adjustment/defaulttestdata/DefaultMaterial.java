package com.example.material_flow_rate_adjustment.defaulttestdata;

import java.math.BigDecimal;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultMaterial implements BaseSQLNoJoinColumn<MaterialSQL>{
	DUMMY_0("name0", "desti0", null, "g", false),
	DUMMY_1("name1", "desti1", new BigDecimal("1.05"), "kg", false),
	DUMMY_2("name2", "desti2", new BigDecimal("100"), "個", true);
	
	private final String name;
	private final String destination;
	private final BigDecimal base;
	private final String unit;
	private final boolean hasDeleted;
	
	@Override
	public MaterialSQL getSQL() {
		return MaterialSQL.builder()
				.name(name)
				.destination(destination)
				.base(base)
				.unit(unit)
				.hasDeleted(hasDeleted)
				.build();
	}
}