package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Table(name = "achievement")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AchievementSQL extends BaseSQL{
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adjustment_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MonthPlanSQL monthAdjustment;
	
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private LocalDateTime date;
	
	@Column(length = 30, nullable = false)
	private String lot;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer flow;
	
	@Column(length = 10, nullable = false)
	private String user;
	
	@Column(name = "pass", nullable = false)
	private Boolean hasPassed;
}