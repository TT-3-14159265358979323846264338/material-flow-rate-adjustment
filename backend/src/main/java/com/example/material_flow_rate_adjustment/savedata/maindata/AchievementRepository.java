package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends BaseJpaRepository<AchievementSQL, Integer>{
	@EntityGraph(attributePaths = {"plan", "plan.material"})
	Optional<AchievementSQL> findWithEntityGraphById(int id);
	List<AchievementSQL> findByPlan_IdAndHasDeletedFalse(int planId);
}