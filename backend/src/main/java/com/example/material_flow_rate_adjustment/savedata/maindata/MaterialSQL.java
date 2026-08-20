package com.example.material_flow_rate_adjustment.savedata.maindata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "material", indexes = {
		@Index(name = "idx_material_deleted", columnList = "has_deleted")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MaterialSQL extends BaseSQL{
	@Column(length = 10, nullable = false)
	private String name;
	
	@Column(length = 10, nullable = false)
	private String destination;
	
	@Column(columnDefinition = "INT UNSIGNED")
	private Integer base;
	
	@Column(length = 10, nullable = false)
	private String unit;
}