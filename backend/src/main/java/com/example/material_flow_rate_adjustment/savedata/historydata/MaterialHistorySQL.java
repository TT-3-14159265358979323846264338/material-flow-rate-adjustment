package com.example.material_flow_rate_adjustment.savedata.historydata;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "material_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialHistorySQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@Column(name = "target_id", columnDefinition = "INT UNSIGNED", nullable = false, updatable = false)
	private Integer targetId;
	
	@Column(name = "old_material_name", length = 10, updatable = false)
	private String oldName;
	
	@Column(name = "new_material_name", length = 10, updatable = false)
	private String newName;
	
	@Column(name = "old_material_destination", length = 10, updatable = false)
	private String oldDestination;
	
	@Column(name = "new_material_destination", length = 10, updatable = false)
	private String newDestination;
	
	@Column(length = 10, nullable = false, updatable = false)
	private String action;
	
	@Column(name = "action_id", columnDefinition = "INT UNSIGNED", nullable = false, updatable = false)
	private Integer actionId;
	
	@Column(name = "action_user", length = 10, nullable = false, updatable = false)
	private String actionUser;
	
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private LocalDateTime date;
}