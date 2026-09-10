package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "month_plan", indexes = {
		@Index(name = "idx_plan_date_deleted", columnList = "plan_date, has_deleted")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MonthPlanSQL extends BaseSQL{
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MaterialSQL material;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer year;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer month;
	
	@Column(name = "plan_date", nullable = false)
	private LocalDate planDate;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer flow;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer achievement;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer shipping;
	
	@Column(nullable = false)
	private Integer adjustment;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer remaining;
}