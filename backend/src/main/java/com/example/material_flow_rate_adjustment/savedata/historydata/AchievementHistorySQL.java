package com.example.material_flow_rate_adjustment.savedata.historydata;

import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "achievement_history", indexes = {
		@Index(name = "idx_achievement_history_date", columnList = "date")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AchievementHistorySQL extends BaseHistorySQL{
	@Column(name = "old_user_name", length = 10, updatable = false)
	private String oldUserName;
	
	@Column(name = "new_user_name", length = 10, updatable = false)
	private String newUserName;
	
	@Embedded
	@Delegate
	private BasePlanHistory basePlanHistory;
	
	@Embedded
	@Delegate
	private BaseMaterialHistory baseMaterialHistory;
	
	@Column(name = "old_Date", updatable = false)
	private LocalDate oldDate;
	
	@Column(name = "new_Date", updatable = false)
	private LocalDate newDate;
	
	@Column(name = "old_lot", length = 30, updatable = false)
	private String oldLot;
	
	@Column(name = "new_lot", length = 30, updatable = false)
	private String newLot;
	
	@Column(name = "old_flow", precision = 8, scale = 3, updatable = false)
	private BigDecimal oldFlow;
	
	@Column(name = "new_flow", precision = 8, scale = 3, updatable = false)
	private BigDecimal newFlow;
	
	@Column(name = "old_Inspection", length = 15, updatable = false)
	private String oldInspection;
	
	@Column(name = "new_Inspection", length = 15, updatable = false)
	private String newInspection;
}