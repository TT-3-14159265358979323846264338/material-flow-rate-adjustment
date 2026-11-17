package com.example.material_flow_rate_adjustment.commontest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import com.example.material_flow_rate_adjustment.savedata.maindata.MaterialSQL;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DefaultMaterial {
	DUMMY_0("name0", "desti0", null, "g", false),
	DUMMY_1("name1", "desti1", new BigDecimal("1.05"), "kg", false),
	DUMMY_2("name2", "desti2", new BigDecimal("100"), "個", true);
	
	private final String name;
	private final String destination;
	private final BigDecimal base;
	private final String unit;
	private final boolean hasDeleted;
	
	public static List<MaterialSQL> getMaterialList(){
		return Stream.of(DefaultMaterial.values()).map(DefaultMaterial::getMaterial).toList();
	}
	
	public MaterialSQL getMaterial() {
		return MaterialSQL.builder()
				.name(name)
				.destination(destination)
				.base(base)
				.unit(unit)
				.hasDeleted(hasDeleted)
				.build();
	}
}