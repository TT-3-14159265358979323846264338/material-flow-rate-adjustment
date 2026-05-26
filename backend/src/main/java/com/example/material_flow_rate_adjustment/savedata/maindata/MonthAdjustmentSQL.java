package com.example.material_flow_rate_adjustment.savedata.maindata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "month_adjustment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthAdjustmentSQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MaterialSQL material;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer year;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer month;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer flow;
	
	@Column(columnDefinition = "INT UNSIGNED")
	private Integer achievement;
	
	@Column(columnDefinition = "INT UNSIGNED")
	private Integer shipping;
	
	@Column(columnDefinition = "INT")
	private Integer adjustment;
	
	@Column(columnDefinition = "INT UNSIGNED")
	private Integer remaining;
}