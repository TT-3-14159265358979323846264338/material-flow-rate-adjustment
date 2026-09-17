package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends BaseJpaRepository<CalendarSQL, Integer>{
	List<CalendarSQL> findByHolidayBetweenAndMaterial_IdAndHasDeletedFalse(LocalDate minDate, LocalDate maxDate, int materialId, Sort sort);
	Optional<CalendarSQL> findByHolidayAndMaterial(LocalDate holiday, MaterialSQL material);
}