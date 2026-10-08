package com.example.material_flow_rate_adjustment.savedata.historydata;

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
@Table(name = "alender_history", indexes = {
		@Index(name = "idx_calender_history_date", columnList = "date")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CalenderHistorySQL extends BaseHistorySQL{
	@Embedded
	@Delegate
	private BaseMaterialHistory baseMaterialHistory;
	
	@Column(name = "old_holiday", updatable = false)
	private LocalDate oldHoliday;
	
	@Column(name = "new_holiday", updatable = false)
	private LocalDate newHoliday;
	
	@Column(name = "old_type", length = 10, updatable = false)
	private String oldType;
	
	@Column(name = "new_type", length = 10, updatable = false)
	private String newType;
}
