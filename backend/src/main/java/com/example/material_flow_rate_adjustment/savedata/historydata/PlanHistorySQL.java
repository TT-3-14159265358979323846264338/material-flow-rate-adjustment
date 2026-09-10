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
@Table(name = "plan_history", indexes = {
		@Index(name = "idx_plan_history_date", columnList = "date")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlanHistorySQL extends BaseHistorySQL{
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
	
	@Column(name = "old_year", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldYear;
	
	@Column(name = "new_year", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newYear;
	
	@Column(name = "old_month", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldMonth;
	
	@Column(name = "new_month", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newMonth;
	
	@Column(name = "old_flow", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldFlow;
	
	@Column(name = "new_flow", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newFlow;
	
	@Column(name = "old_achievement", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldAchievement;
	
	@Column(name = "new_achievement", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newAchievement;
	
	@Column(name = "old_shipping", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldShipping;
	
	@Column(name = "new_shipping", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newShipping;
	
	@Column(name = "old_adjustment", updatable = false)
	private Integer oldAdjustment;
	
	@Column(name = "new_adjustment", updatable = false)
	private Integer newAdjustment;
	
	@Column(name = "old_remaining", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldRemaining;
	
	@Column(name = "new_remaining", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newRemaining;
}