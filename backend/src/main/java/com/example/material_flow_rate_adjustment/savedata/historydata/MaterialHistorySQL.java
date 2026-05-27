package com.example.material_flow_rate_adjustment.savedata.historydata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "material_history")
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
}