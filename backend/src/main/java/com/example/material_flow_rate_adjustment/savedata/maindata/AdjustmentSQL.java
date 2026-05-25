package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDateTime;

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
@Table(name = "adjustment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjustmentSQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adjustment_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MonthAdjustmentSQL monthAdjustment;
	
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private LocalDateTime date;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer quantity;
	
	@Column(length = 10, nullable = false)
	private String user;
	
	@Column(length = 50, nullable = false)
	private String reason;
}