package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthPlanRepository extends BaseJpaRepository<MonthPlanSQL, Integer>{
	@EntityGraph(attributePaths = {"material"})
	List<MonthPlanSQL> findByPlanDateBetweenHasDeletedFalse(LocalDate minDate, LocalDate maxDate, Sort sort);
}