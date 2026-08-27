package com.example.material_flow_rate_adjustment.savedata.historydata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "material_history", indexes = {
		@Index(name = "idx_material_history_date", columnList = "date")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MaterialHistorySQL extends BaseHistorySQL{
	@Column(name = "old_material_name", length = 10, updatable = false)
	private String oldName;
	
	@Column(name = "new_material_name", length = 10, updatable = false)
	private String newName;
	
	@Column(name = "old_material_destination", length = 10, updatable = false)
	private String oldDestination;
	
	@Column(name = "new_material_destination", length = 10, updatable = false)
	private String newDestination;
	
	@Column(name = "old_base", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldBase;
	
	@Column(name = "new_base", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newBase;
	
	@Column(name = "old_unit", length = 10, updatable = false)
	private String oldUnit;
	
	@Column(name = "new_unit", length = 10, updatable = false)
	private String newUnit;
}