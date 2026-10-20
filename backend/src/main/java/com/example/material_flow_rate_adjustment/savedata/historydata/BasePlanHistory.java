package com.example.material_flow_rate_adjustment.savedata.historydata;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaseMaterialHistoryを別途@Embeddedすること
 */
@Embeddable
@Data
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasePlanHistory {
	@Column(name = "old_plan_id", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldPlanId;
	
	@Column(name = "new_plan_id", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newPlanId;
	
	@Column(name = "old_year", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldYear;
	
	@Column(name = "new_year", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newYear;
	
	@Column(name = "old_month", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldMonth;
	
	@Column(name = "new_month", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newMonth;
}