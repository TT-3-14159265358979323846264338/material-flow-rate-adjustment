package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends BaseJpaRepository<CalendarSQL, Integer>{
	List<CalendarSQL> findByHolidayBetweenAndMaterial_IdAndHasDeletedFalse(LocalDate minDate, LocalDate maxDate, int materialId, Sort sort);
	@EntityGraph(attributePaths = {"material"})
	List<CalendarSQL> findWithEntityGraphByHolidayBetweenAndMaterial(LocalDate minDate, LocalDate maxDate, MaterialSQL material);
	List<CalendarSQL> findByHolidayBetweenAndMaterialAndHasDeletedFalse(LocalDate minDate, LocalDate maxDate, MaterialSQL material);
}