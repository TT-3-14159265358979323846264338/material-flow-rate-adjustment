package com.example.material_flow_rate_adjustment.savedata.historydata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseMaterialHistory {
	@Column(name = "old_material_id", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldMaterialId;
	
	@Column(name = "new_material_id", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newMaterialId;
	
	@Column(name = "old_material_name", length = 10, updatable = false)
	private String oldName;
	
	@Column(name = "new_material_name", length = 10, updatable = false)
	private String newName;
	
	@Column(name = "old_material_destination", length = 10, updatable = false)
	private String oldDestination;
	
	@Column(name = "new_material_destination", length = 10, updatable = false)
	private String newDestination;
}