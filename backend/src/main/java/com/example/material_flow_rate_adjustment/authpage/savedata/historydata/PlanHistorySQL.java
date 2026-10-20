package com.example.material_flow_rate_adjustment.savedata.historydata;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Delegate;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "plan_history", indexes = {
		@Index(name = "idx_plan_history_date", columnList = "date")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PlanHistorySQL extends BaseHistorySQL{
	@Embedded
	@Delegate
	private BaseMaterialHistory baseMaterialHistory;
	
	@Column(name = "old_year", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldYear;
	
	@Column(name = "new_year", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newYear;
	
	@Column(name = "old_month", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer oldMonth;
	
	@Column(name = "new_month", columnDefinition = "INT UNSIGNED", updatable = false)
	private Integer newMonth;
	
	@Column(name = "old_flow", precision = 12, scale = 3, updatable = false)
	private BigDecimal oldFlow;
	
	@Column(name = "new_flow", precision = 12, scale = 3, updatable = false)
	private BigDecimal newFlow;
	
	@Column(name = "old_achievement", precision = 12, scale = 3, updatable = false)
	private BigDecimal oldAchievement;
	
	@Column(name = "new_achievement", precision = 12, scale = 3, updatable = false)
	private BigDecimal newAchievement;
	
	@Column(name = "old_shipping", precision = 12, scale = 3, updatable = false)
	private BigDecimal oldShipping;
	
	@Column(name = "new_shipping", precision = 12, scale = 3, updatable = false)
	private BigDecimal newShipping;
	
	@Column(name = "old_adjustment", precision = 12, scale = 3, updatable = false)
	private BigDecimal oldAdjustment;
	
	@Column(name = "new_adjustment", precision = 12, scale = 3, updatable = false)
	private BigDecimal newAdjustment;
	
	@Column(name = "old_remaining", precision = 12, scale = 3, updatable = false)
	private BigDecimal oldRemaining;
	
	@Column(name = "new_remaining", precision = 12, scale = 3, updatable = false)
	private BigDecimal newRemaining;
}