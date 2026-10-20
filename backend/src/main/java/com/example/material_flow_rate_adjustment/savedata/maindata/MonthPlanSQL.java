package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.math.BigDecimal;
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
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "month_plan", indexes = {
		@Index(name = "idx_plan_date_deleted", columnList = "plan_date, has_deleted")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MonthPlanSQL extends BaseSQL{
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.RESTRICT)
	private MaterialSQL material;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer year;
	
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer month;
	
	//year, monthから作成したLocalDate。Integerで使用したいときとLocalDateで使用したいときが多々あるので、2重にカラムを作っている。
	@Column(name = "plan_date", nullable = false)
	private LocalDate planDate;
	
	@Column(precision = 12, scale = 3, nullable = false)
	private BigDecimal flow;
	
	@Column(precision = 12, scale = 3, nullable = false)
	private BigDecimal achievement;
	
	@Column(precision = 12, scale = 3, nullable = false)
	private BigDecimal shipping;
	
	@Column(precision = 12, scale = 3, nullable = false)
	private BigDecimal adjustment;
	
	@Column(precision = 12, scale = 3, nullable = false)
	private BigDecimal remaining;
}