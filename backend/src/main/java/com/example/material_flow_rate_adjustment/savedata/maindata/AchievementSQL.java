package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "achievement")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AchievementSQL extends BaseSQL{
	@Column(length = 10, nullable = false)
	private String userName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MonthPlanSQL plan;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(length = 30, nullable = false)
	private String lot;
	
	@Column(precision = 8, scale = 3, nullable = false)
	private BigDecimal flow;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 15, nullable = false)
	private Inspection inspection;
}