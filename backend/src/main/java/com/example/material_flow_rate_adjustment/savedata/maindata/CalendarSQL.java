package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "calendar", indexes = {
		@Index(name = "idx_calendar_holiday_material", columnList = "holiday, material, has_deleted")
})
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Immutable
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CalendarSQL extends BaseSQL{
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "id", columnDefinition = "INT UNSIGNED", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private MaterialSQL material;
	
	@Column(nullable = false)
	private LocalDate holiday;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private HolidayType type;
}