package com.example.material_flow_rate_adjustment.savedata.maindata;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends BaseJpaRepository<AchievementSQL, Integer>{
	List<AchievementSQL> findByPlan_IdAndHasDeletedFalse(int planId);
}