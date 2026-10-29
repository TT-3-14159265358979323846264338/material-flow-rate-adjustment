package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthPlanRepository extends BaseJpaRepository<MonthPlanSQL, Integer>{
	@EntityGraph(attributePaths = {"material"})
	Optional<MonthPlanSQL> findWithEntityGraphById(int id);
	@EntityGraph(attributePaths = {"material"})
	List<MonthPlanSQL> findWithEntityGraphByPlanDateBetweenAndHasDeletedFalse(LocalDate minDate, LocalDate maxDate, Sort sort);
	@EntityGraph(attributePaths = {"material"})
	Optional<MonthPlanSQL> findWithEntityGraphByMaterial_IdAndYearAndMonthAndHasDeletedFalse(int id, int year, int month);
	boolean existsByMaterial_IdAndYearAndMonthAndHasDeletedFalse(int id, int year, int month);
}