package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseSQL {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED AUTO_INCREMENT", updatable = false)
	@Setter(AccessLevel.NONE)
	private Integer id;
	
	@Column(name = "has_deleted", nullable = false)
	private Boolean hasDeleted;
	
	@UpdateTimestamp
	@Column(name = "updated_date", nullable = false)
	@Setter(AccessLevel.NONE)
	private LocalDateTime updatedDate;
	
	@CreationTimestamp
	@Column(name = "created_date", nullable = false, updatable = false)
	@Setter(AccessLevel.NONE)
	private LocalDateTime createdDate;
}
