package com.example.material_flow_rate_adjustment.savedata.historydata;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseHistorySQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@Column(name = "target_id", columnDefinition = "INT UNSIGNED", nullable = false, updatable = false)
	private Integer targetId;
	
	@Column(name = "has_deleted_old", updatable = false)
	private Boolean hasDeletedOld;
	
	@Column(name = "has_deleted_new", updatable = false)
	private Boolean hasDeletedNew;
	
	@Column(length = 10, nullable = false, updatable = false)
	private String action;
	
	@Column(name = "action_id", columnDefinition = "INT UNSIGNED", nullable = false, updatable = false)
	private Integer actionId;
	
	@Column(name = "action_user", length = 10, nullable = false, updatable = false)
	private String actionUser;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private LocalDateTime date;
}